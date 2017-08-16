package com.hanbit.there.api.domain;

import org.springframework.data.annotation.Version;
import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

@Document
public class Airline { // vo랑 비슷 - mapping 될 것임
	
	@Id // couchbase - @Id - int 안됨
	private String key; // 실제 구조상의 Id - 즉 key - 이름하고는 상관없다.
	@Version
	private long version;
	
	@Field // couchbase
	private int id; // @Field 라면 필드로서의 id 값이 나옴
	@Field("type") // 파라미터로 타입 지정 할 수 있음 - 그러면 java의 변수 이름을 맞춰줄 필요 없음
	private String type;
	@Field
	private String name;
	@Field
	private String iata;
	@Field
	private String icao;
	@Field
	private String callsign;
	@Field
	private String country;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIata() {
		return iata;
	}
	public void setIata(String iata) {
		this.iata = iata;
	}
	public String getIcao() {
		return icao;
	}
	public void setIcao(String icao) {
		this.icao = icao;
	}
	public String getCallsign() {
		return callsign;
	}
	public void setCallsign(String callsign) {
		this.callsign = callsign;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	
	
//	{
//		  "id": 10226,
//		  "type": "airline",
//		  "name": "Atifly",
//		  "iata": "A1",
//		  "icao": "A1F",
//		  "callsign": "atifly",
//		  "country": "United States"
//	}
	
}

