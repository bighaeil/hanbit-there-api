package com.hanbit.there.api.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hanbit.there.api.admin.reop.AdminActivityRepository;
import com.hanbit.there.api.domain.Activity;
import com.hanbit.there.api.service.FileService;
import com.hanbit.there.api.vo.FileVO;

@Service
public class AdminActivityService {
	
	@Autowired
	private AdminActivityRepository adminActivityRepository;
	
	@Autowired
	private FileService fileService;
	
	public List<Activity> getActivities(String thereId) {
		return adminActivityRepository.findByThereIdOrderByName(thereId);
	}
	
	public boolean hasActivityId(String id) {
		return adminActivityRepository.exists(id);
	}
	
	@Transactional // CouchBase는 적용되지 않는다. - 지원X - 순서 상관없다면 Transactional 안먹는 것은 뒤쪽으로 
	public void saveActivity(Activity activity, List<MultipartFile> photos) throws Exception{
		Activity oldActivity = adminActivityRepository.findOne(activity.getId());
		
		List<String> photoList = null;
		int lastIndex = 0;
		
		if (oldActivity == null || oldActivity.getPhotos() == null) { // 기존 파일이 없으면
			photoList = new ArrayList<>();
		}
		else {
			photoList = oldActivity.getPhotos();
			
			String lastFileUrl = photoList.get(photoList.size() - 1);
			lastIndex = Integer.valueOf(StringUtils.substringAfterLast(lastFileUrl, "_")) + 1; // 마지막 "_" 뒤에 있는 문자
			
			for (int i=activity.getPhotos().size()-1; i>-1; i--) { // 해결방법
				String photoUrl = activity.getPhotos().get(i);
				
				if (!"_removed_".equals(photoUrl)) { // removed 아닌 것은 pass
					continue;
				}
				
				String oldUrl = photoList.get(i);
				String oldFileId = StringUtils.substringAfterLast(oldUrl, "/"); // 마지막 "/" 뒤 문자
				
				fileService.removeFile(oldFileId); // BD 및 파일 제거
				photoList.remove(i); // %중요% - 앞에서부터 순서대로 돌고 있는 list의 필드를 지우면 index 2가 1이 된다.
				// 해결방법 - 뒤에서 부터 지워 나간다.
			}
		}
		
		for (int i=0; i<photos.size(); i++) {
			MultipartFile photo = photos.get(i);
			
			String fileName = activity.getId() + "_" + (lastIndex + i);
			String fileExt = FilenameUtils.getExtension(photo.getOriginalFilename());
			String fileId = "activity-" + fileName;
			String filePath = "/hanbit2/webpack/hanbit-there/src/img/activities/" + fileName + "." + fileExt;
			
			FileVO fileVO = new FileVO();
			fileVO.setFileId(fileId);
			fileVO.setFilePath(filePath);
			fileVO.setFileName(photo.getOriginalFilename());
			fileVO.setContentType(photo.getContentType());
			fileVO.setContentLength(photo.getSize());
			
			fileService.addFile(fileVO, photo.getInputStream());
			
			String fileUrl = "/api/file/" + fileId;
			photoList.add(fileUrl);
		}
		
		activity.setPhotos(photoList);
		
		adminActivityRepository.save(activity);
	}
}
