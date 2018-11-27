package com.zefu.tiol.constant;

/**
 * 
 * @title: EBusinessType.java
 * @description: 三重一大业务类型枚举类
 * @author lxc
 * @date: 2018年11月4日
 */
public enum EBusinessType {
	
	ITEM("0004","事项清单","item"),
	
	REGULATION("0005","有关制度","regulation"),
	
	MEETING("0006","会议决策","meeting"),
	
	STATICRES("0002","静态资源","staticres");
	
	private String code;
	
	private String name;
	
	private String fileName;
	
	private EBusinessType() {
	}
	private EBusinessType(String code, String name,String fileName) {
		this.code = code;
		this.name = name;
		this.fileName=fileName;
	}


	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
