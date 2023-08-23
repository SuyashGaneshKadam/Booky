package com.SK.Library.Management.System.Repositories;

import com.SK.Library.Management.System.Models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
