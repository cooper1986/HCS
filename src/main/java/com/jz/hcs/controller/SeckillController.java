/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jz.hcs.controller;

import com.jz.hcs.enums.StateEnum;
import com.jz.hcs.exception.RepeatKillException;
import com.jz.hcs.exception.SeckillCloseException;
import com.jz.hcs.model.Seckill;
import com.jz.hcs.model.vo.Exposer;
import com.jz.hcs.model.vo.SeckillExecution;
import com.jz.hcs.model.vo.SeckillResult;
import com.jz.hcs.service.SeckillService;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jesse
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    private static final Logger LOG = LoggerFactory.getLogger(SeckillController.class.getName());
    
    @Autowired
    SeckillService seckillService;
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model){
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list", list);
        return "list";
    }
    
    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){
        if(seckillId == null){
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if(seckill == null){
            return "redirect:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }
    
    @RequestMapping(value = "/{seckillId}/exposer", 
                    method = RequestMethod.POST,
                    produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(Long seckillId){
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exposeSeckillUrl(seckillId);
            result = new SeckillResult<>(true,exposer);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = new SeckillResult<>(false, e.getMessage());
        }
        return result;
    }
    
    @RequestMapping(value = "/{seckillId}/{md5}/execute",
                    method = RequestMethod.POST,
                    produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId")Long seckillId,
                                                   @PathVariable("md5")String md5, 
                                                   @CookieValue(value = "killPhone", required = false)Long userPhone){
        if(userPhone == null){
            return new SeckillResult<>(false, "未注册");
        }
        
        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5);
            return new SeckillResult<>(true, execution);
        } catch(SeckillCloseException e1){
            SeckillExecution execution = new SeckillExecution(seckillId, StateEnum.END);
            return new SeckillResult<>(false, execution);
        }catch(RepeatKillException e2){
            SeckillExecution execution = new SeckillExecution(seckillId, StateEnum.REPEAT_KILL);
            return new SeckillResult<>(false, execution);
        }catch (Exception e) {
            LOG.error(e.getMessage(), e);
            SeckillExecution execution = new SeckillExecution(seckillId, StateEnum.INNER_ERROR);
            return new SeckillResult<>(false, execution);
        }
    }
    
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult<>(true, now.getTime());
    }
}
