package com.example.emos.api.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.example.emos.api.common.util.PageUtils;
import com.example.emos.api.db.dao.TbReimDao;
import com.example.emos.api.db.pojo.TbReim;
import com.example.emos.api.exception.EmosException;
import com.example.emos.api.service.ReimService;
import com.example.emos.api.task.ReimWorkflowTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
@Slf4j
public class ReimServiceImpl implements ReimService {
    @Autowired
    private TbReimDao reimDao;

    @Autowired
    private ReimWorkflowTask reimWorkflowTask;


    @Override
    public PageUtils searchReimByPage(HashMap param) {
        ArrayList<HashMap> list = reimDao.searchReimByPage(param);
        long count = reimDao.searchReimCount(param);
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list, count, start, length);

        return pageUtils;
    }


    @Override
    public int insert(TbReim reim) {
        int rows = reimDao.insert(reim);
        //开启工作流
        if (rows == 1) {
            reimWorkflowTask.startReimWorkflow(reim.getId(), reim.getUserId());
        } else {
            throw new EmosException("报销申请保存失败");
        }
        return rows;
    }


    @Override
    public HashMap searchReimById(HashMap param) {
        HashMap map = reimDao.searchReimById(param);
        String instanceId = MapUtil.getStr(map, "instanceId");
        //把支付订单的URL生成二维码
        QrConfig qrConfig = new QrConfig();
        qrConfig.setWidth(70);
        qrConfig.setHeight(70);
        qrConfig.setMargin(2);
        String qrCodeBase64 = QrCodeUtil.generateAsBase64(instanceId, qrConfig, "jpg");
        map.put("qrCodeBase64", qrCodeBase64);
        return map;
    }

    @Override
    public int deleteReimById(HashMap param) {
        int id = MapUtil.getInt(param, "id");
        String instanceId = reimDao.searchInstanceIdById(id);
        int rows = reimDao.deleteReimById(param);
        if (rows == 1) {
            //关闭工作流
            reimWorkflowTask.deleteReimWorkflow(instanceId, "报销申请", "删除报销申请");
        }
        return rows;
    }


}

