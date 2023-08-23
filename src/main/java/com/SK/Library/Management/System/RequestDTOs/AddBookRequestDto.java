package com.SK.Library.Management.System.RequestDTOs;

import com.SK.Library.Management.System.Enums.Genre;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddBookRequestDto {
    private Integer authorId;
    private String title;
    private Boolean isAvailable;
    private Genre genre;
    private LocalDate publicationDate;
    private Integer price;
}
