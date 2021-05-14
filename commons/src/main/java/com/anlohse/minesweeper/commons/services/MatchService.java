package com.anlohse.minesweeper.commons.services;

import com.anlohse.minesweeper.commons.entities.GameStatus;
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

@Service
public class MatchService extends AbstractCrudService<MinesweeperMatch, Long> {

    private static final int MINE_MASK = 0x01;
    private static final int CLEARED_MASK = 0x02;
    private static final int FLAG_MASK = 0x04;
    private static final int EDGE_MASK = 0xf0;

    @Autowired
    public MatchService(MinesweeperMatchRepository repository) {
        super(repository);
    }

    protected MinesweeperMatchRepository getRepository() {
        return (MinesweeperMatchRepository) this.repository;
    }

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
        getRepository().saveAll(matches);
        setupEdges(data, match);
        match.setData(Base64.encodeBase64String(data));
        return getRepository().save(match);
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

    public GameMoveVO doMove(Long matchId, int x, int y) {
        MinesweeperMatch match = getRepository().findById(matchId).orElseThrow(ResourceNotFoundException::new);
        byte[] data = Base64.decodeBase64(match.getData());
        byte b = getDatum(x, y, data, match.getWidth());
        MoveStatus status = MoveStatus.NONE;
        if ((b & CLEARED_MASK) == 0) {
            if ((b & MINE_MASK) != 0) {
                // HIT MINE!
            } else {
                b |= CLEARED_MASK; // add the clear status to the cell
                int edge = (b & EDGE_MASK) >> 4;
                if (edge == 0) {
                    // clear area
                } else {
                    status = MoveStatus.RISKY;
                }
            }
        }
        setDatum(x, y, data, match.getWidth(), b);
        match.setData(Base64.encodeBase64String(data));
        match = getRepository().save(match);
        return new GameMoveVO(INSTANCE.matchToVO(match), status);
    }

    private byte getDatum(int x, int y, byte[] data, int width) {
        return data[x + y * width];
    }

    private void setDatum(int x, int y, byte[] data, int width, byte b) {
        data[x + y * width] = b;
    }

}
