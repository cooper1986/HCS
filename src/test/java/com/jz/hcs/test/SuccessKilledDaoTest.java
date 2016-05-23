/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jz.hcs.test;

import com.jz.hcs.dao.SuccessKilledDao;
import com.jz.hcs.model.SuccessKilled;
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
public class SuccessKilledDaoTest {
    
    @Resource
    private SuccessKilledDao successkilledDao;
    
    @Test
    public void testInsertSuccessKilled() {
        long id = 1001L;
        long phone = 15812341235L;
        int i = successkilledDao.insertSuccessKilled(id, phone);
        System.out.println("i = " + i);
    }
    
    @Test
    public void testSelectByIdWithSeckill() {
        long id = 1001L;
        long phone = 15812341235L;
        SuccessKilled successKilled = successkilledDao.selectByIdWithSeckill(id, phone);
        System.out.println("successKilled: " + successKilled);
        System.out.println("seckill: " + successKilled.getSeckill());
    }
    
}
