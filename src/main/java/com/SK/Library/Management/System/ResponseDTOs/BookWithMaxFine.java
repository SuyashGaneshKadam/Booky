package com.SK.Library.Management.System.ResponseDTOs;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookWithMaxFine {
    private String bookTitle;
    private Integer totalFineAmount;
}

