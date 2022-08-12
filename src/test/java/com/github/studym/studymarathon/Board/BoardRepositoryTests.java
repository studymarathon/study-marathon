package com.github.studym.studymarathon.Board;

import com.github.studym.studymarathon.domain.board.entity.Board;
import com.github.studym.studymarathon.domain.board.repository.BoardRepository;
import com.github.studym.studymarathon.domain.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;


    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 199).forEach(i -> {
            Board board = Board.builder()
                    .title("제목" + i)
                    .content("내용애용내용" + i)
                    .author("작성자" + i)
                    .build();
            System.out.println(boardRepository.save(board));
        });
    }

    @Test
    public void updateTest() {
        Optional<Board> result = boardRepository.findById(298L);

        if (result.isPresent()) {
            Board board = result.get();

            board.changeTitle("변경한 제목");
            board.changeContent("변경한 내용");

            boardRepository.save(board);
        }
    }

    @Test
    public void test(){
        logic(199L);
    }

    public Board updateOrElse(Long id){
        Optional<Board> optional = boardRepository.findById(id);

        return optional.orElse(null);
    }


    public void logic(Long id){
        Board board = updateOrElse(id);
        board.changeContent("바꾼내용이라구우");
        System.out.println(boardRepository.save(board));
    }








}
