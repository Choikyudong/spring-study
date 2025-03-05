package com.example.boardservice.controller;

import com.example.boardservice.dto.BoardDetailDTO;
import com.example.boardservice.dto.BoardSaveDTO;
import com.example.boardservice.entity.Board;
import com.example.boardservice.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	@GetMapping
	public ResponseEntity<List<Board>> getBoards() {
		return ResponseEntity.ok(boardService.getBoards());
	}

	@GetMapping("/{id}")
	public ResponseEntity<BoardDetailDTO> getBoard(@PathVariable Long id) {
		return ResponseEntity.ok(boardService.getBoard(id));
	}

	@PostMapping
	public ResponseEntity<String> saveBoard(@RequestBody BoardSaveDTO boardSaveDTO) {
		boardService.saveBoard(boardSaveDTO);
		return ResponseEntity.ok("Ok");
	}

}
