package com.panduoma.pdmadmin.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.panduoma.pdmadmin.entity.Admin;
import com.panduoma.pdmadmin.exception.BusinessException;
import com.panduoma.pdmadmin.response.R;
import com.panduoma.pdmadmin.response.ResponseCode;
import com.panduoma.pdmadmin.service.AdminService;
import com.panduoma.pdmadmin.util.CaptchaCache;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "管理员信息管理")
@RestController
public class AdminController {
   
    @Resource
    private AdminService adminService;
    @Resource
    private CaptchaCache captchaCache;
    /**
     * 新增管理员信息
     * @return
     */
    @Operation(summary = "增加管理员")
    @PostMapping("/admin/add")
    @CrossOrigin
    @SaCheckLogin
    public R add(@RequestBody Admin admin){
        LambdaQueryWrapper<Admin> adminWrapper = new LambdaQueryWrapper<>();
        adminWrapper.eq(Admin::getUsername, admin.getUsername());
        long count = adminService.count(adminWrapper);
        if (count > 0) {
            throw new BusinessException(ResponseCode.USERNAME_EXIST);
        }
        // 使用MD5算法对管理员密码进行加密
        admin.setUserpwd(DigestUtils.md5DigestAsHex(admin.getUserpwd().getBytes()));
        //头像为空时默认头像
        if(StringUtils.isBlank(admin.getHeadurl())){
            admin.setHeadurl("upload\\nopic.png");
        }
        adminService.save(admin);
        return R.success();
    }

    /**
     * 查询所有管理员信息
     * @param pagenum 当前页码
     * @param pagesize 每页条数
     * @return
     */
    @Operation(summary = "查询管理员列表")
    @PostMapping("/admin/list")
    @CrossOrigin
    @SaCheckLogin
    public R<PageInfo<Admin>> list(@RequestBody Admin admin,@RequestParam Integer pagenum, @RequestParam Integer pagesize){
       LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(admin.getName() != null, Admin::getName, admin.getName());
        lambdaQueryWrapper.like(admin.getTel() != null, Admin::getTel, admin.getTel());
        lambdaQueryWrapper.orderByDesc(Admin::getId);
        PageHelper.startPage(pagenum, pagesize);
        List<Admin> list = adminService.list(lambdaQueryWrapper);
        PageInfo<Admin> pageInfo = new PageInfo(list);
        return R.data(pageInfo);
    }

    /**
     * 修改管理员信息
     * @return
     */
    @Operation(summary = "修改管理员信息")
    @PostMapping("/admin/update")
    @CrossOrigin
    @SaCheckLogin
    public R update(@RequestBody Admin admin){
        adminService.updateById(admin);
        return R.success();
    }

    /**
     * 删除管理员信息
     * @return
     */
    @Operation(summary = "删除管理员")
    @PostMapping("/admin/delete")
    @CrossOrigin
    @SaCheckLogin
    public R delete(@RequestParam List<Long> ids){
        adminService.removeByIds(ids);
        return R.success();
    }


    /**
     * 管理员登录
     * @return
     */
    @Operation(summary = "管理员登录")
    @PostMapping("/admin/login")
    @CrossOrigin
    public R<Admin> login(@RequestBody Admin admin){
        if (StringUtils.isBlank(admin.getCaptchaId()) || StringUtils.isBlank(admin.getCaptchaCode())) {
            return R.fail(ResponseCode.CAPTCHA_ERROR);
        }
        boolean result = captchaCache.validateCaptcha(admin.getCaptchaId(), admin.getCaptchaCode());
        if (!result) {
            return R.fail(ResponseCode.CAPTCHA_ERROR);
        }
        captchaCache.removeCaptcha(admin.getCaptchaId());
        //根据传入的用户名和密码去数据库查询
        LambdaQueryWrapper<Admin> adminWrapper = new LambdaQueryWrapper<>();
        adminWrapper.eq(Admin::getUsername,admin.getUsername());
        adminWrapper.eq(Admin::getUserpwd, DigestUtils.md5DigestAsHex(admin.getUserpwd().getBytes()));
        admin = adminService.getOne(adminWrapper);
        //数据库没有数据，就提示用户名密码错误
        if(admin == null){
            throw new BusinessException(ResponseCode.USERNAME_USERPWD_ERROR);
        }
        //根据ID进行登录
        StpUtil.login(admin.getId());
        //获取当前登录会话的token，赋值给admin的token属性
        admin.setToken(StpUtil.getTokenValue());
        return R.data(admin);
    }
    /**
     * 管理员退出登录
     * @return
     */
    @Operation(summary = "管理员退出登录")
    @PostMapping("/admin/loginout")
    @CrossOrigin
    public R loginout(){
        //退出当前会话
        StpUtil.logout();
        return R.success();
    }

    /**
     * 检验用户名是否唯一
     * @param username
     * @return
     */
    @GetMapping(value = "/admin/checkUsername")
    @CrossOrigin
    public R checkUsername(@RequestParam String username)  {
        LambdaQueryWrapper<Admin> adminWrapper = new LambdaQueryWrapper<>();
        adminWrapper.eq(Admin::getUsername,username);
        List<Admin> adminList = adminService.list(adminWrapper);
        if (adminList != null && adminList.size() > 0) {
            return R.fail(ResponseCode.USERNAME_EXIST);
        }
        return R.success();
    }


    /**
     * 重置管理员密码。
     * @return 返回一个表示操作结果的对象，通常表示操作成功。
     */
    @PostMapping(value = "/admin/resetPwd")
    @CrossOrigin
    public R resetPwd(@RequestParam Integer id)  {
        // 根据ID从数据库中获取管理员对象
        Admin admin = adminService.getById(id);
        // 使用MD5加密新密码，并更新管理员对象的密码字段
        admin.setUserpwd(DigestUtils.md5DigestAsHex("123456".getBytes()));
        // 通过ID更新管理员对象的密码信息
        adminService.updateById(admin);
        // 返回操作成功的响应对象
        return R.success();
    }
}