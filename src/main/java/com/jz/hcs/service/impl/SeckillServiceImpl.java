/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jz.hcs.service.impl;

import com.jz.hcs.dao.SeckillDao;
import com.jz.hcs.dao.SuccessKilledDao;
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
import java.util.List;
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
        Seckill seckill = getById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
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
            int updateCount = seckillDao.updateNumber(seckillId, new Date());
            if (updateCount <= 0) {
                throw new SeckillCloseException("seckill closed");
            } else {
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (insertCount <= 0) {
                    throw new RepeatKillException("seckill repeated");
                } else {
                    SuccessKilled successKilled = successKilledDao.selectByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, StateEnum.SUCCESS, successKilled);
                }
            }
        } catch(SeckillCloseException e1){
            throw e1;
        }catch(RepeatKillException e2){
            throw e2;
        }catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new SeckillCloseException("seckill inner error" + e.getMessage());
        }
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

}
