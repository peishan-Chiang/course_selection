package com.example.course_selection.service.ifs;



import com.example.course_selection.vo.StudentAddInfoResponse;
import com.example.course_selection.vo.StudentRequest;
import com.example.course_selection.vo.StudentResponse;



public interface StudentService {
	
	public StudentAddInfoResponse  addStudent(StudentRequest  req);//新增學生資料
	public StudentResponse selectCourse(StudentRequest req);//學生選課
	public StudentResponse addCourse(StudentRequest req);//加選
	public StudentResponse dropCourse(StudentRequest req);//退選
	

}
