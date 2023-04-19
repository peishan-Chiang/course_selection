package com.example.course_selection.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="student_info")
public class Student {
	
	
	@Id
	@Column(name="student_id")
	private String studentId;//學生學號
	
	
	@Column(name="student_name")
	private String studentName;//學生姓名
	
	
	@Column(name="course_id")
	private String courseId;//課程代號


	
	
	
	
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Student(String studentId, String studentName, String courseID) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.courseId = courseID;
	}


	public String getStudentId() {
		return studentId;
	}


	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}


	public String getStudentName() {
		return studentName;
	}


	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}


	public String getCourseId() {
		return courseId;
	}


	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}



	
	
}
