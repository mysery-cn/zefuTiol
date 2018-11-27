package com.zefu.tiol.vo;

import java.util.Date;
import java.util.List;

/**
 * 会议
 * 
 * @author long
 *
 */
public class TiolMeetingVO {
	private String sno;// 序号
	private String name;// 会议名称
	private String type;// 会议类型
	private Date time;// 会议时间
	private String moderator;// 主持人
	private List<TiolAttendeeVO> attendeeList; // 出席者
	private List<TiolSubjectVO> subjectList;
	private String attendeeStr;// 列席人员字符串

	private int row;// 行数

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getModerator() {
		return moderator;
	}

	public void setModerator(String moderator) {
		this.moderator = moderator;
	}

	public List<TiolAttendeeVO> getAttendeeList() {
		return attendeeList;
	}

	public void setAttendeeList(List<TiolAttendeeVO> attendeeList) {
		this.attendeeList = attendeeList;
	}

	public List<TiolSubjectVO> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<TiolSubjectVO> subjectList) {
		this.subjectList = subjectList;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	// 列序人员字符串拼接
	public String getAttendeeStr() {
		if (null == attendeeStr || "".equals(attendeeStr)) {
			StringBuffer sb = new StringBuffer();
			if (null != attendeeList) {
				for (TiolAttendeeVO vo : attendeeList) {
					String name = vo.getName();
					String reason = vo.getReason();
					if (null == name || "".equals(name))
						continue;
					if (sb.length() > 0) {
						sb.append("、");
					}
					sb.append(name);
					if (null == reason || "".equals(reason))
						reason = "";
					if ("否" == vo.getAttendFlag()) {
						sb.append("（");
						sb.append(reason);
						sb.append("）");
					}
				}
			}
			attendeeStr = sb.toString();
		}
		return attendeeStr;
	}

}
