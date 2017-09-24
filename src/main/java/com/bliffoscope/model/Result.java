package com.bliffoscope.model;

import java.util.List;

public class Result {

	private String resultPlottingUrl;
	private List<ResultData> results;
	
	public Result(String resultPlottingUrl, List<ResultData> results) {
		super();
		this.resultPlottingUrl = resultPlottingUrl;
		this.results = results;
	}

	public String getResultPlottingUrl() {
		return resultPlottingUrl;
	}

	public void setResultPlottingUrl(String resultPlottingUrl) {
		this.resultPlottingUrl = resultPlottingUrl;
	}

	public List<ResultData> getResults() {
		return results;
	}

	public void setResults(List<ResultData> results) {
		this.results = results;
	}
	
	
	
}
