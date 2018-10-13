package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategroyDao;
import cn.itcast.travel.dao.impl.CategroyDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategroyService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategroyServiceImpl implements CategroyService {
    CategroyDao dao = new CategroyDaoImpl();
    @Override
    public List<Category> findNavitem() {
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        List<Category> list = null;
        if (category==null||category.size()==0){
            list = dao.findNavitem();
            System.out.println("数据库");
            for (int i = 0; i < list.size(); i++) {
                jedis.zadd("category",list.get(i).getCid(),list.get(i).getCname());
            }
        }else {
            list = new ArrayList<Category>();
            System.out.println("");
            for (Tuple tuple : category) {
                Category c = new Category();
                c.setCid((int)tuple.getScore());
                c.setCname(tuple.getElement());
                list.add(c);
            }
        }

        return list;
    }
}
