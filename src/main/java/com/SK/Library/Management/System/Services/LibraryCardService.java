package com.SK.Library.Management.System.Services;

import com.SK.Library.Management.System.Enums.CardStatus;
import com.SK.Library.Management.System.Models.LibraryCard;
import com.SK.Library.Management.System.Models.Student;
import com.SK.Library.Management.System.Repositories.LibraryCardRepository;
import com.SK.Library.Management.System.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class LibraryCardService {
    @Autowired
    LibraryCardRepository libraryCardRepository;
    @Autowired
    StudentRepository studentRepository;

    public String addCard(LibraryCard libraryCard) throws Exception{
        if(libraryCard.getCardNo() != null){
            throw new Exception("Please don't enter Card Number");
        }
        libraryCardRepository.save(libraryCard);
        return "Card data has been added successfully";
    }

    public String issueCardToStudent(Integer cardNo, Integer rollNo) throws Exception{
        if(!libraryCardRepository.existsById(cardNo)){
            throw new Exception("Invalid Card Number");
        }
        if(!studentRepository.existsById(rollNo)){
            throw new Exception("Invalid Roll Number");
        }

        Student student = studentRepository.findById(rollNo).get();
        LibraryCard libraryCard = libraryCardRepository.findById(cardNo).get();

        libraryCard.setCardStatus(CardStatus.valueOf("Issued"));
        libraryCard.setStudent(student);
        student.setLibraryCard(libraryCard);

        studentRepository.save(student);
        return "Card assigned to the student successfully";
    }
}
