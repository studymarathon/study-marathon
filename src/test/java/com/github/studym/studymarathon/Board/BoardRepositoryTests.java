package com.github.studym.studymarathon.Board;

import com.github.studym.studymarathon.domain.board.entity.Board;
import com.github.studym.studymarathon.domain.board.entity.QBoard;
import com.github.studym.studymarathon.domain.board.repository.BoardRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {


    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 200).forEach(i -> {
            Board board = Board.builder()
                    .title("제목" + i)
                    .content("내용애용내용" + i)
                    .author("작성자" + i)
                    .build();
            System.out.println(boardRepository.save(board));
        });
    }

    @Test
    public void test() {
//        updateIfPresentTest(199L);
        keywordSearchTest("11");
        multiSearchTest("22");
    }

    public void updateIfPresentTest(Long id) {
        boardRepository.findById(id).ifPresentOrElse(board -> {
            board.updateContent("업데이트된 내용이라구");
            System.out.println(boardRepository.save(board));
        }, () -> System.out.println("조회된 결과가 없다구"));
    }

    public void keywordSearchTest(String keyword) {

        QBoard qBoard = QBoard.board;

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression expression = qBoard.title.contains(keyword);

        builder.and(expression);

        Page<Board> result = boardRepository.findAll(builder, pageable);

        result.stream().forEach(System.out::println);
    }

    public void multiSearchTest(String keyword) {

        Pageable pageable = PageRequest.of(0, 20, Sort.by("bno").descending());

        QBoard qBoard = QBoard.board;

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qBoard.title.contains(keyword);

        BooleanExpression exContent = qBoard.content.contains(keyword);

        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll);

        builder.and(qBoard.bno.gt(0L));

        Page<Board> result = boardRepository.findAll(builder, pageable);

        result.stream().forEach(System.out::println);
    }


}
