package se.aleros.bungalow;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

public class Place {
	public Place() {
		
	}
	private LatLng latLng;
	private String title;
	private JSONObject geometry;
	public LatLng getLatLng() {
		return latLng;
	}
	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Place(JSONObject object) throws JSONException {
		this.geometry = object.getJSONObject("geometry");
		
		this.latLng = new LatLng(geometry.getJSONObject("location").getDouble("latitude"), geometry.getJSONObject("location").getDouble("longitude"));
		this.title = object.getString("name");
	}
}
