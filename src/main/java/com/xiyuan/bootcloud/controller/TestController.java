package com.xiyuan.bootcloud.controller;

import com.xiyuan.bootcloud.mybatis.dao.LogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by xiyuan_fengyu on 2018/6/15 14:25.
 */
@Controller
public class TestController {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private LogDao logDao;

    @RequestMapping(value = {"", "/"})
    @ResponseBody
    public String idnex() {
        int i = 3;
        return "index";
    }

    @RequestMapping(value = "test/ftl")
    public String testFtl(Model model) {
        model.addAttribute("user", "Tom");
        model.addAttribute("log", logDao.selectById(1));
        model.addAttribute("logCount", logDao.count());

        try (Jedis jedis = jedisPool.getResource()) {
            String str = jedis.get("test");
            System.out.println(str);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

}
