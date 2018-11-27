package com.zefu.tiol.vo;

public class AnlizedResult {
	private boolean success;// 解析是否成功
	private String errorInfo;// 错误信息
	private Object data;// 数据
	private TemplateType type;// 模板类型

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public TemplateType getType() {
		return type;
	}

	public void setType(TemplateType type) {
		this.type = type;
	}

}
