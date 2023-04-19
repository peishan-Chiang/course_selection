package com.example.course_selection.entity;





import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="course_info")
public class Course {
	
	@Id
	@Column(name="course_id")
	private String courseId;//課程代號
	

	@Column(name="course_name")
	private String courseName;//課程名稱
	
	@Column(name="course_date")
	private String courseDate;//課程星期
	
	@Column(name="course_start_time")
	private String courseStartTime;//課程開始時間
	
	@Column(name="course_end_time")
	private String courseEndTime;//課程結束時間
	
	@Column(name="course_credit")
	private Integer courseCredit;//課程學分
	
	
	@Column(name="course_member")
	private Integer courseMember;//課程修課人數

	
	
	
	
	

	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Course(String courseId, String courseName, String courseDate, String courseStartTime, String courseEndTime,
			int courseCredit, int courseMember) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseDate = courseDate;
		this.courseStartTime = courseStartTime;
		this.courseEndTime = courseEndTime;
		this.courseCredit = courseCredit;
		this.courseMember = courseMember;
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

	public String getCourseDate() {
		return courseDate;
	}

	public void setCourseDate(String courseDate) {
		this.courseDate = courseDate;
	}

	public String getCourseStartTime() {
		return courseStartTime;
	}

	public void setCourseStartTime(String courseStartTime) {
		this.courseStartTime = courseStartTime;
	}

	public String getCourseEndTime() {
		return courseEndTime;
	}

	public void setCourseEndTime(String courseEndTime) {
		this.courseEndTime = courseEndTime;
	}

	public Integer getCourseCredit() {
		return courseCredit;
	}

	public Integer getCourseMember() {
		return courseMember;
	}

	public void setCourseMember(Integer courseMember) {
		this.courseMember = courseMember;
	}

	public void setCourseCredit(Integer courseCredit) {
		this.courseCredit = courseCredit;
	}



	

	


}
