package com.anlohse.minesweeper.commons.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbs_mines_match", indexes = @Index(unique = true, columnList = "user_id", name = "idx_user_match"))
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MinesweeperMatch extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private int width;

    private int height;

    private int mines;

    private int minesDiscovered;

    private int cleared;

    private GameStatus status;

    private String data;

}
