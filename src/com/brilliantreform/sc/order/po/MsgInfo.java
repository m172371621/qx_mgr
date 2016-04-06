package com.brilliantreform.sc.order.po;

public class MsgInfo {

	private int msg_info_id;//短信ID
	private String msg_template_code;//模板编码
	private String msg_info_phone;//微信模板编码 固定，写在程序配置文件中
	private String msg_info_detail;//短信内容，字段之间以双竖线||分割
	private int msg_info_failcount;//发送失败次数 0表示待发送 大于1表示发送失败次数
	private String create_time;//生成时间
	private String update_time;//更新时间
	public int getMsg_info_id() {
		return msg_info_id;
	}
	public void setMsg_info_id(int msgInfoId) {
		msg_info_id = msgInfoId;
	}
	public String getMsg_template_code() {
		return msg_template_code;
	}
	public void setMsg_template_code(String msgTemplateCode) {
		msg_template_code = msgTemplateCode;
	}
	public String getMsg_info_phone() {
		return msg_info_phone;
	}
	public void setMsg_info_phone(String msgInfoPhone) {
		msg_info_phone = msgInfoPhone;
	}
	public String getMsg_info_detail() {
		return msg_info_detail;
	}
	public void setMsg_info_detail(String msgInfoDetail) {
		msg_info_detail = msgInfoDetail;
	}
	public int getMsg_info_failcount() {
		return msg_info_failcount;
	}
	public void setMsg_info_failcount(int msgInfoFailcount) {
		msg_info_failcount = msgInfoFailcount;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String createTime) {
		create_time = createTime;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String updateTime) {
		update_time = updateTime;
	}
	
	
}
