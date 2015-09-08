package com.cyberone.scourt.document.service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cyberone.scourt.document.dao.DocumentDao;
import com.cyberone.scourt.exception.BizException;
import com.cyberone.scourt.model.Product;
import com.cyberone.scourt.model.UserInfo;
import com.cyberone.scourt.utils.StringUtil;

@Service
public class DocumentService {
	
	@Autowired
	private DocumentDao docDao;

	public List<HashMap<String, Object>> selectCategory(HashMap<String, Object> paramMap) throws Exception {
		return docDao.selectCategory(paramMap);
	}

	public List<HashMap<String, Object>> selectProductList(Product dto) throws Exception {
		return docDao.selectProductList(dto);
	}
	
	public void insertCategory(HashMap<String, Object> paramMap) throws Exception {
		docDao.insertCategory(paramMap);
	}

	public void updateCategory(HashMap<String, Object> paramMap) throws Exception {
		docDao.updateCategory(paramMap);
	}	
	
	public void insertProduct(HttpServletRequest request, HashMap<String, Object> paramMap, List<MultipartFile> mpfList) throws Exception {
        for (MultipartFile mpf : mpfList) {
        	if (mpf.getSize() > 0) {
        		paramMap.put("fileYn", "y");
        	}
        }
        docDao.insertProduct(paramMap);
        
        updateDcmtFile(request, (Integer)paramMap.get("parent"), mpfList, (Integer)paramMap.get("pId"));
	}
	
	public void updateProduct(HttpServletRequest request, HashMap<String, Object> paramMap, List<MultipartFile> mpfList) throws Exception {
        int fileCount = updateDcmtFile(request, (Integer)paramMap.get("parent"), mpfList, (Integer)paramMap.get("pId"));
        if (fileCount > 0) {
        	paramMap.put("fileYn", "y");
        }
        docDao.updateProduct(paramMap);
	}
	
	public int updateDcmtFile(HttpServletRequest request, int nParent, List<MultipartFile> mpfList, int pId) throws Exception {
		
		int fileCount = 0;
        for (MultipartFile mpf : mpfList) {
        	if (mpf.getSize() == 0) continue;
        	
        	String sHomePath = (new File("/")).getAbsolutePath();

        	File uploadHome = new File(sHomePath + "/scourt/");
            if (!uploadHome.exists()) {
            	uploadHome.mkdir();
            }
        	
            uploadHome = new File(sHomePath + "/scourt/productFiles/");
            if (!uploadHome.exists()) {
            	uploadHome.mkdir();
            }
        	
        	File uploadSub = new File(sHomePath + "/scourt/productFiles/" + nParent + "/");
        	if (!uploadSub.exists()) uploadSub.mkdir();
        	
        	String fileName = mpf.getOriginalFilename();
        	String invalid_ext[] = {"jsp", "html", "htm"};
        	String fileExt = fileName.substring(fileName.lastIndexOf('.') + 1);
        	for(int i = 0; i < invalid_ext.length; i++) {
        	   if (fileExt.equalsIgnoreCase(invalid_ext[i])) {
        		   throw new BizException("업로드가 불가능 파일입니다.");
        	   }
        	}
        	
        	File uploadFile = new File(sHomePath + "/scourt/productFiles/" + nParent + "/" + mpf.getOriginalFilename());
        	uploadFile = StringUtil.rename(uploadFile);
            
            mpf.transferTo(uploadFile);
            
            UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
            
        	HashMap<String, Object> paramMap = new HashMap<String, Object>();
        	paramMap.put("pId", pId);
        	paramMap.put("fileSct", nParent);
        	paramMap.put("fileLoc", uploadFile.getAbsolutePath());
        	paramMap.put("fileSize", uploadFile.length());
        	paramMap.put("fileOrgNm", mpf.getOriginalFilename());
        	paramMap.put("fileSavNm", uploadFile.getName());
        	paramMap.put("regr", userInfo.getAcct().getAcctId());
        	paramMap.put("regDtime", new Date());
        	paramMap.put("modr", userInfo.getAcct().getAcctId());
        	paramMap.put("modDtime", new Date());

            fileCount++;
            
            docDao.insertDcmtFile(paramMap);
        }
		return fileCount;
	}
	
}
