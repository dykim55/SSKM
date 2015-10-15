package com.cyberone.sskm.files.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cyberone.sskm.Common;
import com.cyberone.sskm.Constants;
import com.cyberone.sskm.exception.BizException;
import com.cyberone.sskm.files.service.FilesService;
import com.cyberone.sskm.model.Files;
import com.cyberone.sskm.model.Product;
import com.cyberone.sskm.model.UserInfo;
import com.cyberone.sskm.utils.DataUtil;
import com.cyberone.sskm.utils.StringUtil;


@Controller
public class FilesController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private FilesService filesService;

    /**
     * 산출물 관련 페이지 구성 - 카테고리 트리
     */
    @RequestMapping(value = {"/files/security_control", "/files/security_diagnosis", "/files/info_protection_manage", "/files/info_protection_policy", "/files/info_protection_trend", "/files/security_news"})
    public String files(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	    	
       	String sPrtsCd = "";
       	if (request.getServletPath().equals("/files/security_control")) {
       		sPrtsCd = "1000";
       	} else if (request.getServletPath().equals("/files/security_diagnosis")) {
       		sPrtsCd = "2000";
       	} else if (request.getServletPath().equals("/files/info_protection_manage")) {
       		sPrtsCd = "3000";
       	} else if (request.getServletPath().equals("/files/info_protection_policy")) {
       		sPrtsCd = "4000";
       	} else if (request.getServletPath().equals("/files/info_protection_trend")) {
       		sPrtsCd = "5000";
       	} else if (request.getServletPath().equals("/files/security_news")) {
       		sPrtsCd = "6000";
       	}
       	
       	HashMap<String, Object> paramMap = new HashMap<String, Object>();
       	paramMap.put("prtsCd", Integer.valueOf(sPrtsCd));
       	List<HashMap<String, Object>> subMenuList = filesService.selectMenu(paramMap);
       	
       	HashMap<String, Object> subMenuMap = subMenuList.get(0);
       	
       	HashMap<String, Object> sideMap = new HashMap<String, Object>();
       	
       	for (HashMap<String, Object> hMap : subMenuList) {
           	paramMap.put("ctgSct", hMap.get("menuCd"));
        	List<HashMap<String, Object>> ctgList = filesService.selectCategoryTree(paramMap);
        	sideMap.put(StringUtil.convertString(hMap.get("menuCd")), ctgList);
       	}
       	
    	model.addAttribute("gubun", subMenuMap.get("menuCd"));
    	model.addAttribute("subMenuList", subMenuList);
    	model.addAttribute("sideMap", sideMap);
        
        return "/files/contents";
    }

    /**
     * 카테고리 폴더 생성 페이지 요청
     */
    @RequestMapping("/files/create_folder")
    public String createFolder(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	    	
    	String sCtgId = request.getParameter("id");

    	if (!StringUtil.isEmpty(sCtgId)) {
	       	HashMap<String, Object> paramMap = new HashMap<String, Object>();
	       	paramMap.put("ctgId", Integer.valueOf(sCtgId));
	    	HashMap<String, Object> category = filesService.selectCategory(paramMap);
	    	model.addAttribute("category", category);
    	}
    	return "/files/create_folder";
    }

    /**
     * 카테고리 폴더 생성/수정
     */
    @RequestMapping("/files/folder_register")
    public ModelAndView folderRegister(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	    	
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
    	String sId = request.getParameter("id");
    	String sSct = request.getParameter("gubun");
    	String sParent = request.getParameter("parent");
    	String sName = StringUtil.replaceHtml(request.getParameter("name"));
    	String sDesc = StringUtil.replaceHtml(request.getParameter("desc"));
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
       	paramMap.put("ctgNm", sName);
       	paramMap.put("ctgDesc", sDesc);
       	paramMap.put("modr", userInfo.getAcct().getAcctId());
       	paramMap.put("modDtime", new Date());

       	if (StringUtil.isEmpty(sId)) {
       		logger.info("폴더를 생성했습니다.");
           	paramMap.put("ctgSct", Integer.valueOf(sSct));
           	paramMap.put("ctgParent", Integer.valueOf(sParent));
           	paramMap.put("regr", userInfo.getAcct().getAcctId());
           	paramMap.put("regDtime", new Date());
       		filesService.insertCategory(paramMap);
       	} else {
       		logger.info("폴더를 수정했습니다.");
       		paramMap.put("ctgId", sId);
       		filesService.updateCategory(paramMap);
       	}
    	logger.info("폴더명 : {}", sName);
    	logger.info("폴더설명 : {}", sDesc);
    	logger.info("만든사람 : {}({})", userInfo.getAcct().getAcctId(), userInfo.getAcct().getAcctNm());
    	
    	ModelAndView modelAndView = new ModelAndView("jsonView");
    	modelAndView.addObject("gubun", Integer.valueOf(sSct));
    	modelAndView.addObject("id", paramMap.get("ctgId"));
    	modelAndView.addObject("name", sName);
    	modelAndView.addObject("parent", Integer.valueOf(sParent));

    	return modelAndView.addObject("status", "success");
    }
    
    /**
     * 카테고리 폴더 삭제
     */
    @RequestMapping("/files/delete_folder")
    public ModelAndView deleteFolder(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	String sId = request.getParameter("id");
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("ctgId", sId);
    	HashMap<String, Object> ctgMap = filesService.selectCategory(paramMap);

    	if (StringUtil.isEmpty(ctgMap)) {
    		throw new BizException("삭제할 업무폴더를 선택하세요.");
    	}
    	
   		paramMap.clear();
   		paramMap.put("ctgParent", (Integer)ctgMap.get("ctgId"));
    	List<HashMap<String, Object>> acctList = filesService.selectCategoryList(paramMap);
    	
    	Product dto = new Product();
    	dto.setParent((Integer)ctgMap.get("ctgId"));
    	List<HashMap<String, Object>> productList = filesService.selectProductList(dto);
    	
    	if (acctList.size() > 0 || productList.size() > 0) {
    		throw new BizException("하위 업무폴더나 파일이 존재하는 폴더는 삭제할 수 없습니다");
    	}

		StringBuffer sMemo = new StringBuffer();
		sMemo = sMemo.append("위치:").append(StringUtil.convertString(ctgMap.get("pathNm"))).append("\n");
		sMemo = sMemo.append("폴더:").append(StringUtil.convertString(ctgMap.get("ctgNm"))).append("\n");
   		
    	paramMap.clear();
   		paramMap.put("ctgId", (Integer)ctgMap.get("ctgId"));    	
    	filesService.deleteCategory(paramMap);
    	
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
        Common.insertAuditHist(Constants.AUDIT_FILES, "폴더가 삭제되었습니다.", "S", sMemo.toString(), userInfo.getAcct().getAcctId());
    	
        logger.info("폴더를 삭제했습니다.");
        logger.info("폴더경로 : {}", StringUtil.convertString(ctgMap.get("pathNm")));
        logger.info("폴더명 : {}", StringUtil.convertString(ctgMap.get("ctgNm")));
        logger.info("삭제자 : {}({})", userInfo.getAcct().getAcctId(), userInfo.getAcct().getAcctNm());
        
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("parent", ctgMap.get("ctgParent"));
        return modelAndView.addObject("status", "success"); 
    }
    
    /**
     * 산출물 카테고리의 트리 데이타 요청
     */
    @RequestMapping("/files/tree_ajax")
    public String treePane(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	    	
    	String sSct = request.getParameter("gubun");
       	
       	HashMap<String, Object> paramMap = new HashMap<String, Object>();
       	paramMap.put("ctgSct", Integer.valueOf(sSct));
    	List<HashMap<String, Object>> ctgList = filesService.selectCategoryTree(paramMap);
       	
        model.addAttribute("ctgList", ctgList);
        
        return "/files/tree_ajax";
    }

    /**
     * 산출물 등록 페이지 요청
     */
    @RequestMapping("/files/file_upload")
    public String fileUpload(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	    	
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
    	String sId = request.getParameter("id");
    	
    	if (!StringUtil.isEmpty(sId)) {
    		HashMap<String, Object> paramMap = new HashMap<String, Object>();
    		paramMap.put("pId", Integer.valueOf(sId));
    		HashMap<String, Object> filesMap =  filesService.selectProduct(paramMap);
    		model.addAttribute("filesMap", filesMap);
    		
        	Files files = new Files();
        	files.setpId(Integer.valueOf(sId));
        	List<Files> filesList = filesService.selectFiles(files);
        	model.addAttribute("filesList", filesList);
        	
        	//조회 카운트 업데이트
        	if (!StringUtil.convertString(filesMap.get("regr")).equals(userInfo.getAcct().getAcctId())) {
	        	paramMap.put("query", 1);
	        	paramMap.put("parent", filesMap.get("parent"));
	        	filesService.updateProduct(request, paramMap, null);
        	}
    	}
    	
    	return "/files/file_upload";
    }

    /**
     * 산출물 등록/수정
     */
    @RequestMapping("/files/file_register")
    public ModelAndView fileRegister(MultipartHttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	    
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
        List<MultipartFile> mpfList = request.getFiles("file-data");					//첨부파일 리스트
    	
        String sId = request.getParameter("uf_id");										//산출물 ID
        String sParent = request.getParameter("uf_parent");								//상위카테고리
        String sTitle = StringUtil.replaceHtml(request.getParameter("uf_title"));		//제목
        String sContent = StringUtil.replaceHtml(request.getParameter("uf_content"));	//내용
        String sDel = request.getParameter("uf_del");									//삭제요청 파일 리스트
        
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("title", sTitle);
    	paramMap.put("content", sContent);
    	paramMap.put("parent", Integer.valueOf(sParent));
    	paramMap.put("modr", userInfo.getAcct().getAcctId());
    	paramMap.put("modDtime", new Date());

    	if (sId != null) {
    		paramMap.put("pId", Integer.valueOf(sId));
    		filesService.updateProduct(request, paramMap, mpfList);
    		
    		if (!StringUtil.isEmpty(sDel)) {
    			String[] saDel = sDel.split(",");
    			for (String f : saDel) {
    				if (StringUtil.isEmpty(f)) {
    					continue;
    				}
    				removeFile(f, sId, userInfo);
    			}
    		}
    		
    		logger.info("산출물을 수정했습니다.");
    	} else {
        	paramMap.put("fileYn", "n");
        	paramMap.put("delYn", "n");
        	paramMap.put("regr", userInfo.getAcct().getAcctId());
        	paramMap.put("regDtime", new Date());
    		
        	filesService.insertProduct(request, paramMap, mpfList);
    		sId = StringUtil.convertString(paramMap.get("pId"));
    		
    		logger.info("산출물을 등록했습니다.");
    	}
    	
        
        logger.info("제목 : {}", sTitle);
        logger.info("내용 : {}", sContent);
        logger.info("등록자 : {}({})", userInfo.getAcct().getAcctId(), userInfo.getAcct().getAcctNm());
    	
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("parent", paramMap.get("parent"));
        return modelAndView.addObject("status", "success");
    }    
    
    /**
     * 산출물 파일 삭제
     */
    @RequestMapping("/files/file_delete")
    public ModelAndView fileDelete(HttpServletRequest request) throws Exception {
    	
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
    	String sId = request.getParameter("id");
    	
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pId", Integer.valueOf(sId));
		HashMap<String, Object> filesMap =  filesService.selectProduct(paramMap);

		if (filesMap == null) {
			throw new BizException("이미 삭제되었습니다.");
		}
		
		filesService.deleteProduct(paramMap);
		
		Files appxFile = new Files();
		appxFile.setpId(Integer.parseInt(sId));
		List<Files> appxFiles = filesService.selectFiles(appxFile);
		
		for (Files f : appxFiles) {
			File uFile = new File(f.getFileLoc());
			if (!uFile.delete()) { }
		}
		filesService.deleteFilesByRef(paramMap);
		
		StringBuffer sMemo = new StringBuffer();
		sMemo = sMemo.append("위치:").append(StringUtil.convertString(filesMap.get("pathNm"))).append("\n");
		sMemo = sMemo.append("제목:").append(StringUtil.convertString(filesMap.get("title"))).append("\n");
		
		Common.insertAuditHist(Constants.AUDIT_FILES, "산출물이 삭제되었습니다.", "S", sMemo.toString(), userInfo.getAcct().getAcctId());

        logger.info("산출물을 삭제했습니다.");
        logger.info("산출물 경로 : {}", StringUtil.convertString(filesMap.get("pathNm")));
        logger.info("산출물 제목 : {}", StringUtil.convertString(filesMap.get("title")));
        logger.info("삭제자 : {}({})", userInfo.getAcct().getAcctId(), userInfo.getAcct().getAcctNm());
        
        ModelAndView modelAndView = new ModelAndView("jsonView");
        return modelAndView.addObject("status", "success"); 
    }
    
    /**
     * 산출물 파일 목록 조회
     */
    @RequestMapping("/files/file_ajax")
    public String fileAjax(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	    	
    	String sParent = request.getParameter("parent");

       	Product product = (Product)DataUtil.dtoBuilder(request, Product.class);

    	if (StringUtil.isEmpty(sParent)) {
    		model.addAllAttributes(product.createModelMap(null));
    		return "/files/file_ajax";
    	}
       	
    	List<HashMap<String, Object>> productList = filesService.selectProductList(product);
       	
    	model.addAllAttributes(product.createModelMap(productList));
    	 
    	return "/files/file_ajax";
    }

    /**
     * 산출물 파일에 대한 전체 검색
     */
    @RequestMapping("/files/allover_ajax")
    public String allOverAjax(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	    	
       	Product product = (Product)DataUtil.dtoBuilder(request, Product.class);
       	product.setSearchSel("1");
       	
    	List<HashMap<String, Object>> productList = filesService.selectProductList(product);
       	
    	model.addAllAttributes(product.createModelMap(productList));
    	 
    	return "/files/allover_ajax";
    }

    /**
     * 산출물 첨부파일 리스트
     */
    @RequestMapping("/files/appx_list")
    public String appxList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	 
    	String sId = request.getParameter("id");
    	
    	if (!StringUtil.isEmpty(sId)) {
        	Files files = new Files();
        	files.setpId(Integer.valueOf(sId));
        	List<Files> appxList = filesService.selectFiles(files);
        	model.addAttribute("appxList", appxList);
    	}

    	return "/files/file_box";
    }
    
    /**
     * 산출물 첨부파일 다운로드
     */
    @RequestMapping("/files/downloadFile")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	String sFileSeq = request.getParameter("seq");
    	
    	 Files appxFile = new Files();
    	 appxFile.setFileId(Integer.valueOf(sFileSeq));
    	 List<Files> appxFiles = filesService.selectFiles(appxFile);
    	
    	if (appxFiles.size() == 0) {
    		throw new Exception();
    	}
    	
		String sOrgFileName = appxFiles.get(0).getFileOrgNm();
		
		File uFile = new File(appxFiles.get(0).getFileLoc());
		int fSize = (int) uFile.length();
 
		if (fSize > 0) {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(uFile));

			String mimetype = "text/html";
 
			response.setBufferSize(fSize);
			response.setContentType(mimetype);
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ URLEncoder.encode(sOrgFileName, "UTF-8") + "\"");
			response.setContentLength(fSize);
 
			FileCopyUtils.copy(in, response.getOutputStream());
			in.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} else {
			throw new Exception();
		}
	}
    
    /**
     * 첨부파일 삭제
     */
	public void removeFile(String sFileSeq, String sRef, UserInfo userInfo) throws Exception {

    	List<Files> appxFiles = null;
    	HashMap<String, Object> hRefMap = null;
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("pId", Integer.parseInt(sRef));
		hRefMap = filesService.selectProduct(paramMap);
		
		Files appxFile = new Files();
		appxFile.setFileId(Integer.valueOf(sFileSeq));
		appxFiles = filesService.selectFiles(appxFile);
    	
		if (appxFiles.size() == 0) {
			throw new BizException("파일을 찾을 수 없습니다.");
		}
		
		paramMap.clear();
		paramMap.put("fileId", Integer.parseInt(sFileSeq));
		filesService.deleteFilesById(paramMap);

		File uFile = new File(appxFiles.get(0).getFileLoc());
		if (!uFile.delete()) { }
		
		StringBuffer sMemo = new StringBuffer();
		sMemo = sMemo.append("위치:").append(StringUtil.convertString(hRefMap.get("pathNm"))).append("\n");
		sMemo = sMemo.append("제목:").append(StringUtil.convertString(hRefMap.get("title"))).append("\n");
		sMemo = sMemo.append("파일:").append(StringUtil.convertString(appxFiles.get(0).getFileOrgNm())).append("\n");
		
		Common.insertAuditHist(Constants.AUDIT_FILES, "파일이 삭제되었습니다.", "S", sMemo.toString(), userInfo.getAcct().getAcctId());
		
		appxFile = new Files();
		appxFile.setpId(Integer.parseInt(sRef));
		appxFiles = filesService.selectFiles(appxFile);
		if (appxFiles.size() == 0) {
			paramMap.clear();
			paramMap.put("pId", Integer.parseInt(sRef));
			paramMap.put("parent", hRefMap.get("parent"));
			paramMap.put("fileYn", "n");
			filesService.updateProduct(null, paramMap, null);
		}
	}
}