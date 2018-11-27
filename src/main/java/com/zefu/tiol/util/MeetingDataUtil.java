package com.zefu.tiol.util;

import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.common.util.StringUtils;
import com.zefu.tiol.constant.CollectDataConstant;
import com.zefu.tiol.constant.StatisticsConstant;
import com.zefu.tiol.pojo.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetingDataUtil {

    /**
     * 根据前台传入信息议题实体
     * @param parameter
     * @return
     * @by-李缝兴
     */
    public static Subject getSubjectFromParam(Map<String,Object> parameter, String subjectId){
        Subject subject = new Subject();
        subject.setFid(subjectId);
        subject.setcModuleid(CollectDataConstant.MEETING_MODULE_ID);
        subject.setcModulename(CollectDataConstant.MEETING_MODULE_NAME);
        subject.setcBizid(CollectDataConstant.MEETING_BIZ_ID);
        subject.setcBizname(CollectDataConstant.MEETING_BIZ_NAME);
        subject.setcCreaterid(CollectDataConstant.CREATER_ID);
        subject.setcCreatername(CollectDataConstant.CREATER_NAME);
        subject.setcCreatedeptid(CollectDataConstant.CREATE_DEPT_ID);
        subject.setcCreatedeptname(CollectDataConstant.CREATE_DEPT_NAME);
        subject.setcCompanyid(parameter.get("companyId")+"");
        subject.setStatus("1");

        subject.setSubjectId(subjectId);
        subject.setSubjectName(parameter.get("subjectName") == null ? null : parameter.get("subjectName").toString());
        subject.setMeetingId(parameter.get("meetingId") == null ? null : parameter.get("meetingId").toString());
        subject.setSubjectCode(parameter.get("subjectCode") == null ? null : parameter.get("subjectCode").toString());
        subject.setSourceId(parameter.get("sourceId") == null ? null : parameter.get("sourceId").toString());
        subject.setSpecialId(parameter.get("specialId") == null ? null : parameter.get("specialId").toString());
        subject.setPassFlag(parameter.get("passFlag") == null ? null : parameter.get("passFlag").toString());
        subject.setOpinionFileId(parameter.get("opinionFileId") == null ? null : parameter.get("opinionFileId").toString());
        subject.setSubjectFileId(parameter.get("subjectFile") == null ? null : parameter.get("subjectFile").toString());
        subject.setAdoptFlag(parameter.get("adoptFlag") == null ? null : parameter.get("adoptFlag").toString());
        subject.setApprovalFlag(parameter.get("approvalFlag") == null ? null : parameter.get("approvalFlag").toString());
        subject.setSubjectResult(parameter.get("subjectResult") == null ? null : parameter.get("subjectResult").toString());
        subject.setRemark(parameter.get("remark") == null ? null : parameter.get("remark").toString());
        return subject;
    }
    /**
     * 根据前台传入信息获取关联会议议题
     * @param parameter
     * @param subjectId
     * @return
     * @by-李缝兴
     */
    public static List<Map<String,Object>> getItemRelevance(Map<String, Object> parameter, String subjectId){
        String itemIds = parameter.get("itemIds")+"";
        if(parameter.get("itemIds") == null || "".equals(itemIds)) return null;
        List<Map<String,Object>> itemRelevanceList = new ArrayList<Map<String,Object>>();
        String[] items = itemIds.split(",");
        for (int i = 0; i < items.length; i++) {
            Map<String,Object> itemRelevance = new HashMap<String,Object>();
            itemRelevance.put("relevanceId",CommonUtil.getUUID());
            itemRelevance.put("subjectId",subjectId);
            itemRelevance.put("itemId",items[i]);
            itemRelevanceList.add(itemRelevance);
        }
        return itemRelevanceList;
    }

    /**
     * 根据前台传入信息获取关联会议议题
     * @param parameter
     * @param subjectId
     * @return
     * @by-李缝兴
     */
    public static List<Map<String,Object>> getSubjectRelevance(Map<String, Object> parameter, String subjectId){
        String relMeetingId = parameter.get("relMeetingId")+"";//关联会议ID
        String relMeetingName = parameter.get("relMeetingName")+"";//关联会议名称
        String relSubjectId = parameter.get("relSubjectId")+"";//关联议题ID
        String relSubjectName = parameter.get("relSubjectName")+"";//关联议题名称
        if(parameter.get("relMeetingId") == null || "".equals(relMeetingId)) return null;
        List<Map<String,Object>> subjectRelevanceList = new ArrayList<Map<String,Object>>();
        String[] relMeetingIds = relMeetingId.split(",");
        String[] relMeetingNames = relMeetingName.split(",");
        String[] relSubjectIds = relSubjectId.split(",");
        String[] relSubjectNames = relSubjectName.split(",");
        for (int i = 0; i < relMeetingIds.length; i++) {
            if("".equals(relMeetingIds[i])) continue;
            Map<String,Object> subjectRelevance = new HashMap<String,Object>();
            subjectRelevance.put("relevanceId",CommonUtil.getUUID());
            subjectRelevance.put("subjectId",subjectId);
            subjectRelevance.put("relMeetingId",relMeetingIds[i]);
            subjectRelevance.put("relMeetingName",relMeetingNames[i]);
            subjectRelevance.put("relSubjectId",relSubjectIds[i]);
            subjectRelevance.put("relSubjectName",relSubjectNames[i]);
            subjectRelevanceList.add(subjectRelevance);
        }
        return subjectRelevanceList;
    }
    /**
     * 根据前台传入信息获取审议结果
     * @param deliberations
     * @param meetingId
     * @param subjectId
     * @return
     * @by-李缝兴
     */
    public static List<Deliberation> getDeliberationList(String deliberations, String meetingId, String subjectId){
        if("".equals(deliberations) || "null".equals(deliberations)) return null;
        List<Deliberation> deliberationList = new ArrayList<Deliberation>();
        deliberations = deliberations.replaceAll("，",",").replaceAll("、",",").replaceAll("（","(").replaceAll("）",")");
        String[] results = deliberations.split(",");
        for (int i = 0; i < results.length; i++) {
            if("".equals(results[i])) continue;
            String name = null;
            String result = null;
            if(results[i].indexOf("(") > -1){
                name = results[i].split("[(]")[0];
                result = results[i].split("[(]")[1].trim();
                result = result.substring(0,result.length()-1);
            }else{
                name = results[i];
            }
            Deliberation deliberation = new Deliberation();
            deliberation.setDeliberationId(CommonUtil.getUUID());
            deliberation.setMeetingId(meetingId);
            deliberation.setSubjectId(subjectId);
            deliberation.setDeliberationPersonnel(name);
            deliberation.setDeliberationResult(result);
            deliberationList.add(deliberation);
        }
        return deliberationList;
    }

    /**
     * 根据前台传入信息获取列席人员
     * @param userString
     * @param meetingId
     * @param subjectId
     * @return
     * @by-李缝兴
     */
    public static List<Attendance> getAttendanceList(String userString, String meetingId, String subjectId){
        if("".equals(userString) || "null".equals(userString)) return null;
        List<Attendance> attendanceList = new ArrayList<Attendance>();
        //过滤干扰符号
        userString = userString.replaceAll("，",",").replaceAll("、",",").replaceAll("（","(").replaceAll("）",")");
        String[] users = userString.split(",");
        for (int i = 0; i < users.length; i++) {
            if("".equals(users[i])) continue;
            String userName = null;
            String userRole = null;
            if(users[i].indexOf("(") > -1){
                userName = users[i].split("[(]")[0];
                userRole = users[i].split("[(]")[1].trim();
                userRole = userRole.substring(0,userRole.length()-1);
            }else{
                userName = users[i];
            }
            Attendance attendance = new Attendance();
            attendance.setAttendanceId(CommonUtil.getUUID());
            attendance.setMeetingId(meetingId);
            attendance.setSubjectId(subjectId);
            attendance.setAttendanceName(userName);
            if(userRole != null){
                attendance.setPosition(userRole);
                //匹配经济师
                if(userRole.indexOf(StatisticsConstant.COUNSEL_TYPE_ECONOMIC) > -1) attendance.setCounselType(StatisticsConstant.COUNSEL_TYPE_ECONOMIC);
                //匹配法律顾问
                if(userRole.indexOf(StatisticsConstant.COUNSEL_TYPE_LEGAL) > -1) attendance.setCounselType(StatisticsConstant.COUNSEL_TYPE_LEGAL);
            }
            attendanceList.add(attendance);
        }
        return attendanceList;
    }

    /**
     * 获取参会人员信息
     * @param parameter
     * @param meetingId
     */
    public static List<Attendee> getAttendeeList(Map<String, Object> parameter,String meetingId){
        //参会人员解析
        List<Attendee> attendeeList = new ArrayList<Attendee>();
        if(null != parameter.get("attendeeMember") && !"".equals(parameter.get("attendeeMember")+"")){
            String attendeeMember = parameter.get("attendeeMember").toString().replaceAll("）",")").replaceAll("、",",").replaceAll("，",",");
            String[] users = attendeeMember.split(",");
            for (int i = 0; i < users.length; i++) {
                String userName = users[i];
                if(StringUtils.isBlank(userName)) continue;
                String reason = null;
                //存在括号，该人员未参会
                if(userName.indexOf(")") > -1) {
                    reason = userName.split("[(]")[1];
                    userName = userName.split("[(]")[0];
                    reason = reason.substring(0 ,reason.length()-1);
                }
                Attendee attendee = new Attendee();
                attendee.setAttendeeId(CommonUtil.getUUID());
                attendee.setMeetingId(meetingId);
                attendee.setAttendeeName(userName);
                attendee.setAttendFlag("1");
                if(reason != null) {
                    attendee.setAttendFlag("0");
                    attendee.setReason(reason);
                }
                attendeeList.add(attendee);
            }
        }
        return attendeeList;
    }

    /**
     * 根据MAP参数获取会议实体
     * @param parameter
     * @param meetingId
     */
    public static Meeting mapToMeeting(Map<String, Object> parameter,String meetingId){
        Meeting meeting = new Meeting();
        meeting.setFid(meetingId);
        meeting.setcModuleid(CollectDataConstant.MEETING_MODULE_ID);
        meeting.setcModulename(CollectDataConstant.MEETING_MODULE_NAME);
        meeting.setcBizid(CollectDataConstant.MEETING_BIZ_ID);
        meeting.setcBizname(CollectDataConstant.MEETING_BIZ_NAME);
        meeting.setcCreaterid(CollectDataConstant.CREATER_ID);
        meeting.setcCreatername(CollectDataConstant.CREATER_NAME);
        meeting.setcCreatedeptid(CollectDataConstant.CREATE_DEPT_ID);
        meeting.setcCreatedeptname(CollectDataConstant.CREATE_DEPT_NAME);
        meeting.setcCompanyid(parameter.get("companyId")+"");
        meeting.setStatus("1");

        meeting.setMeetingId(meetingId);
        meeting.setMeetingName(parameter.get("meetingName") == null ? null : parameter.get("meetingName").toString());
        meeting.setTypeId(parameter.get("meetingType") == null ? null : parameter.get("meetingType").toString());
        meeting.setMeetingTime(parameter.get("meetingTime") == null ? null : parameter.get("meetingTime").toString());
        meeting.setCompanyId(parameter.get("companyId") == null ? null : parameter.get("companyId").toString());
        meeting.setModerator(parameter.get("moderator") == null ? null : parameter.get("moderator").toString());
        meeting.setSummaryFileId(parameter.get("summaryFileId") == null ? null : parameter.get("summaryFileId").toString());
        meeting.setNoticeFileId(parameter.get("noticeFileId") == null ? null : parameter.get("noticeFileId").toString());
        meeting.setAlias(parameter.get("typeName") == null ? null : parameter.get("typeName").toString());
        meeting.setRegisterType(parameter.get("registerType") == null ? null : parameter.get("registerType").toString());
        meeting.setUploadStatus(parameter.get("uploadStatus") == null ? null : parameter.get("uploadStatus").toString());
        return meeting;
    }

    /**
     * 去除字符串存在分行、单双引号，传到前端用el表达式会有问题
     * @param s
     * @return
     */
    public static String formatStr(String s){
        if(s!=null && s.length()>0){
            s = s.replaceAll("(\r|\n|\r\n|\n\r)", " ");
            s = s.replaceAll("\"","\\\\"+"\"");
            s = s.replaceAll("\'","\\\\"+"\'");
            return s;
        }else{
            return "";
        }
    }

}
