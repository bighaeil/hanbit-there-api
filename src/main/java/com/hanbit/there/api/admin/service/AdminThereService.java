package com.hanbit.there.api.admin.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hanbit.there.api.admin.dao.AdminThereDAO;
import com.hanbit.there.api.admin.vo.AdminThereGroupVO;
import com.hanbit.there.api.admin.vo.AdminThereVO;
import com.hanbit.there.api.service.FileService;
import com.hanbit.there.api.service.MenuService;
import com.hanbit.there.api.vo.FileVO;
import com.hanbit.there.api.vo.ThereVO;

@Service
public class AdminThereService {

	@Autowired
	private AdminThereDAO adminThereDAO;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private FileService fileService;
	
	public List<AdminThereGroupVO> listThereGroups() {
		return adminThereDAO.selectThereGroups();
	}
	
	@Transactional
	public void modifyThereGroupOrder(String idUp, String idDown) {
		// transaction - service에서 2개의 명령을 보내는데 한 게만 실행되면 모든 데이터가 꼬인다.
		//             - 2개 모두 성공해야 만하고, 아니면 동작하면 안된다.
		//             - 두 개 모두 성공하면 commit 날라옴, 그때 DB에서 적용함 - 실패하면 Rollback 
		adminThereDAO.updateThereGroupOrder(idUp, -1);
		adminThereDAO.updateThereGroupOrder(idDown, 1);
		// 업데이트해도 홈페이지가 바뀌지 않는다. - 캐쉬에 저장되있기 때문에 - 케시 지워준다.
		menuService.invalidateCache();
	}
	
	public AdminThereGroupVO getThereGroup(String id) {
		return adminThereDAO.selectThereGroup(id);
	}
	
	public void modifyThereGroup(AdminThereGroupVO groupVO) {
		adminThereDAO.updateThereGroup(groupVO);
		menuService.invalidateCache();
	}
	
	public void removeThereGroup(String id) {
		adminThereDAO.deleteThereGroup(id);
		menuService.invalidateCache();
	}
	
	public void addThereGroup(AdminThereGroupVO groupVO) {
		adminThereDAO.insertThereGroup(groupVO);
	}
	
	public boolean hasThereGroupId(String id) {
		return adminThereDAO.countThereGroup(id) > 0; // condition - 조건식
	}
	
	public List<AdminThereVO> listTheres(String groupId) {
		return adminThereDAO.selectTheres(groupId);
	}
	
	public boolean hasThereId(String id) {
		return adminThereDAO.countThereId(id) > 0;
	}
	
	@Transactional
	public void modifyThere(ThereVO thereVO, MultipartFile background) {
		String backgroundFileId = "there-" + thereVO.getId();
		
		if (background != null) {
			thereVO.setBackground("/api/file/" + backgroundFileId);
		}
		
		adminThereDAO.updateThere(thereVO);
		adminThereDAO.replaceLocation(thereVO);
		
		adminThereDAO.deleteInfos(thereVO.getId());
		if(thereVO.getTraffics().size() > 0) { // 있으면 수정
			adminThereDAO.insertTraffics(thereVO);
		}
		adminThereDAO.deleteTraffics(thereVO.getId());
		if(thereVO.getAreaInfo().size() > 0) {
			adminThereDAO.insertInfos(thereVO);
		}
		

		if (background != null) {
			FileVO fileVO = new FileVO();
			fileVO.setFileId(backgroundFileId);
			
			String fileExt = FilenameUtils.getExtension(background.getOriginalFilename());
			String fileName = thereVO.getId() + "." + fileExt;
			
			fileVO.setFilePath("/hanbit2/webpack/hanbit-there/src/img/theres/" + fileName); // 경로까지 폴더를 만들지 않는다. - 만들어져 있어야됨			
			fileVO.setFileName(fileName);
			fileVO.setContentType(background.getContentType());
			fileVO.setContentLength(background.getSize());
			
			try {
				fileService.modifyFile(fileVO, background.getInputStream()); // throws 하면 안됨 - 에러나면 롤백 해야하기 때문
			} catch (IOException e) {
				throw new RuntimeException(); // 롤백(@Transactional)은 RuntimeException 이 익셉션만 받음
			}
		}

		menuService.invalidateCache();
	}

	@Transactional
	public void addThere(ThereVO thereVO, MultipartFile background) {
		String backgroundFileId = "there-" + thereVO.getId();

		thereVO.setBackground("/api/file/" + backgroundFileId);

		adminThereDAO.insertThere(thereVO);
		adminThereDAO.replaceLocation(thereVO);
		
		if(thereVO.getAreaInfo().size() > 0) { // 있으면 추가
			adminThereDAO.insertInfos(thereVO);
		}
		if(thereVO.getTraffics().size() > 0) {
			adminThereDAO.insertTraffics(thereVO);
		}
		
		String fileExt = FilenameUtils.getExtension(background.getOriginalFilename());
		String fileName = thereVO.getId() + "." + fileExt;
		
		FileVO fileVO = new FileVO();
		fileVO.setFileId(backgroundFileId);
		fileVO.setContentType(background.getContentType());
		fileVO.setContentLength(background.getSize());
		fileVO.setFileName(fileName);
		fileVO.setFilePath("/hanbit/webpack/hanbit-there/src/img/theres/" + fileName);

		try {
			fileService.addFile(fileVO, background.getInputStream());
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}

		menuService.invalidateCache();
	}

	@Transactional
	public void removeThere(String id) {
		String backgroundFileId = "there-" + id;

		adminThereDAO.deleteLocation(id);
		adminThereDAO.deleteInfos(id);
		adminThereDAO.deleteTraffics(id);
		adminThereDAO.deleteThere(id);

		fileService.removeFile(backgroundFileId);

		menuService.invalidateCache();

	}
}
