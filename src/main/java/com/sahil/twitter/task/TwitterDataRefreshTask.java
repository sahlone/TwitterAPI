package com.sahil.twitter.task;

import com.sahil.twitter.data.bo.FilterCriteria;
import com.sahil.twitter.service.TwitterService;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sahil.lone on 11/11/2016.
 */
public class TwitterDataRefreshTask extends TimerTask
{
	private TwitterService twitterService = null;

	public TwitterDataRefreshTask(TwitterService twitterService)
	{
		this.twitterService = twitterService;
	}

	@Override
	public void run()
	{
		try
		{
			twitterService.loadData();
		}

		catch (Exception e)

		{
			e.printStackTrace();
		}
	}
}
