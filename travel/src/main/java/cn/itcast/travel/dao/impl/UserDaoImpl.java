package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate t = new JdbcTemplate(JDBCUtils.getDataSource());
//    添加用户信息
    @Override
    public void addUser(User u) {
        String sql = "INSERT INTO tab_user(username,password,name,birthday,sex," +
                "telephone,email,status,code) VALUES (?,?,?,?,?,?,?,?,?)";
         t.update(sql,u.getUsername(),
                 u.getPassword(),u.getName(),
                 u.getBirthday(),u.getSex(),
                 u.getTelephone(),u.getEmail(),
                 u.getStatus(),u.getCode());
    }
// 查询用户信息
    @Override
    public User findAll(User u) {
        try {
            String sql = "SELECT * FROM tab_user WHERE username = ?";
            return t.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),u.getUsername());
        } catch (DataAccessException e) {
            return null;
        }

    }
//查询激活码
    @Override
    public User active(String code) {
        try {
            String sql = "SELECT * FROM tab_user WHERE code=?";
            return t.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),code);
        } catch (DataAccessException e) {
            return null;
        }
    }
//修改激活状态
    @Override
    public void updateStatus(User user) {
     String sql = "UPDATE tab_user SET status='Y' WHERE uid=?";
     t.update(sql,user.getUid());
    }
//用户登录查询
    @Override
    public User loginFind(User user) {
        try {
            String sql = "SELECT * FROM tab_user WHERE username=? AND password=?";
            return t.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),user.getUsername(),user.getPassword());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public User findUser(String username) {
        String sql = "SELECT * FROM tab_user WHERE username=?";
        return t.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),username);
    }
}
