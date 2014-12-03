package se.aleros.bungalow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.w3c.dom.Element;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents an RSS Item 
 * I also implement Comparable to make it easy to compare feeds.
 * @author Alexander Forselius <alex@artistconnector.com>
 * @implements Parcelable
 * @implements Comparable<RSSItem>
 *
 */
public class RSSItem implements Comparable<RSSItem>, Parcelable {
	public RSSItem () {
		
	}
	public RSSItem (Parcel parcel) {
		this.title = parcel.readString();
		this.description = parcel.readString();
		this.pubDate = new Date(parcel.readLong());
		this.url = parcel.readString();
	}
	public static Parcelable.Creator<RSSItem> CREATOR = new Parcelable.Creator<RSSItem> () {

		@Override
		public RSSItem createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new RSSItem(source);
		}

		@Override
		public RSSItem[] newArray(int size) {
			// TODO Auto-generated method stub
			return new RSSItem[size];
		}
		
	};
	private Date time;
	/**
	 * Returns the inner text value of the tag name
	 * @param element The element to get the tag from.
	 * 
	 * @param tagName The name of the tag
	 * @implements Comparable
	 * @return the innerText
	 */
	public static String getValue(Element element, String tagName) {
		return element.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue();
	}
	/**
	 * Constructs a new RSS item based on the element in the feed
	 * @param element
	 */
	public RSSItem(Element element) {
		this.title = RSSItem.getValue(element, "title");
		this.setDescription(RSSItem.getValue(element, "description"));
		this.setPubDate((RSSItem.getValue(element, "pubDate")));
		this.setUrl((RSSItem.getValue(element, "link")));
		
		
	}
	public void setPubDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
		try {
			this.time = formatter.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			this.time = new Date();
		}
	
	}
	private String url;
	private String title;
	private String description;
	private Date pubDate;
	private String author;
	private String imageUrl;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int compareTo(RSSItem another) {
		// TODO Auto-generated method stub
		return this.getUrl().compareTo(another.getUrl());
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(title);
		dest.writeString(description);
		if (this.time != null)
			dest.writeLong(getTime().getTime());
		else
			dest.writeLong(0);
		dest.writeString(url);
		
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}
