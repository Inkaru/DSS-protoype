package bth.dss.group2.backend.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import bth.dss.group2.backend.model.AbstractUser;
import bth.dss.group2.backend.repository.AbstractUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("customUserDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

	private final AbstractUserRepository<? extends AbstractUser> userRepository;

	private final HttpServletRequest request;

	public CustomUserDetailsService(AbstractUserRepository<? extends AbstractUser> userRepository, HttpServletRequest request) {
		super();
		this.userRepository = userRepository;
		this.request = request;
	}

	@Override
	public UserDetails loadUserByUsername(final String loginName) throws UsernameNotFoundException {
	/*	final String ip = getClientIP();
		if (loginAttemptService.isBlocked(ip)) {
			throw new RuntimeException("blocked");
		}*/
		AbstractUser user = userRepository.findByLoginName(loginName)
				.orElseThrow(() -> new UsernameNotFoundException("User name not found: " + loginName));
		return new org.springframework.security.core.userdetails.User(user.getLoginName(), user.getHashedPassword(), true, true, true, true, Collections
				.emptyList());
		//return new org.springframework.security.core.userdetails.User(user.getEmailAddress(), user.getHashedPassword(), true, true, true, true, getAuthorities(user.getRoles()));
	}

	/*private final Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
		return getGrantedAuthorities(getPrivileges(roles));
	}*/

	/*private final List<String> getPrivileges(final Collection<Role> roles) {
		final List<String> privileges = new ArrayList<String>();
		final List<Privilege> collection = new ArrayList<Privilege>();
		for (final Role role : roles) {
			collection.addAll(role.getPrivileges());
		}
		for (final Privilege item : collection) {
			privileges.add(item.getName());
		}

		return privileges;
	}*/

	private final List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (final String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

	private final String getClientIP() {
		final String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}
}
