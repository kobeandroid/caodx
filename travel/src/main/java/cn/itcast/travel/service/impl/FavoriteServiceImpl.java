package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao dao  = new FavoriteDaoImpl();
//    查询用户是否收藏
    @Override
    public boolean findFavorite(String rid, int uid) {
        Favorite favorite = dao.findFavorite(Integer.parseInt(rid),uid);
        if (favorite!=null){
            return true;
        }
            return false;
    }
//  收藏
    @Override
    public void add(String rid, int uid) {
        dao.add(Integer.parseInt(rid),uid);
    }
}
