package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private RouteService service = new RouteServiceImpl();
    private  FavoriteService favoriteServiceservice = new FavoriteServiceImpl();
//    线路查询
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPagestr = request.getParameter("currentPage");//当前页码
        String rowsstr = request.getParameter("rows");//每页数据显示条数
        String cidstr = request.getParameter("cid");//当前线路id
        String rname = request.getParameter("rname");//查询条件
        if (rname!=null&&rname.length()!=0&&!"null".equals(rname)){

        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        }

        int cid = 0;//当前线路id,如果没有默认为0
        if (cidstr != null && cidstr.length() > 0 && !"null".equals(cidstr)) {
            cid = Integer.parseInt(cidstr);
        }

        int currentPage = 0;//当前页码,如果没有默认为1
        if (currentPagestr != null && currentPagestr.length() > 0) {
            currentPage = Integer.parseInt(currentPagestr);
        } else {
            currentPage = 1;
        }
        int rows = 0;//每页数据显示条数,如果没有默认为10
        if (rowsstr != null && rowsstr.length() > 0) {
            rows = Integer.parseInt(rowsstr);
        } else {
            rows = 10;
        }
        PageBean<Route> p = service.pageQuery(cid, currentPage, rows, rname);
        writeValue(p, response);
    }

    //    查询线路详细信息
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String rid = request.getParameter("rid");
        Route route = service.findOne(rid);
        writeValue(route, response);
    }

    public void favorite(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            uid = 0;
        } else {
            uid = user.getUid();

        }
        boolean flag = favoriteServiceservice.findFavorite(rid, uid);
        writeValue(flag,response);
    }
//    用户收藏
    public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        ResultInfo info = new ResultInfo();
        int uid;
        if (user == null) {
            return;
        } else {
            uid = user.getUid();
            info.setFlag(true);
           }
         favoriteServiceservice.add(rid, uid);

         }
//         收藏排行分页
    public void favoriteAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String currentPagestr = request.getParameter("currentPage");//当前页码
        String rowsstr = request.getParameter("rows");//每页数据显示条数
        int currentPage = 0;//当前页码,如果没有默认为1
        if (currentPagestr != null && currentPagestr.length() > 0) {
            currentPage = Integer.parseInt(currentPagestr);
        } else {
            currentPage = 1;
        }
        int rows = 0;//每页数据显示条数,如果没有默认为10
        if (rowsstr != null && rowsstr.length() > 0) {
            rows = Integer.parseInt(rowsstr);
        } else {
            rows = 10;
        }
          PageBean pb = service.favoriteAll(currentPage,rows);
          writeValue(pb,response);
       }
//       我的收藏
    public void myfavorite(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int uid = Integer.parseInt(request.getParameter("uid"));
        String currentPagestr = request.getParameter("currentPage");//当前页码
        String rowsstr = request.getParameter("rows");//每页数据显示条数
        String rname = request.getParameter("rname");
        String hp = request.getParameter("hp");
        String dp = request.getParameter("dp");

        int currentPage = 0;//当前页码,如果没有默认为1
        if (currentPagestr != null && currentPagestr.length() > 0) {
            currentPage = Integer.parseInt(currentPagestr);
        } else {
            currentPage = 1;
        }
        int rows = 0;//每页数据显示条数,如果没有默认为12
        if (rowsstr != null && rowsstr.length() > 0) {
            rows = Integer.parseInt(rowsstr);
        } else {
            rows = 12;
        }
        PageBean<Route> routes = service.fingmyfavorite(uid,currentPage,rows);
        writeValue(routes,response);
       }
    }
