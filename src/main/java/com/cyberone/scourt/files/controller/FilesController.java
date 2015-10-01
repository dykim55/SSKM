package com.cyberone.scourt.files.controller;

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

import com.cyberone.scourt.Common;
import com.cyberone.scourt.Constants;
import com.cyberone.scourt.exception.BizException;
import com.cyberone.scourt.files.service.FilesService;
import com.cyberone.scourt.model.Files;
import com.cyberone.scourt.model.Product;
import com.cyberone.scourt.model.UserInfo;
import com.cyberone.scourt.utils.DataUtil;
import com.cyberone.scourt.utils.StringUtil;


@Controller
public class FilesController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private FilesService filesService;

    @RequestMapping(value = {"/files/security_control", "/files/security_diagnosis", "/files/info_protection_manage", "/files/info_protection_policy", "/files/info_protection_trend", "/files/security_news"})
    public String files(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
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

    @RequestMapping("/files/create_folder")
    public String createFolder(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	String sCtgId = request.getParameter("id");

    	if (!StringUtil.isEmpty(sCtgId)) {
	       	HashMap<String, Object> paramMap = new HashMap<String, Object>();
	       	paramMap.put("ctgId", Integer.valueOf(sCtgId));
	    	HashMap<String, Object> category = filesService.selectCategory(paramMap);
	    	model.addAttribute("category", category);
    	}
    	return "/files/create_folder";
    }

    @RequestMapping("/files/folder_register")
    public ModelAndView folderRegister(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
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
           	paramMap.put("ctgSct", Integer.valueOf(sSct));
           	paramMap.put("ctgParent", Integer.valueOf(sParent));
           	paramMap.put("regr", userInfo.getAcct().getAcctId());
           	paramMap.put("regDtime", new Date());
       		filesService.insertCategory(paramMap);
       	} else {
       		paramMap.put("ctgId", sId);
       		filesService.updateCategory(paramMap);
       	}
       	
    	ModelAndView modelAndView = new ModelAndView("jsonView");
    	modelAndView.addObject("gubun", Integer.valueOf(sSct));
    	modelAndView.addObject("id", paramMap.get("ctgId"));
    	modelAndView.addObject("name", sName);
    	modelAndView.addObject("parent", Integer.valueOf(sParent));

    	return modelAndView.addObject("status", "success");
    }
    
    @RequestMapping("/files/delete_folder")
    public ModelAndView deleteFolder(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());

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
    	
    	paramMap.clear();
   		paramMap.put("ctgId", (Integer)ctgMap.get("ctgId"));    	
    	filesService.deleteCategory(paramMap);
    	
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
        Common.insertAuditHist(Constants.AUDIT_FILES, "업무폴더 '" + StringUtil.convertString(ctgMap.get("ctgNm")) + "'이 삭제되었습니다.", "S", "", userInfo.getAcct().getAcctId());
    	
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("parent", ctgMap.get("ctgParent"));
        return modelAndView.addObject("status", "success"); 
    }
    
    @RequestMapping("/files/tree_ajax")
    public String treePane(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	String sSct = request.getParameter("gubun");
       	
       	HashMap<String, Object> paramMap = new HashMap<String, Object>();
       	paramMap.put("ctgSct", Integer.valueOf(sSct));
    	List<HashMap<String, Object>> ctgList = filesService.selectCategoryTree(paramMap);
       	
        model.addAttribute("ctgList", ctgList);
        
        return "/files/tree_ajax";
    }
    
    @RequestMapping("/files/file_upload")
    public String fileUpload(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
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
        	
        	if (!StringUtil.convertString(filesMap.get("regr")).equals(userInfo.getAcct().getAcctId())) {
	        	paramMap.put("query", 1);
	        	paramMap.put("parent", filesMap.get("parent"));
	        	filesService.updateProduct(request, paramMap, null);
        	}
    	}
    	
    	return "/files/file_upload";
    }

    @RequestMapping("/files/file_register")
    public ModelAndView fileRegister(MultipartHttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
        List<MultipartFile> mpfList = request.getFiles("file-data");
    	
        String sId = request.getParameter("uf_id");
        
        String sParent = request.getParameter("uf_parent");
        String sTitle = StringUtil.replaceHtml(request.getParameter("uf_title"));
        String sContent = StringUtil.replaceHtml(request.getParameter("uf_content"));
        String sDel = request.getParameter("uf_del");
        
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
    		
    	} else {
        	paramMap.put("fileYn", "n");
        	paramMap.put("delYn", "n");
        	paramMap.put("regr", userInfo.getAcct().getAcctId());
        	paramMap.put("regDtime", new Date());
    		
        	filesService.insertProduct(request, paramMap, mpfList);
    		sId = StringUtil.convertString(paramMap.get("pId"));
    	}
    	
    	/* Daum Editor
    	//삭제된 본문 이미지 삭제
    	if (sRemoveImages != null) {
    		boardService.removeEditorImage(request, paramMap, sRemoveImages);
    	}

        //본문첨부 이미지정보 저장
    	if (sAttachImages != null) {
    		boardService.insertAttachments(request, paramMap, sAttachImages);
    	}
    	*/
        
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("parent", paramMap.get("parent"));
        return modelAndView.addObject("status", "success");
    }    
    
    @RequestMapping("/files/file_delete")
    public ModelAndView fileDelete(HttpServletRequest request) throws Exception {
    	logger.debug(request.getServletPath());

    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
    	String sId = request.getParameter("id");
    	
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pId", Integer.valueOf(sId));
		HashMap<String, Object> filesMap =  filesService.selectProduct(paramMap);

		if (filesMap == null) {
			throw new BizException("이미 삭제되었습니다.");
		}
		
		filesService.deleteProduct(paramMap);
		
		filesService.deleteFilesByRef(paramMap);
		
		Common.insertAuditHist(Constants.AUDIT_FILES, "산출물이 삭제되었습니다.", "S", StringUtil.convertString(filesMap.get("title")), userInfo.getAcct().getAcctId());
		
        ModelAndView modelAndView = new ModelAndView("jsonView");
        return modelAndView.addObject("status", "success"); 
    }
    
    @RequestMapping("/files/file_ajax")
    public String fileAjax(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	String sParent = request.getParameter("parent");
    	
    	if (StringUtil.isEmpty(sParent)) {
    		return "/files/file_ajax";
    	}
       	
       	HashMap<String, Object> paramMap = new HashMap<String, Object>();
       	paramMap.put("parent", Integer.valueOf(sParent));
       	
       	Product product = (Product)DataUtil.dtoBuilder(request, Product.class);
       	
    	List<HashMap<String, Object>> productList = filesService.selectProductList(product);
       	
    	model.addAllAttributes(product.createModelMap(productList));
    	 
    	return "/files/file_ajax";
    }
    
    @RequestMapping("/files/appx_list")
    public String appxList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
 
    	String sId = request.getParameter("id");
    	
    	if (!StringUtil.isEmpty(sId)) {
        	Files files = new Files();
        	files.setpId(Integer.valueOf(sId));
        	List<Files> appxList = filesService.selectFiles(files);
        	model.addAttribute("appxList", appxList);
    	}

    	return "/files/file_box";
    }
    
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

    @RequestMapping("/files/removeAppxFile")
	public ModelAndView removeAppxFile(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
    	String sType = request.getParameter("type");
    	String sRef = request.getParameter("ref");
    	String sFileSeq = request.getParameter("seq");
    	
    	List<Files> appxFiles = null;
    	HashMap<String, Object> hRefMap = null;
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	if (sType.equals("1")) {
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
    		
    		Common.insertAuditHist(Constants.AUDIT_FILES, "첨부파일 '" + appxFiles.get(0).getFileOrgNm() + "'이 삭제되었습니다.", "S", StringUtil.convertString(hRefMap.get("title")), userInfo.getAcct().getAcctId());
    		
    		appxFile = new Files();
    		appxFile.setpId(Integer.parseInt(sRef));
    		appxFiles = filesService.selectFiles(appxFile);
    		if (appxFiles.size() == 0) {
    			paramMap.clear();
    			paramMap.put("pId", Integer.parseInt(sRef));
    			paramMap.put("parent", hRefMap.get("parent"));
    			paramMap.put("fileYn", "n");
    			filesService.updateProduct(request, paramMap, null);
    		}
    		
    	} else if (sType.equals("일반")) {
    	}
		
		ModelAndView modelAndView = new ModelAndView("jsonView");
    	modelAndView.addObject("message", "삭제되었습니다.");

    	return modelAndView.addObject("status", "success"); 
	}
    
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
		
		Common.insertAuditHist(Constants.AUDIT_FILES, "첨부파일 '" + appxFiles.get(0).getFileOrgNm() + "'이 삭제되었습니다.", "S", StringUtil.convertString(hRefMap.get("title")), userInfo.getAcct().getAcctId());
		
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @RequestMapping("product")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
       	String sSct = request.getParameter("gubun");
       	
       	HashMap<String, Object> paramMap = new HashMap<String, Object>();
       	paramMap.put("ctgSct", Integer.valueOf(sSct));
    	List<HashMap<String, Object>> ctgList = filesService.selectCategoryTree(paramMap);
       	
        model.addAttribute("ctgList", ctgList);
        
        return "/document/" + request.getServletPath();
    }


    @RequestMapping("file_pane")
    public String filePane(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	String sParent = request.getParameter("parent");
       	
       	HashMap<String, Object> paramMap = new HashMap<String, Object>();
       	paramMap.put("parent", Integer.valueOf(sParent));
       	
       	Product product = (Product)DataUtil.dtoBuilder(request, Product.class);
       	
    	List<HashMap<String, Object>> productList = filesService.selectProductList(product);
       	
    	model.addAllAttributes(product.createModelMap(productList));
    	 
    	return "/document/file_pane";
    }
    
    @RequestMapping("product/upload_file")
    public String uploadFile(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	return "/document/upload_file";
    }

   
     
}