package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteDao {
//  查询是否收藏
    Favorite findFavorite(int rid, int uid);
//    查询收藏次数
    int findCount(int rid);
    //  收藏
    void add(int rid, int uid);
//查询rid
    List<Route> findRid(int start, int rows);

    int count();

    List<Route> findMyfavorite(int uid, int start, int rows);

    int uidCount(int uid);
}
