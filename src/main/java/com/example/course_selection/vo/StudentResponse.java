package com.example.course_selection.vo;

import java.util.List;

import javax.persistence.Column;

import com.example.course_selection.entity.Course;
import com.example.course_selection.entity.Student;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentResponse {

	public StudentResponse(String message, List<String> courseList) {
		super();
		this.message = message;
		this.courseList = courseList;
	}






	private String studentId;//學生學號
	private String studentName;//學生姓名
	private String courseID;//課程代號
	private String message;//回傳訊息
	
	
	private Integer courseCredit;//課程學分
	
	
	
	private Integer courseMember;//課程修課人數
	
	

	@JsonProperty("student_infomation")
	private Student studentItem;
	private Course course;
	
	@JsonProperty("selected_course_info")
	private List<Course>  mutiCourseList;
	@JsonProperty("student_selection")
	private List<Student> student;
	
	@JsonProperty("Maximum_num_course")
	private List<String> errList;//需要調整的清單
	private List<String> courseList;//多筆選課課程清單
	
	@JsonProperty("drop_course")
	private List<String> dropList;//多筆選課課程清單
	
	
	
	public StudentResponse(String courseID, String message, List<String> courseList) {
		super();
		this.courseID = courseID;
		this.message = message;
		this.courseList = courseList;
	}
	
	

	public StudentResponse(String message, List<Course> mutiCourseList, List<String> errList) {
		super();
		this.message = message;
		this.mutiCourseList = mutiCourseList;
		this.errList = errList;
	}





	public StudentResponse(String message, Integer courseCredit) {
		super();
		this.message = message;
		this.courseCredit = courseCredit;
	}



	public StudentResponse(List<Course> mutiCourseList , String message) {
		super();
		this.message = message;
		this.mutiCourseList = mutiCourseList;
	}









	public StudentResponse(String message, Student studentItem, Course course) {
		super();
		this.message = message;
		this.studentItem = studentItem;
		this.course = course;
	}

	public StudentResponse(String message, Student studentItem) {
		super();
		this.message = message;
		this.studentItem = studentItem;
	}

	
	public StudentResponse() {
		super();
		// TODO Auto-generated constructor stub
	}


	
	


//	public StudentResponse(String message, List<Student> student) {
//		super();
//		this.message = message;
//		this.student = student;
//	}

	

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

	public Student getStudentItem() {
		return studentItem;
	}

	public void setStudentItem(Student studentItem) {
		this.studentItem = studentItem;
	}






	public Integer getCourseCredit() {
		return courseCredit;
	}






	public void setCourseCredit(Integer courseCredit) {
		this.courseCredit = courseCredit;
	}






	public Integer getCourseMember() {
		return courseMember;
	}






	public void setCourseMember(Integer courseMember) {
		this.courseMember = courseMember;
	}






	public List<Course> getMutiCourseList() {
		return mutiCourseList;
	}






	public void setMutiCourseList(List<Course> mutiCourseList) {
		this.mutiCourseList = mutiCourseList;
	}



	public List<String> getErrList() {
		return errList;
	}



	public void setErrList(List<String> errList) {
		this.errList = errList;
	}



	public List<String> getDropList() {
		return dropList;
	}



	public void setDropList(List<String> dropList) {
		this.dropList = dropList;
	}
	
	
}
