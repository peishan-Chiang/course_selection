package com.example.course_selection.respository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.course_selection.entity.Student;



@ Repository
public interface StudentDao extends JpaRepository<Student,String> {

	void save(String word);


}
