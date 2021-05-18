package com.anlohse.minesweeper.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoMoveVO {

    private long matchId;
    private int x;
    private int y;
    private boolean mark;

}
