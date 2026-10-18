package com.cque1.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cque1.reggie.entity.OrderDetail;

/*
    mybatisplus管得很宽，明明是持久层框架，但是他把service也给管了
    当前的service是操作employee模块的service
 */
public interface OrderDetailService extends IService<OrderDetail> {

}
