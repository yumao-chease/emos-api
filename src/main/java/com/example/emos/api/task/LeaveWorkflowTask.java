package com.example.emos.api.task;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.emos.api.db.dao.TbLeaveDao;
import com.example.emos.api.db.dao.TbUserDao;
import com.example.emos.api.exception.EmosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class LeaveWorkflowTask {
    @Value("${emos.code}")
    private String code;

    @Value("${emos.tcode}")
    private String tcode;

    @Value("${workflow.url}")
    private String workflow;

    @Value("${emos.recieveNotify}")
    private String recieveNotify;

    @Autowired
    private TbUserDao userDao;

    @Autowired
    private TbLeaveDao leaveDao;

    @Async("AsyncTaskExecutor")
    public void startLeaveWorkflow(int id, int creatorId, String days) {
        HashMap info = userDao.searchUserInfo(creatorId);
        JSONObject json = new JSONObject();
        json.set("url", recieveNotify);
        json.set("creatorId", creatorId);
        json.set("creatorName", info.get("name").toString());
        json.set("code", code);
        json.set("tcode", tcode);
        json.set("title", info.get("dept").toString() + info.get("name").toString() + "的请假");
        Integer managerId = userDao.searchDeptManagerId(creatorId);
        json.set("managerId", managerId);
        Integer gmId = userDao.searchGmId();
        json.set("gmId", gmId);
        json.set("days", Double.parseDouble(days));

        String url = workflow + "/workflow/startLeaveProcess";
        HttpResponse resp = HttpRequest.post(url).header("Content-Type", "application/json")
                .body(json.toString()).execute();
        if (resp.getStatus() == 200) {
            json = JSONUtil.parseObj(resp.body());
            String instanceId = json.getStr("instanceId");
            HashMap param = new HashMap();
            param.put("id", id);
            param.put("instanceId", instanceId);
            int row = leaveDao.updateLeaveInstanceId(param);
            if (row != 1) {
                throw new EmosException("保存请假工作流实例ID失败");
            }
        } else {
            log.error(resp.body());
        }
    }

    @Async("AsyncTaskExecutor")
    public void deleteLeaveWorkflow(String instanceId, String type, String reason) {
        JSONObject json = new JSONObject();
        json.set("instanceId", instanceId);
        json.set("type", type);
        json.set("reason", reason);
        json.set("code", code);
        json.set("tcode", tcode);
        String url = workflow + "/workflow/deleteProcessById";
        HttpResponse resp = HttpRequest.post(url).header("Content-Type", "application/json")
                .body(json.toString()).execute();
        if (resp.getStatus() != 200) {
            log.error(resp.body());
            throw new EmosException("请假工作流删除失败");
        }
    }


}


