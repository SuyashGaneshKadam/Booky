package com.SK.Library.Management.System.Repositories;

import com.SK.Library.Management.System.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
