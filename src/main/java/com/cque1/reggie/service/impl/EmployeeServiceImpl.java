package com.cque1.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cque1.reggie.entity.Employee;
import com.cque1.reggie.mapper.EmployeeMapper;
import com.cque1.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> 
        implements EmployeeService{ 
}