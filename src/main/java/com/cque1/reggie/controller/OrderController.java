package com.cque1.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cque1.reggie.common.R;
import com.cque1.reggie.dto.DishDto;
import com.cque1.reggie.dto.OrdersDto;
import com.cque1.reggie.entity.Dish;
import com.cque1.reggie.entity.OrderDetail;
import com.cque1.reggie.entity.Orders;
import com.cque1.reggie.service.OrderDetailService;
import com.cque1.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;
    /**
     * 订单信息分页查询
     */
    @GetMapping("/page")
    public R<Page<OrdersDto>> page(int page, int pageSize, String number,
                                   String beginTime,String endTime){
        //构造分页构造器对象
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(number != null,Orders::getNumber,number);
        queryWrapper.between(beginTime!=null && endTime!=null  ,
                Orders::getOrderTime,beginTime,endTime);
        //添加排序条件
        queryWrapper.orderByDesc(Orders::getOrderTime);
        //执行分页查询
        orderService.page(pageInfo,queryWrapper);
        Page<OrdersDto> ordersDtoPage = new Page<OrdersDto>();
        BeanUtils.copyProperties(pageInfo,ordersDtoPage,"records");
        //完成订单详情的集合封装
        List<OrdersDto> ordersDtos = pageInfo.getRecords().stream().map(orders -> {
            OrdersDto ordersDto = new OrdersDto();
            //订单基本信息封装到ordersDto
            BeanUtils.copyProperties(orders, ordersDto);
            //订单详情集合
            LambdaQueryWrapper<OrderDetail> orderDetailLambdaQueryWrapper =
                    new LambdaQueryWrapper<>();
            orderDetailLambdaQueryWrapper.eq(OrderDetail::getOrderId, orders.getId());
            List<OrderDetail> ordersDetailList =
                    orderDetailService.list(orderDetailLambdaQueryWrapper);
            ordersDto.setOrderDetails(ordersDetailList);
            return ordersDto;
        }).collect(Collectors.toList());
        //手动封装ordersDtos分页中的records属性
        ordersDtoPage.setRecords(ordersDtos);
        return R.success(ordersDtoPage);
    }
    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }
    @GetMapping("/userPage")
    public R<Page<OrdersDto>> ordersPage(Integer page, Integer pageSize){
        Page<OrdersDto> pageInfo = orderService.pageOrdersAndOrdersDetail(page,pageSize);

        return R.success(pageInfo);
    }

    /**
     * 订单状态修改
     */
    @PutMapping
    public R<String> editStatus(@RequestBody Orders orders){
        //条件构造器
        LambdaUpdateWrapper<Orders> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Orders::getId,orders.getId());
        int statusNum = orders.getStatus() + 1;
        updateWrapper.set(Orders::getStatus,statusNum);
        orderService.update(updateWrapper);
        return R.success("订单状态修改成功");
    }
}