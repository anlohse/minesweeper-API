package com.anlohse.minesweeper.game.api;

import com.anlohse.minesweeper.commons.entities.MinesweeperMatch;
import com.anlohse.minesweeper.commons.exceptions.InvalidRequestException;
import com.anlohse.minesweeper.commons.exceptions.ResourceNotFoundException;
import com.anlohse.minesweeper.commons.services.MatchService;
import com.anlohse.minesweeper.commons.services.UserService;
import com.anlohse.minesweeper.commons.vo.GameMoveVO;
import com.anlohse.minesweeper.commons.vo.MinesweeperMatchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.anlohse.minesweeper.commons.mapper.MinesweeperMatchMapper.INSTANCE;

@RestController
@RequestMapping("api/game/match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('SCOPE_user')")
    @GetMapping
    public ResponseEntity<MinesweeperMatchVO> newMatch(@RequestParam("width") int width, @RequestParam("height") int height, @RequestParam("mines") int mines) {
        MinesweeperMatch match = matchService.newGame(width, height, mines, System.nanoTime(), userService.currentUser());
        return ResponseEntity.ok(INSTANCE.matchToVO(match));
    }

    @PreAuthorize("hasAuthority('SCOPE_user')")
    @GetMapping("{matchId}")
    public ResponseEntity<MinesweeperMatchVO> load(@PathVariable("matchId") long matchId) {
        MinesweeperMatch match = matchService.findByIdAndUserId(matchId, userService.currentUser().getId());
        return ResponseEntity.ok(INSTANCE.matchToVO(match));
    }

    @PreAuthorize("hasAuthority('SCOPE_user')")
    @PostMapping
    public ResponseEntity<GameMoveVO> doMove(@RequestParam("matchId") long matchId, @RequestParam("x") int x, @RequestParam("y") int y, @RequestParam("mark") boolean mark) {
        GameMoveVO moveVO = matchService.doMove(matchId, x, y, mark);
        return ResponseEntity.ok(moveVO);
    }

}
