package cn.jkego.emall.filter;


import cn.jkego.emall.service.UserService;
import cn.jkego.emall.domain.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAutoLoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        //强转成HttpServlet
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //判断是否已经登录
        User user = (User) request.getSession().getAttribute("user");

        if (user == null){ //用户没有登录，尝试自动登录

            String cookie_username = null;
            String cookie_password = null;

            //获取存储账号密码的cookie
            Cookie[] cookies = request.getCookies();
            if (cookies!=null){

                for (Cookie cookie :cookies){

                    if ("cookie_username".equals(cookie.getName())){
                        cookie_username = cookie.getValue();
                    }
                    if ("cookie_password".equals(cookie.getName())){
                        cookie_password = cookie.getValue();
                    }
                }
            }

            //利用从cookie获得到的账号密码尝试登录
            if(cookie_username!=null&&cookie_password!=null){
                //去数据库校验该用户名和密码是否正确
                UserService service = new UserService();
                user = service.userLogin(cookie_username,cookie_password);

                //完成自动登录
                request.getSession().setAttribute("user", user);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
