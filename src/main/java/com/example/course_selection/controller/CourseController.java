package com.example.course_selection.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_selection.entity.Course;
import com.example.course_selection.service.ifs.CourseService;
import com.example.course_selection.vo.CourseRequest;
import com.example.course_selection.vo.CourseResponse;

@RestController
public class CourseController {

	@Autowired
	private CourseService courseService;

	@PostMapping("/add_Course")
	public CourseResponse addCourse(@RequestBody CourseRequest req) {
		return courseService.addCourse(req);
	}
	
	@PostMapping("/modi_Course")
	public CourseResponse modiCourse(@RequestBody CourseRequest req) {
		return courseService.modiCourse(req);
	}
	

	@PostMapping("/find_By_Course_Id_Containing")
	public List<Course> findByCourseIdContaining(@RequestBody CourseRequest req) {
		return courseService.findByCourseIdContaining(req.getCourseId());
	}
	
	@PostMapping("/find_By_Course_Name_Containing")
	public List<Course> findByCourseNameContaining(@RequestBody CourseRequest req) {
		return courseService.findByCourseNameContaining(req.getCourseName());
	}
	
	
	@PostMapping("/findByCourseDateAndCourseStartTimeBetween")
	public List<Course> findByCourseDateAndCourseStartTimeBetween(@RequestBody CourseRequest req) {
		return courseService.findByCourseDateAndCourseStartTimeBetween(req.getNewDate(),req.getNewStartTime(),req.getNewEndTime());
	}
	

}
