package com.bliffoscope.model;

import com.bliffoscope.commons.BliffoscopeObjectType;

public class BliffoscopeImage {

	private Integer imageHeight;
	private char[][] imagePixel;
	private BliffoscopeObjectType bliffoscopeObject;
	private Integer totalPixels;
	
	public BliffoscopeImage() {
		super();
	}

	public BliffoscopeImage(Integer imageHeight, char[][] imagePixel, BliffoscopeObjectType bliffoscopeObject) {
		super();
		this.imageHeight = imageHeight;
		this.imagePixel = imagePixel;
		this.bliffoscopeObject = bliffoscopeObject;
	}
	
	public BliffoscopeImage(Integer imageHeight, char[][] imagePixel) {
		super();
		this.imageHeight = imageHeight;
		this.imagePixel = imagePixel;
	}

	public Integer getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public char[][] getImagePixel() {
		return imagePixel;
	}

	public void setImagePixel(char[][] imagePixel) {
		this.imagePixel = imagePixel;
	}

	public BliffoscopeObjectType getBliffoscopeObject() {
		return bliffoscopeObject;
	}

	public void setBliffoscopeObject(BliffoscopeObjectType bliffoscopeObject) {
		this.bliffoscopeObject = bliffoscopeObject;
	}

	public Integer getTotalPixels() {
		if(this.totalPixels == null){
			return this.imageHeight * this.imagePixel[0].length;
		}
		return totalPixels;
	}

	public void setTotalPixels() {
		this.totalPixels = this.imageHeight * this.imagePixel[0].length;
	}
	
	
}
