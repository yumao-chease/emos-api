package com.example.emos.api.service;

import com.example.emos.api.common.util.PageUtils;
import com.example.emos.api.db.pojo.TbRole;

import java.util.ArrayList;
import java.util.HashMap;

public interface RoleService {
    public ArrayList<HashMap> searchAllRole();

    public HashMap searchById(int id);

    public PageUtils searchRoleByPage(HashMap param);


    public int insert(TbRole role);


    public ArrayList<Integer> searchUserIdByRoleId(int roleId);

    public int update(TbRole role);

    public int deleteRoleByIds(Integer[] ids);


}
