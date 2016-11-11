package com.sahil.twitter.data.access.impl;

import com.sahil.twitter.data.acess.TwitterDao;
import com.sahil.twitter.data.bo.TwitterData;
import org.springframework.stereotype.Repository;
import twitter4j.*;

import java.util.List;

/**
 * Created by sahil.lone on 11/10/2016.
 */
@Repository
public class TwitterDaoImpl implements TwitterDao
{

	private TwitterData twitterData = new TwitterData();

	public TwitterData getTwitterData()
	{
		return twitterData;
	}

	public void setTwitterData(TwitterData twitterData)
	{
		this.twitterData = twitterData;
	}

	@Override
	public TwitterData getAllTweets()
	{
		if (twitterData.getTweetTextData() == null)
		{
			throw new RuntimeException("Data Not Available at the moment. Try after some time.");
		}
		return twitterData;
	}

	@Override
	public List<Status> loadData(Twitter twitter, String queryString) throws TwitterException
	{
		Query query = new Query(queryString);
		QueryResult result = twitter.search(query);
		List<Status> tweets = result.getTweets();
		return tweets;
	}

	@Override
	public void dumpData(TwitterData twitterData)
	{
		if (null == twitterData)
		{
			throw new RuntimeException("ERROR_NULL_DATA: Can't store null data");
		}
		setTwitterData(twitterData);
	}

}
