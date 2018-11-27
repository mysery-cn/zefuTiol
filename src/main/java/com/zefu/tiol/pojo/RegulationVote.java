package com.zefu.tiol.pojo;

/**
 * 事项实体类
 * 
 * @author linch
 * @date 2018年10月26日
 */
public class RegulationVote extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// 自定义字段
	private String voteId; // 制度表决ID
	private String regulationId;// 制度ID
	private String itemId;// 事项ID
	private String modeId;// 表决方式ID
	private String remark;// 表决方式ID

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getVoteId() {
		return voteId;
	}

	public void setVoteId(String voteId) {
		this.voteId = voteId;
	}

	public String getRegulationId() {
		return regulationId;
	}

	public void setRegulationId(String regulationId) {
		this.regulationId = regulationId;
	}

	public String getModeId() {
		return modeId;
	}

	public void setModeId(String modeId) {
		this.modeId = modeId;
	}
}
