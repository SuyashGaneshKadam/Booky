package com.SK.Library.Management.System.Services;

import com.SK.Library.Management.System.Enums.CardStatus;
import com.SK.Library.Management.System.Models.Student;
import com.SK.Library.Management.System.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    JavaMailSender javaMailSender;

    public String addStudent(Student student) throws Exception{
        if(student.getRollNo() != null){
            throw new Exception("Please don't enter Roll Number");
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String body = "Hi " + student.getName() + ",\n \n" +
                "You are successfully registered with Booky - a platform for variety of books." +
                "A library card will be issued to you shortly.\n" +
                "You can explore books on our platform and select some books according to your preferences.\n" +
                "We hope you have a good time reading books.\n\n\n" +
                "Best Regards,\n" +
                "Team Booky";

        mailMessage.setSubject("Welcome to Booky");
        mailMessage.setFrom("letstravelbot@gmail.com");
        mailMessage.setTo(student.getEmailId());
        mailMessage.setText(body);
        javaMailSender.send(mailMessage);
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
