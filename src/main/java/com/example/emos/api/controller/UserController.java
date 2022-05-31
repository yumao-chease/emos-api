package com.example.emos.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import com.example.emos.api.common.util.PageUtils;
import com.example.emos.api.common.util.R;
import com.example.emos.api.controller.form.*;
import com.example.emos.api.db.pojo.TbUser;
import com.example.emos.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Feather
 */
@RestController
@RequestMapping("/user")
@Tag(name = "UserController", description = "用户Web接口")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 生成登陆二维码的字符串
     */
    @GetMapping("/createQrCode")
    @Operation(summary = "生成二维码Base64格式的字符串")
    public R createQrCode() {
        HashMap map = userService.createQrCode();
        return R.ok(map);
    }

    /**
     * 检测登陆验证码
     *
     * @param form
     * @return
     */
    @PostMapping("/checkQrCode")
    @Operation(summary = "检测登陆验证码")
    public R checkQrCode(@Valid @RequestBody CheckQrCodeForm form) {
        boolean bool = userService.checkQrCode(form.getCode(), form.getUuid());
        return R.ok().put("result", bool);
    }

    @PostMapping("/wechatLogin")
    @Operation(summary = "微信小程序登陆")
    public R wechatLogin(@Valid @RequestBody WechatLoginForm form) {
        HashMap map = userService.wechatLogin(form.getUuid());
        boolean result = (boolean) map.get("result");
        if (result) {
            int userId = (int) map.get("userId");
            StpUtil.setLoginId(userId);
            Set<String> permissions = userService.searchUserPermissions(userId);
            map.remove("userId");
            map.put("permissions", permissions);
        }
        return R.ok(map);
    }

    /**
     * 登陆成功后加载用户的基本信息
     */
    @GetMapping("/loadUserInfo")
    @Operation(summary = "登陆成功后加载用户的基本信息")
    @SaCheckLogin
    public R loadUserInfo() {
        int userId = StpUtil.getLoginIdAsInt();
        HashMap summary = userService.searchUserSummary(userId);
        return R.ok(summary);
    }

    @PostMapping("/searchById")
    @Operation(summary = "根据ID查找用户")
    @SaCheckPermission(value = {"ROOT", "USER:SELECT"}, mode = SaMode.OR)
    public R searchById(@Valid @RequestBody SearchUserByIdForm form) {
        HashMap map = userService.searchById(form.getUserId());
        return R.ok(map);
    }

    @GetMapping("/searchAllUser")
    @Operation(summary = "查询所有用户")
    @SaCheckLogin
    public R searchAllUser() {
        ArrayList<HashMap> list = userService.searchAllUser();
        return R.ok().put("list", list);
    }

    @PostMapping("/login")
    @Operation(summary = "登录系统")
    public R login(@Valid @RequestBody LoginForm loginForm) {
        HashMap param = JSONUtil.parse(loginForm).toBean(HashMap.class);
        Integer userId = userService.login(param);
        R r = R.ok().put("result", userId != null);
        if (userId != null) {
            StpUtil.login(userId);
            Set<String> permissions = userService.searchUserPermissions(userId);
            String token = StpUtil.getTokenInfo().getTokenValue();
            r.put("permissions", permissions).put("token", token);
        }
        return r;
    }

    @GetMapping("/logout")
    @Operation(summary = "退出登录")
    public R logout() {
        StpUtil.logout();
        return R.ok();
    }


    @PostMapping("/updateUserInfo")
    @SaCheckLogin
    @Operation(summary = "修改个人信息")
    public R updateUserInfo(@Valid @RequestBody UpdateUserInfoForm updateUserInfoForm) {
        int userId = StpUtil.getLoginIdAsInt();
        HashMap param = new HashMap() {{
            put("userId", userId);
            put("tel", updateUserInfoForm.getTel());
            put("email", updateUserInfoForm.getEmail());
        }};
        int rows = userService.updateUserInfo(param);
        return R.ok().put("rows", rows);
    }
    
    @PostMapping("/updatePassword")
    @SaCheckLogin
    @Operation(summary = "修改密码")
    public R updatePassword(@Valid @RequestBody UpdatePasswordForm updatePasswordForm) {
        int userId = StpUtil.getLoginIdAsInt();
        HashMap param = new HashMap() {{
            put("userId", userId);
            put("password", updatePasswordForm.getPassword());
            put("newPassword", updatePasswordForm.getNewPassword());
        }};
        if (!userService.passwordVerify(param)) {
            return R.error("原密码输入不正确，请重新输入");
        }
        int rows = userService.updatePassword(param);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/searchUserByPage")
    @Operation(summary = "查询用户分页记录")
    @SaCheckPermission(value = {"ROOT", "USER:SELECT"}, mode = SaMode.OR)
    public R searchUserByPage(@Valid @RequestBody SearchUserByPageForm searchUserByPageForm) {
        int pageIndex = searchUserByPageForm.getPageIndex();
        int pageSize = searchUserByPageForm.getPageSize();
        int start = (pageIndex - 1) * pageSize;
        HashMap param = JSONUtil.parse(searchUserByPageForm).toBean(HashMap.class);
        param.put("start", start);
        PageUtils pageUtils = userService.searchUserByPage(param);
        return R.ok().put("page", pageUtils);
    }

    @PostMapping("/insert")
    @Operation(summary = "新增用户")
    @SaCheckPermission(value = {"ROOT", "USER:INSERT"}, mode = SaMode.OR)
    public R insert(@Valid @RequestBody InsertUserForm insertUserForm) {
        TbUser user = JSONUtil.parse(insertUserForm).toBean(TbUser.class);
        user.setStatus((byte) 1);
        user.setRole(JSONUtil.parseArray(insertUserForm.getRole()).toString());
        user.setCreateTime(new Date());
        int rows = userService.insert(user);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/update")
    @Operation(summary = "修改用户信息")
    @SaCheckPermission(value = {"ROOT", "USER:UPDATE"}, mode = SaMode.OR)
    public R update(@Valid @RequestBody UpdateUserForm updateUserForm) {
        HashMap param = JSONUtil.parse(updateUserForm).toBean(HashMap.class);
        param.replace("role", JSONUtil.parseArray(updateUserForm.getRole()).toString());
        int rows = userService.update(param);
        if (rows == 1) {
            //改完用户信息之后把用户踢下线
            StpUtil.logoutByLoginId(updateUserForm.getUserId());
        }
        return R.ok().put("rows", rows);
    }

    @PostMapping("/deleteUserByIds")
    @Operation(summary = "删除用户信息")
    @SaCheckPermission(value = {"ROOT", "USER:DELETE"}, mode = SaMode.OR)
    public R deleteUserByIds(@Valid @RequestBody DeleteUserByIdsForm deleteUserByIdsForm) {
        Integer userId = StpUtil.getLoginIdAsInt();
        if (ArrayUtil.contains(deleteUserByIdsForm.getIds(), userId)) {
            return R.error("不能删除自己的账户");
        }
        int rows = userService.deleteUserByIds(deleteUserByIdsForm.getIds());
        if (rows > 1) {
            for (Integer id : deleteUserByIdsForm.getIds()) {
                //删除用户信息之后把用户踢下线
                StpUtil.logoutByLoginId(id);
            }
        }
        return R.ok().put("rows", rows);
    }


    @PostMapping("/searchNameAndDept")
    @Operation(summary = "查找员工姓名和部门")
    @SaCheckLogin
    public R searchNameAndDept(@Valid @RequestBody SearchNameAndDeptForm form) {
        HashMap map = userService.searchNameAndDept(form.getId());
        return R.ok(map);
    }


}
