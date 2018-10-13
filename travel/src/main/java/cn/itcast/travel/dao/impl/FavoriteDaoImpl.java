package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate t = new JdbcTemplate(JDBCUtils.getDataSource());
    //  查询是否收藏
    @Override
    public Favorite findFavorite(int rid, int uid) {
        try {
            String sql = "SELECT * FROM tab_favorite WHERE rid=? AND uid=?";
            return t.queryForObject(sql,new BeanPropertyRowMapper<>(Favorite.class),rid,uid);
        } catch (DataAccessException e) {
            return null;
        }
    }
    //    查询收藏次数
    @Override
    public int findCount(int rid) {
        try {
            String sql = "SELECT COUNT(*) FROM tab_favorite WHERE rid=?";
            return t.queryForObject(sql,Integer.class,rid);
        } catch (DataAccessException e) {
            return 0;
        }
    }
    //  收藏
    @Override
    public void add(int rid, int uid) {
      String sql = "INSERT INTO tab_favorite VALUES (?,?,?)";
      t.update(sql,rid,new Date(),uid);
    }
//查询收藏的rid
    @Override
    public List<Route> findRid(int start, int rows) {
        String sql = "SELECT * FROM tab_favorite GROUP BY rid ORDER BY COUNT(rid) DESC limit ?,?";
        return t.query(sql,new BeanPropertyRowMapper<>(Route.class),start,rows);
    }
//查询总记录数
    @Override
    public int count() {
        String sql = "SELECT COUNT(DISTINCT rid) FROM tab_favorite";
        return t.queryForObject(sql,Integer.class);
    }
//查询我的收藏
    @Override
    public List<Route> findMyfavorite(int uid, int start, int rows) {
        String sql = "SELECT  " +
                "t1.*  " +
                "FROM  " +
                "tab_route t1, " +
                "tab_favorite t2 " +
                "WHERE t2.uid=? AND t1.rid=t2.rid  " +
                "LIMIT ?,?";
        System.out.println(sql);
        return t.query(sql,new BeanPropertyRowMapper<>(Route.class),uid,start,rows);
    }

    @Override
    public int uidCount(int uid) {
        String sql = "SELECT COUNT(*) FROM tab_favorite WHERE uid=?";
        return t.queryForObject(sql,Integer.class,uid);
    }
}
