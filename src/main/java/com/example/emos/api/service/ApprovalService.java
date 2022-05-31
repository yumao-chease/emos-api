package com.example.emos.api.service;

import com.example.emos.api.common.util.PageUtils;

import java.util.HashMap;

public interface ApprovalService {
    public PageUtils searchTaskByPage(HashMap param);


    public HashMap searchApprovalContent(HashMap param);


    public void approvalTask(HashMap param);


    public void archiveTask(HashMap param);


}
