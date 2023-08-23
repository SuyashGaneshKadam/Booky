package com.SK.Library.Management.System.Controllers;

import com.SK.Library.Management.System.Models.LibraryCard;
import com.SK.Library.Management.System.Services.LibraryCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libraryCard")
public class LibraryCardController {
    @Autowired
    LibraryCardService libraryCardService;

    @PostMapping("/add")
    public ResponseEntity addCard(@RequestBody LibraryCard libraryCard){
        try{
            return new ResponseEntity(libraryCardService.addCard(libraryCard), HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/issueCardToStudent")
    public ResponseEntity issueCardToStudent(@RequestParam("cardNo") Integer cardNo, @RequestParam("rollNo")Integer rollNo){
        try{
            return new ResponseEntity(libraryCardService.issueCardToStudent(cardNo, rollNo),HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
