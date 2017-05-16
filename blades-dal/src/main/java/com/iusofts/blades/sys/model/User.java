/*
 * Copyright © 2010-2016 iusofts. All rights reserved.
 */
package com.iusofts.blades.sys.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Pattern;

import com.iusofts.blades.sys.common.util.validation.PhoneMode;
import org.hibernate.validator.constraints.NotBlank;

import com.iusofts.blades.sys.common.util.validation.Phone;

/**
 * 用户信息
 * 
 * @author
 * @date 2016-03-10 13:54:18
 */
public class User extends Account implements Serializable {
	/**
	 * 主键
	 */
	private String id;	

	/**
	 * 姓名
	 */
	@NotBlank(message = "姓名不能为空")
	private String name;

	/**
	 * 拼音名称
	 */
	private String pyName;

	/**
	 * 英文名称
	 */
	private String enName;

	/**
	 * 身份证号
	 */
	//@NotBlank(message = "身份证号不能为空")
	@Pattern(regexp = "(^$|\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x))", message = "身份证格式不正确")
	private String idNo;

	/**
	 * 性别
	 */
	@NotBlank(message = "请选择性别")
	private String gender;

	/**
	 * 照片
	 */
	private String photo;

	/**
	 * 学历
	 */
	private String education;

	/**
	 * 职称
	 */
	private String identity;

	/**
	 * 出生日期
	 */
	private String birthdate;

	/**
	 * 警号
	 */
	@Pattern(regexp = "^$|[0-9]*[1-9][0-9]*$", message = "警号格式不正确")
	private String code;

	/**
	 * 办公电话
	 */
	@Phone(value = PhoneMode.PHONE, message = "办公电话格式不正确")
	private String officePhone;

	/**
	 * 家庭电话
	 */
	@Phone(value = PhoneMode.PHONE, message = "家庭电话格式不正确")
	private String phone;

	/**
	 * 家庭住址
	 */
	private String address;

	/**
	 * 邮编
	 */
	private String postCode;

	/**
	 * 入职时间
	 */
	private String entryTime;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 更新时间
	 */
	private String updateTime;

	/**
	 * 昵称
	 */
	private String nickName;
	
	private List<Role> roleList = new ArrayList<Role>(); // 拥有角色列表
	
	private List<Organization> orgList = new ArrayList<Organization>(); // 拥有角色列表

	private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPyName() {
		return pyName;
	}

	public void setPyName(String pyName) {
		this.pyName = pyName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Organization> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<Organization> orgList) {
		this.orgList = orgList;
	}

	
	
}