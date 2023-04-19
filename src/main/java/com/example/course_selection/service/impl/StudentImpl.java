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
	public StudentResponse addStudent(StudentRequest req) {

		List<Student> errorList = new ArrayList<>();
		List<Student> studentList = req.getStudentList();

		for (Student item : studentList) {
			if (!StringUtils.hasText(item.getStudentId()) || !StringUtils.hasText(item.getStudentName())) {
				return new StudentResponse("404,ID and Name were not allow empty");

			}
			if (studentDao.existsById(item.getStudentId())) {
				errorList.add(item);
			}
		}

		if (!errorList.isEmpty()) {
			return new StudentResponse("404,Depulicate student ID", errorList);
		}

		studentDao.saveAll(studentList);
		return new StudentResponse(req.getStudentId(), "add successed!");

	}

	// 學生選課
	@Override
	public StudentResponse selectCourse(StudentRequest req) {
		int left = 10;
		String studentId = req.getStudentId();
		String studentName = req.getStudentName();
		List<String> courseList = req.getCourseList();
		List<String> dateTimeConflict = new ArrayList<>();

		List<String> newDateTimeConflict = new ArrayList<>(); // 新課程擁有同名
		List<String> memberfilled = new ArrayList<>(); // 人數額滿清單
		List<String> nameSame = new ArrayList<>(); // 課程擁有同名
		List<String> nameNewSame = new ArrayList<>(); // 新課程擁有同名
		List<String> outCredit = new ArrayList<>(); // 超出學分的課程
		List<String> okCourse = new ArrayList<>(); // 沒有問題的課程

		Set<String> datecollection = new HashSet<>(); // 收集獨立日期
		Set<String> namecollection = new HashSet<>(); // 收集獨立日期

//		List<String> nameList = new ArrayList<>(); // 裝名字

		if (!StringUtils.hasText(studentId) || !StringUtils.hasText(studentName)) {
			return new StudentResponse("404,ID and Name were not allow empty");

		}

		Optional<Student> op = studentDao.findById(studentId);
		if (!op.isPresent()) {
			return new StudentResponse("404,student is null");
		}
		Student student = op.get();// 獲取單筆資料
		String courseIdvar;
		
		
		
		for (String courseItem : courseList) {
			
			Optional<Course> courseOp = courseDao.findById(courseItem);// 針對課程ID去找課程所有資料
			if (!courseOp.isPresent()) {
				return new StudentResponse("404,coursr info is null");
			}
			Course course = courseOp.get();
			
			String StrID = course.getCourseId();
			String StrName = course.getCourseName();// 每筆的課程姓名
			String StrDay = course.getCourseDate();// 每筆的課程星期
			Integer StrMember=course.getCourseMember();
			
			long countNum = courseDao.countByCourseDateAndCourseStartTimeBetween(course.getCourseDate(),
					course.getCourseStartTime(), course.getCourseEndTime());
			List<Course> courseRepeated = courseDao.findByCourseDateAndCourseStartTimeBetween(course.getCourseDate(),
					course.getCourseStartTime(), course.getCourseEndTime());
			List<Course> fillCourse = courseDao.findByCourseMemberGreaterThanEqual(3);
			long countName = courseDao.countByCourseName(course.getCourseName());
			List<Course> sameNameCourse = courseDao.findByCourseName(course.getCourseName());

			Integer End = Integer.valueOf(course.getCourseEndTime());
			Integer begin = Integer.valueOf(course.getCourseStartTime());
			int during = (End - begin);
			
			
	
			if(countNum>1){
				if(courseRepeated.contains(course)) {
				return new StudentResponse( "dateTimeConflict",course);
				}
				courseList.remove(0);
			}
			if(countName>1){
			if (sameNameCourse.contains(course)) {
				return new StudentResponse( "dateTimeConflict",course);}}
				
//				for (Course timeItem : courseRepeated) {// 一們課當天的開始上課下課(清單)
//					Integer start = Integer.valueOf(timeItem.getCourseStartTime());
//					Integer close = Integer.valueOf(timeItem.getCourseEndTime());
//					if ((begin + during >= close) || (begin >= start && End <= close)) {
//						if (timeItem.getCourseDate().equals(StrDay)) {
//							
						

		

					
			

			
			if (fillCourse.contains(course)) {
				for (Course fillItem : fillCourse) {
					if (StrMember.equals(fillItem.getCourseMember())) {
						memberfilled.add(StrID);
					}

				}return new StudentResponse(memberfilled, "filled course");
			}}

//				for (Course sameNameItem : sameNameCourse) {
//					int length = sameNameItem.getCourseName().length();
					// 我只要前三字一樣的課程
//				if (nameSame.contains(StrName)) {
//					if (StrName.substring(0, length - 1).equals(sameNameItem.getCourseName().substring(0, length - 1))
//							&& countName > 1) {
//						namecollection.add(StrID);
//						if (namecollection.size() > 1) {
//							nameSame.addAll(namecollection);
//						}
//
//					}
//
//				}
//			}

//			if (left >= 0) {
//				left -= course.getCourseCredit();
//				if (left <= 0) {
//					outCredit.add(courseItem);
//				}
//				okCourse.add(courseItem);
//
//			}
		
		
		
		
//		if (!nameSame.isEmpty()) {
//
//			nameNewSame.addAll(namecollection);
//
//			return new StudentResponse(nameNewSame, "Same content course");
//
//		}
				
		if (!outCredit.isEmpty()) {
			return new StudentResponse("not rest of credit could add,please adjust");
		}
		return new StudentResponse(okCourse, "finish selected, thank");
	}}

		
