package com.brilliantreform.sc.community.po;


public class TreeNode {

	private int id;//节点ID
	private String text;//节点标题
	private String parent;//父节点ID
	
	
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
