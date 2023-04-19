package com.example.course_selection.vo;

import java.util.List;

import com.example.course_selection.entity.Course;
import com.example.course_selection.entity.Student;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentAddInfoResponse {
	



	


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
		
		
		private List<String> courseList;
		
		
		
		
		
		
		

		

		public StudentAddInfoResponse(String message, List<Student> student) {
			super();
			this.message = message;
			this.student = student;
		}


		public StudentAddInfoResponse() {
			super();
			// TODO Auto-generated constructor stub
		}


		

		public StudentAddInfoResponse(String message) {
			super();
			this.message = message;
		}


		public StudentAddInfoResponse(String courseID, String message) {
			super();
			this.courseID = courseID;
			this.message = message;
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


		public Student getStudentItem() {
			return studentItem;
		}


		public void setStudentItem(Student studentItem) {
			this.studentItem = studentItem;
		}


		public Course getCourse() {
			return course;
		}


		public void setCourse(Course course) {
			this.course = course;
		}


		public List<Course> getMutiCourseList() {
			return mutiCourseList;
		}


		public void setMutiCourseList(List<Course> mutiCourseList) {
			this.mutiCourseList = mutiCourseList;
		}


		public List<Student> getStudent() {
			return student;
		}


		public void setStudent(List<Student> student) {
			this.student = student;
		}


		public List<String> getCourseList() {
			return courseList;
		}


		public void setCourseList(List<String> courseList) {
			this.courseList = courseList;
		}//多筆選課課程清單
		
	}
		
	