package com.example.course_selection.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.course_selection.service.ifs.StudentService;
import com.example.course_selection.vo.StudentAddInfoResponse;
import com.example.course_selection.vo.StudentRequest;
import com.example.course_selection.vo.StudentResponse;

@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;
	


	@PostMapping("/add_Student")
	public StudentAddInfoResponse addStudent(@RequestBody StudentRequest req) {
		return studentService.addStudent(req);
	}
	
	@PostMapping("/select_Course")
	public StudentResponse selectCourse(@RequestBody StudentRequest req) {
		return studentService.selectCourse(req);
	}
	
	@PostMapping("/add_New_Course")
	public StudentResponse addCourse(@RequestBody StudentRequest req) {
		return studentService.addCourse(req);
	}
	
	@PostMapping("/drop_Exist_Course")
	public StudentResponse dropCourse(@RequestBody StudentRequest req) {
		return studentService.dropCourse(req);
	}
	
}
