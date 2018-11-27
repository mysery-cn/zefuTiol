package com.zefu.tiol.pojo;

/**
   * @工程名 : szyd
   * @文件名 : SubjectItem.java
   * @工具包名：com.zefu.tiol.pojo
   * @功能描述: TODO 议题关联事项实体类
   * @创建人 ：林鹏
   * @创建时间：2018年11月13日 下午1:56:37
   * @版本信息：V1.0
 */
public class SubjectItem extends BaseEntity {

	private static final long serialVersionUID = 1L;


	// 自定义字段
	private String subjectId; // 议题ID
	private String relevanceId;// 会议ID
	private String itemId;// 事项ID
	
	//Excle导入字段
	private String itemCode;//事项编码

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getRelevanceId() {
		return relevanceId;
	}

	public void setRelevanceId(String relevanceId) {
		this.relevanceId = relevanceId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
    
	
	

}
