package com.SK.Library.Management.System.Models;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authorId;

    private String name;

    @Column(unique = true)
    private String emailId;

    private Integer age;

    private String penName;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> bookList = new ArrayList<>();
}
