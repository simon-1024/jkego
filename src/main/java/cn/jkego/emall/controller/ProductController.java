package cn.jkego.emall.controller;

import cn.jkego.emall.domain.Cart;
import cn.jkego.emall.service.ProductService;
import cn.jkego.emall.domain.PageBean;
import cn.jkego.emall.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 在首页显示热门商品和最新商品
     *
     * @param model
     * @return
     */
    @GetMapping("/index")
    public String getHotProductListAndNewProductList(Model model) {

        List<Product> hotProductList = productService.getHotProductList();
        List<Product> newProductList = productService.getNewProductList();

        model.addAttribute("hotProductList", hotProductList);
        model.addAttribute("newProductList", newProductList);
        return "/index.jsp";

    }

    /**
     * 点击导航栏，进入商品列表页面
     * 根据cid查询相应类别的商品
     */
    @GetMapping("productList")
    public String getProductListByCidAndCurrentPage(String cid, @RequestParam("currentPage") String currentPageStr,
                                                    HttpServletRequest request, Model model) {

        PageBean<Product> pageBean = productService.getProductListByCidAndCurrentPage(cid, currentPageStr);

        model.addAttribute("pageBean", pageBean);
        model.addAttribute("cid", cid);

        //历史数据业务处理

        //定义一个集合，保存历史数据
        List<Product> historyProductList = new ArrayList<Product>();

        //得到请求携带的cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("pids".equals(cookie.getName())) {
                    String pids = cookie.getValue();
                    String[] split = pids.split("-");
                    for (String pid : split) {
                        Product product = productService.findProductInfoByPid(pid);
                        //将获得到的product放入historyList数组
                        historyProductList.add(product);
                    }
                }
            }
        }

        //将历史数据放入域中
        /*request.setAttribute("historyProductList",historyProductList);*/
        model.addAttribute("historyProductList", historyProductList);

        return "/product_list.jsp";

    }


    /**
     * 点击商品 进入商品详情页面
     *
     * @param pid
     * @param cid
     * @param currentPage
     * @param model
     * @return
     */
    @GetMapping("/productInfo")
    public String productInfo(String pid, String cid, String currentPage, Model model,
                              HttpServletResponse response, HttpServletRequest request) {

        Product productInfo = productService.findProductInfoByPid(pid);
        model.addAttribute("productInfo", productInfo);
        model.addAttribute("cid", cid);
        model.addAttribute("currentPage", currentPage);

        // 浏览过本商品之后，就把本商品id拼接到名称为pids的cookie前面，为页面显示历史数据使用

        //获得客户端携带的名称为pids的cookie
        String pids = pid;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("pids".equals(cookie.getName())) {
                    pids = cookie.getValue();
                    //1-3-2 本次访问商品pid是8----->8-1-3-2
                    //1-3-2 本次访问商品pid是3----->3-1-2
                    //1-3-2 本次访问商品pid是2----->2-1-3

                    //将pids拆成一个数组
                    String[] split = pids.split("-");
                    //放入集合，方便插入新的数据
                    List<String> asList = Arrays.asList(split);
                    LinkedList<String> list = new LinkedList<String>(asList);

                    //判断集合中是否存在当前商品的pid,即是否曾经浏览过本商品
                    if (list.contains(pid)) {
                        list.remove(pid);
                    }
                    //将当前id插入到LinkedList第一个位置
                    list.addFirst(pid);

                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < list.size() && i < 7; i++) {
                        sb.append(list.get(i));
                        sb.append("-");//3-1-2-
                    }
                    //去掉3-1-2-后的-
                    pids = sb.substring(0, sb.length() - 1);

                }
            }
        }

        Cookie cookie_pids = new Cookie("pids", pids);
        response.addCookie(cookie_pids);

        return "/product_info.jsp";

    }

    /**
     * 将商品加入购物车
     * @param pid
     * @param buyNum
     * @param request
     * @return
     */
    @GetMapping("/addProductToCart")
    public String addProductToCart(String pid, int buyNum, HttpServletRequest request) {

        HttpSession session = request.getSession();

        Product product = productService.findProductInfoByPid(pid);

        Cart cart = (Cart) session.getAttribute("cart");

        cart = productService.addProductToCart(product, cart, buyNum);

        session.setAttribute("cart", cart);

        //跳转至购物车页面 -- 重定向：防止再次提交
        return "redirect:/cart.jsp";
    }


    /**
     * 删除购物车中的一项
     *
     * @param pid     要删除的购物项的pid
     * @param request 用来获得session 获得购物车
     * @return
     */
    @GetMapping("/delProFromCart")
    public String delProFromCart(String pid, HttpServletRequest request) {

        HttpSession session = request.getSession();
        //得到购物车对象
        Cart cart = (Cart) session.getAttribute("cart");

        cart = productService.delProFromCart(pid, cart);

        //将修改过的购物车放入session域
        session.setAttribute("cart", cart);

        //跳转至购物车页面 -- 重定向：防止再次提交
        return "redirect:/cart.jsp";
    }


    /**
     * 清空购物车
     * @param request
     * @return
     */
    @GetMapping("/clearCart")
    public String clearCart(HttpServletRequest request){

        HttpSession session = request.getSession();
        session.removeAttribute("cart");

        return "redirect:/cart.jsp";

    }

}
