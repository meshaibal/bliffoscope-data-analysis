package com.bliffoscope.model;

import com.bliffoscope.commons.BliffoscopeObjectType;

public class ResultData {

	private BliffoscopeObjectType objectType;
	private float matchPercentage;
	private String corordinateText;
	private int xCoordinate;
	private int yCoordinate;
	
	public ResultData() {
		super();
	}

	

	public ResultData(BliffoscopeObjectType objectType, float matchPercentage, String corordinateText, int xCoordinate,
			int yCoordinate) {
		super();
		this.objectType = objectType;
		this.matchPercentage = matchPercentage;
		this.corordinateText = corordinateText;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}



	public BliffoscopeObjectType getObjectType() {
		return objectType;
	}

	public void setObjectType(BliffoscopeObjectType objectType) {
		this.objectType = objectType;
	}

	public float getMatchPercentage() {
		return matchPercentage;
	}

	public void setMatchPercentage(float matchPercentage) {
		this.matchPercentage = matchPercentage;
	}

	public String getCorordinateText() {
		return corordinateText;
	}

	public void setCorordinateText(String corordinateText) {
		this.corordinateText = corordinateText;
	}



	public int getxCoordinate() {
		return xCoordinate;
	}



	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}



	public int getyCoordinate() {
		return yCoordinate;
	}



	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	
	
	
}
