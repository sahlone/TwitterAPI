package com.sahil.twitter.service.impl;

import com.sahil.twitter.data.bo.FilterCriteria;
import com.sahil.twitter.data.bo.TwitterData;
import com.sahil.twitter.service.TwitterService;
import com.sahil.twitter.data.acess.TwitterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by sahil.lone on 11/10/2016.
 */
@Service
public class TwitterServiceImpl implements TwitterService
{
	private final String CONSUMER_KEY        = "ruQQ9Gs92LNrS0WbFS018c63P";
	private final String CONSUMER_SECRET_KEY = "u9vhW1J42z3KGXGC1Fd8tQEdMgnx5yQTz1sl7HicFVeh5FP3a8";
	private final String ACCESS_TOKEN        = "2220106574-kd8cEWGgotVJndwmLfAfHnbdOvzltImouxeIpti";
	private final String ACCESS_TOKEN_SECRET = "T5TeVeUSCDwTC4MjX4XRK5x7CRQk6wFIk2MdXksgrGnDp";

	org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TwitterService.class);
	private TwitterDao twitterDao = null;

	@Autowired
	public TwitterServiceImpl(TwitterDao twitterDao)
	{
		this.twitterDao = twitterDao;
	}

	@Override
	public List<Status> getAllTweets()
	{
		List<Status> tweets = new ArrayList<Status>(10);
		try
		{
			TwitterData twitterData = twitterDao.getAllTweets();
			tweets.addAll(twitterData.getUserNameData());
			tweets.addAll(twitterData.getHashTagData());
			tweets.addAll(twitterData.getTweetTextData());
			tweets.addAll(twitterData.getEmbeddedWordData());
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		return tweets;
	}

	@Override
	public void loadData()
	{
		try
		{
			String queryTerm = FilterCriteria.BASE_SEARCH_TERM.value();
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true).setOAuthConsumerKey(CONSUMER_KEY).setOAuthConsumerSecret(CONSUMER_SECRET_KEY)
					.setOAuthAccessToken(ACCESS_TOKEN).setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();
			TwitterData twitterData = new TwitterData();
			List<Status> data = twitterDao.loadData(twitter, FilterCriteria.SEARCH_BY_HASH_TAG.value() + queryTerm);
			twitterData.setHashTagData(data);

			List<String> tokenizedString = tokenizeQuery(queryTerm);
			data.clear();
			for (String token : tokenizedString)
			{
				data.addAll(twitterDao.loadData(twitter, token));
			}
			twitterData.setEmbeddedWordData(data);
			data = twitterDao.loadData(twitter, FilterCriteria.SEARCH_BY_TWEET_TEXT.value() + queryTerm);
			twitterData.setTweetTextData(data);
			data = twitterDao.loadData(twitter, FilterCriteria.SEARCH_BY_USER_NAME.value() + queryTerm);
			twitterData.setUserNameData(data);
			twitterDao.dumpData(twitterData);
		}
		catch (Exception e)
		{
			logger.error(e);
		}
	}

	@Override
	public void updateSearchTerm(String searchQuery)
	{
		try
		{
			FilterCriteria.BASE_SEARCH_TERM.setValue(searchQuery);
			loadData();
		}
		catch (Exception e)
		{
			logger.error(e);
		}
	}

	@Override
	public List<Status> searchTweets(FilterCriteria criteria)
	{
		List<Status> filteredTweets = new ArrayList<Status>(10);
		try
		{

			switch (criteria)
			{
				case SEARCH_BY_EMBEDED_WORD:
					filteredTweets.addAll(twitterDao.getAllTweets().getEmbeddedWordData());
					break;
				case SEARCH_BY_TWEET_TEXT:
					filteredTweets.addAll(twitterDao.getAllTweets().getTweetTextData());
					break;
				case SEARCH_BY_HASH_TAG:
					filteredTweets.addAll(twitterDao.getAllTweets().getHashTagData());
					break;
				case SEARCH_BY_USER_NAME:
					filteredTweets.addAll(twitterDao.getAllTweets().getUserNameData());
					break;
				default:
					break;
			}
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		return filteredTweets;
	}

	private List<String> tokenizeQuery(String query)
	{
		StringTokenizer stringTokenizer = new StringTokenizer(query, FilterCriteria.SEARCH_BY_EMBEDED_WORD.value());
		List<String> tokens = new ArrayList<String>(1);
		while (stringTokenizer.hasMoreElements())
		{
			tokens.add(stringTokenizer.nextToken());
		}
		return tokens;
	}

}
