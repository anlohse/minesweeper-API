package com.anlohse.minesweeper.commons.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

	private Long id;
	
	@Email
	@NotNull
	@NotEmpty
	private String email;
	
	@NotNull
	@NotEmpty
	private String name;

	@NotNull
	@NotEmpty
	private String lastName;

	private String title;
	
	private String password;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;

	private String activationCode;

	private String recoveryCode;

}
