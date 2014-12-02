package se.aleros.bungalow;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;

public class RssMonitorService extends Service {
	Handler handler = new Handler();
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @from {@link http://stackoverflow.com/questions/6493517/detect-if-android-device-has-internet-connection}
	 * @return
	 */
	public boolean isOnline() {
	    Context context = this;
	    ConnectivityManager cm = (ConnectivityManager) context
	        .getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	Timer timer;
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						if (!RssMonitorService.this.isOnline()) {
							return;
						}
						final SharedPreferences prefs = (SharedPreferences)PreferenceManager.getDefaultSharedPreferences(RssMonitorService.this);
						String latestURI = prefs.getString("latestURI", null);
						RSSFeed feed = new RSSFeed(null);
						try {
							List<RSSItem> items = feed.downloadFeed(Bungalow.RSS_URL);
							final RSSItem firstItem = items.get(0);
							final String newURI = firstItem.getUrl();
							if (latestURI == null || !newURI.equals(latestURI)) {
								// New item found
								final Editor editor = prefs.edit();
								handler.post(new Runnable() {
									@Override
									public void run() {
										// Notify user
										NotificationManager nm = (NotificationManager)getSystemService(Service.NOTIFICATION_SERVICE);
										Intent startIntent = new Intent(RssMonitorService.this, RSSItemActivity.class);
										startIntent.putExtra("item", firstItem);
										PendingIntent pi = PendingIntent.getActivity(RssMonitorService.this, 0, startIntent, 0);
										Notification.Builder builder = new Notification.Builder(RssMonitorService.this);
										builder.setDeleteIntent(pi);
										builder.setAutoCancel(true);
										builder.setContentIntent(pi);
										builder.setContentText(firstItem.getTitle());
										builder.setContentTitle("Bungalow");
										builder.setSmallIcon(R.drawable.ic_launcher);
										Notification notification = builder.getNotification();
										nm.notify(9124, notification);
										editor.putString("latestURI", firstItem.getUrl());
										editor.apply();
									}
								});
							}
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SAXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParserConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
				t.start();
			}
			
		};
		timer.scheduleAtFixedRate(task, 0, 60000);
		
	}
	/**
	 * This method checks for news updates by
	 * checking if the first item in the list is different since
	 * last update.
	 */
	public void checkNews() {
		
		
	}
}
