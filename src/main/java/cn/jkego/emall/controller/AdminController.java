package cn.jkego.emall.controller;


import cn.jkego.emall.domain.Category;
import cn.jkego.emall.domain.Order;
import cn.jkego.emall.domain.Product;
import cn.jkego.emall.domain.User;
import cn.jkego.emall.service.CategoryService;
import cn.jkego.emall.service.OrderService;
import cn.jkego.emall.service.ProductService;
import cn.jkego.emall.service.UserService;
import cn.jkego.emall.utils.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserService userService;



    /**
     * 后台管理员登录
     *
     * @param username
     * @param password
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/adminLogin")
    public String adminLogin(String username, String password, HttpServletRequest request, Model model ) {


        User user = userService.userLogin(username, password);

        if (user != null && user.getRole() == 1) {

            request.setAttribute("username",username);
            model.addAttribute("username",username);
            return "forward:/admin/home.jsp";

        } else {
            //用户名密码错误，显示错误信息
            model.addAttribute("adminLoginError", "用户名或密码错误");
            return "/admin/index.jsp";
        }
    }


    /**
     * 后台管理系统展示所有商品种类
     *
     * @return
     */
    @GetMapping("/findAllCategories")
    public String findAllCategories(Model model) {

        List<Category> CategoryList = categoryService.findAllCategories();

        model.addAttribute("CategoryList", CategoryList);

        return "/admin/category/list.jsp";

    }


    /**
     * 后台添加商品分类
     * 从前台接收到cname,在Service层封装id
     *
     * @param category
     * @return
     */
    @PostMapping("/addCategory")
    public String addCategory(Category category) {

        categoryService.saveCategory(category);

        return "redirect:/admin/findAllCategories";

    }


    /**
     * 后台删除商品分类
     *
     * @param cid 前台传过来的cid
     * @return
     */
    @GetMapping("/delCategory")
    public String delCategory(String cid) {

        categoryService.delCategory(cid);

        return "redirect:/admin/findAllCategories";
    }


    /**
     * 后台点击修改时，将cid传给后台
     * 后台查询到该分类信息 回显给前台
     *
     * @param cid
     * @param model
     * @return
     */
    @GetMapping("/findCategoryByCid")
    public String findCategoryByCid(String cid, Model model) {

        Category categoryByCid = categoryService.findCategoryByCid(cid);
        model.addAttribute("categoryByCid", categoryByCid);

        return "/admin/category/edit.jsp";

    }


    /**
     * 修改商品分类信息
     *
     * @param category
     * @return
     */
    @PostMapping("/editCategory")
    public String editCategory(Category category) {

        categoryService.editCategory(category);

        return "redirect:/admin/findAllCategories";
    }


    /**
     * 后台获得全部的商品信息
     *
     * @param model
     * @return
     */
    @GetMapping("/findAllProducts")
    public String findAllProducts(Model model) {

        List<Product> productList = productService.findAllProducts();
        model.addAttribute("productList", productList);

        return "/admin/product/list.jsp";
    }


    /**
     * 后台查询所有订单信息
     *
     * @param model
     * @return
     */
    @GetMapping("/findAllOrders")
    public String findAllOrders(Model model) {


        List<Order> orderList = orderService.findAllOrders();

        model.addAttribute("orderList", orderList);

        return "/admin/order/list.jsp";

    }


    /**
     * 根据oid查到订单详情
     *
     * @param oid
     * @return
     */
    @PostMapping("/findOrderInfoByOid")
    @ResponseBody
    public List<Map<String, Object>> findOrderInfoByOid(String oid) {

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Map<String, Object>> mapList = orderService.findOrderInfoByOid(oid);

        return mapList;

    }


    /**
     * 后台商品上传
     *
     * @return
     */
    @PostMapping("/addProduct")
    public String addProduct(MultipartFile file, Product product, String pid,
                             String cid, HttpServletRequest request) {

        if (file != null) {
            String path = request.getSession().getServletContext().getRealPath("upload");

            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }
            try {
                file.transferTo(new File(path, file.getOriginalFilename()));
                product.setPimage("upload/" + file.getOriginalFilename());
                if (StringUtils.isBlank(pid)) {
                    product.setPid(idWorker.nextId());
                }
                product.setPdate(new Date());
                Category category = new Category();
                category.setCid(cid);
                product.setCategory(category);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        productService.saveProduct(product);

        return "redirect:/admin/findAllProducts";

    }


    /**
     * 删除商品
     *
     * @param pid
     * @return
     */
    @GetMapping("/delProduct")
    public String delProduct(String pid) {

        productService.delProduct(pid);

        return "redirect:/admin/findAllProducts";
    }


    /**
     * 修改商品信息，根据pid查询到之后回显
     *
     * @param pid
     * @param model
     * @return
     */
    @GetMapping("/findProductByPid")
    public String findProductByPid(String pid, Model model) {

        Product productByPid = productService.findProductInfoByPid(pid);
        model.addAttribute("productByPid", productByPid);

        return "/admin/product/edit.jsp";

    }



}
