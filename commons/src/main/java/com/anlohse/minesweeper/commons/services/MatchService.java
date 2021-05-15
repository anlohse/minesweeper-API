package com.anlohse.minesweeper.commons.services;

import com.anlohse.minesweeper.commons.entities.GameStatus;
import com.anlohse.minesweeper.commons.entities.HiScore;
import com.anlohse.minesweeper.commons.entities.MinesweeperMatch;
import com.anlohse.minesweeper.commons.entities.User;
import com.anlohse.minesweeper.commons.exceptions.ResourceNotFoundException;
import com.anlohse.minesweeper.commons.repositories.MinesweeperMatchRepository;
import com.anlohse.minesweeper.commons.vo.GameMoveVO;
import com.anlohse.minesweeper.commons.vo.MoveStatus;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.anlohse.minesweeper.commons.mapper.MinesweeperMatchMapper.INSTANCE;

/**
 * MatchService implements features related to the match.
 */
@Service
public class MatchService extends AbstractCrudService<MinesweeperMatch, Long> {

    private static final int MINE_MASK = 0x01;
    private static final int CLEARED_MASK = 0x02;
    private static final int FLAG_MASK = 0x04;
    private static final int EDGE_MASK = 0xf0;
    private HiScoreService hiScoreService;

    @Autowired
    public MatchService(MinesweeperMatchRepository repository, HiScoreService hiScoreService) {
        super(repository);
        this.hiScoreService = hiScoreService;
    }

    protected MinesweeperMatchRepository getRepository() {
        return (MinesweeperMatchRepository) this.repository;
    }

    /**
     * Find a match by its id and the user id
     * @param matchId the id of the match
     * @param userId the id of the user who was playing it
     * @return the match found
     */
    public MinesweeperMatch findByIdAndUserId(Long matchId, Long userId) {
        return getRepository().findByIdAndUserId(matchId, userId).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * Create a new match with the parameters
     * @param width the number of columns
     * @param height the number of rows
     * @param mines the number of mines
     * @param seed a seed for randomness
     * @param user the playing user
     * @return a new match
     */
    public MinesweeperMatch newGame(int width, int height, int mines, long seed, User user) {
        MinesweeperMatch match = MinesweeperMatch.builder()
                .startTime(new Date())
                .width(width)
                .height(height)
                .mines(mines)
                .user(user)
                .build();
        byte[] data = new byte[width * height];
        Random rnd = new Random(seed);
        while (mines > 0) {
            int pos = rnd.nextInt(width * height);
            while (data[pos] != 0) pos = rnd.nextInt(width * height);
            data[pos] = MINE_MASK;
            mines--;
        }
        List<MinesweeperMatch> matches = getRepository().findByUserIdAndStatus(user.getId(), GameStatus.PLAYING);
        matches.forEach(oldMatch -> {
            oldMatch.setEndTime(new Date());
            oldMatch.setStatus(GameStatus.ABANDONED);
        });
        saveAll(matches);
        setupEdges(data, match);
        match.setData(Base64.encodeBase64String(data));
        return save(match);
    }

    private void setupEdges(byte[] data, MinesweeperMatch match) {
        int width = match.getWidth();
        int height = match.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if ((getDatum(x, y, data, width) & MINE_MASK) != 0) {
                    incrementSides(x, y, data, width, height);
                }
            }
        }
    }

    private void incrementSides(int x0, int y0, byte[] data, int width, int height) {
        for (int y = Math.max(y0 - 1, 0); y < Math.min(y0 + 2, height); y++) {
            for (int x = Math.max(x0 - 1, 0); x < Math.min(x0 + 2, width); x++) {
                byte b = getDatum(x, y, data, width);
                if ((b & MINE_MASK) == 0) {
                    int num = (b & EDGE_MASK) >> 4;
                    num++;
                    b = (byte) ((b & MINE_MASK) | ((num << 4) & EDGE_MASK));
                    setDatum(x, y, data, width, b);
                }
            }
        }
    }

    /**
     * Do a move in a match. Automatically detects if the game is over and if the player won.
     * @param matchId the id of the match
     * @param x the clicked column
     * @param y the clicked row
     * @param mark true to mark a flag
     * @return an object with a match and a status
     */
    public GameMoveVO doMove(Long matchId, int x, int y, boolean mark) {
        MinesweeperMatch match = getRepository().findById(matchId).orElseThrow(ResourceNotFoundException::new);
        byte[] data = Base64.decodeBase64(match.getData());
        byte b = getDatum(x, y, data, match.getWidth());
        MoveStatus status = MoveStatus.NONE;
        if ((b & CLEARED_MASK) == 0) {
            if (mark) {
                b ^= FLAG_MASK;
            } else {
                b |= CLEARED_MASK; // add the clear status to the cell
                if ((b & MINE_MASK) != 0) {
                    // if clicked on a mine, then game is over
                    status = MoveStatus.GAMEOVER;
                    match.setEndTime(new Date());
                    match.setStatus(GameStatus.LOSE);
                } else {
                    int edge = (b & EDGE_MASK) >> 4;
                    if (edge == 0) {
                        status = MoveStatus.EASY;
                        clearPoint(x - 1, y, data, match.getWidth(), match.getHeight());
                        clearPoint(x + 1, y, data, match.getWidth(), match.getHeight());
                        clearPoint(x, y - 1, data, match.getWidth(), match.getHeight());
                        clearPoint(x, y + 1, data, match.getWidth(), match.getHeight());
                    } else {
                        status = MoveStatus.RISKY;
                    }
                    if (checkWin(data)) {
                        match.setStatus(GameStatus.WIN);
                        match.setEndTime(new Date());
                        status = MoveStatus.WIN;
                        match.setScore((int) (1000_000 / (match.getEndTime().getTime() - match.getStartTime().getTime())));
                        HiScore hiScore = HiScore.builder()
                                .match(match)
                                .user(match.getUser())
                                .build();
                        hiScoreService.save(hiScore);
                    }
                }
            }
        }
        setDatum(x, y, data, match.getWidth(), b);
        match.setData(Base64.encodeBase64String(data));
        match = save(match);
        return new GameMoveVO(INSTANCE.matchToVO(match), status);
    }

    private boolean checkWin(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            if ((b & CLEARED_MASK) == 0 && (b & MINE_MASK) == 0)
                return false;
        }
        return true;
    }

    private void clearPoint(int x, int y, byte[] data, int width, int height) {
        // if the point is outside the box leave
        if (x < 0 || y < 0 || x >= width || y >= height) return;
        byte b = getDatum(x, y, data, width);
        // if it is already cleared leave
        if ((b & 0x0f) != 0) return;
        b |= CLEARED_MASK;
        setDatum(x, y, data, width, b);
        // if it's not an edge try to clear adjacent cells
        if ((b & EDGE_MASK) == 0) {
            clearPoint(x - 1, y, data, width, height);
            clearPoint(x + 1, y, data, width, height);
            clearPoint(x, y - 1, data, width, height);
            clearPoint(x, y + 1, data, width, height);
        }
    }

    private byte getDatum(int x, int y, byte[] data, int width) {
        return data[x + y * width];
    }

    private void setDatum(int x, int y, byte[] data, int width, byte b) {
        data[x + y * width] = b;
    }

}
