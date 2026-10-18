package com.cque1.reggie.dto;

import com.cque1.reggie.entity.Dish;
import com.cque1.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {
    //菜品对应的口味数据
    private List<DishFlavor> flavors = new ArrayList<>();
    private String categoryName;
    private Integer copies;
}
