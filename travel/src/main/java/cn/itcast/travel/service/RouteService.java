package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteService {
    PageBean<Route> pageQuery(int cid, int currentPage, int rows, String rname);

    Route findOne(String rid);

    PageBean<Route> favoriteAll(int currentPage, int rows);

    PageBean<Route> fingmyfavorite(int uid, int currentPage, int rows);
}
