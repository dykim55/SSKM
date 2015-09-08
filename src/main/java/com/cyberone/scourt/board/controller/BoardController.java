package com.cyberone.scourt.board.controller;

import java.io.File;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cyberone.scourt.Constants;
import com.cyberone.scourt.board.service.BoardService;
import com.cyberone.scourt.exception.BizException;
import com.cyberone.scourt.model.AppxFile;
import com.cyberone.scourt.model.Board;
import com.cyberone.scourt.model.UserInfo;
import com.cyberone.scourt.utils.DataUtil;
import com.cyberone.scourt.utils.StringUtil;


@Controller
public class BoardController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private BoardService boardService;

    @RequestMapping(value = {"notice", "raws", "privacy", "securityTrend"})
    public String board(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);

       	String sBbsSct = Constants.getBbsSct(request.getServletPath());
       	
        /*
        if (!userInfo.isAuth("2006", "R")) {
        	throw new BizException("권한이 없습니다.");
        }
        */
        
       	Board dto = (Board) DataUtil.dtoBuilder(request, Board.class);
       	dto.setBbsSct(sBbsSct);
       	
    	List<HashMap<String, Object>> bbsList = boardService.selectBoardList(dto);
       	
        model.addAllAttributes(dto.createModelMap(bbsList));
        
        return "/board/" + request.getServletPath();
    }

    @RequestMapping("noticeList")
    public ModelAndView boardList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);

        List<HashMap<String, Object>> noticeList = null;
        Board board = (Board) DataUtil.dtoBuilder(request, Board.class);
        
        noticeList = boardService.selectBoardList(board);
        
    	ModelAndView modelAndView = new ModelAndView("jsonView");
    	modelAndView.addAllObjects(board.createModelMap(noticeList));
    	return modelAndView.addObject("status", "success"); 
    }
    
    @RequestMapping(value = {"noticeWrite", "rawsWrite", "privacyWrite", "securityTrendWrite"})
    public String boardWrite(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
        
        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);
        
        return "/board/" + request.getServletPath();
    }
    
    @RequestMapping(value = {"noticeDtl", "rawsDtl", "privacyDtl", "securityTrendDtl"})
    public String boardDtl(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
        
        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);

        String sBbsSct = Constants.getBbsSct(request.getServletPath());
        
        int nBbsId = Integer.valueOf(request.getParameter("bbs"));

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("bbsId", nBbsId);
        paramMap.put("bbsSct", sBbsSct);
    	HashMap<String, Object> mapDetail = boardService.selectBoard(paramMap);

    	AppxFile appxFile = new AppxFile();
    	appxFile.setRefCd(nBbsId);
    	appxFile.setFileTp(sBbsSct);
    	List<AppxFile> appxFiles = boardService.selectAppxFiles(appxFile);
    	model.addAttribute("appxFiles", appxFiles);
    	
    	model.addAttribute("mapDetail", mapDetail);
    	if (StringUtil.convertString(mapDetail.get("regr")).equals(userInfo.getAcct().getAcctId())) {
    		model.addAttribute("editable", true);
    	} else {
    		boardService.updateQryCnt(paramMap);
    	}
    	
    	return "/board/" + request.getServletPath();
    }
    
    @RequestMapping(value = {"noticeReg", "rawsReg", "privacyReg", "securityTrendReg"})
    public String boardReg(MultipartHttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());

        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);
        
    	String[] sAttachImages = request.getParameterValues("attach_image");
    	String[] sRemoveImages = request.getParameterValues("remove_image");
    	
        List<MultipartFile> mpfList = request.getFiles("file-data");
        
        String sBbsId = request.getParameter("bbs");
        String sBbsSct = Constants.getBbsSct(request.getServletPath());

        String sTitle = request.getParameter("p_title");
        String sContent = request.getParameter("tx_content");
        if (StringUtil.isEmpty(sTitle)) {
        	throw new BizException("제목을 입력하십시요.");
        }

    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("bbsSct", sBbsSct);
    	paramMap.put("bbsTit", sTitle);
    	paramMap.put("bbsCont", sContent);
    	paramMap.put("modr", userInfo.getAcct().getAcctId());
    	paramMap.put("modDtime", new Date());

    	if (sBbsId != null) {
    		paramMap.put("bbsId", Integer.valueOf(sBbsId));

    		boardService.updateBoard(request, paramMap, sBbsSct, mpfList);
    	} else {
        	paramMap.put("regr", userInfo.getAcct().getAcctId());
        	paramMap.put("regDtime", new Date());
    		
    		boardService.insertBoard(request, paramMap, sBbsSct, mpfList);
    		sBbsId = StringUtil.convertString(paramMap.get("bbsId"));
    	}
    	
    	String sMapping = "";
    	if (sBbsSct.equals(Constants.BBS_NOTICE)) {
        	sMapping = "redirect:/notice";
    	} else if (sBbsSct.equals(Constants.BBS_RAWS)) {
    		sMapping = "redirect:/raws";
    	} else if (sBbsSct.equals(Constants.BBS_TREND)) {
    		sMapping = "redirect:/securityTrend";
    	} else if (sBbsSct.equals(Constants.BBS_PRIVACY)) {
    		sMapping = "redirect:/privacy";
    	}
    	
    	//삭제된 본문 이미지 삭제
    	if (sRemoveImages != null) {
    		boardService.removeEditorImage(request, paramMap, sRemoveImages);
    	}

        //본문첨부 이미지정보 저장
    	if (sAttachImages != null) {
    		boardService.insertAttachments(request, paramMap, sAttachImages);
    	}
    	
    	return sMapping;
    }
    
    @RequestMapping(value = {"noticeDel", "rawsDel", "privacyDel", "securityTrendDel"})
    public String boardDel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());

        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);

        String sBbsId = request.getParameter("bbs");
        String sBbsSct = Constants.getBbsSct(request.getServletPath());

    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("bbsId", Integer.valueOf(sBbsId));
    	paramMap.put("bbsSct", sBbsSct);
    	paramMap.put("modr", userInfo.getAcct().getAcctId());
    	paramMap.put("modDtime", new Date());
    	paramMap.put("delYn", "y");
    	
    	boardService.deleteBoard(request, paramMap);
    	
    	String sMapping = "";
    	if (sBbsSct.equals(Constants.BBS_NOTICE)) {
        	sMapping = "redirect:/notice";
    	} else if (sBbsSct.equals(Constants.BBS_RAWS)) {
    		sMapping = "redirect:/raws";
    	} else if (sBbsSct.equals(Constants.BBS_TREND)) {
    		sMapping = "redirect:/securityTrend";
    	} else if (sBbsSct.equals(Constants.BBS_PRIVACY)) {
    		sMapping = "redirect:/privacy";
    	}
    	
    	return sMapping;
    }
    
    @RequestMapping("editorImageUpload")
	public ModelAndView editorImageUpload(MultipartHttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
    	ModelAndView modelAndView = new ModelAndView("jsonView");
    	
    	String sBbsSct = Constants.getBbsSct(request.getParameter("opener_url"));
    	
		List<MultipartFile> mpfList = request.getFiles("file-data");
		
        for (MultipartFile mpf : mpfList) {
        	if (mpf.getSize() == 0) continue;
        	String sHomePath = request.getSession().getServletContext().getRealPath("/");
        	File uploadHome = new File(sHomePath + "/editorimage/");
            if (!uploadHome.exists()) {
            	uploadHome.mkdir();
            }
        	File uploadSub = new File(sHomePath + "/editorimage/" + sBbsSct + "/");
        	if (!uploadSub.exists()) uploadSub.mkdir();
        	String fileName = mpf.getOriginalFilename();
        	if (!StringUtil.isImageFile(fileName)) {
        		throw new BizException("업로드가 불가능 파일입니다.");
        	}
        	File uploadFile = new File(sHomePath + "/editorimage/" + sBbsSct + "/" + mpf.getOriginalFilename());
        	uploadFile = StringUtil.rename(uploadFile);
            mpf.transferTo(uploadFile);
            
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("bbsSct", sBbsSct);
			paramMap.put("regDtime", new Date());
			paramMap.put("imageurl", StringUtil.convertString(request.getHeader("origin")) + "/editorimage/" + sBbsSct + "/" + uploadFile.getName());
			paramMap.put("filename", uploadFile.getName());
            paramMap.put("filesize", uploadFile.length());
            paramMap.put("originalurl", StringUtil.convertString(request.getHeader("origin")) + "/editorimage/" + sBbsSct + "/" + uploadFile.getName());
            paramMap.put("thumburl", StringUtil.convertString(request.getHeader("origin")) + "/editorimage/" + sBbsSct + "/" + uploadFile.getName());
            
           	boardService.insertAttachments(paramMap);	
            
            modelAndView.addObject("imageurl", StringUtil.convertString(request.getHeader("origin")) + "/editorimage/" + sBbsSct + "/" + uploadFile.getName());
            modelAndView.addObject("filename", uploadFile.getName());
            modelAndView.addObject("seqno", paramMap.get("seqno"));
            modelAndView.addObject("filesize", mpf.getSize());
            modelAndView.addObject("originalurl", StringUtil.convertString(request.getHeader("origin")) + "/editorimage/" + sBbsSct + "/" + uploadFile.getName());
            modelAndView.addObject("thumburl", StringUtil.convertString(request.getHeader("origin")) + "/editorimage/" + sBbsSct + "/" + uploadFile.getName());
        }
        
        return modelAndView.addObject("status", "success"); 
	}

    @RequestMapping("attachInfo")
	public ModelAndView attachInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
    	ModelAndView modelAndView = new ModelAndView("jsonView");
    	
    	String sBbsId = request.getParameter("bbs");
 
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("bbsId", Integer.valueOf(sBbsId));
    	
        List<HashMap<String, Object>> attachList = boardService.selectAttachments(paramMap);
        
        List<HashMap<String, Object>> attachInfo = new ArrayList<HashMap<String, Object>>();
        
        for (HashMap<String, Object> hMap : attachList) {
        	HashMap<String, Object> dMap = new HashMap<String, Object>();
        	dMap.put("imageurl", hMap.get("imageurl"));
        	dMap.put("seqno", hMap.get("seqno"));
        	dMap.put("filename", hMap.get("filename"));
        	dMap.put("filesize", hMap.get("filesize"));
        	dMap.put("originalurl", hMap.get("originalurl"));
        	dMap.put("thumburl", hMap.get("thumburl"));
        	
        	HashMap<String, Object> pMap = new HashMap<String, Object>();
        	pMap.put("attacher", "image");
        	pMap.put("data", dMap);

        	attachInfo.add(pMap);
        }
        modelAndView.addObject("attachInfo", attachInfo);
        		
        return modelAndView.addObject("status", "success"); 
	}
    
}