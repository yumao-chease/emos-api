package com.example.emos.api.service;

import com.example.emos.api.common.util.PageUtils;
import com.example.emos.api.db.pojo.TbLeave;

import java.util.HashMap;

public interface LeaveService {
    public PageUtils searchLeaveByPage(HashMap param);

    public boolean searchContradiction(HashMap param);

    public int insert(TbLeave leave);

    public int deleteLeaveById(HashMap param);


    public HashMap searchLeaveById(HashMap param);


}
