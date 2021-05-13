package com.anlohse.minesweeper.commons.vo;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CurrentUserVO {

	private Long id;

	private String name;

	private String lastName;

	private String email;

}
