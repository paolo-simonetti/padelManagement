package it.solving.padelmanagement.securitymodel;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import it.solving.padelmanagement.model.Player;

public class PlayerPrincipal implements UserDetails {

	private final Player player;
	
	public PlayerPrincipal(Player player) {
		this.player=player;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("player"));
	}

	@Override
	public String getPassword() {
		return player.getPassword();
	}

	@Override
	public String getUsername() {
		return player.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
