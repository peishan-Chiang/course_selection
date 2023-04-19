package com.example.course_selection.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.course_selection.entity.Course;

@ Repository
public interface CourseDao extends JpaRepository<Course,String> {
	
	public Course findByCourseMemberAndCourseId(int courseMember,String courseId);
	
	public List<Course> findByCourseIdContaining(String courseId);
	
	public List<Course> findByCourseNameContaining(String courseName);
	
	public long countByCourseDateAndCourseStartTimeBetween(String courseDate,String courseStartTime,String courseEndTime);//針對星期藍位確認有幾筆
	
	public List<Course> findByCourseDateAndCourseStartTimeBetween(String courseDate,String courseStartTime,String courseEndTime);//針對星期藍位確認有幾筆
	
	public List<Course> findByCourseMemberGreaterThanEqual(int courseMember);//如果大於三個人那就代表這們課不讓選擇
	
	public long countByCourseName(String courseName);//針對星期藍位確認有幾筆
	
	public List<Course> findByCourseName(String courseName);//相同名稱我要提出
	
}
