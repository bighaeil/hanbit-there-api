package com.hanbit.there.api.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanbit.there.api.dao.FileDAO;
import com.hanbit.there.api.utils.ImageUtils;
import com.hanbit.there.api.vo.FileVO;

@Service
public class FileService {
	
	@Autowired
	private FileDAO fileDAO;
	
	public FileVO getFile(String fileId) {
		return fileDAO.selectFile(fileId);
	}
	
	@Transactional // file 저장 실패하면 취소해야 하니까 - 에러나면 롤백 - RuntimeException 류만 rollback 해줌 - 중요
	public void addFile(FileVO fileVO, InputStream inputStream) {
		fileDAO.insertFile(fileVO);
		saveFile(fileVO.getFilePath(), inputStream, 0);
	}
	@Transactional
	public void addFile(FileVO fileVO, InputStream inputStream, int width) {
		saveFile(fileVO.getFilePath(), inputStream, width);
		
		if (width > 0) { // file 사이즈 바뀌면 - 크기도 바뀌니까
			fileVO.setContentLength(new File(fileVO.getFilePath()).length());
		}
		
		fileDAO.insertFile(fileVO);
	}
	
	@Transactional
	public void modifyFile(FileVO fileVO, InputStream inputStream) {
		fileDAO.replaceFile(fileVO);
		saveFile(fileVO.getFilePath(), inputStream, 0);
	}
	
	@Transactional
	public void modifyFile(FileVO fileVO, InputStream inputStream, int width) {
		saveFile(fileVO.getFilePath(), inputStream, width);
		
		if (width > 0) {
			fileVO.setContentLength(new File(fileVO.getFilePath()).length());
		}
		
		fileDAO.replaceFile(fileVO);
	}
	
	private void saveFile(String filePath, InputStream inputStream, int width) {
		if (width < 1) {
			try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
				IOUtils.copyLarge(inputStream, outputStream);
			}
			catch (Exception e) { // @Transactional은 RuntimeException류만 롤백 해줌 
				throw new RuntimeException(e);
			}
		}
		else {
			try {
				ImageUtils.resize(inputStream, width, filePath);
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public void removeFile(String fileId) {
		FileVO fileVO = fileDAO.selectFile(fileId);
		String filePath = fileVO.getFilePath();

		fileDAO.deleteFile(fileId);

		FileUtils.deleteQuietly(new File(filePath)); // common.io - 파일을 조용히 지워준다.
	}

}
