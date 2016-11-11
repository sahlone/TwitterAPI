package com.sahil.twitter.data.acess;

import com.sahil.twitter.data.bo.TwitterData;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;

/**
 * Created by sahil.lone on 11/10/2016.
 */
public interface TwitterDao
{
	public TwitterData getAllTweets();

	public List<Status> loadData(Twitter twitter, String queryString) throws TwitterException;

	void dumpData(TwitterData twitterData);
}
