package se.aleros.bungalow;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.os.Handler;
import android.util.Log;

/**
 * Represents a RSS Feed
 * @author alecca
 *
 */
public class RSSFeed implements List<RSSItem> {
	private List<RSSItem> items;
	public List<RSSItem> getItems() {
		return items;
	}
	public RSSFeed(String feedUrl) {
		this.items = new ArrayList<RSSItem>();
	}
	/**
	 * Downloads items of the feed. Runs synchronous, so 
	 * this is a blocking method, this must be run asynchronous in another thread
	 * if called by an ui implementation
	 * @return
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws MalformedURLException 
	 */
	public List<RSSItem> downloadFeed(String feedUrl) throws MalformedURLException, SAXException, IOException, ParserConfigurationException {
		List<RSSItem> items = new ArrayList<RSSItem>();
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new URL(feedUrl).openStream()));
		NodeList nodeList = doc.getElementsByTagName("item");
	
		for (int i = 0; i < nodeList.getLength(); i++) {
			
			Node n = nodeList.item(i);
			if (n instanceof Element) {
				RSSItem item = new RSSItem((Element)n);
				items.add(item);
			}
		}
		return items;
		
	}
	
	/**
	 * Downloads RSS feed async and notify it's 
	 * @param feedUrl
	 * @param onComplete
	 */
	public void downloadFeedAsync(final String feedUrl, final RSSDownloadCompleteHandler onComplete) {
		final Handler handler = new Handler();
		new Thread(new Runnable() {

			@Override
			public void run() {
				final List<RSSItem> items = new ArrayList<RSSItem>();
				// TODO Auto-generated method stub
				try {
					 items.addAll(downloadFeed(feedUrl));
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
				if (onComplete != null) {
					RSSFeed.this.getItems().addAll(items);
					handler.post(new Runnable() {
						@Override
						public void run() {
							onComplete.downloadComplete(RSSFeed.this);
							
						}
					});
				}
			}
				
		}).start();
	}
	
	@Override
	public void add(int location, RSSItem object) {
		// TODO Auto-generated method stub
		this.items.add(location, object);
	}
	@Override
	public boolean add(RSSItem object) {
		// TODO Auto-generated method stub
		return this.items.add(object);
	}
	@Override
	public boolean addAll(int location, Collection<? extends RSSItem> collection) {
		// TODO Auto-generated method stub
		return this.items.addAll(location, collection);
	}
	@Override
	public boolean addAll(Collection<? extends RSSItem> collection) {
		// TODO Auto-generated method stub
		return this.addAll(collection);
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.items.clear();
	}
	@Override
	public boolean contains(Object object) {
		// TODO Auto-generated method stub
		return this.items.contains(object);
	}
	@Override
	public boolean containsAll(Collection<?> collection) {
		// TODO Auto-generated method stub
		return this.items.containsAll(collection);
	}
	@Override
	public RSSItem get(int location) {
		// TODO Auto-generated method stub
		return this.items.get(location);
	}
	@Override
	public int indexOf(Object object) {
		// TODO Auto-generated method stub
		return this.items.indexOf(object);
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.items.isEmpty();
	}
	@Override
	public Iterator<RSSItem> iterator() {
		// TODO Auto-generated method stub
		return this.items.iterator();
	}
	@Override
	public int lastIndexOf(Object object) {
		// TODO Auto-generated method stub
		return this.items.lastIndexOf(object);
	}
	@Override
	public ListIterator<RSSItem> listIterator() {
		// TODO Auto-generated method stub
		return this.items.listIterator();
	}
	@Override
	public ListIterator<RSSItem> listIterator(int location) {
		// TODO Auto-generated method stub
		return this.items.listIterator(location);
	}
	@Override
	public RSSItem remove(int location) {
		// TODO Auto-generated method stub
		return this.items.remove(location);
	}
	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return this.items.remove(object);
	}
	@Override
	public boolean removeAll(Collection<?> collection) {
		// TODO Auto-generated method stub
		return this.items.removeAll(collection);
	}
	@Override
	public boolean retainAll(Collection<?> collection) {
		// TODO Auto-generated method stub
		return this.items.retainAll(collection);
	}
	@Override
	public RSSItem set(int location, RSSItem object) {
		// TODO Auto-generated method stub
		return this.items.set(location, object);
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.items.size();
	}
	@Override
	public List<RSSItem> subList(int start, int end) {
		// TODO Auto-generated method stub
		return this.items.subList(start, end);
	}
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return this.items.toArray();
	}
	@Override
	public <T> T[] toArray(T[] array) {
		// TODO Auto-generated method stub
		return this.items.toArray(array);
	}
	
}

