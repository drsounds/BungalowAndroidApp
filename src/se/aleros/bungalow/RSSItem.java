package se.aleros.bungalow;

import java.util.Date;

import org.w3c.dom.Element;

/**
 * Represents an RSS Item 
 * I also implement Comparable to make it easy to compare feeds.
 * @author Alexander Forselius <alex@artistconnector.com>
 *
 */
public class RSSItem implements Comparable<RSSItem> {
	/**
	 * Returns the inner text value of the tag name
	 * @param element The element to get the tag from.
	 * 
	 * @param tagName The name of the tag
	 * @implements Comparable
	 * @return the innerText
	 */
	public static String getValue(Element element, String tagName) {
		return element.getElementsByTagName(tagName).item(0).getNodeValue();
	}
	/**
	 * Constructs a new RSS item based on the element in the feed
	 * @param element
	 */
	public RSSItem(Element element) {
		this.title = RSSItem.getValue(element, "title");
		this.setDescription(RSSItem.getValue(element, "description"));

		this.setPubDate(new Date(RSSItem.getValue(element, "pubDate")));
		
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
}
