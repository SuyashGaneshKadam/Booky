package com.SK.Library.Management.System.ResponseDTOs;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAuthorByIdResponseDto {

    private String name;
    private String emailId;
    private Integer age;
    private String penName;

}
