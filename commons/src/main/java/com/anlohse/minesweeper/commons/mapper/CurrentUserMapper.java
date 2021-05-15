package com.anlohse.minesweeper.commons.mapper;

import com.anlohse.minesweeper.commons.entities.User;
import com.anlohse.minesweeper.commons.vo.CurrentUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurrentUserMapper {

    CurrentUserMapper INSTANCE = Mappers.getMapper(CurrentUserMapper.class);

    CurrentUserVO userToVO(User entity);

}
