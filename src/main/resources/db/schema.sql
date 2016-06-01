use seckill;

create table seckill(
 seckill_id bigint not null auto_increment comment '商品库存编号',
 name varchar(120) not null comment '商品名称',
 number int not null comment '库存数量',
 start_time timestamp not null comment '秒杀开始时间',
 end_time timestamp default current_timestamp not null comment '秒杀结束时间',
 create_time timestamp not null default current_timestamp comment '秒杀创建时间',
  primary key(seckill_id),
  key idx_start_time(start_time),
  key idx_end_time(end_time),
  key idx_create_time(create_time)
)engine=InnoDB auto_increment=1000 default charset=utf8 comment='秒杀库存表';

create table success_killed(
  seckill_id bigint not null comment '商品库存编号',
  user_phone bigint not null comment '用户手机号',
  state tinyint not null default -1 comment '状态标识：-1：无效; 0：成功; 1：已付款; 2：已发货',
  create_time timestamp not null comment '创建时间',
  primary key (seckill_id, user_phone),
  key idx_create_time(create_time)
)engine=InnoDB default charset=utf8 comment='秒杀成功明细表';

insert into 
   seckill(name, number, start_time, end_time)
values
   ('100元秒杀iphone', 100, '2016-05-01 00:00:00','2016-05-02 00:00:00'),
   ('200元秒杀ipad', 200, '2016-05-01 00:00:00','2016-05-02 00:00:00'),
   ('300元秒杀Mac', 300, '2016-05-01 00:00:00','2016-05-02 00:00:00'),
   ('10元秒杀小米4', 400, '2016-05-01 00:00:00','2016-05-02 00:00:00'),
   ('50元秒杀某鞋', 500, '2016-05-01 00:00:00','2016-05-02 00:00:00'),
   ('10元秒杀衣服', 600, '2016-05-01 00:00:00','2016-05-02 00:00:00'),
   ('1元秒杀篮球', 700, '2016-05-01 00:00:00','2016-05-02 00:00:00');