/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jz.hcs.service;

import com.jz.hcs.exception.RepeatKillException;
import com.jz.hcs.exception.SeckillCloseException;
import com.jz.hcs.exception.SeckillException;
import com.jz.hcs.model.Seckill;
import com.jz.hcs.model.vo.Exposer;
import com.jz.hcs.model.vo.SeckillExecution;
import java.util.List;

/**
 *
 * @author jesse
 */
public interface SeckillService {
    List<Seckill> getSeckillList();
    Seckill getById(long seckillId);
    
    /**
     * 秒杀开启时输出秒杀接口地址
     * 否则输出系统时间和秒杀时间
     * @param secKillId 
     */
    Exposer exposeSeckillUrl(long seckillId);
    
    /**
     * 执行秒杀
     * @param seckillId
     * @param userPhone
     * @param md5 
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) 
            throws SeckillException, RepeatKillException, SeckillCloseException;
    
     /**
     * 通过存储过程执行秒杀
     * @param seckillId
     * @param userPhone
     * @param md5 
     */
    SeckillExecution executeSeckillByProcedure(long seckillId, long userPhone, String md5); 
}
