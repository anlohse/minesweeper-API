package com.anlohse.minesweeper.commons.repositories;

import com.anlohse.minesweeper.commons.entities.GameStatus;
import com.anlohse.minesweeper.commons.entities.MinesweeperMatch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MinesweeperMatchRepository extends CrudRepository<MinesweeperMatch, Long> {

    List<MinesweeperMatch> findByUserIdAndStatus(Long id, GameStatus status);

    Optional<MinesweeperMatch> findByIdAndUserId(Long matchId, Long userId);
}
