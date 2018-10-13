package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategroyService;
import cn.itcast.travel.service.impl.CategroyServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/categroy/*")
public class CategroyServlet extends BaseServlet {
    public void navitem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         CategroyService service = new CategroyServiceImpl();
         List<Category> list= service.findNavitem();
        if (list!=null){
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("text/html;charset=utf-8");
            mapper.writeValue(response.getOutputStream(),list);

        }
    }
}
