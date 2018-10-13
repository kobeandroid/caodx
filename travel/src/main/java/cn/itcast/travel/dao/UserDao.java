package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
//    添加用户
    void addUser(User u);
//查找用户
    User findAll(User u);
//激活
    User active(String code);
//修改状态
    void updateStatus(User user);
//用户登录
    User loginFind(User user);

    User findUser(String username);
}
