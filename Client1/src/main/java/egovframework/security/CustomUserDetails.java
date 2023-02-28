package egovframework.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
	
	/* Spring Security에서 사죵아즤 정보를 담는 인터페이스는 UseDetails인터페이스이다. */
	/* 우리가 이 인터페이슬륵 ㅜ현하게 되면 Spring Security에서 구현한 클래스를 사용자 정보로 인식하고 인증 작업을 한다. 숩게 말하면 UserDatails 인터페이스는 VO역할을 한다고 보면 된다. */
	
	private String user_id;
	private String user_pwd;
	private String authority;
	private boolean enabled;
	private String user_name;

	@Override /* 계정이 갖고 있는 권한을 목록으로 리턴하기 위한 설정 */
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> auth = new ArrayList<>();
		auth.add(new SimpleGrantedAuthority(authority));
		return auth;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user_pwd;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user_id;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}
	
	
	/* 사용자의 추가 정보 반환 */
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	
	
	
}
