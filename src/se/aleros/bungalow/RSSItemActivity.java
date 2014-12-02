package se.aleros.bungalow;

import android.os.Bundle;

/**
 * Activity to show an individual news item
 * @extends WebActivity
 * @author alecca
 *
 */
public class RSSItemActivity extends WebActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		RSSItem item = (RSSItem)getIntent().getParcelableExtra("item");
		loadUrl(item.getUrl());
	}
	
}
