package com.example.course_selection.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.course_selection.entity.Course;
import com.example.course_selection.respository.CourseDao;
import com.example.course_selection.service.ifs.CourseService;
import com.example.course_selection.vo.CourseRequest;
import com.example.course_selection.vo.CourseResponse;

@Service
public class CourseImpl implements CourseService {

	@Autowired
	private CourseDao courseDao;

	// 新增課程資訊
	@Override
	public CourseResponse addCourse(CourseRequest req) {

		List<Course> errorList = new ArrayList<>(); // new出一個錯誤呈現清單，將錯誤收集
		List<Course> courseList = req.getCourse(); // 將請求的課程資訊，放入清單

		for (Course item : courseList) { // 每筆資料作確認
			if (!StringUtils.hasText(item.getCourseId()) || !StringUtils.hasText(item.getCourseName())) {
				return new CourseResponse("404,ID and Name are empty!"); // 如果課程代號或課程名稱前端不輸入，跳出錯誤訊息
			}

			if (courseDao.existsById(item.getCourseId())) {
				errorList.add(item); // 如果課程代號已經存在資料庫，收集到錯誤清單

			}

		}
		if (!errorList.isEmpty()) {
			return new CourseResponse("404,ID duplicated!"); // 全部資料確認完，確認錯誤清單是否有值，有值，拋出錯誤的請求清單內容
		}

		courseDao.saveAll(courseList); // 如果沒有錯誤發生，請求新增的清單進入資料庫
		return new CourseResponse(courseList, "course added successed!"); // 成功結果拋出

	}

	//修改課程
	@Override
	public CourseResponse modiCourse(CourseRequest req) {

		Course courseInfo = req.getCourseitem(); // 將請求的課程資訊，放入清單
		// 每筆資料作確認
		if (!StringUtils.hasText(req.getCourseId())) {
			return new CourseResponse("404,ID is empty!"); // 如果課程代號前端不輸入，跳出錯誤訊息
		}
		Optional<Course> op = courseDao.findById(req.getCourseId()); // 搜尋單筆的所有資料欄位
		if (!op.isPresent()) {
			return new CourseResponse("404,data request is not exist");
		} // 資料如不存在丟出錯誤訊息
		Course result = op.get();// 取得單筆資訊
		// 資料存在，只要有請求，就協助更新
		if (StringUtils.hasText(req.getNewName())) {
			result.setCourseName(req.getNewName());
		}
		if (StringUtils.hasText(req.getNewDate())) {
			result.setCourseDate(req.getNewDate());
		}
		if (StringUtils.hasText(req.getNewStartTime())) {
			result.setCourseStartTime(req.getNewStartTime());
		}
		if (StringUtils.hasText(req.getNewEndTime())) {
			result.setCourseEndTime(req.getNewEndTime());
		}
		if (req.getNewCredit()!=result.getCourseCredit()) {
			result.setCourseCredit(req.getNewCredit());
		}

		courseDao.save(result); // 單筆資料存入
		return new CourseResponse(result, "Update successed"); // 完成更新拋出結果
	}

	
	
	@Override
	public CourseResponse deletCourse(CourseRequest req) {
		
		
		if (!StringUtils.hasText(req.getCourseId())) {
			return new CourseResponse("404,ID is empty!"); // 如果課程代號前端不輸入，跳出錯誤訊息
		}
		if (!courseDao.existsById(req.getCourseId())) {
			return new CourseResponse("404,ID is not exist!"); // 如果課程代號不存在資料庫，跳出錯誤訊息

		}
				
		Course result=courseDao.findByCourseMemberAndCourseId(0,req.getCourseId()); //搜尋同時為零位學生且是前端要求課程代號的資料 
		if(result.getCourseName().isEmpty()) {	
		return new CourseResponse("member have,could not delete");
		}
		
		
		courseDao.delete(result);
		return new CourseResponse("delete info");
	}

	@Override
	public List<Course> findByCourseIdContaining(String courseId) {
		
		return courseDao.findByCourseIdContaining(courseId);
	}

	@Override
	public List<Course> findByCourseNameContaining(String courseName) {
		
		return courseDao.findByCourseNameContaining(courseName);
	}

	@Override
	public List<Course> findByCourseDateAndCourseStartTimeBetween(String courseDate, String courseStartTime,
			String courseEndTime) {
		
		return courseDao.findByCourseDateAndCourseStartTimeBetween(courseDate, courseStartTime, courseEndTime);
	}

}
