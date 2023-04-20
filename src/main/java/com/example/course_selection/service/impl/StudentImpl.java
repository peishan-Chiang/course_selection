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
	public StudentAddInfoResponse addStudent(StudentRequest req) {

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
			return new StudentAddInfoResponse("已經存在請確認", errorList);
		}

		studentDao.saveAll(studentList);
		return new StudentAddInfoResponse("學生資料請確認", studentList);

	}

	// 學生選課
	@Override
	public StudentResponse selectCourse(StudentRequest req) {

		List<String> courseList = req.getCourseList(); // 前端請求的課程資料
		List<Course> courseWholeList = new ArrayList<>();// 全部資料

		List<String> nameErrList = new ArrayList<>();// 重複名字
		List<String> weekReList = new ArrayList<>();// 星期有重複
		List<String> timeList = new ArrayList<>();// 時間有重複
//		List<String> scoreList = new ArrayList<>();// 學分
		List<String> approvList = new ArrayList<>();// 可加入的
		List<String> creditList = new ArrayList<>();// 滿了
		List<Course> finalList = new ArrayList<>();// 已經算完10學分是否有超額
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
//		String classId = student.getCourseId();// 學生的ID !!!後面要接這個變數

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
			return new StudentResponse("下列課程，名稱與其中一門發生課程名稱重複(下列課程，請作刪除重新申請或是調整)", nameErrList);
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
			return new StudentResponse("下列課程，與其中一門發生衝堂(下列課程，請作刪除重新申請或是調整 )", timeList);
		}

		int left = 10;
		for (int i = 0; i < courseWholeList.size(); i++) {
			int memberNum = courseWholeList.get(i).getCourseMember();
			String courseId = courseWholeList.get(i).getCourseId();
			int credit = courseWholeList.get(i).getCourseCredit();

			if (memberNum >= 3) {
				creditList.add(courseId);
				continue;
			}

			if (memberNum < 3) {
				left = left - credit;
				if (left <= 0) {
					break;
				}
				approvList.add(courseId);
				memberNum = memberNum + 1;
				courseWholeList.get(i).setCourseMember(memberNum);
				Optional<Course> newOp = courseDao.findById(courseId);
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

		for (Course courseItem : finalList) {
			courseItem.setCourseCredit(null);
			courseItem.setCourseDate(null);
			courseItem.setCourseEndTime(null);
			courseItem.setCourseStartTime(null);
			courseItem.setCourseMember(null);
			tidyList.add(courseItem);
		}

		return new StudentResponse("選課完成，請確認資訊，修課上限為10學分，如額滿課程則無法加選，如要調整，請於時間內加退選!", tidyList, creditList);

	}

//=====================================================================================================
	private StudentResponse doAddOrDropCourse(StudentRequest req, boolean isAdd) {

		if (isAdd) {// 加選

			List<String> newCourseIdList = req.getCourseList();// 請求新增CourseID List

			List<String> timeList = new ArrayList<>();// 時間有重複
			List<Course> finalList = new ArrayList<>();// 調整格式使用
			List<Course> tidyList = new ArrayList<>();// 格式

			List<String> weekReList = new ArrayList<>();// 星期有重複
			List<String> courseIdList = new ArrayList<>();// 已選過CourseID List
			List<String> courseNewIdList = new ArrayList<>();// 有新增CourseID List
			List<Course> existWholeCourseInfo = new ArrayList<>(); // 根據學生已有ID迴圈收集的所有課程資訊

			List<Course> courseWholeList = new ArrayList<>();// 加選 CourseID List

			if (!StringUtils.hasText(req.getStudentId()) || !StringUtils.hasText(req.getStudentName())) {
				return new StudentResponse("404,基本資料(【學號】及【姓名】不允許空白，請調整並重發請求)");
			}
			Optional<Student> op = studentDao.findById(req.getStudentId());
			if (!op.isPresent()) {
				return new StudentResponse("404,學生資訊不存在，請重下資訊");
			}
			Student student = op.get();// 獲取【原先】單筆資料

			String stuClassID = student.getCourseId().trim();
			String[] str = stuClassID.split(";");// 將{A;B;C}拆成單一筆數清單

			for (String item : str) {
				courseIdList.add(item);
				Optional<Course> courseOp = courseDao.findById(item);
				Course existedCourseInfo = courseOp.get();
				existWholeCourseInfo.add(existedCourseInfo);// 學生已經選上的課程所有資訊
			}

			int total = 10;// 學分上限
			int credit;// 學分
			int sum = 0;// 累計學分
			for (int i = 0; i < existWholeCourseInfo.size(); i++) { // 抓出學分總和
				credit = existWholeCourseInfo.get(i).getCourseCredit();
				sum += credit;// 累積學分總和
			}

			if ((sum >= 10)) {
				return new StudentResponse("404,學分上限以到達，先退選");
			} // 不滿足10學分仍然可以選課

			for (String classId : newCourseIdList) { // 從請求而來
				Optional<Course> courseOp = courseDao.findById(classId);
				if (!courseOp.isPresent()) {
					return new StudentResponse("404,此資料不存在資料庫");
				}
				Course CourseInfo = courseOp.get();
				courseWholeList.add(CourseInfo);// 新增的所有課程資訊

				for (int i = 0; i < courseWholeList.size(); i++) {
					String classDay = courseWholeList.get(i).getCourseDate();
					for (int j =0; j < existWholeCourseInfo.size(); j++) {
						String checkDay = existWholeCourseInfo.get(j).getCourseDate();
						if (classDay.equals(checkDay)) {
							weekReList.add(classDay);
						}
					}
				}

				if (!weekReList.isEmpty()) {
					for (int i = 0; i < courseWholeList.size(); i++) {  //外迴圈為新增
						String courseIdRepeat = courseWholeList.get(i).getCourseId();
						String classTime = courseWholeList.get(i).getCourseStartTime();
						Integer inClassTime = Integer.valueOf(classTime);
						String endTime = courseWholeList.get(i).getCourseEndTime();
						Integer inEndTime = Integer.valueOf(endTime);
						for (int j = 0; j < existWholeCourseInfo.size(); j++) { //內迴圈為過去既有
							String checkTime = existWholeCourseInfo.get(j).getCourseStartTime();
							Integer inCheckTime = Integer.valueOf(checkTime);
							String checkEndTime = existWholeCourseInfo.get(j).getCourseEndTime();
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
					return new StudentResponse("下列課程，與其中一門發生衝堂(下列課程，請作刪除重新申請或是調整 )", timeList);
				}
			}

			String stuExistedID = student.getCourseId().trim();
			String[] strExistedId = stuExistedID.split(";");// 將{A;B;C}拆成單一筆數清單(已存在)

			int restCredit = 10 - sum;
			int reqNewMember=0;
			int reqMember=0;
			for (int i = 0; i < courseWholeList.size(); i++) {
				int reqCredit = courseWholeList.get(i).getCourseCredit();// 如果請求的學分
				reqMember = courseWholeList.get(i).getCourseMember();// 如果請求的修課人數
				String courseId = courseWholeList.get(i).getCourseId();
				if (reqCredit > (restCredit)) {// 沒剩餘學分可上課
					return new StudentResponse("每人上限10學分，欲新增之課程大於剩餘允許額度(提供剩餘學分)", restCredit);
				}
				if (reqMember >= 3) {// 學生>3人
					return new StudentResponse("下列修課人數以達上限3人，無法新增該課，請調整", courseId);
				}
				
				if (reqCredit <= (restCredit) && reqMember < 3) {
					reqNewMember = reqMember + 1;
					courseNewIdList.add(courseId);// 新的清單收集新增課程
					courseNewIdList.addAll(courseIdList); // 過去選課+新的課程都統一到新的清單
					for (String item : courseNewIdList) {
						Optional<Course> courseExistOp = courseDao.findById(item);
						Course bothNewAndOldCourseInfo = courseExistOp.get();// 過去的選個資料
						bothNewAndOldCourseInfo.setCourseMember(reqNewMember);// 新的課程人數
						courseDao.save(bothNewAndOldCourseInfo); // 更新
//						finalList.add(bothNewAndOldCourseInfo);
					}
				}

			}

			if (!courseNewIdList.equals(courseIdList)) {
				String string = "";
				student.setCourseId(string);
				studentDao.save(student);

				for (String newAddCourse : courseNewIdList) {
					String strNewId = newAddCourse.toString();
					string = strNewId + ";" + string;
				}student.setCourseId(string);
				 studentDao.save(student);

			}
			
			for (Course courseItem : courseWholeList) {
				courseItem.setCourseCredit(null);
				courseItem.setCourseDate(null);
				courseItem.setCourseEndTime(null);
				courseItem.setCourseStartTime(null);
				courseItem.setCourseMember(null);
				tidyList.add(courseItem);

			}
			return new StudentResponse("加選完成。", tidyList, null);

		} else {// 退選

			List<Course> existCourseInfo = new ArrayList<>(); // 根據學生已有ID迴圈收集的所有課程資訊
			List<String> courseIdList = new ArrayList<>();// 已選過CourseID List
			List<String> courseDropWholeList = req.getDropList();// 退課
			List<Course> ensureDropList = new ArrayList<>();// 退課確認

			if (!StringUtils.hasText(req.getStudentId()) || !StringUtils.hasText(req.getStudentName())) {
				return new StudentResponse("404,基本資料(【學號】及【姓名】不允許空白，請調整並重發請求)");
			}
			Optional<Student> dropOp = studentDao.findById(req.getStudentId());
			if (!dropOp.isPresent()) {
				return new StudentResponse("404,學生資訊不存在，請重下資訊");
			}
			Student student = dropOp.get();// 獲取【原先】單筆資料

			String stuClassID = student.getCourseId().trim();
			String[] str = stuClassID.split(";");// 將{A;B;C}拆成單一筆數清單

			for (String item : str) {
				courseIdList.add(item);

				Optional<Course> courseOp = courseDao.findById(item);
				if (!courseOp.isPresent()) {
					return new StudentResponse("404,課程資訊不存在，請重下資訊");
				}
				Course existedCourseInfo = courseOp.get();
				existCourseInfo.add(existedCourseInfo);// 學生已經選上的課程所有資訊
			}

			for (String classId : courseDropWholeList) { // 從請求而來
				Optional<Course> courseOp = courseDao.findById(classId);
				if (!courseOp.isPresent()) {
					return new StudentResponse("404,課程資訊不存在，請重下資訊");
				}
				Course CourseInfo = courseOp.get();
				ensureDropList.add(CourseInfo);// 學生請求的退掉清單
				int member = CourseInfo.getCourseMember();
				member = member - 1;
				CourseInfo.setCourseMember(member);
				courseDao.save(CourseInfo);// 釋出名額

				String string = " ";
				for (int i = 0; i < courseIdList.size(); i++) {
					String classExistedId = courseIdList.get(i);
					for (int j = 0; j < courseDropWholeList.size(); j++) {
						String checkId = courseIdList.get(j);
						if (classExistedId.equals(checkId)) {
							continue;
						}

						else {
							String strNewId = classExistedId.toString();
							string = strNewId + ";" + string;

						}

					}
				}
				student.setCourseId(string);
				studentDao.save(student);

			}
			return new StudentResponse("退選完成");
		}
	}

	@Override
	public StudentResponse addCourse(StudentRequest req) {

		return doAddOrDropCourse(req, true);
	}

	@Override
	public StudentResponse dropCourse(StudentRequest req) {

		return doAddOrDropCourse(req, false);
	}

}
