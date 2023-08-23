package com.SK.Library.Management.System.Services;

import com.SK.Library.Management.System.Enums.CardStatus;
import com.SK.Library.Management.System.Models.Student;
import com.SK.Library.Management.System.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public String addStudent(Student student) throws Exception{
        if(student.getRollNo() != null){
            throw new Exception("Please don't enter Roll Number");
        }
        studentRepository.save(student);
        return "Student data has been added successfully";
    }
    public Enum findDeptByRollNo(Integer rollNo) throws Exception{
        Optional<Student> optionalStudent = studentRepository.findById(rollNo);
        if(optionalStudent.isEmpty()){
            throw new Exception("Invalid Roll Number");
        }
        return optionalStudent.get().getDepartment();
    }
    public CardStatus getCardStatusByRollNo(Integer rollNo) throws Exception{
        if(!studentRepository.existsById(rollNo)){
            throw new Exception("Invalid Roll Number");
        }
        Student student = studentRepository.findById(rollNo).get();
        if(student.getLibraryCard() == null){
            throw new Exception("Card not assigned yet");
        }
        return student.getLibraryCard().getCardStatus();
    }
}
