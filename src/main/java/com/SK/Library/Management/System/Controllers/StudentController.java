package com.SK.Library.Management.System.Controllers;

import com.SK.Library.Management.System.Models.Student;
import com.SK.Library.Management.System.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity addStudent(@RequestBody Student student){
        try{
            String result = studentService.addStudent(student);
            return new ResponseEntity(result, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
    @GetMapping("/findDeptByRollNo")
    public ResponseEntity findDeptByRollNo(@RequestParam("rollNo")Integer rollNo){
        try{
            return new ResponseEntity(studentService.findDeptByRollNo(rollNo), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCardStatusByRollNo")
    public ResponseEntity getCardStatusByRollNo(@RequestParam("rollNo") Integer rollNo){
        try {
            return new ResponseEntity(studentService.getCardStatusByRollNo(rollNo), HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}