package com.SK.Library.Management.System.Models;

import com.SK.Library.Management.System.Enums.*;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    @Column(unique = true)
    private String title;

    private Boolean isAvailable;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    private LocalDate publicationDate;

    private Integer price;

    @ManyToOne
    @JoinColumn
    private Author author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Transaction> transactionList = new ArrayList<>();

    public Book(String title, Boolean isAvailable, Genre genre, LocalDate publicationDate, Integer price) {
        this.title = title;
        this.isAvailable = isAvailable;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.price = price;
    }
}
