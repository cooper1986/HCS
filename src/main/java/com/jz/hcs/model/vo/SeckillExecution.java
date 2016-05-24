/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jz.hcs.model.vo;

import com.jz.hcs.enums.StateEnum;
import com.jz.hcs.model.SuccessKilled;

/**
 *
 * @author jesse
 */
public class SeckillExecution {
    private long seckillId;
    private int state;
    private String stateInfo;
    private SuccessKilled successKilled;

    public SeckillExecution() {
    }

    public SeckillExecution(long seckillId, StateEnum states, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = states.getState();
        this.stateInfo = states.getStateInfo();
        this.successKilled = successKilled;
    }

    public SeckillExecution(long seckillId, StateEnum states) {
        this.seckillId = seckillId;
        this.state = states.getState();
        this.stateInfo = states.getStateInfo();
    }

    
    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" + "seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo + ", successKilled=" + successKilled + '}';
    }
    
}
