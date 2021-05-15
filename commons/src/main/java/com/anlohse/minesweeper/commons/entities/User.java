package com.anlohse.minesweeper.commons.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbs_user", indexes = {
        @Index(unique = true, columnList = "email", name = "uk_user_mail"),
        @Index(unique = true, columnList = "nickname", name = "uk_user_nick")
})
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 60, nullable = false)
    private String name;

    @Column(length = 120, nullable = false)
    private String lastName;

    @Column(length = 60, nullable = false)
    private String nickname;

    @Column(length = 200, nullable = false)
    private String password;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(length = 200, nullable = true)
    private String activationCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date activationExpires;

    @Column(length = 200, nullable = true)
    private String recoveryCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date recoveryExpires;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dtLocked;

}
