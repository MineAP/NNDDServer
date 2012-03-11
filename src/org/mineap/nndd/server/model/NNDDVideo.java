/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mineap.nndd.server.model;

import java.util.Date;

/**
 * ìÆâÊèÓïÒÇï\åªÇ∑ÇÈÉNÉâÉX
 *
 * @author shiraminekeisuke(MineAP)
 */
public class NNDDVideo
{

	private Long id = null;
	private String key = null;
	private String uri = null;
	private String videoName = null;
	private boolean isEconomy = false;
	private Date modificationDate = null;
	private Date creationDate = null;
	private String thumbUrl = null;
	private long playCount = 0;
	private long time = 0;
	private Date lastPlayDate = null;
	private boolean yetReading = false;

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return the key
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key)
	{
		this.key = key;
	}

	/**
	 * @return the uri
	 */
	public String getUri()
	{
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri)
	{
		this.uri = uri;
	}

	/**
	 * @return the videoName
	 */
	public String getVideoName()
	{
		return videoName;
	}

	/**
	 * @param videoName the videoName to set
	 */
	public void setVideoName(String videoName)
	{
		this.videoName = videoName;
	}

	/**
	 * @return the isEconomy
	 */
	public boolean isIsEconomy()
	{
		return isEconomy;
	}

	/**
	 * @param isEconomy the isEconomy to set
	 */
	public void setIsEconomy(boolean isEconomy)
	{
		this.isEconomy = isEconomy;
	}

	/**
	 * @return the modificationDate
	 */
	public Date getModificationDate()
	{
		return modificationDate;
	}

	/**
	 * @param modificationDate the modificationDate to set
	 */
	public void setModificationDate(Date modificationDate)
	{
		this.modificationDate = modificationDate;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return the thumbUrl
	 */
	public String getThumbUrl()
	{
		return thumbUrl;
	}

	/**
	 * @param thumbUrl the thumbUrl to set
	 */
	public void setThumbUrl(String thumbUrl)
	{
		this.thumbUrl = thumbUrl;
	}

	/**
	 * @return the playCount
	 */
	public long getPlayCount()
	{
		return playCount;
	}

	/**
	 * @param playCount the playCount to set
	 */
	public void setPlayCount(long playCount)
	{
		this.playCount = playCount;
	}

	/**
	 * @return the time
	 */
	public long getTime()
	{
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(long time)
	{
		this.time = time;
	}

	/**
	 * @return the lastPlayDate
	 */
	public Date getLastPlayDate()
	{
		return lastPlayDate;
	}

	/**
	 * @param lastPlayDate the lastPlayTime to set
	 */
	public void setLastPlayDate(Date lastPlayDate)
	{
		this.lastPlayDate = lastPlayDate;
	}

	/**
	 * @return the yetReading
	 */
	public boolean isYetReading()
	{
		return yetReading;
	}

	/**
	 * @param yetReading the yetReading to set
	 */
	public void setYetReading(boolean yetReading)
	{
		this.yetReading = yetReading;
	}
}
