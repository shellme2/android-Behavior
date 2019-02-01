package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr;

import android.content.ContentValues;

import com.eebbk.bfc.sdk.behavior.db.constant.BFCColumns;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IAttr;

public class UserAttr implements IAttr {
	/**
	 * 用户唯一id
	 */
	private String userId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 用户生日
	 */
	private String birthday;
	/**
	 * 年级
	 */
	private String grade;
	/**
	 * 用户手机号码
	 */
	private String phoneNum;
	/**
	 * 作为属性封装在extend字段里，字段名以“user_”开头
	 */
	private String userExtend;
	/**
	 * 年龄
	 */
	private String age = "";
	/**
	 * 学校
	 */
	private String school = "";
	/**
	 * 年级类型
	 */
	private String gradeType = "";
	/**
	 * 学科
	 */
	private String subjects = "";

	@Override
	public void insert(ContentValues values) {
		values.put(BFCColumns.COLUMN_UA_USERID, userId);
		values.put(BFCColumns.COLUMN_UA_USERNAME, userName);
		values.put(BFCColumns.COLUMN_UA_SEX, sex);
		values.put(BFCColumns.COLUMN_UA_BIRTHDAY, birthday);
		values.put(BFCColumns.COLUMN_UA_GRADE, grade);
		values.put(BFCColumns.COLUMN_UA_PHONENUM, phoneNum);
		values.put(BFCColumns.COLUMN_UA_USEREXTEND, userExtend);
		values.put(BFCColumns.COLUMN_UA_AGE, age);
		values.put(BFCColumns.COLUMN_UA_SCHOOL, school);
		values.put(BFCColumns.COLUMN_UA_SUBJECTS, subjects);
		values.put(BFCColumns.COLUMN_UA_GRADETYPE, gradeType);
	}

	public String getUserId() {
		return userId;
	}


	public UserAttr setUserId(String userId) {
		this.userId = userId;
		return this;
	}


	public String getUserName() {
		return userName;
	}


	public UserAttr setUserName(String userName) {
		this.userName = userName;
		return this;
	}


	public String getSex() {
		return sex;
	}


	public UserAttr setSex(String sex) {
		this.sex = sex;
		return this;
	}


	public String getBirthday() {
		return birthday;
	}


	public UserAttr setBirthday(String birthday) {
		this.birthday = birthday;
		return this;
	}


	public String getGrade() {
		return grade;
	}


	public UserAttr setGrade(String grade) {
		this.grade = grade;
		return this;
	}


	public String getPhoneNum() {
		return phoneNum;
	}


	public UserAttr setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
		return this;
	}


	public String getUserExtend() {
		return userExtend;
	}

	public String getAge() {
		return age;
	}

	public UserAttr setAge(String age) {
		this.age = age;
		return this;
	}

	public String getSchool() {
		return school;
	}

	public UserAttr setSchool(String school) {
		this.school = school;
		return this;
	}

	public String getGradeType() {
		return gradeType;
	}

	public UserAttr setGradeType(String gradeType) {
		this.gradeType = gradeType;
		return this;
	}

	public String getSubjects() {
		return subjects;
	}

	public UserAttr setSubjects(String subjects) {
		this.subjects = subjects;
		return this;
	}

	public UserAttr setUserExtend(String userExtend) {
		this.userExtend = userExtend;
		return this;
	}

}
