package se.aleros.bungalow;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

/**
 * A generic Activity subclass for dealing with web views
 * @author alecca
 *
 */
public class WebActivity extends Activity {
	private WebView webView;
	
	/**
	 * Returns the web view for the activity
	 * @return
	 */
	public WebView getWebView() {
		return this.webView;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		this.webView = (WebView)findViewById(R.id.webView);
		
	}
	
	public void loadUrl(String url) {
		this.webView.loadUrl(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.web, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
