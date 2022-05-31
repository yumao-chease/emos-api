package com.example.emos.api.db.dao;

import com.example.emos.api.db.pojo.TbRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;


@Mapper
public interface TbRoleDao {
    public ArrayList<HashMap> searchAllRole();

    public HashMap searchById(int id);

    public ArrayList<HashMap> searchRoleByPage(HashMap param);

    public long searchRoleCount(HashMap param);

    public int insert(TbRole role);


    public ArrayList<Integer> searchUserIdByRoleId(int roleId);

    public int update(TbRole role);

    public boolean searchCanDelete(Integer[] ids);

    public int deleteRoleByIds(Integer[] ids);


}