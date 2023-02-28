package egovframework.security;




import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private UserDetailsService userDeSer;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String user_id = (String) authentication.getPrincipal();			// 화면에 입력한 아이디를 username에 담는다
		String user_pwd = (String) authentication.getCredentials();			// 화면에 입력한 비밀번호를 password에 담는다.
		
		CustomUserDetails user = (CustomUserDetails)userDeSer.loadUserByUsername(user_id); // 화면에서 입력한 아이디로 DB에 있는 사용자 정보를 UserDetails형으로 가져와 user에 담는다.
		
		if(!matchPassword(user_pwd,user.getPassword())) {					// 화면에서 입력한 비밀번호와 DB에 저장된 비밀번호를 비교하는 로직 맞지 않다면 예외로 간다.
			
			throw new BadCredentialsException(user_id);
		}
		if(!user.isEnabled()) {
			throw new BadCredentialsException(user_id);					// 계정활성화 여부 확인 로직. AuthenticationProvide인터페이스를 구현하게 되면 계정 잠금 여부나 활성화 여부등은 여기서 확인한다.
		}
		return new UsernamePasswordAuthenticationToken(user_id, user_pwd,user.getAuthorities());	// 계정이 인증됐다면 UsernamePasswordAuthenticationToken객체에 화면에서 입력한 정보와 DB에서 가져온 권한을 담아서 리턴한다.
			
	}

	@Override
	public boolean supports(Class<?> arg0) { 								// AuthenticationProvider인터페이스가 지정된 Authentication 객체를 지원하는 경우 true를 리턴한다.
		return true;
	}
	
	private boolean matchPassword(String user_pwd, String password) {		// 비밀번호 비교 메서드 맞으면 true반환
		return user_pwd.equals(password);
	}

}
