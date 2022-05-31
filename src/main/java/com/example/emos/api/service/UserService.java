package com.example.emos.api.service;

import cn.hutool.db.Page;
import com.example.emos.api.common.util.PageUtils;
import com.example.emos.api.db.pojo.TbUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public interface UserService {
    public HashMap createQrCode();

    public boolean checkQrCode(String code, String uuid);

    public HashMap wechatLogin(String uuid);

    public Set<String> searchUserPermissions(int userId);

    public HashMap searchUserSummary(int userId);

    public HashMap searchById(int userId);

    public ArrayList<HashMap> searchAllUser();

    public Integer login(HashMap param);

    public int updateUserInfo(HashMap param);

    public int updatePassword(HashMap param);

    public Boolean passwordVerify(HashMap param);

    public PageUtils searchUserByPage(HashMap param);

    public int insert(TbUser tbUser);

    public int update(HashMap param);

    public int deleteUserByIds(Integer[] ids);


    public ArrayList<String> searchUserRoles(int userId);


    public HashMap searchNameAndDept(int userId);


}
