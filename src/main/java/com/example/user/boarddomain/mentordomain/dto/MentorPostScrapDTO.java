package com.example.user.boarddomain.mentordomain.dto;

import com.example.user.userdomain.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MentorPostScrapDTO {

    private UserDTO userDTO;

    private MentorPostDTO mentorPostDTO;
}
