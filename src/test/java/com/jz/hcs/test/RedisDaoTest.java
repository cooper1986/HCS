/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jz.hcs.test;

import com.jz.hcs.dao.SeckillDao;
import com.jz.hcs.dao.cache.RedisDao;
import com.jz.hcs.model.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author jesse
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class RedisDaoTest {
    private long id = 1001L;
    
    @Autowired
    private RedisDao redisDao;
    
    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void test() {
         Seckill seckill = redisDao.getSeckill(id);
         if(seckill == null){
             seckill = seckillDao.selectById(id);
             if(seckill != null){
                 String result = redisDao.putSeckill(seckill);
                 System.out.println("------------ result:" + result);
                 seckill = redisDao.getSeckill(id);
                 System.out.println("------------ seckill:" + seckill);
             }
         }
    }

}
