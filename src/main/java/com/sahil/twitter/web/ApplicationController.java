package com.sahil.twitter.web;

import com.sahil.twitter.data.bo.FilterCriteria;
import com.sahil.twitter.service.TwitterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import twitter4j.Status;

import java.util.List;

/**
 * Created by sahil.lone on 11/10/2016.
 */
@Controller
@RequestMapping("/")
public class ApplicationController
{

	private TwitterService twitterService = null;

	public ApplicationController(TwitterService twitterService)
	{
		this.twitterService = twitterService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model)
	{
		List<Status> tweets =twitterService.getAllTweets();
		model.addAttribute("data", tweets);
		model.addAttribute("queryTerm",FilterCriteria.BASE_SEARCH_TERM.value());
		return "index";
	}



	@RequestMapping(value = "/updateSearchTerm", method = RequestMethod.GET)
	public String updateSearchTerm(Model model,
			@RequestParam(value = "searchQuery", required = false, defaultValue = "twitter") String searchQuery)
	{
		twitterService.updateSearchTerm(searchQuery);
		return index(model);
	}

	@RequestMapping(value = "/filterTweets", method = RequestMethod.GET)
	public String filterTweets(Model model,
			@RequestParam(value = "filterCriteria", required = false) String filterCriteria)
	{
		if (filterCriteria == null || filterCriteria.isEmpty())
		{
			filterCriteria = FilterCriteria.BASE_SEARCH_TERM.value();
		}
		FilterCriteria criteria = FilterCriteria.valueOf(filterCriteria);
		List<Status> tweets = twitterService.searchTweets(criteria);
		model.addAttribute("data", tweets);
		model.addAttribute("queryTerm",FilterCriteria.BASE_SEARCH_TERM.value());
		return "index";
	}

	@ResponseBody
	@RequestMapping(value = "//healthCheck", method = RequestMethod.GET)
	public String healthCheck(Model model			)
	{
		return "working";
	}
}
