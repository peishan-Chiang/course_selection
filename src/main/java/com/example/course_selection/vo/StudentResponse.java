package com.example.course_selection.vo;

import java.util.List;

import com.example.course_selection.entity.Course;
import com.example.course_selection.entity.Student;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentResponse {

	private String studentId;//學生學號
	private String studentName;//學生姓名
	private String courseID;//課程代號
	private String message;//回傳訊息
	
	private Course course;
	
	private List<Student> student;
	

	private List<String> courseList;//多筆選課課程清單
	
	
	
	
	
	
	

	public StudentResponse( List<String> courseList,String message) {
		this.message = message;
		this.courseList = courseList;
	}

	public StudentResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentResponse(String studentId, String message, List<String> courseList) {
		super();
		this.studentId = studentId;
		this.message = message;
		this.courseList = courseList;
	}
	
	


	public StudentResponse(String message, List<Student> student) {
		super();
		this.message = message;
		this.student = student;
	}

	

	public StudentResponse(String message, Course course) {
		super();
		this.message = message;
		this.course = course;
	}

	public StudentResponse(String message) {
		super();
		this.message = message;
	}

	public StudentResponse(String courseID, String message) {
		super();
		this.courseID = courseID;
		this.message = message;
	}

	public List<Student> getStudent() {
		return student;
	}

	public void setStudent(List<Student> student) {
		this.student = student;
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

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<String> courseList) {
		this.courseList = courseList;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	
}
