package com.example.course_selection.vo;

import java.util.List;



import com.example.course_selection.entity.Student;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentRequest {
	@JsonProperty("student")
	private Student student;
	private String studentId;//學生學號
	private String studentName;//學生姓名
	private String courseId;//課程代號
	
	
	@JsonProperty("student_List")
	private List<Student> studentList;
	
	
	private List<String> courseList;//多筆選課課程清單
	@JsonProperty("drop_course")
	private List<String> dropList;//多筆選課課程清單

	public StudentRequest() {
		super();
		// TODO Auto-generated constructor stub
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

	public void setCourseId(String courseID) {
		this.courseId = courseID;
	}

	public List<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}

	public void setStudent(Student student) {
		this.student = student;
	}



	public Student getStudent() {
		return student;
	}



	public List<String> getCourseList() {
		return courseList;
	}



	public void setCourseList(List<String> courseList) {
		this.courseList = courseList;
	}



	public List<String> getDropList() {
		return dropList;
	}



	public void setDropList(List<String> dropList) {
		this.dropList = dropList;
	}


	
}
