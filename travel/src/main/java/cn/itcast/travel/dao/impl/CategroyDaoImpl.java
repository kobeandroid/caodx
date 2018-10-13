package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategroyDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategroyDaoImpl implements CategroyDao {
    private JdbcTemplate t = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> findNavitem() {
        String sql = "SELECT * FROM tab_category";
        return t.query(sql,new BeanPropertyRowMapper<>(Category.class));
    }
}
