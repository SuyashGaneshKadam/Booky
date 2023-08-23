package com.SK.Library.Management.System.Controllers;

import com.SK.Library.Management.System.Enums.Genre;
import com.SK.Library.Management.System.RequestDTOs.*;
import com.SK.Library.Management.System.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;
    //BookService bookService = new BookService();

    @PostMapping("/add")
    public ResponseEntity addBook(@RequestBody AddBookRequestDto requestDto){
        try{
            return new ResponseEntity(bookService.addBook(requestDto), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
    @GetMapping("/countOfBooksOfGenre")
    public ResponseEntity countOfBooksOfGenre(@RequestParam("genre")Genre genre){
        try {
            return new ResponseEntity(bookService.countOfBooksOfGenre(genre), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
    @GetMapping("/get")
    public ResponseEntity getBook(@RequestParam("bookId") Integer bookId){
        try {
            return new ResponseEntity(bookService.getBook(bookId), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
