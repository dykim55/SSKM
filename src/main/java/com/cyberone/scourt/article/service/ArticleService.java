package com.cyberone.scourt.article.service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cyberone.scourt.article.dao.ArticleDao;
import com.cyberone.scourt.exception.BizException;
import com.cyberone.scourt.model.AppxFile;
import com.cyberone.scourt.model.Board;
import com.cyberone.scourt.model.UserInfo;
import com.cyberone.scourt.utils.StringUtil;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleDao articleDao;

	public List<HashMap<String, Object>> selectBoardList(Board dto) throws Exception {
		return articleDao.selectBoardList(dto);
	}
	
	public HashMap<String, Object> selectBoard(HashMap<String, Object> paramMap) throws Exception {
		return articleDao.selectBoard(paramMap);
	}

	public List<AppxFile> selectAppxFiles(AppxFile appxFile) throws Exception {
		return articleDao.selectAppxFiles(appxFile);
	}

	public void insertBoard(HttpServletRequest request, HashMap<String, Object> paramMap, String sFileType, List<MultipartFile> mpfList) throws Exception {
        for (MultipartFile mpf : mpfList) {
        	if (mpf.getSize() > 0) {
        		paramMap.put("fileYn", "y");
        	}
        }
        articleDao.insertBoard(paramMap);
        updateAppxFile(request, sFileType, mpfList, (Integer)paramMap.get("bbsId"));
	}
	
	public void updateBoard(HashMap<String, Object> paramMap) throws Exception {
		articleDao.updateBoard(paramMap);
	}
	
	public void updateBoard(HttpServletRequest request, HashMap<String, Object> paramMap, String sFileType, List<MultipartFile> mpfList) throws Exception {
        int appxFileCount = updateAppxFile(request, sFileType, mpfList, (Integer)paramMap.get("bbsId"));
        if (appxFileCount > 0) {
        	paramMap.put("fileYn", "y");
        }
        articleDao.updateBoard(paramMap);
	}
	
	public int updateAppxFile(HttpServletRequest request, String sFileType, List<MultipartFile> mpfList, int refCd) throws Exception {
		
		int fileCount = 0;
        for (MultipartFile mpf : mpfList) {
        
        	if (mpf.getSize() == 0) continue;
        	
        	String sHomePath = (new File("/")).getAbsolutePath();

        	File uploadHome = new File(sHomePath + "/scourt/");
            if (!uploadHome.exists()) {
            	uploadHome.mkdir();
            }
        	
            uploadHome = new File(sHomePath + "/scourt/uploadFiles/");
            if (!uploadHome.exists()) {
            	uploadHome.mkdir();
            }
        	
        	File uploadSub = new File(sHomePath + "/scourt/uploadFiles/" + sFileType + "/");
        	if (!uploadSub.exists()) uploadSub.mkdir();
        	
        	String fileName = mpf.getOriginalFilename();
        	String invalid_ext[] = {"jsp", "html", "htm"};
        	String fileExt = fileName.substring(fileName.lastIndexOf('.') + 1);
        	for(int i = 0; i < invalid_ext.length; i++) {
        	   if (fileExt.equalsIgnoreCase(invalid_ext[i])) {
        		   throw new BizException("업로드가 불가능 파일입니다.");
        	   }
        	}
        	
        	File uploadFile = new File(sHomePath + "/scourt/uploadFiles/" + sFileType + "/" + mpf.getOriginalFilename());
        	uploadFile = StringUtil.rename(uploadFile);
            
            mpf.transferTo(uploadFile);
            
            UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
            
            AppxFile appxFile = new AppxFile();
            appxFile.setRefCd(refCd);
            appxFile.setFileTp(sFileType);
            appxFile.setFileLoc(uploadFile.getAbsolutePath());
            appxFile.setFileOrgNm(mpf.getOriginalFilename());
            appxFile.setFileSavNm(uploadFile.getName());
            appxFile.setRegr(userInfo.getAcct().getAcctId());
            appxFile.setRegDtime(new Date());
            appxFile.setModr(userInfo.getAcct().getAcctId());
            appxFile.setModDtime(new Date());
            
            fileCount++;
            articleDao.writeAppxFile(appxFile);
        }
		return fileCount;
	}

	public int deleteAppxById(HashMap<String, Object> paramMap) throws Exception {
		return articleDao.deleteAppxById(paramMap);
	}

	public int deleteAppxByRef(HashMap<String, Object> paramMap) throws Exception {
		return articleDao.deleteAppxByRef(paramMap);
	}
	
}
