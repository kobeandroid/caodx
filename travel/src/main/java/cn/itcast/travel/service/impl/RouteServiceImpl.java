package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouImgDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import java.util.ArrayList;
import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao dao = new RouteDaoImpl();
    private FavoriteDao fdao  = new FavoriteDaoImpl();
    private RouteImgDao imgDao = new RouImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int rows, String rname) {
        PageBean<Route> pb = new PageBean<>();
//        总记录数
        int totalCount = dao.totalCount(cid,rname);
//        总页码
        int totalPage = 0;
        if (totalCount%rows==0){
            totalPage = totalCount/rows;
        }else if(totalCount%rows!=0){
            totalPage = (totalCount/rows)+1;
        }
//        开始记录数
        int start = (currentPage-1)*rows;
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        pb.setTotalCount(totalCount);
        pb.setTotalPage(totalPage);

//        查询数据
        List<Route> routes = dao.pageQuery(cid, start, rows,rname);
        pb.setList(routes);
        return pb;
    }

    @Override
    public Route findOne(String rid) {

//        查询详情
        Route route = dao.findOne(Integer.parseInt(rid));
//        查询全部图片
        List<RouteImg> imgList = imgDao.findAll(Integer.parseInt(rid));
//        查询商家信息
        Seller seller = sellerDao.findSeller(route.getSid());
//        查询收藏次数
        int count = fdao.findCount(route.getRid());
        route.setCount(count);
        route.setSeller(seller);
        route.setRouteImgList(imgList);

        return route;
    }
//查询全部收藏并排序，分页
    @Override
    public PageBean<Route> favoriteAll(int currentPage, int rows) {
        PageBean pb = new PageBean();
        pb.setRows(rows);
        pb.setCurrentPage(currentPage);
//        总记录数
        int totalCount = fdao.count();
        pb.setTotalPage(totalCount);
//        总页数
        int totalPage;
        if (totalCount%rows==0){
            totalPage = totalCount/rows;
        }else {
            totalPage = (totalCount/rows)+1;
        }
        pb.setTotalPage(totalPage);
        //        开始记录数
        int start = (currentPage-1)*rows;
        List<Route> favoriteslist = fdao.findRid(start,rows);
        List<Route> routes = new ArrayList<>();
        for (int i = 0; i < favoriteslist.size(); i++) {
            Route route = dao.findOne(favoriteslist.get(i).getRid());
            int count = fdao.findCount(route.getRid());
            route.setCount(count);
            routes.add(route);
        }
//        全部数据
        pb.setList(routes);
        return pb;
    }

    @Override
    public PageBean<Route> fingmyfavorite(int uid, int currentPage, int rows) {
        PageBean pb = new PageBean();
        pb.setRows(rows);
        pb.setCurrentPage(currentPage);
        //        总记录数
        int totalCount = fdao.uidCount(uid);
        //        开始记录数
        int start = (currentPage-1)*rows;
        List<Route> favoriteslist = fdao.findMyfavorite(uid,start,rows);
        //        总记录数
        pb.setTotalCount(totalCount);
//        总页数
        int totalPage;
        if (totalCount%rows==0){
            totalPage = totalCount/rows;
        }else {
            totalPage = (totalCount/rows)+1;
        }
        pb.setTotalPage(totalPage);
        pb.setList(favoriteslist);
        return pb;
    }
}
