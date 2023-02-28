package egovframework.site.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import egovframework.site.service.SiteService;
import egovframework.site.vo.SiteVO;

@RequestMapping("/main")
@RestController
public class SiteController {
	
	@Resource(name="SiteService")
	private SiteService siteService;
	
	
	@RequestMapping(value="/main.json",method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> main(){
		List<SiteVO> list = siteService.getAllList();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("data", list);
		result.put("count", list.size());
		System.out.println(list);
		System.out.println("================================");
		System.out.println(result);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@PostMapping(value="/write.do")
	public void writeSave(@RequestBody SiteVO input){
		System.out.println(input);
		siteService.BoardWrite(input);
	}
	@GetMapping(value="/content/one/{title}.json")
	public ResponseEntity<SiteVO> getContent(@PathVariable("title") String title){
		SiteVO siteVo = siteService.getContent(title);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application","json",Charset.forName("utf-8")));
		return new ResponseEntity<SiteVO>(siteVo,headers,HttpStatus.OK);
	}
	@GetMapping(value="/content/edit/{seq}.json")
	public ResponseEntity<SiteVO> editContent(@PathVariable("seq") String seq){
		SiteVO siteVo = siteService.getContentEdit(seq);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application","json",Charset.forName("utf-8")));
		return new ResponseEntity<SiteVO>(siteVo,headers,HttpStatus.OK);
	}
	
	@RequestMapping(value="/content/user/{seq}.json")
	public ResponseEntity<String> editUser(@PathVariable("seq")String seq){
		String user = siteService.getUser(seq);
		System.out.println(user);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@RequestMapping(value="/content/edit/save.json",method=RequestMethod.POST)
	public void editSaveT(@RequestBody SiteVO input, HttpServletResponse res) throws IOException{
		siteService.boardEdit(input);
		
		
	}
	
}
