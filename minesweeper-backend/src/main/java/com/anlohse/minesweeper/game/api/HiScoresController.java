package com.anlohse.minesweeper.game.api;

import com.anlohse.minesweeper.commons.services.HiScoreService;
import com.anlohse.minesweeper.commons.services.UserService;
import com.anlohse.minesweeper.commons.vo.HiScoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/hiscores")
public class HiScoresController {

    @Autowired
    private HiScoreService hiScoreService;

    @Autowired
    private UserService userService;

    @GetMapping("{count}")
    @Transactional
    public ResponseEntity<List<HiScoreVO>> hiScores(@PathVariable("count") int count) {
        List<HiScoreVO> hiScores = hiScoreService.findTopAllHiScores(count);
        return ResponseEntity.ok(hiScores);
    }

    @GetMapping("user/{count}")
    @Transactional
    public ResponseEntity<List<HiScoreVO>> hiScoresUser(@PathVariable("count") int count) {
        List<HiScoreVO> hiScores = hiScoreService.findTopUserHiScores(count, userService.currentUser());
        return ResponseEntity.ok(hiScores);
    }

}
