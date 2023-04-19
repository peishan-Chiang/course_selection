package com.example.course_selection.service.ifs;

import java.util.List;

import com.example.course_selection.entity.Course;
import com.example.course_selection.vo.CourseRequest;
import com.example.course_selection.vo.CourseResponse;

public interface CourseService {

	public CourseResponse addCourse(CourseRequest req); // 新增課程

	public CourseResponse modiCourse(CourseRequest req);// 修改課程

	public CourseResponse deletCourse(CourseRequest req);// 修改課程

	public List<Course> findByCourseIdContaining(String courseId); //課程代號搜尋
	
	public List<Course> findByCourseNameContaining(String courseName); //課程名稱搜尋
	
	public List<Course> findByCourseDateAndCourseStartTimeBetween(String courseDate,String courseStartTime,String courseEndTime);//針對星期藍位確認有幾筆
	

}
