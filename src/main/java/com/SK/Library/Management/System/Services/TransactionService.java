package com.SK.Library.Management.System.Services;

import com.SK.Library.Management.System.CustomExceptions.*;
import com.SK.Library.Management.System.Enums.*;
import com.SK.Library.Management.System.Models.*;
import com.SK.Library.Management.System.Repositories.*;
import com.SK.Library.Management.System.ResponseDTOs.BookWithMaxFine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    LibraryCardRepository libraryCardRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    LibraryCardRepository cardRepository;

    @Autowired
    AuthorRepository authorRepository;
    @Value("${book.maxBookLimit}")
    private Integer maxBookLimit;

    //private static final Integer maxBookLimit = 2;

    public String issueBook(Integer cardNo, Integer bookId) throws Exception{
        if(!libraryCardRepository.existsById(cardNo)){
            throw new CardNotFoundException("Invalid Card Number");
        }
        if(!bookRepository.existsById(bookId)){
            throw new BookNotFoundException("Invalid Book ID");
        }

        Transaction transaction = new Transaction(TransactionStatus.Pending, TransactionType.Issue,0);

        Book book = bookRepository.findById(bookId).get();
        LibraryCard card = libraryCardRepository.findById(cardNo).get();

        if(book.getIsAvailable() == Boolean.FALSE){
            transaction.setTransactionStatus(TransactionStatus.Failed);
            transaction = transactionRepository.save(transaction);
            throw new BookNotAvailableException("Currently book is not available");
        }
        if(card.getCardStatus() != CardStatus.Active && card.getCardStatus() != CardStatus.Issued){
            transaction.setTransactionStatus(TransactionStatus.Failed);
            transaction = transactionRepository.save(transaction);
            throw new CardStatusException("Book cannot be issued for your current Card Status");
        }
        if(card.getNumberOfBooksIssued() == maxBookLimit){
            transaction.setTransactionStatus(TransactionStatus.Failed);
            transaction = transactionRepository.save(transaction);
            throw new MaxBookLimitReachedException("Book cannot be issued as your maximum book limit has been reached. Kindly return some of your books.");
        }

        //All failed and invalid cases are done

        transaction.setTransactionStatus(TransactionStatus.Success);

        if(card.getNumberOfBooksIssued() == 0){
            card.setCardStatus(CardStatus.Active);
        }
        card.setNumberOfBooksIssued(card.getNumberOfBooksIssued()+1);
        book.setIsAvailable(Boolean.FALSE);

        transaction.setBook(book);
        transaction.setLibraryCard(card);

        Transaction transactionWithID = transactionRepository.save(transaction);

        book.getTransactionList().add(transactionWithID);
        card.getTransactionList().add(transactionWithID);

        bookRepository.save(book);
        libraryCardRepository.save(card);

        return "Book issued successfully";
    }

    public String returnBook(Integer cardNo, Integer bookId) throws Exception{
        if(!libraryCardRepository.existsById(cardNo)){
            throw new CardNotFoundException("Invalid Card Number");
        }
        if(!bookRepository.existsById(bookId)){
            throw new BookNotFoundException("Invalid Book ID");
        }

        Book book = bookRepository.findById(bookId).get();
        LibraryCard card = libraryCardRepository.findById(cardNo).get();

        List<Transaction> transactionList = transactionRepository.findTransactionsByBookAndLibraryCardAndTransactionStatusAndTransactionType(book,card,TransactionStatus.Success,TransactionType.Issue);

        if(transactionList.size() == 0){
            throw new Exception("There is no Book with ID : " + bookId + " issued to Card No : " + cardNo);
        }
        Transaction latestTransaction = transactionList.get(transactionList.size()-1);
        Date issueDate = latestTransaction.getCreatedAt();
        long milliSecondTime = Math.abs(System.currentTimeMillis() - issueDate.getTime());
        long noOfDaysIssued = TimeUnit.DAYS.convert(milliSecondTime,TimeUnit.MILLISECONDS);

        int fineAmount = 0;
        if(noOfDaysIssued > 15){
            fineAmount = (int)((noOfDaysIssued - 15) * 5);
        }
        Transaction transaction = new Transaction(TransactionStatus.Success, TransactionType.Return, fineAmount);
        book.setIsAvailable(Boolean.TRUE);
        if(card.getNumberOfBooksIssued() == 1){
            card.setCardStatus(CardStatus.Issued);
        }
        card.setNumberOfBooksIssued(card.getNumberOfBooksIssued() - 1);

        transaction.setBook(book);
        transaction.setLibraryCard(card);

        Transaction transactionWithId = transactionRepository.save(transaction);

        book.getTransactionList().add(transactionWithId);
        card.getTransactionList().add(transactionWithId);

        bookRepository.save(book);
        libraryCardRepository.save(card);

        return "Book return successfully";
    }
    public Integer totalFineInYear(Integer year) throws Exception{
        List<Transaction> transactionList = transactionRepository.findTransactionsByTransactionStatusAndTransactionType(TransactionStatus.Success, TransactionType.Return);
        if(transactionList.size() == 0){
            log.info("There are no transactions which are in Success status and return type");
            return 0;
        }
        Integer totalFine = 0;
        for(Transaction transaction : transactionList){
            Integer yearOfTransaction = transaction.getCreatedAt().getYear()+1900;
            if(yearOfTransaction == year){
                totalFine += transaction.getFineAmount();
            }
        }
        return totalFine;
    }
    public List<String> studentsWithCardNotActive() throws Exception{
        List<LibraryCard> libraryCardList = cardRepository.findAll();
        List<Student> studentList = studentRepository.findAll();
        if(libraryCardList.size() == 0){
            log.error("Card table is empty");
            throw new CardTableEmptyException("There is no card data present");
        }
        if(studentList.size() == 0){
            log.error("Student table is empty");
            throw new StudentTableEmptyException("There is no student data present");
        }
        List<String> studentNames = new ArrayList<>();
        for(LibraryCard card : libraryCardList){
            if(!card.getCardStatus().equals(CardStatus.Active)){
                Student student = card.getStudent();
                studentNames.add(student.getName());
            }
        }
        return studentNames;
    }
    public String studentsWhoReadMaxDistinctBooks() throws Exception{
        List<Transaction> transactionList = transactionRepository.findTransactionsByTransactionStatusAndTransactionType(TransactionStatus.Success, TransactionType.Issue);
        if(transactionList.size()==0){
            throw new TransactionTableEmptyException("There is no transaction data present");
        }
        HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
        for(Transaction transaction : transactionList){
            Integer cardNo = transaction.getLibraryCard().getCardNo();
            Integer bookId = transaction.getBook().getBookId();
            HashSet<Integer> set = map.getOrDefault(cardNo, new HashSet<>());
            set.add(bookId);
            map.put(cardNo, set);
        }
        int maxNoOfBooksRead = Integer.MIN_VALUE;
        int cardNoWithMaxBooks = 0;
        for(Integer cardNo : map.keySet()){
            if(map.get(cardNo).size() > maxNoOfBooksRead){
                maxNoOfBooksRead = map.get(cardNo).size();
                cardNoWithMaxBooks = cardNo;
            }
        }
        LibraryCard card = cardRepository.findById(cardNoWithMaxBooks).get();
        return card.getStudent().getName();
    }
    public BookWithMaxFine bookWithMaxFine() throws Exception{
        List<Transaction> transactionList = transactionRepository.findTransactionsByTransactionStatusAndTransactionType(TransactionStatus.Success, TransactionType.Return);
        if(transactionList.size()==0){
            throw new TransactionTableEmptyException("There is no transaction data present");
        }
        HashMap<Integer, Integer> map = new HashMap<>();//HM<BookId,TotalFine>
        for(Transaction transaction : transactionList){
            int fine = transaction.getFineAmount();
            Integer bookId = transaction.getBook().getBookId();
            map.put(bookId, map.getOrDefault(bookId, 0) + fine);
        }
        Integer maxFine = Integer.MIN_VALUE;
        String bookTitleWithMaxFine = "";
        for(Integer bookId : map.keySet()){
            Book book = bookRepository.findById(bookId).get();
            if(map.get(bookId) > maxFine){
                maxFine = map.get(bookId);
                bookTitleWithMaxFine = book.getTitle();
            }
            else if(map.get(bookId) == maxFine){
                if(book.getTitle().compareTo(bookTitleWithMaxFine) < 0){
                    bookTitleWithMaxFine = book.getTitle();
                }
            }
        }
        return new BookWithMaxFine(bookTitleWithMaxFine, maxFine);
    }
    public String mostPopularAuthor() throws Exception{
        List<Transaction> transactionList = transactionRepository.findTransactionsByTransactionStatusAndTransactionType(TransactionStatus.Success, TransactionType.Issue);
        if(transactionList.size()==0){
            log.error("Transaction table is empty");
            throw new TransactionTableEmptyException("There is no transaction data present");
        }
        List<Author> authorList = authorRepository.findAll();
        if(authorList.size() == 0){
            log.error("Author table is empty");
            throw new AuthorTableEmptyException("There is no author data present");
        }
        HashMap<String, HashSet<Integer>> map = new HashMap<>(); // Key :- Author Name:bookIds separated by "," -> Value :- Set of unique Card No's
        for(Author author : authorList){
            List<Book> bookList = author.getBookList();
            if(bookList.size() == 0) { continue; }
            String s = author.getName() + ":";
            for (Book book : bookList){
                s += book.getBookId() + ",";
            }
            map.put(s,new HashSet<>());
        }
        int maxStudents = Integer.MIN_VALUE;
        String authorName = "";
        for(String str : map.keySet()){
            for(Transaction transaction : transactionList){
                if(str.contains(transaction.getBook().getBookId() + "")){
                    HashSet<Integer> set = map.getOrDefault(str,new HashSet<>());
                    set.add(transaction.getLibraryCard().getCardNo());
                    map.put(str,set);
                }
            }
            if(map.get(str).size() > maxStudents){
                maxStudents = map.get(str).size();
                authorName = str.substring(0, str.indexOf(":"));
            }
        }
        return authorName;
    }
}