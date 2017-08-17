package com.hanbit.there.api.domain;

import java.util.List;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import com.hanbit.there.api.vo.ActivityListVO;
import com.hanbit.there.api.vo.LocationVO;
import com.hanbit.there.api.vo.ThereVO;

@Document
public class Activity {

	@Id
	private String id;
	@Field
	private String thereId;
	@Field
	private ThereVO there;
	@Field
	private String name;
	@Field
	private List<String> photos;
	@Field
	private String video;
	@Field
	private LocationVO location;
	@Field
	private String intro;
	@Field
	private List<ActivityListVO> lists;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getPhotos() {
		return photos;
	}
	public void setPhotos(List<String> photos) {
		this.photos = photos;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public LocationVO getLocation() {
		return location;
	}
	public void setLocation(LocationVO location) {
		this.location = location;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public List<ActivityListVO> getLists() {
		return lists;
	}
	public void setLists(List<ActivityListVO> lists) {
		this.lists = lists;
	}
	public String getThereId() {
		return thereId;
	}
	public void setThereId(String thereId) {
		this.thereId = thereId;
	}
	public ThereVO getThere() {
		return there;
	}
	public void setThere(ThereVO there) {
		this.there = there;
	}

}
