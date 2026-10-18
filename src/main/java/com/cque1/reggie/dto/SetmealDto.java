package com.cque1.reggie.dto;

import com.cque1.reggie.entity.Setmeal;
import com.cque1.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;
@Data
public class SetmealDto extends Setmeal {
    private List<SetmealDish> setmealDishes;
    private String categoryName;
}
