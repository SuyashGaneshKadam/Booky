package com.SK.Library.Management.System.Repositories;

import com.SK.Library.Management.System.Models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query(value = "select * from author",nativeQuery = true)
    public List<Author> findAuthors();
}
