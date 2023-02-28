package egovframework.site.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.site.vo.SiteVO;

@Mapper("SiteDAO")
public interface SiteDAO {

	List<SiteVO> getAllList();

	void BoardWrite(SiteVO input);

	SiteVO getContent(String title);

	SiteVO getContentEdit(String seq);

	String getUser(String seq);

	void boardEdit(SiteVO input);

}
