package com.anlohse.minesweeper.userservices.api;

import com.anlohse.minesweeper.commons.services.UserService;
import com.anlohse.minesweeper.commons.util.Messages;
import com.anlohse.minesweeper.commons.vo.CurrentUserVO;
import com.anlohse.minesweeper.commons.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Messages messages;
	
	@Autowired
	private LocaleResolver localeResolver;

	@PreAuthorize("!hasAuthority('SCOPE_user')")
	@PostMapping
	public ResponseEntity<UserVO> createNew(@Valid @RequestBody UserVO user) throws Exception {
		userService.createNew(user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PreAuthorize("!hasAuthority('SCOPE_user')")
	@PostMapping("resendActivation")
	public ResponseEntity<UserVO> resendActivationCode(@RequestParam(value = "email", required = true) String email, HttpServletRequest request) throws Exception {
		userService.resendActivationCode(email);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PreAuthorize("hasAuthority('SCOPE_user')")
	@PatchMapping
	public ResponseEntity<UserVO> update(@Valid @RequestBody UserVO user) {
		userService.update(user);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasAuthority('SCOPE_user')")
	@GetMapping("/current")
	public ResponseEntity<CurrentUserVO> update() {
		return ResponseEntity.ok(userService.currentUserVo());
	}

	@PreAuthorize("!hasAuthority('SCOPE_user')")
	@PostMapping("/activate/{activationCode}")
	public ResponseEntity<CurrentUserVO> activate(@PathVariable("activationCode") String activationCode) throws Exception {
		userService.activate(activationCode);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("!hasAuthority('SCOPE_user')")
	@PostMapping("/recover/{email}")
	public ResponseEntity<CurrentUserVO> generateRecover(@PathVariable("email") String email) throws Exception {
		userService.generateRecover(email);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("!hasAuthority('SCOPE_user')")
	@PostMapping("/recover")
	public ResponseEntity<CurrentUserVO> recover(
			@RequestParam("recoverCode") String recoverCode,
			@RequestParam("password") String password,
			@RequestParam("passwordConfirmation") String passwordConfirmation) throws Exception {
		userService.recover(recoverCode, password, passwordConfirmation);
		return ResponseEntity.ok().build();
	}

}
