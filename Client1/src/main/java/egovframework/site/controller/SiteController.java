package egovframework.site.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.site.vo.SiteVO;

@Controller
public class SiteController {
	
	@Resource(name="egovIdGnrService")
	private EgovIdGnrService egovIdgnrservice;
	
	

	@RequestMapping(value="/main.do")
	public String main() {
		return "main";
	}

	@PostMapping(value="/main/fileuploads.do")
	public void fileupload(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest multiFile
			,@RequestParam MultipartFile upload) throws Exception {

		OutputStream out = null;
		PrintWriter printWriter = null;

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		try {
			String fileName = upload.getOriginalFilename();
			System.out.println(":::::::::::"+fileName+"::::::::::::::::::::");
			byte[] bytes = upload.getBytes();

			String path = "C:\\Users\\c358\\Desktop\\ex\\Client1\\src\\main\\webapp\\images\\site\\";
			String ckUploadPath = path+fileName;
			File foler = new File(path); //생성
			System.out.println("path :::::::::::::::::: " + path);

			if(!foler.exists()) {
				try {
					foler.mkdir();
				}catch(Exception e) {
					e.getStackTrace();
				}
			}
			out = new FileOutputStream(new File(ckUploadPath));
			out.write(bytes);
			out.flush();

			String callback = request.getParameter("CKEditorFuncNum");
			printWriter = response.getWriter();
			String fileUrl = "/main/ckImgSubmits.do?fileName=" + fileName; // 작성화면

			// 업로드시 메시지 출력
			printWriter.println("{\"filename\" : \""+fileName+"\", \"uploaded\" : 1, \"url\":\""+fileUrl+"\"}");
			printWriter.flush();

		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(out != null) { out.close(); }
				if(printWriter != null) { printWriter.close(); }
			} catch(Exception e) { e.printStackTrace(); }
		}
		return;
	}
	@RequestMapping(value="/main/ckImgSubmits.do")
	public void ckSubmit( @RequestParam(value="fileName") String fileName
			, HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException{
		//서버에 저장된 이미지 경로
		String path = "C:\\Users\\c358\\Desktop\\ex\\Client1\\src\\main\\webapp\\images\\site\\";	// 저장된 이미지 경로
		// C:\\Users\\c358\\Desktop\\ex\\Client\\src\\main\\webapp\\images\\site\\
		System.out.println("path::::::::::::::::"+path);
		String sDirPath = path + fileName;

		File imgFile = new File(sDirPath);

		//사진 이미지 찾지 못하는 경우 예외처리로 빈 이미지 파일을 설정한다.
		if(imgFile.isFile()){
			byte[] buf = new byte[1024];
			int readByte = 0;
			int length = 0;
			byte[] imgBuf = null;

			FileInputStream fileInputStream = null;
			ByteArrayOutputStream outputStream = null;
			ServletOutputStream out = null;

			try{
				fileInputStream = new FileInputStream(imgFile);
				outputStream = new ByteArrayOutputStream();
				out = response.getOutputStream();

				while((readByte = fileInputStream.read(buf)) != -1){
					outputStream.write(buf, 0, readByte); 
				}

				imgBuf = outputStream.toByteArray();
				length = imgBuf.length;
				out.write(imgBuf, 0, length);
				out.flush();
				System.out.println(fileName);

			}catch(IOException e){
				e.printStackTrace();
			}finally {
				outputStream.close();
				fileInputStream.close();
				out.close();
			}
		}
	}
	
	@RequestMapping(value="/main/list.do",method=RequestMethod.GET)
	public String mainList(Model model) throws Exception{
		String result = "";
		String url_host = "http://localhost:8084/main/main.json";
		try {
			URL url = new URL(url_host.toString());
			BufferedReader rd;
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			result = rd.readLine();
			System.out.println(result);
			JSONParser jsonparser = new JSONParser();
			JSONObject jObject = (JSONObject)jsonparser.parse(result);
			JSONArray data = (JSONArray)jObject.get("data");
			System.out.println(data );
			model.addAttribute("list1",data);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "List/AllList";
	}
	
	
	@RequestMapping(value="/main/login.do")
	public String login() {
		return "login/login";
	}
	
	
	@RequestMapping(value="/main/write.do")
	public String Write() {
		return "Write/Write";
	}
	
	
	
	
	@RequestMapping(value="/main/writeSave.do",method=RequestMethod.POST)
	public String writeSave(Principal auth,SiteVO vo) throws Exception {
		String seq = egovIdgnrservice.getNextStringId();
		String user_id = auth.getName();
		String con = vo.getContent();
		System.out.println("===================================="+con);
		con = con.replace("&lt;", "<");
		con = con.replace("&gt", ">");
		con = con.replace("&amp;lt", "<");
		con = con.replace("&amp;gt;", ">");
		con = con.replace("&amp;nbsp;", " ");
		con = con.replace("&amp;amp;", "&");
		con = con.replace("&quot;&quot;", "\"\"");
		con = con.replace("&quot;", "\"");
		con = con.replace(";","\n");
		vo.setContent(con);
		System.out.println("===================================="+con);
		vo.setSeq(seq);
		vo.setWriter(user_id);
		JSONObject data = new JSONObject();
		data.put("seq", vo.getSeq());
		data.put("title", vo.getTitle());
		data.put("content", vo.getContent());
		data.put("writer", vo.getWriter());
		String line = data.toString();
		System.out.println(line);
		try {
			String url_host = "http://localhost:8084/main/write.do";
			HttpURLConnection conn = null;
			URL url = new URL(url_host);
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
			conn.setDoOutput(true);
			try(OutputStream os = conn.getOutputStream()) {
				byte[] input = line.getBytes("utf-8");
				os.write(input, 0, input.length);	
			}
			try(BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				System.out.println(response.toString());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "main";
	}
	
	@RequestMapping(value="/main/content.do",method=RequestMethod.GET)
	public String content(SiteVO vo,Model model) {
		String title = vo.getTitle();
		String result = "";
		try {
			StringBuilder urlBuider = new StringBuilder("http://localhost:8084/main/content/one/"+title+".json");
			URL url = new URL(urlBuider.toString());
			BufferedReader rd;
			rd = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
			result = rd.readLine();
			model.addAttribute("list",result);
			JSONParser jsonparser = new JSONParser();
			JSONObject jObject = (JSONObject)jsonparser.parse(result);
			model.addAttribute("list1",jObject);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "content/content";
	}
	
	@RequestMapping(value="/main/edit.do")
	public String edit(SiteVO vo,Model model) {
		String seq = vo.getSeq();
		String result="";
		try {
			StringBuilder urlBuider = new StringBuilder("http://localhost:8084/main/content/edit/"+seq+".json");
			URL url = new URL(urlBuider.toString());
			BufferedReader rd;
			rd = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
			result = rd.readLine();
			model.addAttribute("list",result);
			JSONParser jsonparser = new JSONParser();
			JSONObject jObject = (JSONObject)jsonparser.parse(result);
			model.addAttribute("list1",jObject);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "content/edit";
	}
	
	//글 수정시 글작성 유저와 변경하려는 유저 확인
	@RequestMapping(value="/main/editSave.do")
	public void editSave(SiteVO vo,Model model,Principal auth,HttpServletResponse res) {
		String user = auth.getName();
		String seq = vo.getSeq();
		String result = "";
		try {
			StringBuilder urlBuider = new StringBuilder("http://localhost:8084/main/content/user/"+seq+".json");
			URL url = new URL(urlBuider.toString());
			BufferedReader rd;
			rd = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
			result = rd.readLine();
			int resulutSize = result.length();
			String resultString = result.substring(1,resulutSize-1);
			res.setContentType("text/html; charset=utf-8");
			PrintWriter out = res.getWriter();
			if(user.equals(resultString)) {
				out.println("<script>");
				out.println("alert('동일');");
				out.println("</script>");
				out.flush();
			}else {
				out.println("<script>");
				out.println("alert('다름');");
				out.println("</script>");
				out.flush();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			JSONObject data = new JSONObject();
			data.put("title",vo.getTitle());
			data.put("content",vo.getContent());
			data.put("seq", vo.getSeq());
			String line = data.toString();
			String urlBuider ="http://localhost:8084/main/content/edit/save.json";
			HttpURLConnection conn = null;
			URL url = new URL(urlBuider);
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			conn.setDoOutput(true);

			try(OutputStream os = conn.getOutputStream()) {
				byte[] input = line.getBytes("utf-8");
				os.write(input, 0, input.length);			
			}

			try(BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				System.out.println(response.toString());
			}
			res.setContentType("text/html; charset=utf-8");
			PrintWriter out = res.getWriter();
			out.println("<script>alert('수정 완뇨');");
			out.println("window.location.href='/main/list.do';");
			out.println("</script>");
			out.flush();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
