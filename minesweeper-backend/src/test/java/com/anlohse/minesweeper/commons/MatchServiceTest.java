package com.anlohse.minesweeper.commons;

import com.anlohse.minesweeper.commons.entities.MinesweeperMatch;
import com.anlohse.minesweeper.commons.entities.User;
import com.anlohse.minesweeper.commons.repositories.MinesweeperMatchRepository;
import com.anlohse.minesweeper.commons.services.MatchService;
import com.anlohse.minesweeper.game.MinesweeperGameApplication;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = MinesweeperGameApplication.class)
public class MatchServiceTest {

    @MockBean
    private MinesweeperMatchRepository minesweeperMatchRepository;

    @Autowired
    private MatchService matchService;

    @Test
    public void testNewMatch() {
        when(minesweeperMatchRepository.save(any())).thenAnswer(a -> a.getArguments()[0]);
        MinesweeperMatch match = matchService.newGame(10, 10, 10, 1234567L, User.builder().id(1L).build());
        byte[] data = Base64.decodeBase64(match.getData());
        assertEquals(1, data.length);
    }

}
