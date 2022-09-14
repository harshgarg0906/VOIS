package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CsvModalData {

	String dataTime;
	String eventId;
	String productId;
	String latitude;
	String longitide;
	String battery;
	String light;
	String airplaneMode;
	
	
	public CsvModalData() {
	}


	public CsvModalData(String dataTime, String eventId, String productId, String latitude, String longitide,
			String battery, String light, String airplaneMode) {
		this.dataTime = dataTime;
		this.eventId = eventId;
		this.productId = productId;
		this.latitude = latitude;
		this.longitide = longitide;
		this.battery = battery;
		this.light = light;
		this.airplaneMode = airplaneMode;
	}


	public String getDataTime() {
		return dataTime;
	}


	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}


	public String getEventId() {
		return eventId;
	}


	public void setEventId(String eventId) {
		this.eventId = eventId;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getLongitide() {
		return longitide;
	}


	public void setLongitide(String longitide) {
		this.longitide = longitide;
	}


	public String getBattery() {
		return battery;
	}


	public void setBattery(String battery) {
		this.battery = battery;
	}


	public String getLight() {
		return light;
	}


	public void setLight(String light) {
		this.light = light;
	}


	public String getAirplaneMode() {
		return airplaneMode;
	}


	public void setAirplaneMode(String airplaneMode) {
		this.airplaneMode = airplaneMode;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airplaneMode == null) ? 0 : airplaneMode.hashCode());
		result = prime * result + ((battery == null) ? 0 : battery.hashCode());
		result = prime * result + ((dataTime == null) ? 0 : dataTime.hashCode());
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((light == null) ? 0 : light.hashCode());
		result = prime * result + ((longitide == null) ? 0 : longitide.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CsvModalData other = (CsvModalData) obj;
		if (airplaneMode == null) {
			if (other.airplaneMode != null)
				return false;
		} else if (!airplaneMode.equals(other.airplaneMode))
			return false;
		if (battery == null) {
			if (other.battery != null)
				return false;
		} else if (!battery.equals(other.battery))
			return false;
		if (dataTime == null) {
			if (other.dataTime != null)
				return false;
		} else if (!dataTime.equals(other.dataTime))
			return false;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (light == null) {
			if (other.light != null)
				return false;
		} else if (!light.equals(other.light))
			return false;
		if (longitide == null) {
			if (other.longitide != null)
				return false;
		} else if (!longitide.equals(other.longitide))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "CsvModalData [dataTime=" + dataTime + ", eventId=" + eventId + ", productId=" + productId
				+ ", latitude=" + latitude + ", longitide=" + longitide + ", battery=" + battery + ", light=" + light
				+ ", airplaneMode=" + airplaneMode + "]";
	}
	
	
	
}
