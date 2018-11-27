package com.zefu.tiol.vo;

import java.util.List;

/**
 * 议题
 * 
 * @author long
 *
 */
public class TiolSubjectVO {
	private String name;// 议题名称
	private String sourceName;// 任务来源
	private String specialName;// 专项名称
	private String itemCode;// 事项编码
	private String relMeetingName;// 关联会议名称
	private String relSubjectName;// 关联议题名称
	private List<TiolAttendanceVO> attendanceList;// 列席人数组
	private List<TiolDeliberationVO> deliberationList;// 审议结果
	private String passFlag;// 是否通过
	private String approvalFlag;// 是否需报国资委审批
	private String adoptFlag;// 是否听取意见
	private String subjectResult;// 议题决议
	private String remark;// 备注

	private String attendanceStr;// 列席人字符串
	private String deliberationStr;// 审议结果字符串

	private int row;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getRelMeetingName() {
		return relMeetingName;
	}

	public void setRelMeetingName(String relMeetingName) {
		this.relMeetingName = relMeetingName;
	}

	public String getRelSubjectName() {
		return relSubjectName;
	}

	public void setRelSubjectName(String relSubjectName) {
		this.relSubjectName = relSubjectName;
	}

	public List<TiolAttendanceVO> getAttendanceList() {
		return attendanceList;
	}

	public void setAttendanceList(List<TiolAttendanceVO> attendanceList) {
		this.attendanceList = attendanceList;
	}

	public List<TiolDeliberationVO> getDeliberationList() {
		return deliberationList;
	}

	public void setDeliberationList(List<TiolDeliberationVO> deliberationList) {
		this.deliberationList = deliberationList;
	}

	public String getPassFlag() {
		return passFlag;
	}

	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}

	public String getApprovalFlag() {
		return approvalFlag;
	}

	public void setApprovalFlag(String approvalFlag) {
		this.approvalFlag = approvalFlag;
	}

	public String getAdoptFlag() {
		return adoptFlag;
	}

	public void setAdoptFlag(String adoptFlag) {
		this.adoptFlag = adoptFlag;
	}

	public String getSubjectResult() {
		return subjectResult;
	}

	public void setSubjectResult(String subjectResult) {
		this.subjectResult = subjectResult;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getAttendanceStr() {
		StringBuffer sb = new StringBuffer();
		if (null != attendanceList) {
			for (TiolAttendanceVO vo : attendanceList) {
				String name = vo.getName();
				String position = vo.getPosition();
				System.out.println("name: " + name);
				if (null == name || "".equals(name))
					continue;
				if (null == position || "".equals(position))
					continue;
				if (sb.length() > 0) {
					sb.append("、");
				}
				sb.append(name);
				sb.append("（");
				sb.append(position);
				sb.append("）");
			}
		}
		attendanceStr = sb.toString();

		return attendanceStr;
	}

	public void setAttendanceStr(String attendanceStr) {
		this.attendanceStr = attendanceStr;
	}

	public String getDeliberationStr() {
		StringBuffer sb = new StringBuffer();
		if (null != deliberationList) {
			for (TiolDeliberationVO vo : deliberationList) {
				String name = vo.getPersonnel();
				if (null == name || "".equals(name))
					continue;
				if (sb.length() > 0) {
					sb.append("、");
				}
				sb.append(name);
				sb.append("（");
				sb.append(vo.getResult());
				sb.append("）");
			}
		}
		deliberationStr = sb.toString();

		return deliberationStr;
	}

	public void setDeliberationStr(String deliberationStr) {
		this.deliberationStr = deliberationStr;
	}

}
