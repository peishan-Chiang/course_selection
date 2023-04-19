package com.example.course_selection.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.course_selection.entity.Course;
import com.example.course_selection.entity.Student;
import com.example.course_selection.respository.CourseDao;
import com.example.course_selection.respository.StudentDao;
import com.example.course_selection.service.ifs.StudentService;
import com.example.course_selection.vo.StudentAddInfoResponse;
import com.example.course_selection.vo.StudentRequest;
import com.example.course_selection.vo.StudentResponse;

@Service
public class StudentImpl implements StudentService {

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private CourseDao courseDao;

	// 新增學生資料
	@Override
	public StudentAddInfoResponse  addStudent(StudentRequest req) {

		List<Student> errorList = new ArrayList<>();
		List<Student> studentList = req.getStudentList();

		for (Student item : studentList) {
			if (!StringUtils.hasText(item.getStudentId()) || !StringUtils.hasText(item.getStudentName())) {
				return new StudentAddInfoResponse("不得為空");

			}
			if (studentDao.existsById(item.getStudentId())) {
				errorList.add(item);
			}
		}

		if (!errorList.isEmpty()) {
			return  new StudentAddInfoResponse("已經存在請確認",errorList);
		}

		studentDao.saveAll(studentList);
		return new StudentAddInfoResponse("學生資料請確認",studentList);

	}

	// 學生選課
	@Override
	public StudentResponse selectCourse(StudentRequest req) {

		List<String> courseList = req.getCourseList(); // 前端請求的課程資料
		List<Course> courseWholeList = new ArrayList<>();// 全部資料

		List<String> nameErrList = new ArrayList<>();// 重複名字
		List<String> weekReList = new ArrayList<>();// 星期有重複
		List<String> timeList = new ArrayList<>();// 時間有重複
		List<String> scoreList = new ArrayList<>();// 學分
		List<String> approvList = new ArrayList<>();// 可加入的
		List<String> creditList = new ArrayList<>();// 滿了
		List<Course> finalList = new ArrayList<>();// 格式
		List<Course> tidyList = new ArrayList<>();// 格式

		Course newCourse = null;
		if (!StringUtils.hasText(req.getStudentId()) || !StringUtils.hasText(req.getStudentName())) {
			return new StudentResponse("404,基本資料(【學號】及【姓名】不允許空白，請調整並重發請求)");
		}

		Optional<Student> op = studentDao.findById(req.getStudentId());
		if (!op.isPresent()) {
			return new StudentResponse("404,學生資訊不存在，請重下資訊");
		}
		Student student = op.get();// 獲取單筆資料
		String classId = student.getCourseId();// 學生的ID !!!後面要接這個變數

		for (String courseItem : courseList) { // 用請求清單確認是否課程都存在
			Optional<Course> courseOp = courseDao.findById(courseItem);
			if (!courseOp.isPresent()) {
				return new StudentResponse("404,課程資訊不存在，請重下資訊");
			}
			newCourse = courseOp.get();// 獲取單筆資料
			courseWholeList.add(newCourse);
		} // 學生選到的所有課程

//		for (Course courseItem : courseWholeList) {	//確認名稱

		for (int i = 0; i < courseWholeList.size(); i++) {
			String courseIdRepeat = courseWholeList.get(i).getCourseId();
			String className = courseWholeList.get(i).getCourseName();
			for (int j = i + 1; j < courseWholeList.size(); j++) {
				String checkName = courseWholeList.get(j).getCourseName();
				if (className.equals(checkName)) {
					nameErrList.add(courseIdRepeat);
				}
			}
		}

		if (!nameErrList.isEmpty()) {
			return new StudentResponse( "下列課程，名稱與其中一門發生課程名稱重複(下列課程，請作刪除重新申請或是調整)", nameErrList);
		}

		for (int i = 0; i < courseWholeList.size(); i++) {

			String classDay = courseWholeList.get(i).getCourseDate();
			for (int j = i + 1; j < courseWholeList.size(); j++) {
				String checkDay = courseWholeList.get(j).getCourseDate();
				if (classDay.equals(checkDay)) {
					weekReList.add(classDay);
				}
			}
		}

		if (!weekReList.isEmpty()) {
			for (int i = 0; i < courseWholeList.size(); i++) {
				String courseIdRepeat = courseWholeList.get(i).getCourseId();
				String classTime = courseWholeList.get(i).getCourseStartTime();
				Integer inClassTime = Integer.valueOf(classTime);
				String endTime = courseWholeList.get(i).getCourseEndTime();
				Integer inEndTime = Integer.valueOf(endTime);
				for (int j = i + 1; j < courseWholeList.size(); j++) {
					String checkTime = courseWholeList.get(j).getCourseStartTime();
					Integer inCheckTime = Integer.valueOf(checkTime);
					String checkEndTime = courseWholeList.get(j).getCourseEndTime();
					Integer inCheckEndTime = Integer.valueOf(checkEndTime);
					if (inClassTime >= inCheckTime || inEndTime >= inCheckEndTime) {
						if (!timeList.contains(courseIdRepeat)) {
							timeList.add(courseIdRepeat);
						}
					}
					if ((inCheckTime > inClassTime) && (inEndTime > inCheckEndTime)) {// 包含
						if (!timeList.contains(courseIdRepeat)) {
							timeList.add(courseIdRepeat);
						}
					}

					if ((inCheckTime < inClassTime) && (inCheckEndTime > inClassTime)) { // 其他課程提早上課，卻中間重疊上課時間，主課程開始，其他課程卻還沒結束
						if (!timeList.contains(courseIdRepeat)) {
							timeList.add(courseIdRepeat);
						}
					}
					if ((inCheckTime < inEndTime) && (inCheckEndTime > inEndTime)) { // 主課程還沒結束，比對課程已經準備上課
						if (!timeList.contains(courseIdRepeat)) {
							timeList.add(courseIdRepeat);
						}

					}

				}

			}

		}
		if (!timeList.isEmpty()) {
			return new StudentResponse("下列課程，與其中一門發生衝堂(下列課程，請作刪除重新申請或是調整 )",timeList);
		}

		
		int left = 10;
		for (int i = 0; i < courseWholeList.size(); i++) {
			int memberNum = courseWholeList.get(i).getCourseMember();
			String courseId = courseWholeList.get(i).getCourseId();
			int credit = courseWholeList.get(i).getCourseCredit();
			if(memberNum>=3){
				creditList.add(courseId);continue;}
			
			
			if (memberNum<3) {
				left = left - credit;
				if (left <= 0) {
					break;
				}
				approvList.add(courseId);
				memberNum = memberNum + 1;
				courseWholeList.get(i).setCourseMember(memberNum);
				Optional<Course> newOp =courseDao.findById(courseId);
				newCourse = newOp.get();
				finalList.add(newCourse);
			}

		}
		
		

		String string = " ";
		for (String courseListitem : approvList) {
			String str = courseListitem.toString();
			string = str + ";" + string;
		}
		student.setCourseId(string);
		studentDao.save(student);

		courseDao.saveAll(finalList);
		
		for(Course courseItem :finalList) {
			courseItem.setCourseCredit(null);
			courseItem.setCourseDate(null);
			courseItem.setCourseEndTime(null);
			courseItem.setCourseStartTime(null);
			courseItem.setCourseMember(null);
			tidyList.add(courseItem);
		}
		
		
		
		
		return new StudentResponse("選課完成，請確認資訊，修課上限為10學分，如額滿課程則無法加選，如要調整，請於時間內加退選!",tidyList,creditList);

	}
}

