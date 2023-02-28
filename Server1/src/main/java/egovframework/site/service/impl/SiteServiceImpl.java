package egovframework.site.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.site.service.SiteService;
import egovframework.site.vo.SiteVO;
@Service("SiteService")
public class SiteServiceImpl implements SiteService {
	
	@Resource(name="SiteDAO")
	private SiteDAO siteDao;

	@Override
	public List<SiteVO> getAllList() {
		return siteDao.getAllList();
	}

	@Override
	public void BoardWrite(SiteVO input) {
		siteDao.BoardWrite(input);
	}

	@Override
	public SiteVO getContent(String title) {
		return siteDao.getContent(title);
	}

	@Override
	public SiteVO getContentEdit(String seq) {
		return siteDao.getContentEdit(seq);
	}

	@Override
	public String getUser(String seq) {
		return siteDao.getUser(seq);
	}

	@Override
	public void boardEdit(SiteVO input) {
		siteDao.boardEdit(input);
	}

	
}
