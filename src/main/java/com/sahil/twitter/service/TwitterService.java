package com.sahil.twitter.service;

import com.sahil.twitter.data.bo.FilterCriteria;
import com.sahil.twitter.data.bo.TwitterData;
import twitter4j.Status;

import java.util.List;

/**
 * Created by sahil.lone on 11/10/2016.
 */
public interface TwitterService
{


	public List<Status> getAllTweets();

	public void loadData();


	public void updateSearchTerm(String searchQuery);

	public List<Status> searchTweets(FilterCriteria criteria);
}
