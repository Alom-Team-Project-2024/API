package com.example.user.boarddomain.questiondomain.dto;

import com.example.user.userdomain.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class QuestionPostScrapDTO {

    private UserDTO userDTO;

    private QuestionPostDTO questionPostDTO;
}
