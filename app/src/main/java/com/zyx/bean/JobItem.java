package com.zyx.bean;

public class JobItem {
	
	private int id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 链接
	 */
	private String link;
	/**
	 * 时间
	 */
	private String date;
	/**
	 * 图片的链接
	 */
	private String imgLink;
	/**
	 * 地点
	 */
	private String address;
	
	/**
	 * 薪酬
	 * 
	 */
	private String salary;
	
	/**
	 * 工作状态 报名中/已结束
	 * 
	 */
	private String jobstatus;

	/**
	 * 工作类型
	 * 
	 */
	private int catId;
	
	/**
	 * 工作地区
	 *  
	 */
	private int areaId;
	
	/**
	 * 数据量
	 * 
	 */
	private int cursor;

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getImgLink() {
		return imgLink;
	}

	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getJobstatus() {
		return jobstatus;
	}

	public void setJobstatus(String jobstatus) {
		this.jobstatus = jobstatus;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public int getCursor() {
		return cursor;
	}

	public void setCursor(int cursor) {
		this.cursor = cursor;
	}
	
	 @Override  
	    public String toString()  
	    {  
	        return "JobItem [id=" + id + ", title=" + title + ", link=" + link + ", date=" + date + ", imgLink=" + imgLink  
	                + ", address=" + address + ", cursor=" + cursor + ", salary=" + salary + ", jobstatus=" + jobstatus + ",catId=" + catId + ", areaId=" + areaId + "]";  
	    }  
	

}
