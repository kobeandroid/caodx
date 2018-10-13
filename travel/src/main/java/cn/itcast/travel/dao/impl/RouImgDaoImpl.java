package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouImgDaoImpl implements RouteImgDao {
    private JdbcTemplate t = new JdbcTemplate(JDBCUtils.getDataSource());
//    查询全部图片
    @Override
    public List<RouteImg> findAll(int rid) {
        String sql = "SELECT * FROM tab_route_img WHERE rid =?";
        return t.query(sql,new BeanPropertyRowMapper<>(RouteImg.class),rid);
    }
}
