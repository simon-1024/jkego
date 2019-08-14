package cn.jkego.emall.controller;

import cn.jkego.emall.domain.User;
import cn.jkego.emall.service.SMSService;
import cn.jkego.emall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SMSService smsService;


    /**
     * 用户注册
     *
     * @param user ：前端传来的数据  直接封装成对象
     */
    @PostMapping(value = "/register")
    public String register(User user) {


        //注册--保存user
        boolean isRegister = userService.register(user);

        if (isRegister) {

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //跳转到首页
            return "/default.jsp";

        } else {
            //注册失败 跳转至失败页面
            return "/registerFail.jsp";
        }
    }


    /**
     * 异步校验用户名是否已经存在，返回ajax json形式的数据
     *
     * @param username
     * @return
     */
    @PostMapping("/checkUsername")
    @ResponseBody
    public String checkUsername(@RequestParam("username") String username) {

        boolean isExist = userService.checkUsername(username);

        return String.valueOf(isExist);
    }


    /**
     * 用户激活
     *
     * @param code
     * @return
     */
    @GetMapping("/active")
    public String activeUser(@RequestParam("activeCode") String code) {

        userService.activeUser(code);

        return "/login.jsp";
    }


    /**
     * 用户登录
     *
     * @return
     */
/*    @PostMapping("/login")
    public String userLogin(String username, String password, String checkCode, String autoLogin,
                            Model model, HttpServletRequest request, HttpServletResponse response) {


        HttpSession session = request.getSession();
        //获取session域中的验证码
        String checkcode_session = (String) session.getAttribute("checkcode_session");

        if (checkcode_session.equals(checkCode)) {
            User user = userService.userLogin(username, password);
            if (user != null) {
                //判断是否勾选自动登录
                if ("autoLogin".equals(autoLogin)) {
                    //勾选了自动登录
                    Cookie cookie_username = new Cookie("cookie_username", user.getUsername());
                    Cookie cookie_password = new Cookie("cookie_password", user.getPassword());
                    cookie_username.setMaxAge(10 * 60);
                    cookie_password.setMaxAge(10 * 60);
                    response.addCookie(cookie_username);
                    response.addCookie(cookie_password);
                }
                //将用户状态保存到seesion域中
                session.setAttribute("user", user);
                //跳转到首页
                return "/default.jsp";
            } else {
                //用户名密码错误，显示错误信息
                model.addAttribute("loginError", "用户名或密码错误");
                return "/login.jsp";
            }
        }
        //验证码错误，显示提示信息
        model.addAttribute("loginError", "验证码错误");
        return "/login.jsp";

    }*/
    @PostMapping("/login")
    public String login(String username, String password, HttpServletRequest request,Model model) {

        //得到session对象
        HttpSession session = request.getSession();
        User user = userService.userLogin(username, password);

        if (user != null) {
            session.setAttribute("user",user);
            //跳转到首页
            return "/default.jsp";

        }else{
            //用户名密码错误，显示错误信息
            model.addAttribute("loginError", "用户名或密码错误");
            return "/login.jsp";
        }

    }


    /**
     * 用户退出登录：自动登录也失效
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        session.removeAttribute("user");

        //退出后：自动登录也失效
        //创建存储用户名的cookie
        Cookie cookie_username = new Cookie("cookie_username", "");
        cookie_username.setMaxAge(0);
        //创建存储密码的cookie
        Cookie cookie_password = new Cookie("cookie_password", "");
        cookie_password.setMaxAge(0);

        response.addCookie(cookie_username);
        response.addCookie(cookie_password);

        return "/login.jsp";

    }

    /**
     * 前端ajax发送短信到后台
     *
     * @param mobile
     */
    @PostMapping("/sendCheckcode")
    public void sendCheckcode(String mobile) {

        smsService.sendSms(mobile);

    }


    @PostMapping("/checkcode")
    @ResponseBody
    public String checkcode(String checkcode, String mobile) {

        boolean success = smsService.checkcode(mobile, checkcode);


        return String.valueOf(success);

    }

}
