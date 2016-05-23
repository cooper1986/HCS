/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jz.hcs.dao;

import com.jz.hcs.model.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author jesse
 */
public interface SuccessKilledDao {
    int insertSuccessKilled(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone);
    
    SuccessKilled selectByIdWithSeckill(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone);
}
