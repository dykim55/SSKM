package com.cyberone.scourt.board.service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cyberone.scourt.board.dao.BoardDao;
import com.cyberone.scourt.exception.BizException;
import com.cyberone.scourt.model.AppxFile;
import com.cyberone.scourt.model.Board;
import com.cyberone.scourt.model.UserInfo;
import com.cyberone.scourt.utils.StringUtil;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;

	public List<HashMap<String, Object>> selectBoardList(Board dto) throws Exception {
		return boardDao.selectBoardList(dto);
	}
	
	public HashMap<String, Object> selectBoard(HashMap<String, Object> paramMap) throws Exception {
		return boardDao.selectBoard(paramMap);
	}
	
	public void insertBoard(HttpServletRequest request, HashMap<String, Object> paramMap, String sFileType, List<MultipartFile> mpfList) throws Exception {
        for (MultipartFile mpf : mpfList) {
        	if (mpf.getSize() > 0) {
        		paramMap.put("fileYn", "y");
        	}
        }
        boardDao.insertBoard(paramMap);
        updateAppxFile(request, sFileType, mpfList, (Integer)paramMap.get("bbsId"));
	}

	public void updateBoard(HttpServletRequest request, HashMap<String, Object> paramMap, String sFileType, List<MultipartFile> mpfList) throws Exception {
        int appxFileCount = updateAppxFile(request, sFileType, mpfList, (Integer)paramMap.get("bbsId"));
        if (appxFileCount > 0) {
        	paramMap.put("fileYn", "y");
        }
        boardDao.updateBoard(paramMap);
	}

	public void updateBoard(HashMap<String, Object> paramMap) throws Exception {
		boardDao.updateBoard(paramMap);
	}

	public void deleteBoard(HttpServletRequest request, HashMap<String, Object> paramMap) throws Exception {
		
    	AppxFile appxFile = new AppxFile();
    	appxFile.setRefCd((Integer)paramMap.get("bbsId"));

    	List<AppxFile> appxFiles = selectAppxFiles(appxFile);
		
		paramMap.put("refCd", (Integer)paramMap.get("bbsId"));
    	//첨부파일 삭제여부 업데이트
		boardDao.updateAppxFile(paramMap);

    	//에디터 첨부이미지 삭제
		String sHomePath = request.getSession().getServletContext().getRealPath("/");
		String sBbsSct = StringUtil.convertString(paramMap.get("bbsSct"));
		 
		List<HashMap<String, Object>> attachList = selectAttachments(paramMap);
		 
		boardDao.deleteAttachments(paramMap);
		
		for (HashMap<String, Object> hMap : attachList) {
			File imageFile = new File(sHomePath + "/editorimage/" + sBbsSct + "/" + StringUtil.convertString(hMap.get("filename")));
			paramMap.put("filename", imageFile.getName());
			imageFile.delete();
		}
    	
		//게시글삭제
		boardDao.updateBoard(paramMap);
		
		for (AppxFile af : appxFiles) {

        	sHomePath = (new File("/")).getAbsolutePath();

        	File uploadHome = new File(sHomePath + "/scourt/");
            if (!uploadHome.exists()) {
            	uploadHome.mkdir();
            }

            uploadHome = new File(sHomePath + "/scourt/deletedFiles/");
            if (!uploadHome.exists()) {
            	uploadHome.mkdir();
            }
            
        	File uploadSub = new File(sHomePath + "/scourt/deletedFiles/" + af.getFileTp() + "/");
        	if (!uploadSub.exists()) uploadSub.mkdir();
            
        	File moveFile = new File(sHomePath + "/scourt/deletedFiles/" + af.getFileTp() + "/" + af.getFileSavNm());
        	
        	File deleteFile = new File(af.getFileLoc());
        	
        	boolean isMoved = deleteFile.renameTo(moveFile);
        	
        	if (isMoved) {
        		System.out.println("Moved file : " + moveFile.getAbsolutePath());
        	}
		}
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
            boardDao.writeAppxFile(appxFile);
        }
		return fileCount;
	}
	
	public List<AppxFile> selectAppxFiles(AppxFile appxFile) throws Exception {
		return boardDao.selectAppxFiles(appxFile);
	}

	public void removeEditorImage(HttpServletRequest request, HashMap<String, Object> paramMap, String[] sRemoveImages) throws Exception {
		String sHomePath = request.getSession().getServletContext().getRealPath("/");
		String sBbsSct = StringUtil.convertString(paramMap.get("bbsSct"));
		for (String sSeqno : sRemoveImages) {
			HashMap<String, Object> pMap = new HashMap<String, Object>();
			pMap.put("seqno", Integer.valueOf(sSeqno));
			List<HashMap<String, Object>> attachList = selectAttachments(pMap);
			for (HashMap<String, Object> hMap : attachList) {
				File imageFile = new File(sHomePath + "/editorimage/" + sBbsSct + "/" + hMap.get("filename"));
				imageFile.delete();
				boardDao.deleteAttachments(pMap);	
			}
		}
	}

	public void insertAttachments(HttpServletRequest request, HashMap<String, Object> paramMap, String[] sAttachImages) throws Exception {
		for (String sSeqno : sAttachImages) {
			HashMap<String, Object> pMap = new HashMap<String, Object>();
			pMap.put("seqno", Integer.valueOf(sSeqno));
			List<HashMap<String, Object>> attachList = selectAttachments(pMap);
			for (HashMap<String, Object> hMap : attachList) {
				if (StringUtil.isEmpty(hMap.get("bbsId"))) {
					pMap.put("bbsId", (Integer)paramMap.get("bbsId"));
					boardDao.updateAttachments(pMap);
				}
			}
		}
	}

	public void insertAttachments(HashMap<String, Object> paramMap) throws Exception {
		boardDao.insertAttachments(paramMap);	
	}
	
	public List<HashMap<String, Object>> selectAttachments(HashMap<String, Object> paramMap) throws Exception {
		return boardDao.selectAttachments(paramMap);
	}

	public void updateQryCnt(HashMap<String, Object> paramMap) throws Exception {
		boardDao.updateQryCnt(paramMap);
	}
	
}
