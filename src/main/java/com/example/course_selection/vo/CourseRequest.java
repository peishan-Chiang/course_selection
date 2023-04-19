package com.example.course_selection.vo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import com.example.course_selection.entity.Course;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseRequest {
	
	
	
	
	private Course courseitem;//單筆course的資訊 
	private String courseId;//課程代號
	private String courseName;//課程名稱
	private String newName;//新課程名稱
	private String newDate;//新課程星期
	private String newStartTime;//新開始上課時間
	private String newEndTime;//新結束上課時間
	private int newCredit;//新學分
	

	@JsonProperty("course_list")
	private List <Course> course;//多筆course的資訊
	
	

	

	public CourseRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Course> getCourse() {
		return course;
	}

	public void setCourse(List<Course> course) {
		this.course = course;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getNewDate() {
		return newDate;
	}

	public void setNewDate(String newDate) {
		this.newDate = newDate;
	}

	

	public String getNewEndTime() {
		return newEndTime;
	}

	public void setNewEndTime(String newEndTime) {
		this.newEndTime = newEndTime;
	}

	public int getNewCredit() {
		return newCredit;
	}

	public void setNewCredit(int newCredit) {
		this.newCredit = newCredit;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getNewStartTime() {
		return newStartTime;
	}

	public void setNewStartTime(String newStartTime) {
		this.newStartTime = newStartTime;
	}

	public Course getCourseitem() {
		return courseitem;
	}

	public void setCourseitem(Course courseitem) {
		this.courseitem = courseitem;
	}

	

	
	
}
