package com.SK.Library.Management.System.Models;

import com.SK.Library.Management.System.Enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardNo;

    @Enumerated(value = EnumType.STRING)
    private CardStatus cardStatus;

    private Integer numberOfBooksIssued;

    @OneToOne
    @JoinColumn
    private Student student;

    @OneToMany(mappedBy = "libraryCard", cascade = CascadeType.ALL)
    private List<Transaction> transactionList = new ArrayList<>();
}
