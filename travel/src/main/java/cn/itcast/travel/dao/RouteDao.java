package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
//    查询总页数
    int totalCount(int cid, String rname);
    List<Route> pageQuery(int cid, int start, int rows, String rname);

    Route findOne(int rid);
}
