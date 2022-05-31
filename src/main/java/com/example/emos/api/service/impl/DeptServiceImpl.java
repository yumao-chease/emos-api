package com.example.emos.api.service.impl;

import com.example.emos.api.common.util.PageUtils;
import com.example.emos.api.db.dao.TbDeptDao;
import com.example.emos.api.db.pojo.TbDept;
import com.example.emos.api.exception.EmosException;
import com.example.emos.api.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private TbDeptDao deptDao;

    @Override
    public ArrayList<HashMap> searchAllDept() {
        ArrayList<HashMap> list = deptDao.searchAllDept();
        return list;
    }

    @Override
    public HashMap searchById(int id) {
        HashMap map = deptDao.searchById(id);
        return map;
    }

    @Override
    public PageUtils searchDeptByPage(HashMap param) {
        ArrayList<HashMap> list = deptDao.searchDeptByPage(param);
        long count = deptDao.searchDeptCount(param);
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list, count, start, length);

        return pageUtils;
    }

    @Override
    public int insert(TbDept dept) {
        int rows = deptDao.insert(dept);
        return rows;
    }

    @Override
    public int update(TbDept dept) {
        int rows = deptDao.update(dept);
        return rows;
    }

    @Override
    public int deleteDeptByIds(Integer[] ids) {
        if (!deptDao.searchCanDelete(ids)) {
            throw new EmosException("无法删除关联用户的部门");
        }
        int rows = deptDao.deleteDeptByIds(ids);
        return rows;
    }


}
