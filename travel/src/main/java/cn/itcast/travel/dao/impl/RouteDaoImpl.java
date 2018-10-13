package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate t = new JdbcTemplate(JDBCUtils.getDataSource());
//    查询总页数
    @Override
    public int totalCount(int cid, String rname) {
        String sql = "SELECT COUNT(*) FROM tab_route WHERE 1=1";
        StringBuilder sb = new StringBuilder(sql);
        List<Object> list = new ArrayList<Object>();
        if (cid!=0){
            sb.append(" and cid=? ");
            list.add(cid);
        }
        if (rname!=null&&rname.length()>0&&!"null".equals(rname)){
            sb.append(" and rname like ? ");
            list.add("%"+rname+"%");
        }
        sql = sb.toString();
        System.out.println(sql);
        return t.queryForObject(sql,Integer.class,list.toArray());
    }

    @Override
    public List<Route> pageQuery(int cid, int start, int rows, String rname) {
        String sql = "SELECT * FROM tab_route WHERE 1=1  ";
        StringBuilder sb = new StringBuilder(sql);
        List<Object> list = new ArrayList<Object>();
        if (cid!=0){
            sb.append(" and cid=? ");
            list.add(cid);
        }
        if (rname!=null&&rname.length()>0&&!"null".equals(rname)){
            sb.append(" and rname like ? ");
            list.add("%"+rname+"%");
        }
        sb.append(" limit ?,? ");
        list.add(start);
        list.add(rows);
        sql = sb.toString();
        return t.query(sql,new BeanPropertyRowMapper<>(Route.class),list.toArray());
    }
//查询详情
    @Override
    public Route findOne(int rid) {
        String sql = "SELECT * FROM tab_route WHERE rid=?";
        return t.queryForObject(sql,new BeanPropertyRowMapper<>(Route.class),rid);

    }
}
