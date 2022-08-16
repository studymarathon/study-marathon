package com.github.studym.studymarathon.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardDTO {

    private Long bno;

    private String author;

    private String title;

    private String content;

    private LocalDateTime regDate, modDate;
}
