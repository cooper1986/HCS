/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jz.hcs.test;

import com.jz.hcs.dao.SeckillDao;
import com.jz.hcs.model.Seckill;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author jesse
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class SecKillDaoTest {
    
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void testSelectById() {
        long id = 1000;
        Seckill seckill = seckillDao.selectById(id);
        System.out.println("number:" + seckill.getNumber());
        System.out.println("seckill:" + seckill);
    }
    
     @Test
    public void testSelectAll() {
        List<Seckill> seckills = seckillDao.selectAll(1, 3);
        for(Seckill seckill : seckills){
            System.out.println("seckills: " +  seckill);
        }
    }
    
     @Test
    public void testUpdateNumber() {
        int i = seckillDao.updateNumber(1000L, new Date());
         System.out.println("i = " + i);
    }
    
}
