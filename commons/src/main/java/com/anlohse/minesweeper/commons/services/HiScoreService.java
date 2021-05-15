package com.anlohse.minesweeper.commons.services;

import com.anlohse.minesweeper.commons.entities.HiScore;
import com.anlohse.minesweeper.commons.entities.User;
import com.anlohse.minesweeper.commons.repositories.HiScoreRepository;
import com.anlohse.minesweeper.commons.vo.HiScoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.anlohse.minesweeper.commons.mapper.HiScoreMapper.INSTANCE;

/**
 * Service to save and load hi-scores.
 */
@Service
public class HiScoreService extends AbstractCrudService<HiScore, Long> {

    @Autowired
    public HiScoreService(HiScoreRepository repository) {
        super(repository);
    }

    protected HiScoreRepository getRepository() {
        return (HiScoreRepository) this.repository;
    }

    /**
     * Find all top hi-scores.
     * @param topCount the number of results to load
     * @return a list with the top hi-scores
     */
    public List<HiScoreVO> findTopAllHiScores(int topCount) {
        List<HiScore> list = getRepository().findTopAllHiScores(PageRequest.of(0, topCount));
        return list.stream().map(INSTANCE::entityToVo).collect(Collectors.toList());
    }

    /**
     * Find top user's hi-scores.
     * @param topCount the number of results to load
     * @param user the user
     * @return a list with the top user's hi-scores
     */
    public List<HiScoreVO> findTopUserHiScores(int topCount, User user) {
        List<HiScore> list = getRepository().findTopUserHiScores(user.getId(), PageRequest.of(0, topCount));
        return list.stream().map(INSTANCE::entityToVo).collect(Collectors.toList());
    }

}
