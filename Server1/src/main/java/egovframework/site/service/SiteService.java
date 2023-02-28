package egovframework.site.service;

import java.util.List;

import egovframework.site.vo.SiteVO;

public interface SiteService {

	List<SiteVO> getAllList();

	void BoardWrite(SiteVO input);

	SiteVO getContent(String title);

	SiteVO getContentEdit(String seq);

	String getUser(String seq);

	void boardEdit(SiteVO input);

}
