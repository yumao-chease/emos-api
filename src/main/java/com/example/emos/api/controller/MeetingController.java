package com.example.emos.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateRange;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.emos.api.common.util.PageUtils;
import com.example.emos.api.common.util.R;
import com.example.emos.api.config.tencent.TrtcUtil;
import com.example.emos.api.controller.form.*;
import com.example.emos.api.db.pojo.TbMeeting;
import com.example.emos.api.service.MeetingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/meeting")
@Tag(name = "MeetingController", description = "会议接口")
@Slf4j
public class MeetingController {

    @Value("${tencent.trtc.appId}")
    private int appId;

    @Autowired
    private TrtcUtil trtcUtil;

    @Autowired
    private MeetingService meetingService;

    @PostMapping("/searchOfflineMeetingByPage")
    @Operation(summary = "查询线下会议的分页数据")
    @SaCheckLogin
    public R searchOfflineMeetingByPage(@Valid @RequestBody SearchOfflineMeetingByPageForm form) {
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        HashMap param = new HashMap() {{
            put("date", form.getDate());
            put("mold", form.getMold());
            put("userId", StpUtil.getLoginId());
            put("start", start);
            put("length", length);
        }};
        PageUtils pageUtils = meetingService.searchOfflineMeetingByPage(param);
        return R.ok().put("page", pageUtils);
    }

    @PostMapping("/insert")
    @Operation(summary = "添加会议")
    @SaCheckLogin
    public R insert(@Valid @RequestBody InsertMeetingForm form) {
        DateTime start = DateUtil.parse(form.getDate() + " " + form.getStart());
        DateTime end = DateUtil.parse(form.getDate() + " " + form.getEnd());
        if (start.isAfterOrEquals(end)) {
            return R.error("结束时间必须大于开始时间");
        } else if (new DateTime().isAfterOrEquals(start)) {
            return R.error("会议开始时间不能早于当前时间");
        }
        TbMeeting meeting = JSONUtil.parse(form).toBean(TbMeeting.class);
        meeting.setUuid(UUID.randomUUID().toString(true));
        meeting.setCreatorId(StpUtil.getLoginIdAsInt());
        meeting.setStatus((short) 1);
        int rows = meetingService.insert(meeting);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/recieveNotify")
    @Operation(summary = "接收工作流通知")
    public R recieveNotify(@Valid @RequestBody RecieveNotifyForm form) {
        if (form.getResult().equals("同意")) {
            log.debug(form.getUuid() + "的会议审批通过");
        } else {
            log.debug(form.getUuid() + "的会议审批不通过");
        }
        return R.ok();
    }

    @PostMapping("/searchOfflineMeetingInWeek")
    @Operation(summary = "查询某个会议室一周的会议")
    @SaCheckLogin
    public R searchOfflineMeetingInWeek(@Valid @RequestBody SearchOfflineMeetingInWeekForm form) {
        String date = form.getDate();
        DateTime startDate, endDate;
        if (date != null && date.length() > 0) {
            startDate=DateUtil.parseDate(date);
            endDate=startDate.offsetNew(DateField.DAY_OF_WEEK,6);
        }else{
            startDate=DateUtil.beginOfWeek(new Date());
            endDate=DateUtil.endOfWeek(new Date());
        }
        HashMap param = new HashMap() {{
            put("place", form.getName());
            put("startDate", startDate.toDateStr());
            put("endDate", endDate.toDateStr());
            put("mold", form.getMold());
            put("userId", StpUtil.getLoginIdAsLong());
        }};
        ArrayList<HashMap> list=meetingService.searchOfflineMeetingInWeek(param);
        ArrayList days=new ArrayList();
        DateRange range=DateUtil.range(startDate,endDate,DateField.DAY_OF_WEEK);
        range.forEach(one->{
            JSONObject json=new JSONObject();
            json.set("date",one.toString("MM/dd"));
            json.set("day",one.dayOfWeekEnum().toChinese("周"));
            days.add(json);
        });
        return R.ok().put("list",list).put("days",days);
    }

    @PostMapping("/searchMeetingInfo")
    @Operation(summary = "查询会议信息")
    @SaCheckLogin
    public R searchMeetingInfo(@Valid @RequestBody SearchMeetingInfoForm form){
        HashMap map=meetingService.searchMeetingInfo(form.getStatus(),form.getId());
        return R.ok(map);
    }

    @PostMapping("/deleteMeetingApplication")
    @Operation(summary = "删除会议申请")
    @SaCheckLogin
    public R deleteMeetingApplication(@Valid @RequestBody DeleteMeetingApplicationForm form){
        HashMap param=JSONUtil.parse(form).toBean(HashMap.class);
        param.put("creatorId",StpUtil.getLoginIdAsLong());
        param.put("userId",StpUtil.getLoginIdAsLong());
        int rows=meetingService.deleteMeetingApplication(param);
        return R.ok().put("rows",rows);
    }

    @PostMapping("/searchOnlineMeetingByPage")
    @Operation(summary = "查询线上会议分页数据")
    @SaCheckLogin
    public R searchOnlineMeetingByPage(@Valid @RequestBody SearchOnlineMeetingByPageForm form) {
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        HashMap param = new HashMap() {{
            put("date", form.getDate());
            put("mold", form.getMold());
            put("userId", StpUtil.getLoginId());
            put("start", start);
            put("length", length);
        }};
        PageUtils pageUtils = meetingService.searchOnlineMeetingByPage(param);
        return R.ok().put("page", pageUtils);
    }

    @GetMapping("/searchMyUserSig")
    @Operation(summary = "获取用户签名")
    @SaCheckLogin
    public R searchMyUserSig(){
        int userId=StpUtil.getLoginIdAsInt();
        String userSig=trtcUtil.genUserSig(userId+"");
        return R.ok().put("userSig",userSig).put("userId",userId).put("appId",appId);
    }

    @PostMapping("/searchRoomIdByUUID")
    @Operation(summary = "查询视频会议室RoomId")
    @SaCheckLogin
    public R searchRoomIdByUUID(@Valid @RequestBody SearchRoomIdByUUIDForm form){
        Long roomId=meetingService.searchRoomIdByUUID(form.getUuid());
        return R.ok().put("roomId",roomId);
    }

    @PostMapping("/searchOnlineMeetingMembers")
    @Operation(summary = "查询线下会议成员")
    @SaCheckLogin
    public R searchOnlineMeetingMembers(@Valid @RequestBody SearchOnlineMeetingMembersForm form){
        HashMap param=JSONUtil.parse(form).toBean(HashMap.class);
        param.put("userId",StpUtil.getLoginIdAsInt());
        ArrayList<HashMap> list=meetingService.searchOnlineMeetingMembers(param);
        return R.ok().put("list",list);
    }

    @PostMapping("/updateMeetingPresent")
    @Operation(summary = "执行会议签到")
    @SaCheckLogin
    public R updateMeetingPresent(@Valid @RequestBody UpdateMeetingPresentForm form){
        HashMap param=new HashMap(){{
            put("meetingId",form.getMeetingId());
            put("userId",StpUtil.getLoginIdAsInt());
        }};
        boolean bool=meetingService.searchCanCheckinMeeting(param);
        if(bool){
            int rows=meetingService.updateMeetingPresent(param);
            return R.ok().put("rows",rows);
        }
        return R.ok().put("rows",0);
    }
}
