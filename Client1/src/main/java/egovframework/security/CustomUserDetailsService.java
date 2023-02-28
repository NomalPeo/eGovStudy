package egovframework.security;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service("userService")
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	
	/*	CustomUserDetails로 정보를 담은 객체를 만들었다면 DB에서 유저 정보를 직접 가져오는 인터페이스가 필요하다 
	 	loadUserByUsername메소드가 그 역할을 한다. UserdetailsService인터페이스를 구현하면 loadUserByUsername메소드가 오버라이드 된다.
	 	여기서 CustomUserDetails형으로 사용자의 정보를 가져오면 된다. 가져온 사용자의 정보에 따라 예외와 사용자 정보를 리턴하면 된다. 즉,DB에서 유저의 정보를 리턴해주는 작업이다.
	 */
	@Resource(name="UserAuthDAO")
	private UserAuthDAO userAuthDao;
	
	@Override
	public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
		CustomUserDetails user = userAuthDao.getUserById(user_id);
		if(user == null) {
			throw new UsernameNotFoundException(user_id);
		}
		return user;
	}
}
