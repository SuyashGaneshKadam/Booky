package com.SK.Library.Management.System.Controllers;

import com.SK.Library.Management.System.Models.Author;
import com.SK.Library.Management.System.RequestDTOs.AgeAndPenNameRequestDto;
import com.SK.Library.Management.System.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorService authorService;
    //AuthorService authorService = new AuthorService();

    @PostMapping("/add")
    public ResponseEntity addAuthor(@RequestBody Author author){
        try{
            return new ResponseEntity(authorService.addAuthor(author),HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PutMapping("/updateAgeAndPenName")
    public ResponseEntity updateAgeAndPenName(@RequestBody AgeAndPenNameRequestDto requestDto){
        try {
            return new ResponseEntity(authorService.updateAgeAndPenName(requestDto),HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
    @GetMapping("/getAuthorById")
    public ResponseEntity getAuthorById(@RequestParam("authorId") Integer authorId){
        try{
            return new ResponseEntity(authorService.getAuthorById(authorId), HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/getBookTitlesByAuthorId")
    public ResponseEntity getBookTitlesByAuthorId(@RequestParam("authorId")Integer authorId){
        try{
            return new ResponseEntity(authorService.getBookTitlesByAuthorId(authorId),HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/getAuthors")
    public ResponseEntity getAuthors(){
        try{
            return new ResponseEntity(authorService.getAuthors(),HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}