//		List<Integer> sortList = new ArrayList<>();// 日期要確認有沒有重複值
//		List<Integer> sortTimeList = new ArrayList<>(); // 時間確認有沒有重複
//		List<Integer> sortEndList = new ArrayList<>(); // 時間確認有沒有重複
//		List<String> courseList = new ArrayList<>();
//		List<String> courseNewList = new ArrayList<>();
//		Map<Integer, Integer> mapDur = new HashMap();
//		List<String> noApprovList = new ArrayList<>(); // 衝堂不允許選課
//		
//		int num=0;
//
//		courseList = req.getCourseList();
//
//		Optional<Student> op = studentDao.findById(req.getStudentId());
//		if (!op.isPresent()) {
//			return new StudentResponse("404,student is null");
//		}
//		Student student = op.get();// 獲取單筆資料改
//		// 學生沒有請求，不做更新
//		if (courseList.isEmpty()) {
//			return new StudentResponse("404,non Update");
//		}
//
//		for (String item : courseList) {
//			Optional<Course> courseop = courseDao.findById(item);
//			if (!courseop.isPresent()) {
//				return new StudentResponse("404,course is null");
//			}
//			Course course = courseop.get();// 獲取課程資料}
//			Integer date = Integer.parseInt(course.getCourseDate());
//			Integer begin = Integer.parseInt(course.getCourseStartTime());
//			Integer end = Integer.parseInt(course.getCourseEndTime());
//			sortList.add(date);
//			sortTimeList.add(begin);
//			num = (end - begin) / 100;// 幾小時
//			mapDur.put(end, num);// 課程:幾小時
//		
//		
//		
//		String Strdate;
//		Set<String> repeatTimeDur = new HashSet<>();
//		Set<String> repeatBegin = new HashSet<>();
//		Set<String> repeat = new HashSet<>();
//		int tmp = 0;
//		for (int i = 0; i < sortList.size(); i++) {// 找出重複的日期
//			for (int j = i; j < sortList.size(); j++) {
//				if (sortList.get(j) == sortList.get(j+1)) {
//					tmp = sortList.get(j);
//					Integer tmpIn = tmp;
//					Strdate=String.valueOf(tmpIn);
//					repeat.add(Strdate);
//				}
//			}
//		}
//		
//		String StrBegin;
//		String StrrepeatTimeDur;
//
//		for (int i = 0; i < sortTimeList.size(); i++) {// 找出重複的起始時間
//			for (int j = 1; j < sortTimeList.size(); j++) {
//				if (sortList.get(j - 1) == sortList.get(j)) {
//					tmp = sortList.get(j - 1);
//					StrBegin=String.valueOf(tmp);
//					repeatBegin.add(StrBegin);
//				} // 如果我不是同一個上課起始
//
//				if (sortList.get(j- 1) != sortList.get(j)) {
//					Integer classbegin = sortList.get(j - 1);
//					for (Entry<Integer, Integer> entry : mapDur.entrySet()) {
//						if (classbegin + entry.getValue() < entry.getKey()) {// 開始上課不相同，但包含到時間所以應當考慮
//							StrrepeatTimeDur=String.valueOf(classbegin);
//							repeatTimeDur.add(StrrepeatTimeDur);
//						}
//					}
//
//				}
//
//			}
//		}
//
////		String strRepeat = repeat.toString();
////		String strRepeatBegin = repeatBegin.toString();
////		String strrepeatTimeDur = repeatTimeDur.toString();
//
//		for (String item : courseList) {
//			Optional<Course> courseop = courseDao.findById(item);
//			if (!courseop.isPresent()) {
//				return new StudentResponse("404,course is null");
//			}
//			Course course = courseop.get();// 獲取課程資料}
//			if (repeat.contains(course.getCourseDate()) && repeatBegin.contains(course.getCourseStartTime())
//					|| repeat.contains(course.getCourseDate())
//							&& repeatTimeDur.contains(course.getCourseStartTime())) {
//				noApprovList.add(item);
//			} else {
//				courseNewList.add(item);
//			}
//
//		}
//
//		// 有下資料，協助改格式【A;B;C;】
//		String string = " ";
//		for (String courseListitem : courseNewList) {
//			String str = courseListitem.toString();
//			string = str + ";" + string;
//		}
//
//		if (!noApprovList.isEmpty()) {
//			return new StudentResponse(noApprovList, "selected");
//		}
//
//		// 完成格式用更新填入
//		student.setCourseId(string);
//
//		studentDao.save(student);
//		return new StudentResponse("selected");
//	}
