package com.example.emos.api.db.dao;

import com.example.emos.api.db.pojo.TbDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface TbDeptDao {
    public ArrayList<HashMap> searchAllDept();

    public HashMap searchById(int id);

    public ArrayList<HashMap> searchDeptByPage(HashMap param);

    public long searchDeptCount(HashMap param);

    public int insert(TbDept dept);

    public int update(TbDept dept);


    public boolean searchCanDelete(Integer[] ids);

    public int deleteDeptByIds(Integer[] ids);


}