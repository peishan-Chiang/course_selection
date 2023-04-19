package com.example.course_selection.vo;

import java.util.List;

import com.example.course_selection.entity.Course;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseResponse {
	
	
	
	private Course courseitem;//單筆course的資訊 
	@JsonProperty("course_list")
	private List <Course> course;//多筆course的資訊
	private String message; //回傳訊息
	
	
	
	
	
	

	public CourseResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	


	public CourseResponse(String message) {
		super();
		this.message = message;
	}


	public CourseResponse(List<Course> course, String message) {
		super();
		this.course = course;
		this.message = message;
	}
	
	public CourseResponse(Course courseitem, String message) {
		super();
		this.courseitem = courseitem;
		this.message = message;
	}

	public List<Course> getCourse() {
		return course;
	}

	public void setCourse(List<Course> course) {
		this.course = course;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public Course getCourseitem() {
		return courseitem;
	}


	public void setCourseitem(Course courseitem) {
		this.courseitem = courseitem;
	}
	
	

}
