package com.hanbit.there.api.domain;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;

@Document
public class Airport {

	@Field
	private int id;
	@Field
	private String type;
	@Field
	private String airportname;
	@Field
	private String city;
	@Field
	private String country;
	@Field
	private String faa;
	@Field
	private String icao;
	@Field
	private String tz;
	@Field
	private Geo geo;

	static class Geo {
		private double lat;
		private double lon;
		private int alt;

		public double getLat() {
			return lat;
		}
		public void setLat(double lat) {
			this.lat = lat;
		}
		public double getLon() {
			return lon;
		}
		public void setLon(double lon) {
			this.lon = lon;
		}
		public int getAlt() {
			return alt;
		}
		public void setAlt(int alt) {
			this.alt = alt;
		}
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

	public String getAirportname() {
		return airportname;
	}

	public void setAirportname(String airportname) {
		this.airportname = airportname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFaa() {
		return faa;
	}

	public void setFaa(String faa) {
		this.faa = faa;
	}

	public String getIcao() {
		return icao;
	}

	public void setIcao(String icao) {
		this.icao = icao;
	}

	public String getTz() {
		return tz;
	}

	public void setTz(String tz) {
		this.tz = tz;
	}

	public Geo getGeo() {
		return geo;
	}

	public void setGeo(Geo geo) {
		this.geo = geo;
	}

}
