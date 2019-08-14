package cn.jkego.emall.service;

import cn.jkego.emall.dao.OrderDao;
import cn.jkego.emall.dao.OrderItemDao;
import cn.jkego.emall.domain.*;
import cn.jkego.emall.utils.IdWorker;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderService implements Serializable {

    @Autowired
    private OrderDao orderDao;


    @Autowired
    private OrderItemDao orderItemDao;


    @Autowired
    private IdWorker idWorker;


    //用户提交订单：不付款 产生订单页
    public Order submitOrder(Cart cart, User user) {

        //将订单各项封装成一个完整的订单对象
        Order order = new Order();

        //1、private String oid;//该订单的订单号
        order.setOid(idWorker.nextId());

        //2、private Date ordertime;//下单时间
        order.setOrdertime(new Date());

        //3、订单金额
        double total = cart.getTotal();
        order.setTotal(total);

        //订单支付状态 1代表已付款 0代表未付款
        order.setState(0);

        //5、private String address;//收货地址
        order.setAddress(null);

        //6、private String name;//收货人
        order.setName(null);

        //7、private String telephone;//收货人电话
        order.setTelephone(null);

        //8、private User user;//该订单属于哪个用户
        order.setUser(user);

        //9该订单中有多少订单List<OrderItems> ordrItems =  new ArrayList<>();

        //获得购物车中的购物项  key(id)--value(包含product对象)
        Map<String, CartItem> cartItems = cart.getCartItems();
        for (Map.Entry<String, CartItem> entry : cartItems.entrySet()) {

            //取出每一个购物项(pid + product + 小计+数量)
            CartItem cartItem = entry.getValue();
            //创建新的订单项
            OrderItem orderItem = new OrderItem();
            //订单项id
            orderItem.setItemid(idWorker.nextId());
            //订单项内的商品购买数量
            orderItem.setCount(cartItem.getBuyNum());
            //订单项小计
            orderItem.setSubtotal(cartItem.getSubtotal());
            //订单项内部的商品
            orderItem.setProduct(cartItem.getProduct());
            //该订单项属于哪个订单
            orderItem.setOrder(order);
            // orderItem.setOid(order.getOid());

            order.getOrderItems().add(orderItem);

        }

        //保存订单
        orderDao.save(order);

        //保存订单项
        orderItemDao.saveAll(order.getOrderItems());

        return order;

    }


    /**
     * 根据uid获得其对应的order
     *
     * @param user
     * @return
     */
    public List<Order> findOrderByUid(User user) {

        List<Order> orderList = orderDao.findOrderByUserUid(user.getUid());

        //遍历订单，为每个订单填充 订单项 数据
        if (orderList != null) {
            for (Order order : orderList) {
                //获得每一个order的id
                String oid = order.getOid();
                //根据oid查询订单项 -- mapList封装的是多个订单项和该订单项的商品信息
                List<Map<String, Object>> mapList = orderItemDao.findOrderItemsAndProductByOrderOid(oid);

                //将mapList转成List<OrderItem> orderItems
                for (Map<String, Object> map : mapList) {
                    //key：是查询的字段 value:是对应的值

                    try {
                        //从map中取出count subtotal 封装到OrderItem中
                        OrderItem orderItem = new OrderItem();
                        //使用BeanUtils对其封装
                        BeanUtils.populate(orderItem, map);

                        //从map中取出pimage pname  shop_price 封装到Product
                        Product product = new Product();
                        BeanUtils.populate(product, map);

                        //将product封装到orderItem
                        orderItem.setProduct(product);
                        //将orderItem封装到 OrderItemList
                        order.getOrderItems().add(orderItem);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        return orderList;
    }


    //后台获取全部的订单信息
    public List<Order> findAllOrders() {

        List<Order> orderList = orderDao.findAll();

        return orderList;

    }


    //后台根据订单oid出查看订单详情
    public List<Map<String, Object>> findOrderInfoByOid(String oid) {


        List<Map<String, Object>> mapList = orderDao.findOrderInfoByOid(oid);
        return mapList;

    }


}
