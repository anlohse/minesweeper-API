package com.anlohse.minesweeper.commons.vo;

import com.anlohse.minesweeper.commons.entities.GameStatus;
import com.anlohse.minesweeper.commons.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MinesweeperMatchVO {

    private Long id;

    private Date startTime;

    private Date endTime;

    private int width;

    private int height;

    private int mines;

    private int minesDiscovered;

    private int cleared;

    private GameStatus status;

    private String data;

}
