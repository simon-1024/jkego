package cn.jkego.emall.service;

import cn.jkego.emall.dao.ProductDao;
import cn.jkego.emall.domain.Cart;
import cn.jkego.emall.domain.CartItem;
import cn.jkego.emall.domain.PageBean;
import cn.jkego.emall.domain.Product;
import cn.jkego.emall.utils.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductService implements Serializable {


    @Autowired
    private ProductDao productDao;


    //获得热门商品列表
    public List<Product> getHotProductList() {

        List<Product> hotProductList = productDao.findHotProduct();
        return hotProductList;
    }


    //获得最新商品列表
    public List<Product> getNewProductList() {

        List<Product> newProductList = productDao.findNewProduct();

        return newProductList;
    }


    //根据ID和currentPage查询本页的数据
    public PageBean<Product> getProductListByCidAndCurrentPage(String cid, String currentPageStr) {


        int currentPage;
        //判断是否是第一次访问
        if (StringUtils.isBlank(currentPageStr)) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(currentPageStr);
        }
        int currentCount = 12;

        //封装一个PageBean返回给web层
        PageBean<Product> pageBean = new PageBean<Product>();

        //1.封装页面
        pageBean.setCurrentPage(currentPage);
        //2.封装每页的条数
        pageBean.setCurrentCount(currentCount);
        //3.封装总条数
        int totalCount = productDao.getTotalCountByCid(cid);
        pageBean.setTotalCount(totalCount);
        //4.封装总页数
        int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);
        pageBean.setTotalPage(totalPage);

        //5.当前页显示的数据
        //select * from product where cid = ? limit ?,?;
        //当前页与起始索引的index的关系
        int index = (currentPage - 1) * currentCount;

        List<Product> productList = productDao.findProductByPage(cid, index, currentCount);
        pageBean.setList(productList);

        return pageBean;
    }


    //点商品图片进入商品详情页面
    public Product findProductInfoByPid(String pid) {

        Product product = productDao.findByPid(pid);

        return product;

    }


    //将商品加入购物车车
    public Cart addProductToCart(Product product, Cart cart, int buyNum) {

        //计算小计
        double subTotal = product.getShop_price() * buyNum;
        //封装购物项
        CartItem item = new CartItem();
        item.setProduct(product);
        item.setBuyNum(buyNum);
        item.setSubtotal(subTotal);

        if (cart == null) {
            cart = new Cart();
        }

        //将购物项放进购物车 key:pid--value:CartItem

        //先判断购物车内是否已经含有此购物项（同款商品 分多次购买多个）

        //如果购物车内已经存在该商品了，将现在买的数量与原来的数量相加，并计算小计

        Map<String, CartItem> cartItems = cart.getCartItems();

        double newSubTotal = 0.0;

        if (cartItems.containsKey(product.getPid())) {  //购物车内已经有同一款商品

            //取出商品的数量
            CartItem cartItem = cartItems.get(product.getPid());
            int oldBuyNum = cartItem.getBuyNum();
            oldBuyNum += buyNum;
            cartItem.setBuyNum(oldBuyNum);


            //修改小计金额
            //获得原来购物项小计金额
            double oldSubTotal = cartItem.getSubtotal();
            newSubTotal += buyNum * product.getShop_price();
            cartItem.setSubtotal(newSubTotal);
            cartItem.setSubtotal(newSubTotal + newSubTotal);

        } else {

            //原来购物车内没有该项商品
            cart.getCartItems().put(product.getPid(), item);
            newSubTotal = buyNum * product.getShop_price();

        }

        //计算购物车总金额
        double total = cart.getTotal() + newSubTotal;
        cart.setTotal(total);

        return cart;

    }




    //删除购物车里的一项
    public Cart delProFromCart(String pid, Cart cart) {

        if (cart != null) { //购物车存在

            //得到 pid   和 购物项  对应关系
            Map<String, CartItem> cartItems = cart.getCartItems();
            //修改购物车总价
            cart.setTotal(cart.getTotal() - cartItems.get(pid).getSubtotal());
            //根据pid删除该条记录
            cartItems.remove(pid);
            cart.setCartItems(cartItems);

        }
        return cart;
    }



    //后台获取商品列表
    public List<Product> findAllProducts() {

        List<Product> productList = productDao.findAll();

        return productList;

    }

    public void saveProduct(Product product) {

        productDao.save(product);
    }



    //删除商品
    public void delProduct(String pid) {

        productDao.deleteById(pid);

    }
}
