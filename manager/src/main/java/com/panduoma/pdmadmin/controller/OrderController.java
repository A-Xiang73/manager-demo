package com.panduoma.pdmadmin.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.panduoma.pdmadmin.entity.Admin;
import com.panduoma.pdmadmin.entity.OrderInfo;
import com.panduoma.pdmadmin.response.R;
import com.panduoma.pdmadmin.service.OrderInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.VarHandle;
import java.util.List;

/**
 * @author 24657
 * @apiNote
 * @date 2024/10/14 14:38
 */
@Tag(name = "订单信息管理")
@RestController
public class OrderController {


    @Autowired
    private OrderInfoService orderInfoService;

    @Operation(summary = "查询订单信息列表")
    @PostMapping("/order/list")
    @CrossOrigin
//    @SaCheckLogin
    public R<PageInfo<OrderInfo>> list(@RequestBody OrderInfo orderInfo, @RequestParam Integer pagenum, @RequestParam Integer pagesize){
        LambdaQueryWrapper<OrderInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(orderInfo.getOrderId() != null, OrderInfo::getOrderId, orderInfo.getOrderId());
        lambdaQueryWrapper.like(orderInfo.getUserId() != null, OrderInfo::getUserId, orderInfo.getUserId());
        lambdaQueryWrapper.orderByDesc(OrderInfo::getCreatedTime);
        PageHelper.startPage(pagenum, pagesize);
        List<OrderInfo> list = orderInfoService.list(lambdaQueryWrapper);
        PageInfo<OrderInfo> pageInfo = new PageInfo(list);
        return R.data(pageInfo);
    }

    @Operation(summary = "查询订单是否存在")
    @PostMapping("/order/exist")
    @CrossOrigin
//    @SaCheckLogin
    public R exist(@RequestBody OrderInfo orderInfo){
        LambdaQueryWrapper<OrderInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(orderInfo.getOrderId() != null, OrderInfo::getOrderId, orderInfo.getOrderId());
        OrderInfo order = orderInfoService.getById(lambdaQueryWrapper);
        if(order != null){
            return R.success("true");
        }else{
            return R.success("false");
        }
    }

    @Operation(summary = "增加订单")
    @PostMapping("/order/addOrUpdate")
    @CrossOrigin
//    @SaCheckLogin
    public R addorUpdate(@RequestBody OrderInfo orderInfo){
        boolean save = orderInfoService.saveOrUpdate(orderInfo);
        if(save){
            return R.success("订单添加成功");
        }else{
            return R.fail("订单添加失败");
        }
    }

    @Operation(summary = "增加订单")
    @PostMapping("/order/delete")
    @CrossOrigin
//    @SaCheckLogin
    public R del(@RequestBody List<Integer> ids){
       orderInfoService.removeByIds(ids);
        return R.success("订单删除成功");
    }
}
