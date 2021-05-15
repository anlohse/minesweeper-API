package com.anlohse.minesweeper.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HiScoreVO {

    private String nickname;

    private Date matchDate;

    private int score;

}
