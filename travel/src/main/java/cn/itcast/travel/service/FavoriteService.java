package cn.itcast.travel.service;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteService {
//    判断用户是否收藏
    boolean findFavorite(String rid, int uid);

    void add(String rid, int uid);
}
