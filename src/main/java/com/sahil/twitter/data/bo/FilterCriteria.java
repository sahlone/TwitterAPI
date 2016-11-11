package com.sahil.twitter.data.bo;

/**
 * Created by sahil.lone on 11/11/2016.
 */
public enum FilterCriteria
{

	BASE_SEARCH_TERM("twitter"),

	SEARCH_BY_HASH_TAG("#"),

	SEARCH_BY_EMBEDED_WORD(" "),

	SEARCH_BY_TWEET_TEXT("“"),

	SEARCH_BY_USER_NAME("@");

	private String value = null;

	FilterCriteria(String value)
	{
		this.value = value;
	}

	public String value()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
