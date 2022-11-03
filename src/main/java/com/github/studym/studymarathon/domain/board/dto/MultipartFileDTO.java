package com.github.studym.studymarathon.domain.board.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MultipartFileDTO {

    private List<MultipartFile> files;
}
