var seckill = {
    URL: {
        
    },
    //验证手机号
    validatePhone: function(phone){
      if(phone && phone.length == 11 && !isNaN(phone)){
          return true;
      }else{
          return false;
      }  
    },
    detail: {
        init: function (params){
            var killPhone = $.cookie('killPhone');
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            
            if(!seckill.validatePhone(killPhone)){
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show: true, //显示弹出层
                    backdrop: 'static', //禁止位置关闭
                    keyboard: false  //关闭键盘事件
                });
                $('#killPhoneBtn').click(function(){
                    var inputPhone = $('#killphoneKey').val();
                    if(seckill.validatePhone(inputPhone)){
                        $.cookie('killPhone', inputPhone, {expires:7,path:'/HCS'});
                        window.location.reload();
                    }else{
                        $('#killphoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                    }
                });
            }
        }
    }
};


