package com.example.course_selection.service.ifs;



import com.example.course_selection.vo.StudentRequest;
import com.example.course_selection.vo.StudentResponse;



public interface StudentService {
	
	public StudentResponse addStudent(StudentRequest req);//新增學生資料
	public StudentResponse selectCourse(StudentRequest req);//學生選課

}
