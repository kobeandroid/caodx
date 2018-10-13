package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate t = new JdbcTemplate(JDBCUtils.getDataSource());
//    查询商家信息
    @Override
    public Seller findSeller(int sid) {
        String sql = "SELECT * FROM tab_seller WHERE sid=?";
        return t.queryForObject(sql,new BeanPropertyRowMapper<>(Seller.class),sid);
    }
}
