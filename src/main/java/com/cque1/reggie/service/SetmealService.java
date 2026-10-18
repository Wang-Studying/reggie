package com.cque1.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cque1.reggie.dto.SetmealDto;
import com.cque1.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);
    public SetmealDto getByIdWithDish(Long id);
    public void updateWithDish(SetmealDto setmealDto);
    public void removeWithDish(List<Long> ids);
}