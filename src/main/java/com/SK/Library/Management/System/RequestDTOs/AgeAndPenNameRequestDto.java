package com.SK.Library.Management.System.RequestDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgeAndPenNameRequestDto {
    private Integer authorId;
    private Integer age;
    private String penName;
}
