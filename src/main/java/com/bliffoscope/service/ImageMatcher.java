package com.bliffoscope.service;

import org.springframework.stereotype.Component;

import com.bliffoscope.model.BliffoscopeImage;

@Component
public class ImageMatcher {

	/**
	 * 
	 * @param targetImage -- This is the test data, where the provided input will be matched
	 * @param sourceImage -- This is the input image that is to be matched
	 * @param row -- indicates the row in target image
	 * @param column -- indicated the column of the above row in target image
	 * @return
	 * 
	 * This method takes two arrays of source and target, and finds how many co-ordinates are matched.
	 */
	public int matchImage(BliffoscopeImage targetImage, BliffoscopeImage sourceImage, int row, int column){
		int match = 0;
		int i = 0;
		int j = 0;
		
		char[][] targetImagePixelArray = targetImage.getImagePixel();
		char[][] sourceImagePixelArray = sourceImage.getImagePixel();
		
		for(int rowIndex=row; rowIndex <row+sourceImage.getImageHeight(); rowIndex++){
			j = 0;
			for(int columnIndex=column; columnIndex< column+sourceImagePixelArray[0].length; columnIndex++){
				if(targetImagePixelArray[rowIndex][columnIndex] == sourceImagePixelArray[i][j]){
					match++;
				}
				j++;
			}
			i++;
		}
		
		return match;
	}
}
