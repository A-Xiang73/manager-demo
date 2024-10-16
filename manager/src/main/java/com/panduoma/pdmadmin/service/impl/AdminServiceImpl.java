package com.panduoma.pdmadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panduoma.pdmadmin.entity.Admin;
import com.panduoma.pdmadmin.mapper.AdminMapper;
import com.panduoma.pdmadmin.service.AdminService;
import org.springframework.stereotype.Service;

/**
 * @author 潘多码(微信 : panduoma888)
 * @version 1.0.0
 * @description
 * @website www.panduoma.com
 * @copyright 公众号: 潘多码
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
}