package com.example.boardservice.service;

import com.example.boardservice.dto.BoardDetailDTO;
import com.example.boardservice.dto.BoardSaveDTO;
import com.example.boardservice.dto.UsersResponse;
import com.example.boardservice.entity.Board;
import com.example.boardservice.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private static  final RestTemplate REST_TEMPLATE = new RestTemplate();
	private static final String GATEWAY_URL = "http://localhost:8000";

	public List<Board> getBoards() {
		return boardRepository.findAll();
	}

	public BoardDetailDTO getBoard(Long id) {
		Board board = boardRepository.findById(id)
				.orElseThrow(IllegalArgumentException::new);
		UsersResponse response = REST_TEMPLATE.getForEntity(GATEWAY_URL + "/api/users?id=" + id, UsersResponse.class).getBody();
		return new BoardDetailDTO(
				board.getSubject(),
				board.getContent(),
				response.name()
		);
	}

	public void saveBoard(BoardSaveDTO boardSaveDTO) {
		boardRepository.save(new Board(boardSaveDTO.userId(), boardSaveDTO.subject(), boardSaveDTO.content()));
	}

}
