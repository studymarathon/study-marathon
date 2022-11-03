package com.github.studym.studymarathon.domain.board.service;

import com.github.studym.studymarathon.domain.board.dto.BoardDTO;
import com.github.studym.studymarathon.domain.board.dto.PageRequestDTO;
import com.github.studym.studymarathon.domain.board.dto.PageResultDTO;
import com.github.studym.studymarathon.domain.board.entity.Board;
import com.github.studym.studymarathon.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public Long register(BoardDTO dto) {

        log.info("----------------------------------");
        log.info(dto);

        Board entity = dtoToEntity(dto);

        log.info(entity);

        boardRepository.save(entity);

        return entity.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Board> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("bno").descending());

        Page<Board> result = boardRepository.findAll(pageable);

        Function<Board, BoardDTO> fn = (this::entityToDto);

        return new PageResultDTO<>(result, fn);
    }

    /** 2022-08-31 곰 메서드 구현 상세 어떤 파라미터 어떤 형으로 리턴 받을지 이런식으로 */
    @Override
    public String gom() {
        return null;
    }


}
