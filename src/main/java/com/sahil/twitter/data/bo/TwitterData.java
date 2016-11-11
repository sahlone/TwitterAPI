package com.sahil.twitter.data.bo;

import twitter4j.Status;

import java.util.List;

/**
 * Created by sahil.lone on 11/10/2016.
 */
public class TwitterData
{
	private List<Status> embeddedWordData = null;
	private List<Status> hashTagData      = null;
	private List<Status> tweetTextData    = null;
	private List<Status> userNameData     = null;

	public List<Status> getEmbeddedWordData()
	{
		return embeddedWordData;
	}

	public void setEmbeddedWordData(List<Status> embeddedWordData)
	{
		this.embeddedWordData = embeddedWordData;
	}

	public List<Status> getHashTagData()
	{
		return hashTagData;
	}

	public void setHashTagData(List<Status> hashTagData)
	{
		this.hashTagData = hashTagData;
	}

	public List<Status> getTweetTextData()
	{
		return tweetTextData;
	}

	public void setTweetTextData(List<Status> tweetTextData)
	{
		this.tweetTextData = tweetTextData;
	}

	public List<Status> getUserNameData()
	{
		return userNameData;
	}

	public void setUserNameData(List<Status> userNameData)
	{
		this.userNameData = userNameData;
	}
}
