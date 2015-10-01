package com.cyberone.scourt.files.service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cyberone.scourt.exception.BizException;
import com.cyberone.scourt.files.dao.FilesDao;
import com.cyberone.scourt.model.Files;
import com.cyberone.scourt.model.Product;
import com.cyberone.scourt.model.UserInfo;
import com.cyberone.scourt.utils.StringUtil;

@Service
public class FilesService {
	
	@Autowired
	private FilesDao filesDao;

	public HashMap<String, Object> selectCategory(HashMap<String, Object> paramMap) throws Exception {
		return filesDao.selectCategory(paramMap);
	}

	public List<HashMap<String, Object>> selectCategoryList(HashMap<String, Object> paramMap) throws Exception {
		return filesDao.selectCategoryList(paramMap);
	}
	
	public List<HashMap<String, Object>> selectCategoryTree(HashMap<String, Object> paramMap) throws Exception {
		return filesDao.selectCategoryTree(paramMap);
	}

	public List<HashMap<String, Object>> selectProductList(Product dto) throws Exception {
		return filesDao.selectProductList(dto);
	}
	
	public HashMap<String, Object> selectProduct(HashMap<String, Object> paramMap) throws Exception {
		return filesDao.selectProduct(paramMap);
	}
	
	public int deleteProduct(HashMap<String, Object> paramMap) throws Exception {
		return filesDao.deleteProduct(paramMap);
	}
	
	public List<Files> selectFiles(Files files) throws Exception {
		return filesDao.selectFiles(files);
	}
	
	public int deleteFilesById(HashMap<String, Object> paramMap) throws Exception {
		return filesDao.deleteFilesById(paramMap);
	}

	public int deleteFilesByRef(HashMap<String, Object> paramMap) throws Exception {
		return filesDao.deleteFilesByRef(paramMap);
	}

	public void insertCategory(HashMap<String, Object> paramMap) throws Exception {
		filesDao.insertCategory(paramMap);
	}

	public void updateCategory(HashMap<String, Object> paramMap) throws Exception {
		filesDao.updateCategory(paramMap);
	}	
	
	public void deleteCategory(HashMap<String, Object> paramMap) throws Exception {
		filesDao.deleteCategory(paramMap);
	}
	
	public void insertProduct(HttpServletRequest request, HashMap<String, Object> paramMap, List<MultipartFile> mpfList) throws Exception {
        for (MultipartFile mpf : mpfList) {
        	if (mpf.getSize() > 0) {
        		paramMap.put("fileYn", "y");
        	}
        }
        filesDao.insertProduct(paramMap);
        
        transferFiles(request, (Integer)paramMap.get("parent"), mpfList, (Integer)paramMap.get("pId"));
	}
	
	public void updateProduct(HttpServletRequest request, HashMap<String, Object> paramMap, List<MultipartFile> mpfList) throws Exception {
        int fileCount = transferFiles(request, (Integer)paramMap.get("parent"), mpfList, (Integer)paramMap.get("pId"));
        if (fileCount > 0) {
        	paramMap.put("fileYn", "y");
        }
        filesDao.updateProduct(paramMap);
	}
	
	public int transferFiles(HttpServletRequest request, int nParent, List<MultipartFile> mpfList, int pId) throws Exception {
		
		if (mpfList == null) return 0;
		
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
            
            filesDao.insertFiles(paramMap);
        }
		return fileCount;
	}
	
	public List<HashMap<String, Object>> selectMenu(HashMap<String, Object> paramMap) throws Exception {
		return filesDao.selectMenu(paramMap);
	}
}
