package com.anlohse.minesweeper.commons.repositories;

import com.anlohse.minesweeper.commons.entities.HiScore;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HiScoreRepository extends CrudRepository<HiScore, Long> {

    @Query("select his from HiScore his order by his.match.score desc")
    List<HiScore> findTopAllHiScores(Pageable pageable);

    @Query("select his from HiScore his where his.user.id = ?1 order by his.match.score desc")
    List<HiScore> findTopUserHiScores(Long userId, PageRequest of);

}
