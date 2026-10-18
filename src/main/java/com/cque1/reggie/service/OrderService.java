package com.cque1.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cque1.reggie.dto.OrdersDto;
import com.cque1.reggie.entity.Orders;

/*
    mybatisplus管得很宽，明明是持久层框架，但是他把service也给管了
 */
public interface OrderService extends IService<Orders> {

    void submit(Orders orders);

    Page<OrdersDto> pageOrdersAndOrdersDetail(Integer page, Integer pageSize);

}
