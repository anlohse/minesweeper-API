package com.anlohse.minesweeper.commons.security;

import com.anlohse.minesweeper.commons.entities.User;
import com.anlohse.minesweeper.commons.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Component("userDetailsService")
public class AuthenticationService implements UserDetailsService, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private transient UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username).orElse(null);
		if (user == null)
			throw new UsernameNotFoundException(username + " not found");
		if (user.getActivationCode() != null)
			throw new DisabledException("");
		return new UserDetailsImpl(user);
	}

	public void reloadAuthentication(User user) {
		UserDetailsImpl details = new UserDetailsImpl(user);
		UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(details,
				user.getPassword(), details.getAuthorities());
		newAuthentication.setDetails(details);
		SecurityContextHolder.getContext().setAuthentication(newAuthentication);
	}

	public static class UserDetailsImpl implements UserDetails {

		private static final long serialVersionUID = 1L;
		User user;
		Collection<GrantedAuthority> auths;

		public UserDetailsImpl(User user) {
			super();
			loadRoles(user);
		}

		public void loadRoles(User user) {
			this.user =user;
			Collection<GrantedAuthority> auths;
			auths = new HashSet<>();
			auths.add(new SimpleGrantedAuthority("ROLE_USER"));
			this.auths = auths;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return auths;
		}

		@Override
		public String getPassword() {
			return "********";
		}

		@Override
		public String getUsername() {
			return user.getEmail();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return user.getDtLocked() == null;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return user.getActivationCode() == null;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((user == null) ? 0 : user.getId().hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof UserDetailsImpl))
				return false;
			UserDetailsImpl other = (UserDetailsImpl) obj;
			if (user == null) {
				if (other.user != null)
					return false;
			} else if (!Objects.equals(user.getId(), other.user.getId()))
				return false;
			return true;
		}

		public User getUser() {
			return user;
		}

	}

}
