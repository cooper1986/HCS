/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jz.hcs.dao;

import com.jz.hcs.model.Seckill;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author jesse
 */
public interface SeckillDao {

    /**
     * 减库存
     * @param secKillId
     * @param killDate
     * @return 
     */
    public int updateNumber(@Param("secKillId")long secKillId, @Param("killDate")Date killDate);
    
    /**
     * 根据seckillId查询
     * @param secKillId
     * @return 
     */
    public Seckill selectById(long secKillId);
    
    /**
     * 根据偏移量查询
     * @param offset
     * @param limit
     * @return 
     */
    public List<Seckill> selectAll(@Param("offset")int offset, @Param("limit")int limit);
}
