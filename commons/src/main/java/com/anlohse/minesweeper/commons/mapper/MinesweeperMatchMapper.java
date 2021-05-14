package com.anlohse.minesweeper.commons.mapper;

import com.anlohse.minesweeper.commons.entities.MinesweeperMatch;
import com.anlohse.minesweeper.commons.vo.MinesweeperMatchVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MinesweeperMatchMapper {

    MinesweeperMatchMapper INSTANCE = Mappers.getMapper(MinesweeperMatchMapper.class);

    MinesweeperMatch voToMatch(MinesweeperMatchVO vo);

    MinesweeperMatchVO matchToVO(MinesweeperMatch entity);

}
