/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jz.hcs.model.vo;

/**
 *
 * @author jesse
 */
public class Exposer {
    //是否开启
    private boolean isExposed;
    //加密
    private String md5;
    //id
    private long seckillId;
    //系统当前时间（毫秒）
    private long now;
    //开启时间
    private long start;
    //结束时间
    private long end;

    public Exposer() {
    }

    public Exposer(boolean isExposed, String md5, long seckillId) {
        this.isExposed = isExposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean isExposed, long seckillId, long now, long start, long end) {
        this.isExposed = isExposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }
    
    public Exposer(boolean isExposed, long seckillId) {
        this.isExposed = isExposed;
        this.seckillId = seckillId;
    }

    public boolean isIsExposed() {
        return isExposed;
    }

    public void setIsExposed(boolean isExposed) {
        this.isExposed = isExposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer{" + "isExposed=" + isExposed + ", md5=" + md5 + ", seckillId=" + seckillId + ", now=" + now + ", start=" + start + ", end=" + end + '}';
    }
    
}
