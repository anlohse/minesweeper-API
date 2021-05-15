package com.anlohse.minesweeper.commons.mapper;

import com.anlohse.minesweeper.commons.entities.HiScore;
import com.anlohse.minesweeper.commons.vo.HiScoreVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HiScoreMapper {

    HiScoreMapper INSTANCE = Mappers.getMapper(HiScoreMapper.class);

    @Mappings({
            @Mapping(target="nickname", source="user.nickname"),
            @Mapping(target="matchDate", source="match.endTime"),
            @Mapping(target="score", source="match.score")
    })
    HiScoreVO entityToVo(HiScore ent);

}
