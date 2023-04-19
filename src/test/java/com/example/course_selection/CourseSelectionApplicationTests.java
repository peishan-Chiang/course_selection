package com.example.course_selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import com.example.course_selection.entity.Course;
import com.example.course_selection.entity.Student;
import com.example.course_selection.respository.CourseDao;
import com.example.course_selection.respository.StudentDao;
import com.example.course_selection.service.ifs.CourseService;
import com.example.course_selection.service.ifs.StudentService;
import com.example.course_selection.vo.StudentResponse;






@SpringBootTest(classes=CourseSelectionApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourseSelectionApplicationTests {
//	List<Course> list1=new ArrayList<>();
//	List<Student> list2=new ArrayList<>();
//	@Autowired
//	private StudentDao studentDao;
//
//	@Autowired
//	private CourseDao courseDao;
//	
//	@Autowired
//	private StudentService studentService;
//	@Autowired
//	private CourseService courseService;
//	
//	
//	@Test
//	void contextLoads() {
//		Set<String> classSelect = new HashSet<String>(); // Set避免重複值(名稱)
//		List<String> courseList = Arrays.asList( "001", "002", "003", "004","005","006","007");
//		for (String courseID : courseList) {
//			classSelect.add("經濟");// 課程名稱加入hash清單
//			classSelect.add("社會");
//			classSelect.add("經濟");
//			classSelect.add("統計");
//			String nameSentence = classSelect.toString(); // 單筆就轉成字串變數;
//			System.out.println(classSelect);
//			System.out.println(nameSentence);
//			
//			
//		}
//		
//	}
	
//	@AfterEach//通常用於刪除在@BeforeEach 新增的測試資料
//	   public void afterEachTest() {
//		courseDao.deleteAll(list1);
//		 studentDao.deleteAll(list2);
//	   }
//	   
//	   @BeforeAll//通常用在新增假資料
//	   public void beforeAllTest() {
//		  Course course1 = new Course("001","testName","1","0900","1200","3",3);
//		  Course course2 = new Course("002","testName","1","0930","1100","3",3);
//		  Course course3 = new Course("003","testName","2","1000","1200","3",3);
//		  list1= Arrays.asList(course1,course2,course3);
//		  courseDao.saveAll(list1);
//		  
//		  Student student1=new  Student("a001","test","");
//		  Student student2=new  Student("a001","test","");
//		  Student student3=new  Student("a001","test","");
//		  list2= Arrays.asList(student1,student2,student3);
//		  studentDao.saveAll(list2);
//		  
	   }


