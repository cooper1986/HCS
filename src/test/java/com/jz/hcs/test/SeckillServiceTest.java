/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jz.hcs.test;

import com.jz.hcs.exception.RepeatKillException;
import com.jz.hcs.exception.SeckillCloseException;
import com.jz.hcs.exception.SeckillException;
import com.jz.hcs.model.Seckill;
import com.jz.hcs.model.vo.Exposer;
import com.jz.hcs.model.vo.SeckillExecution;
import com.jz.hcs.service.SeckillService;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author jesse
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class SeckillServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(SeckillServiceTest.class.getName());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() {
        List<Seckill> list = seckillService.getSeckillList();
        LOG.info("list:{}", list);
    }

    @Test
    public void testGetById() {
        Seckill seckill = seckillService.getById(1002L);
        LOG.info("seckill:{} ", seckill);
    }

    @Test
    public void testExposeSeckillLogic() {
        long id = 1001L;
        Exposer exposer = seckillService.exposeSeckillUrl(id);
         if (exposer.isIsExposed()) {
             LOG.info("exposer: {}", exposer);
            long phone = 13912121216L;
            String md5 = exposer.getMd5();

            try {
                SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
                LOG.info("execution:{}", execution);
            } catch (RepeatKillException e) {
                LOG.info(e.getMessage(), e);
            } catch (SeckillCloseException e1) {
                LOG.info(e1.getMessage(), e1);
            }
        } else {
            LOG.warn("exposer: {}", exposer);
        }
    }
}
