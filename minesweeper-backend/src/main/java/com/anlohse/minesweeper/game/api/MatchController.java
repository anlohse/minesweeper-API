package com.anlohse.minesweeper.game.api;

import com.anlohse.minesweeper.commons.entities.MinesweeperMatch;
import com.anlohse.minesweeper.commons.services.MatchService;
import com.anlohse.minesweeper.commons.services.UserService;
import com.anlohse.minesweeper.commons.vo.DoMoveVO;
import com.anlohse.minesweeper.commons.vo.GameMoveVO;
import com.anlohse.minesweeper.commons.vo.MinesweeperMatchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static com.anlohse.minesweeper.commons.mapper.MinesweeperMatchMapper.INSTANCE;

@RestController
@RequestMapping("api/match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('SCOPE_user')")
    @GetMapping
    @Transactional
    public ResponseEntity<MinesweeperMatchVO> newMatch(@RequestParam("width") int width, @RequestParam("height") int height, @RequestParam("mines") int mines) {
        MinesweeperMatch match = matchService.newGame(width, height, mines, System.nanoTime(), userService.currentUser());
        return ResponseEntity.ok(INSTANCE.matchToVO(match));
    }

    @PreAuthorize("hasAuthority('SCOPE_user')")
    @GetMapping("{matchId}")
    @Transactional(readOnly = true)
    public ResponseEntity<MinesweeperMatchVO> load(@PathVariable("matchId") long matchId) {
        MinesweeperMatch match = matchService.findByIdAndUserId(matchId, userService.currentUser().getId());
        return ResponseEntity.ok(INSTANCE.matchToVO(match));
    }

    @PreAuthorize("hasAuthority('SCOPE_user')")
    @PostMapping
    @Transactional
    public ResponseEntity<GameMoveVO> doMove(@RequestBody DoMoveVO vo) {
        GameMoveVO moveVO = matchService.doMove(vo.getMatchId(), vo.getX(), vo.getY(), vo.isMark(), vo.getTimer());
        return ResponseEntity.ok(moveVO);
    }

}
