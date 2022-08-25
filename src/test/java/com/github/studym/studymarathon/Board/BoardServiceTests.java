package com.github.studym.studymarathon.Board;

import com.github.studym.studymarathon.domain.board.dto.BoardDTO;
import com.github.studym.studymarathon.domain.board.dto.PageRequestDTO;
import com.github.studym.studymarathon.domain.board.dto.PageResultDTO;
import com.github.studym.studymarathon.domain.board.entity.Board;
import com.github.studym.studymarathon.domain.board.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService service;

    public void testRegister(String title, String content, String Author) {
        BoardDTO boardDTO = BoardDTO.builder().title(title).content(content).author(Author).build();

        /*System.out.println(service.register(boardDTO));*/
    }

    @Test
    public void test() {
        /*testRegister("곰곰", "내용곰", "콜라곰");*/
        listTest(1,10);
    }

    public void listTest(int page, int size) {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(page)
                .size(size)
                .build();

        PageResultDTO<BoardDTO, Board> resultDTO = service.getList(pageRequestDTO);

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("---------------------------------");
        for (BoardDTO boardDTO : resultDTO.getDtoList()) {
            System.out.println(boardDTO);
        }

        System.out.println("----------------------------------");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

}
