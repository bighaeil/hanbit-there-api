package com.hanbit.there.api.controller;

import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanbit.there.api.service.FileService;
import com.hanbit.there.api.vo.FileVO;

@RestController
@RequestMapping("/api/file")
public class FileController {
	
	@Autowired
	private FileService fileService;
	
//	private static final String FILE_ROOT = "/hanbit/";
	
	@RequestMapping("/{fileId}")
	public void getFile(@PathVariable("fileId") String fileId, HttpServletResponse response) throws Exception { // void - spring 처리 않하고 - ouput에서 직접 쓰기 때문
//		String filePath = FILE_ROOT + fileId + "." + extension; // /{fileId}/{extension} - url 은 .이 안되니까
		FileVO fileVO = fileService.getFile(fileId);
		
		String filePath = fileVO.getFilePath();
		String contentType = fileVO.getContentType();
		long contentLength = fileVO.getContentLength();
		String fileName = fileVO.getFileName(); // 파일의 기본적으로 필요한 정보들 - DB에 저장 
//		byte[] buffer = new byte[4096];
		
		OutputStream outputStream = response.getOutputStream(); // response의 output stream - 응답으로 나가는 stream
		response.setContentType("application/octet-stream");
		// 파일 타입(Content Type) 지정 - 일반 byte - 다운로드 처럼됨
		// image/jpeg - 파일이 이미지다
		response.setContentLengthLong(contentLength); // 파일용량 알려줌 - 얼마나 받아야하는지 알려줌
		response.setHeader("Content-Disposition", "filename=" + fileName); // 파일 이름 설정, attachement - 모든 파일을 "파일 다운로드" 대화상자가 뜨도록 ex)attachement; filename=
		
		try(FileInputStream inputStream = new FileInputStream(filePath)) {
			
			IOUtils.copyLarge(inputStream, outputStream); // 내부적으로 long 사용 - 파일이 2GB 넘는다면 
			
//			while(inputStream.available() > 0) {
//				int readLength = inputStream.read(buffer, 0, Math.min(buffer.length, inputStream.available()));
//				
//				outputStream.write(buffer, 0, readLength);
//			}
		} 
	}
	
}
