package com.anlohse.minesweeper.commons.mapper;

import com.anlohse.minesweeper.commons.entities.User;
import com.anlohse.minesweeper.commons.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User voToUser(UserVO vo);

    UserVO userToVO(User entity);

}
