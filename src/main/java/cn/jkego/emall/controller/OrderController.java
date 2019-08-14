package cn.jkego.emall.controller;


import cn.jkego.emall.domain.Cart;
import cn.jkego.emall.domain.Order;
import cn.jkego.emall.domain.User;
import cn.jkego.emall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;


    /**
     * 用户提交订单
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/submitOrder")
    public String submitOrder(HttpServletRequest request, HttpServletResponse response) {

        //提交订单之前检查用户有没有登录
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            //没有登录
            return "/login.jsp";
        }

        Cart cart = (Cart) session.getAttribute("cart");
        Order order = orderService.submitOrder(cart, user);

        //将订单添加到session域中
        session.setAttribute("order", order);

        return "/order_info.jsp";
    }


    /**
     * 查看我的订单
     * 根据uid获取对应的订单列表
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/myOrders")
    public String myOrders(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        //获得session域中user的对象
        User user = (User) session.getAttribute("user");
        //集合中的每一个Order对象的数据是不完整的 缺少List<OrderItem> orderItems数据
        List<Order> orderList = orderService.findOrderByUid(user);

        model.addAttribute("orderList", orderList);


        return "/order_list.jsp";

    }


}
