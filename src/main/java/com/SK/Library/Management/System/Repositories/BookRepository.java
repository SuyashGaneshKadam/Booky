package com.SK.Library.Management.System.Repositories;

import com.SK.Library.Management.System.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
