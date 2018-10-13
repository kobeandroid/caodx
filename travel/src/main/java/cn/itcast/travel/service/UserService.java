package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
//    添加用户
    void addUser(User u);
//查询用户
    boolean findAll(User u);
//用户激活
    boolean active(String code);
//用户登录
    User loginFind(User user);

    boolean findUser(String username);
}
