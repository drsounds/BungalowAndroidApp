package se.aleros.bungalow;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * List fragment dealing with RSS feed
 * @author alecca
 * @extends Fragment
 * @implements RSSDownloadHandler
 * @implements OnItemClickListener
 * 
 */
public class RSSListFragment extends Fragment implements RSSDownloadCompleteHandler, OnItemClickListener {
	private RSSFeed feed;
	private ListView listView;
	private ProgressBar progressBar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		feed = new RSSFeed(null);
		feed.downloadFeedAsync(Bungalow.RSS_URL, this);
		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.rss_list, container, false);
       this.progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
       this.listView = (ListView)v.findViewById(R.id.listView);
       this.listView.setOnItemClickListener(this);
       this.listView.setAdapter(new RSSAdapter(getActivity()));
       return v;
    }
	
	@Override
	public void downloadComplete(RSSFeed feed) {
		// TODO Auto-generated method stub
		((RSSAdapter)this.listView.getAdapter()).addAll(feed);
		this.progressBar.setVisibility(ProgressBar.GONE);
		this.listView.setVisibility(ListView.VISIBLE);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		RSSItem item = (RSSItem)parent.getItemAtPosition(position);
		

		Intent i = new Intent(this.getActivity(), RSSItemActivity.class);
		i.putExtra("item", item);
		startActivity(i);
	}	
	
}
