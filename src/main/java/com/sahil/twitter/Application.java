package com.sahil.twitter;

/**
 * Created by sahil.lone on 11/11/2016.
 */

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import com.sahil.twitter.service.TwitterService;
import com.sahil.twitter.service.impl.TwitterServiceImpl;
import com.sahil.twitter.task.TwitterDataRefreshTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application extends SpringBootServletInitializer
{

	private static       String         queryText         = "TEST";
	private static       TwitterService twitterService    = null;
	private static final long           TASK_DELAY        = 0L;
	private static final long           TASK_REFRESH_RATE = 60L * 60L * 1000;
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TwitterService.class);
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(Application.class);
	}

	public static void main(String[] args)
	{
		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);

		twitterService = applicationContext.getBean(TwitterService.class);
		TwitterDataRefreshTask twitterDataRefreshTask = new TwitterDataRefreshTask(twitterService);
		Timer timer = new Timer();
		timer.schedule(twitterDataRefreshTask, TASK_DELAY, TASK_REFRESH_RATE);
		logger.info("Please Enter the following URL in your browser to access the Application");
		logger.info("http://localhost:9000/");

		String url = "http://localhost:9000";

		if (Desktop.isDesktopSupported())
		{
			Desktop desktop = Desktop.getDesktop();
			try
			{
				desktop.browse(new URI(url));
			}
			catch (IOException | URISyntaxException e)
			{

			}
		}
		else
		{
			Runtime runtime = Runtime.getRuntime();
			try
			{
				runtime.exec("xdg-open " + url);
			}
			catch (IOException e)
			{
			}
		}

	}

}
