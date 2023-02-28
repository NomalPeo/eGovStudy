package egovframework.security;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("UserAuthDAO")
public interface UserAuthDAO {
	CustomUserDetails getUserById(String user_id);

}
