package com.example.sliding;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

public class GeoResponse {
	
	private List<JSONObject> geoCoord = new LinkedList<JSONObject>();
	
	/**
	 * @return the geoCoord
	 */
	public List<JSONObject> getGeoCoord() {
		return geoCoord;
	}

	/**
	 * @param geoCoord the geoCoord to set
	 */
	public void setGeoCoord(List<JSONObject> geoCoord) {
		this.geoCoord = geoCoord;
	}

	/**
	 * @return the properties
	 */
	public List<JSONObject> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(List<JSONObject> properties) {
		this.properties = properties;
	}

	private List<JSONObject> properties = new LinkedList<JSONObject>();

}
