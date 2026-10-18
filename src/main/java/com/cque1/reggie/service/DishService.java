package com.cque1.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cque1.reggie.dto.DishDto;
import com.cque1.reggie.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);
    public DishDto getByIdWithFlavor(Long id);
    public void updateWithFlavor(DishDto dishDto);
    public void delByIdWithFlavor(List<Long> ids);
}