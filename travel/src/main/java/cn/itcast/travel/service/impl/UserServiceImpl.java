package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;


public class UserServiceImpl implements UserService {
    UserDao dao = new UserDaoImpl();
    @Override
    public void addUser(User u) {
        dao.addUser(u);

    }
//  注册
    @Override
    public boolean findAll(User u) {
//        查询用户是否存在
        User user = dao.findAll(u);
//        不存在将注册在数据库中添加用户信息
        if (user == null){
//            设置激活码
            u.setCode(UuidUtil.getUuid());
           // System.out.println(u.getCode());
//            设置激活状态
            u.setStatus("N");
            dao.addUser(u);
            String conter = "<a href='http://localhost/travel/user/active?code="+u.getCode()+"'>点击激活【黑马旅游网】</a>";
           // System.out.println(u.getCode());
            System.out.println(conter);
            MailUtils.sendMail(u.getEmail(),conter,"激活邮件");
            return true;
        }
//        存在返回false
            return false;
    }
//激活
    @Override
    public boolean active(String code) {
        User user = dao.active(code);
        if (user!=null){
            dao.updateStatus(user);
            return true;
        }
        return false;
    }
//用户登录
    @Override
    public User loginFind(User user) {
        return dao.loginFind(user);
    }
//用户名校验
    @Override
    public boolean findUser(String username) {
        User user =  dao.findUser(username);
        if (user==null){
            return true;
        }
        return false;
    }
}
