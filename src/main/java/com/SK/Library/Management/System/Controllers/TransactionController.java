package com.SK.Library.Management.System.Controllers;

import com.SK.Library.Management.System.Models.Transaction;
import com.SK.Library.Management.System.Services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @PostMapping("/issueBook")
    public ResponseEntity issueBook(@RequestParam Integer cardNo, @RequestParam Integer bookId){
        try{
            return new ResponseEntity(transactionService.issueBook(cardNo,bookId), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/returnBook")
    public ResponseEntity returnBook(@RequestParam Integer cardNo, @RequestParam Integer bookId){
        try{
            return new ResponseEntity(transactionService.returnBook(cardNo,bookId), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/totalFineInYear")
    public ResponseEntity totalFineInYear(@RequestParam Integer year){
        try{
            return new ResponseEntity(transactionService.totalFineInYear(year),HttpStatus.OK);
        }catch (Exception e){
            log.error("Some Exception occurred : " + e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/studentsWithCardNotActive")
    public ResponseEntity studentsWithCardNotActive(){
        try{
            return new ResponseEntity(transactionService.studentsWithCardNotActive(),HttpStatus.OK);
        }catch (Exception e){
            log.error("Some Exception occurred : " + e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/studentsWhoReadMaxDistinctBooks")
    public ResponseEntity studentsWhoReadMaxDistinctBooks(){
        try{
            return new ResponseEntity(transactionService.studentsWhoReadMaxDistinctBooks(),HttpStatus.OK);
        }catch (Exception e){
            log.error("Some Exception occurred : " + e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/bookWithMaxFine")
    public ResponseEntity bookWithMaxFine(){
        try{
            return new ResponseEntity(transactionService.bookWithMaxFine(),HttpStatus.OK);
        }catch (Exception e){
            log.error("Some Exception occurred : " + e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/mostPopularAuthor")
    public ResponseEntity mostPopularAuthor(){
        try{
            return new ResponseEntity(transactionService.mostPopularAuthor(),HttpStatus.OK);
        }catch (Exception e){
            log.error("Some Exception occurred : " + e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}