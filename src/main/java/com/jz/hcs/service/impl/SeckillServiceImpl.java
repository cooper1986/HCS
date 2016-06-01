/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jz.hcs.service.impl;

import com.jz.hcs.dao.SeckillDao;
import com.jz.hcs.dao.SuccessKilledDao;
import com.jz.hcs.dao.cache.RedisDao;
import com.jz.hcs.enums.StateEnum;
import com.jz.hcs.exception.RepeatKillException;
import com.jz.hcs.exception.SeckillCloseException;
import com.jz.hcs.exception.SeckillException;
import com.jz.hcs.model.Seckill;
import com.jz.hcs.model.SuccessKilled;
import com.jz.hcs.model.vo.Exposer;
import com.jz.hcs.model.vo.SeckillExecution;
import com.jz.hcs.service.SeckillService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 *
 * @author jesse
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private static final Logger LOG = LoggerFactory.getLogger(SeckillServiceImpl.class.getName());
    private final String salt = "kdhnkdjfo_+%^&&&&#$@JDKDLDMEMSNI=-";

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.selectAll(0, 3);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.selectById(seckillId);
    }

    @Override
    public Exposer exposeSeckillUrl(long seckillId) {

        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null) {
            seckill = getById(seckillId);
            if (seckill == null) {
                return new Exposer(false, seckillId);
            } else {
                redisDao.putSeckill(seckill);
            }
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    @Override
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }

        try {
            //先插入数据
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0) {
                throw new RepeatKillException("seckill repeated");
            } else {
                //后减库存，热点所在，行级锁在
                int updateCount = seckillDao.updateNumber(seckillId, new Date());
                if (updateCount <= 0) {//rollback
                    throw new SeckillCloseException("seckill closed");
                } else {
                    //秒杀成功，commit
                    SuccessKilled successKilled = successKilledDao.selectByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, StateEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new SeckillCloseException("seckill inner error" + e.getMessage());
        }
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    public SeckillExecution executeSeckillByProcedure(long seckillId, long userPhone, String md5) {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            return new SeckillExecution(seckillId, StateEnum.DATA_REWITE);
        }

        Date killTime = new Date();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("seckillId", seckillId);
        map.put("phone", userPhone);
        map.put("killTime", killTime);
        map.put("result", null);
        try {
            seckillDao.seckillByProcedure(map);
            int result = MapUtils.getInteger(map, "result", -2);
            if (result == 1) {
                SuccessKilled sk = successKilledDao.selectByIdWithSeckill(seckillId, userPhone);
                return new SeckillExecution(seckillId, StateEnum.SUCCESS, sk);
            } else {
                return new SeckillExecution(seckillId, StateEnum.stateOf(result));
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new SeckillExecution(seckillId, StateEnum.INNER_ERROR);
        }
    }

}
