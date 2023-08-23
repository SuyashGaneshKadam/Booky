package com.SK.Library.Management.System.Services;

import com.SK.Library.Management.System.Enums.Genre;
import com.SK.Library.Management.System.Models.*;
import com.SK.Library.Management.System.Repositories.*;
import com.SK.Library.Management.System.RequestDTOs.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    public String addBook(AddBookRequestDto requestDto) throws Exception{
        if(!authorRepository.existsById(requestDto.getAuthorId())){
            throw new Exception("Invalid Author ID");
        }
        Author author = authorRepository.findById(requestDto.getAuthorId()).get();
        Book book = new Book(requestDto.getTitle(), requestDto.getIsAvailable(), requestDto.getGenre(), requestDto.getPublicationDate(), requestDto.getPrice());

        book.setAuthor(author);
        List<Book> bookList = author.getBookList();
        bookList.add(book);
        author.setBookList(bookList);

        authorRepository.save(author);

        return "Book data has been added successfully";
    }
    public Integer countOfBooksOfGenre(Genre genre) throws Exception{
//        boolean flag = false;
//        for (Genre currentGenre : Genre.values()) {
//            if (currentGenre == genre) {
//                flag = true;
//                break;
//            }
//        }
//        if(!flag){
//            throw new Exception("Invalid Genre");
//        }
        int count = 0;
        List<Book> bookList = bookRepository.findAll();
        for(Book book : bookList){
            if(book.getGenre() == genre){
                count++;
            }
        }
        return count;
    }
    public Book getBook(Integer bookId) throws Exception{
        if(!bookRepository.existsById(bookId)){
            throw new Exception("Invalid Book Id");
        }
        return bookRepository.findById(bookId).get();
    }
}