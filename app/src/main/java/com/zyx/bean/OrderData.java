package com.zyx.bean;

import java.util.Date;

public class OrderData {

	private String ordernumber;

	private String orderdate;

	private Integer times;

	private Integer hasfirstpay;

	private Float repayment;

	private Long customerid;

	private Long employeeid;

	private String productnumber;

	private Integer orderstatus;

	private Integer time;

	private String begindate;

	private String enddate;

	private String paydate;

	private Integer repaystatus;

	private Float money;

	private String productname;

	private String productdescription;

	private Float quotoprice;

	private Float retailprice;

	private String imageurls;

	private Integer quantityonhand;

	private Integer categoryid;

	private String custnick;

	private String custname;

	private String custidcard;

	private String custaddress;

	private String custphonenum;

	private String custloginpwd;

	private String custdealpwd;

	private String custparentname;

	private String custparentphonenum;

	private String custuniversity;

	private String custinuniversity;

	private String custeducation;

	private Float custpocketmoney;

	private String custemail;

	private Integer custlevelid;

	private Integer custcreditrest;

	private Integer custpoint;

	private String custzipcode;

	private Integer custsex;

	private String custregdate;

	private Integer custstatus;

	private Integer roleid;

	private byte[] custvedio;

	public String getCustnick() {
		return custnick;
	}

	public void setCustnick(String custnick) {
		this.custnick = custnick == null ? null : custnick.trim();
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname == null ? null : custname.trim();
	}

	public String getCustidcard() {
		return custidcard;
	}

	public void setCustidcard(String custidcard) {
		this.custidcard = custidcard == null ? null : custidcard.trim();
	}

	public String getCustaddress() {
		return custaddress;
	}

	public void setCustaddress(String custaddress) {
		this.custaddress = custaddress == null ? null : custaddress.trim();
	}

	public String getCustphonenum() {
		return custphonenum;
	}

	public void setCustphonenum(String custphonenum) {
		this.custphonenum = custphonenum == null ? null : custphonenum.trim();
	}

	public String getCustloginpwd() {
		return custloginpwd;
	}

	public void setCustloginpwd(String custloginpwd) {
		this.custloginpwd = custloginpwd == null ? null : custloginpwd.trim();
	}

	public String getCustdealpwd() {
		return custdealpwd;
	}

	public void setCustdealpwd(String custdealpwd) {
		this.custdealpwd = custdealpwd == null ? null : custdealpwd.trim();
	}

	public String getCustparentname() {
		return custparentname;
	}

	public void setCustparentname(String custparentname) {
		this.custparentname = custparentname == null ? null : custparentname
				.trim();
	}

	public String getCustparentphonenum() {
		return custparentphonenum;
	}

	public void setCustparentphonenum(String custparentphonenum) {
		this.custparentphonenum = custparentphonenum == null ? null
				: custparentphonenum.trim();
	}

	public String getCustuniversity() {
		return custuniversity;
	}

	public void setCustuniversity(String custuniversity) {
		this.custuniversity = custuniversity == null ? null : custuniversity
				.trim();
	}

	public String getCustinuniversity() {
		return custinuniversity;
	}

	public void setCustinuniversity(String custinuniversity) {
		this.custinuniversity = custinuniversity == null ? null
				: custinuniversity.trim();
	}

	public String getCusteducation() {
		return custeducation;
	}

	public void setCusteducation(String custeducation) {
		this.custeducation = custeducation == null ? null : custeducation
				.trim();
	}

	public Float getCustpocketmoney() {
		return custpocketmoney;
	}

	public void setCustpocketmoney(Float custpocketmoney) {
		this.custpocketmoney = custpocketmoney;
	}

	public String getCustemail() {
		return custemail;
	}

	public void setCustemail(String custemail) {
		this.custemail = custemail == null ? null : custemail.trim();
	}

	public Integer getCustlevelid() {
		return custlevelid;
	}

	public void setCustlevelid(Integer custlevelid) {
		this.custlevelid = custlevelid;
	}

	public Integer getCustcreditrest() {
		return custcreditrest;
	}

	public void setCustcreditrest(Integer custcreditrest) {
		this.custcreditrest = custcreditrest;
	}

	public Integer getCustpoint() {
		return custpoint;
	}

	public void setCustpoint(Integer custpoint) {
		this.custpoint = custpoint;
	}

	public String getCustzipcode() {
		return custzipcode;
	}

	public void setCustzipcode(String custzipcode) {
		this.custzipcode = custzipcode == null ? null : custzipcode.trim();
	}

	public Integer getCustsex() {
		return custsex;
	}

	public void setCustsex(Integer custsex) {
		this.custsex = custsex;
	}

	public String getCustregdate() {
		return custregdate;
	}

	public void setCustregdate(String custregdate) {
		this.custregdate = custregdate;
	}

	public Integer getCuststatus() {
		return custstatus;
	}

	public void setCuststatus(Integer custstatus) {
		this.custstatus = custstatus;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public byte[] getCustvedio() {
		return custvedio;
	}

	public void setCustvedio(byte[] custvedio) {
		this.custvedio = custvedio;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname == null ? null : productname.trim();
	}

	public String getProductdescription() {
		return productdescription;
	}

	public void setProductdescription(String productdescription) {
		this.productdescription = productdescription == null ? null
				: productdescription.trim();
	}

	public Float getQuotoprice() {
		return quotoprice;
	}

	public void setQuotoprice(Float quotoprice) {
		this.quotoprice = quotoprice;
	}

	public Float getRetailprice() {
		return retailprice;
	}

	public void setRetailprice(Float retailprice) {
		this.retailprice = retailprice;
	}

	public String getImageurls() {
		return imageurls;
	}

	public void setImageurls(String imageurls) {
		this.imageurls = imageurls == null ? null : imageurls.trim();
	}

	public Integer getQuantityonhand() {
		return quantityonhand;
	}

	public void setQuantityonhand(Integer quantityonhand) {
		this.quantityonhand = quantityonhand;
	}

	public Integer getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getPaydate() {
		return paydate;
	}

	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}

	public Integer getRepaystatus() {
		return repaystatus;
	}

	public void setRepaystatus(Integer repaystatus) {
		this.repaystatus = repaystatus;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public String getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber == null ? null : ordernumber.trim();
	}

	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Integer getHasfirstpay() {
		return hasfirstpay;
	}

	public void setHasfirstpay(Integer hasfirstpay) {
		this.hasfirstpay = hasfirstpay;
	}

	public Float getRepayment() {
		return repayment;
	}

	public void setRepayment(Float repayment) {
		this.repayment = repayment;
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	public Long getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(Long employeeid) {
		this.employeeid = employeeid;
	}

	public String getProductnumber() {
		return productnumber;
	}

	public void setProductnumber(String productnumber) {
		this.productnumber = productnumber == null ? null : productnumber
				.trim();
	}

	public Integer getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(Integer orderstatus) {
		this.orderstatus = orderstatus;
	}
}
