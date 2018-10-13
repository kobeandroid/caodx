package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService service = new UserServiceImpl();

    //     登录用户名显示
    public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("text/html;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), user);
    }

    //   登录
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
//        判断验证码
        if (!check.equalsIgnoreCase(checkcode_server)) {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("text/html;charset=utf-8");
            mapper.writeValue(response.getOutputStream(), info);
            return;
        }
//判断用户是否登录成功
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //UserService service = new UserServiceImpl();
        User u = service.loginFind(user);
        ResultInfo info = new ResultInfo();
        if (u != null) {
//            判断用户是否激活
            if ("Y".equalsIgnoreCase(u.getStatus())) {
                info.setFlag(true);
                HttpSession session1 = request.getSession();
                session1.setAttribute("user", u);
            } else {
                info.setFlag(false);
                info.setErrorMsg("登录失败，用户未激活");
            }

        } else {
            info.setFlag(false);
            info.setErrorMsg("登录失败，用户名或密码错误");
        }
//        将错误信息写回页面
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("text/html;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);

    }

    //   注册
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        判断验证码是否正确
//        String username = request.getParameter("username");
//        boolean flag = service.findUser(username);
//        if (flag){
//            ResultInfo info = new ResultInfo();
//            info.setFlag(true);
//        }else {
//            ResultInfo info = new ResultInfo();
//            ObjectMapper mapper = new ObjectMapper();
//            info.setFlag(false);
//            info.setErrorMsg("用户名不可用");
//            response.setContentType("text/html;charset=utf-8");
//            mapper.writeValue(response.getOutputStream(),info);
//        }
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
//        不正确提示用户，注册失败
        if (!check.equalsIgnoreCase(checkcode_server)) {
            ResultInfo info = new ResultInfo();
            ObjectMapper mapper = new ObjectMapper();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            response.setContentType("text/html;charset=utf-8");
            mapper.writeValue(response.getOutputStream(), info);
            return;
        }
//       用户注册
        Map<String, String[]> map = request.getParameterMap();
        User u = new User();
        try {
//            将信息封装成user对象
            BeanUtils.populate(u, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//        创建Service对象
        // UserService service = new UserServiceImpl();
        boolean flat = service.findAll(u);
        ResultInfo info = new ResultInfo();
        ObjectMapper mapper = new ObjectMapper();
//        判断用户是否注册成功，并返回相应信息
        if (flat) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("注册失败");
        }
        response.setContentType("text/html;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);

    }

    //    激活
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        System.out.println(code);
        if (code != null) {
            // UserService service = new UserServiceImpl();
            boolean flag = service.active(code);
            String msg = null;
            if (flag) {
                msg = "激活成功，请<a href='../login.html'>登录</a>";
            } else {
                msg = "激活失败，请联系管理员";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }

    }

    //   退出登录
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        销毁session
        request.getSession().invalidate();
//       重定项
        response.sendRedirect(request.getContextPath() + "/index.html");
    }
}
