package com.github.studym.studymarathon.controller;

import com.github.studym.studymarathon.domain.board.dto.BoardDTO;
import com.github.studym.studymarathon.domain.board.dto.MultipartFileDTO;
import com.github.studym.studymarathon.domain.board.dto.PageRequestDTO;
import com.github.studym.studymarathon.domain.board.dto.PageResultDTO;
import com.github.studym.studymarathon.domain.board.entity.Board;
import com.github.studym.studymarathon.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;

    @Value("${com.github.studym.studymarathon.path}")
    private String uploadPath;

    @GetMapping("/list")
    public PageResultDTO<BoardDTO, Board> list() {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        log.info("list.........." + pageRequestDTO);

        return service.getList(pageRequestDTO);

    }

    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void register(BoardDTO dto, MultipartFileDTO fileDTO) {
        service.register(dto);

        log.info("/BoardController/register");
        log.info("boardDTO: " + dto);
        log.info("fileDTO: " + fileDTO);

        if (fileDTO != null) {
            fileDTO.getFiles().forEach(multipartFile -> {
                String originalFilename = multipartFile.getOriginalFilename();
                log.info(originalFilename);
                String uuid = UUID.randomUUID().toString();
                Path savePath = Paths.get(uploadPath, uuid + "_" + originalFilename);

                try {
                    multipartFile.transferTo(savePath);

                    if (Files.probeContentType(savePath).startsWith("image")) {
                        File thumbFile = new File(uploadPath, "s_" + uuid + "_" + originalFilename);

                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void update(){

    }

}
