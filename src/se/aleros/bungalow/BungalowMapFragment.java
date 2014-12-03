package se.aleros.bungalow;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract.Constants;
import android.view.View;

import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class BungalowMapFragment extends MapFragment implements OnMyLocationChangeListener, OnLocationChangedListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	Location location;
	Handler handler = new Handler();
	
	/**
	 * Find nearby places
	 * @param location
	 */
	private void findNearbyPlaces(final Location location) {
	
		final List<Place> places = new ArrayList<Place>();
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					
					HttpClient httpclient = new DefaultHttpClient();
					String url = String.format(Locale.ENGLISH, "https://maps.googleapis.com/maps/api/place/search/json?location="
							+ "%s,%s&radius=100&key=%s", location.getLatitude(), location.getLongitude(), Bungalow.PLACES_API_KEY);
					org.apache.http.HttpResponse response = httpclient.execute(new HttpGet(url));
					
				    StatusLine statusLine = response.getStatusLine();
				    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
				        ByteArrayOutputStream out = new ByteArrayOutputStream();
				        response.getEntity().writeTo(out);
				        out.close();
				        String responseString = out.toString();
				        JSONObject data = new JSONObject(responseString);
				        JSONArray results = data.getJSONArray("results");
				        for (int i = 0; i < results.length(); i++) {
				        	JSONObject marker = results.getJSONObject(i);
				        	Place place = new Place(marker);
				        	places.add(place);
				        }
				        handler.post(new Runnable() {
				        	@Override
				        	public void run() {
				        		for (int i = 0; i < places.size(); i++) {
				        			Place place = places.get(i);
				        			BungalowMapFragment.this.getMap().addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getTitle()));
				        			
				        		}
				        	}
				        });
				    }
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		getMap().setMyLocationEnabled(true); 
		getMap().setOnMyLocationChangeListener(this);
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMyLocationChange(Location arg0) {
		// TODO Auto-generated method stub
		this.location = arg0;
		getMap().animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
		findNearbyPlaces(this.location);
		
	}	
}
