package com.example.emos.api.service;

import com.example.emos.api.common.util.PageUtils;
import com.example.emos.api.db.pojo.TbReim;

import java.util.HashMap;

public interface ReimService {
    public PageUtils searchReimByPage(HashMap param);


    public int insert(TbReim reim);


    public HashMap searchReimById(HashMap param);


    public int deleteReimById(HashMap param);


}
