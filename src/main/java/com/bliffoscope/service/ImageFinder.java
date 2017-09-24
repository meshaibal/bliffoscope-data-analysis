package com.bliffoscope.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bliffoscope.commons.BliffoscopeObjectType;
import com.bliffoscope.model.BliffoscopeImage;
import com.bliffoscope.model.ResultData;

@Component
public class ImageFinder {

	/**
	 * 
	 * @param targetImage -- This is the test data, where the provided input will be searched
	 * @param sourceImage -- This is the input image that is to be searched
	 * @param imageType - This is the type of input image (SLIME_TORPEDO or STARSHIP)
	 * @param minimumMatchPercentage - This is approximate match percentage to determine the probability of source to be present in target 
	 * @return
	 * @throws IOException
	 */
	public List<ResultData> findImage(BliffoscopeImage targetImage, BliffoscopeImage sourceImage, BliffoscopeObjectType imageType, float minimumMatchPercentage) throws IOException{
			
			List<ResultData> resultList =  new ArrayList<>();			
			int totalPixels = sourceImage.getTotalPixels().intValue();			
			int noOfMatch = 0;
			float matchPercentage = 0f;
			int sourceHeight = sourceImage.getImageHeight();
			int sourceWidth = sourceImage.getImagePixel()[0].length;
			/**
			 * Here logic is, for each co-ordinate in target , the input image array is searched in the target.
			 * maximum height considered, the length of the input image array
			 * maximum width considered, the width of the input array.
			 */
			for(int row=0; row <=targetImage.getImageHeight() - sourceHeight; row++){
				for(int column=0; column <= targetImage.getImagePixel()[0].length - sourceWidth; ){
					
					noOfMatch = imageMatcher.matchImage(targetImage, sourceImage, row, column);
					matchPercentage = (float)noOfMatch/totalPixels;
					
					// If matchpercentage is >=  predicted minimum percentage, then it is considered as match.
					// In case of match, array column counter is increased by the length of input image array.
					if(matchPercentage >= minimumMatchPercentage){
						resultList.add(new ResultData(imageType, matchPercentage*100, "[("+row+","+column+")("+(row+sourceHeight)+","+column+")("+(row+sourceHeight)+","+(column+sourceWidth)+")("+row+","+(column+sourceWidth)+")]", row, column));
						column = column + sourceImage.getImagePixel()[0].length;
						
					}else{
						column++;
					}
				}
				
			}
			return resultList;
		}

	private ImageMatcher imageMatcher;

	@Autowired
	public void setImageMatcher(ImageMatcher imageMatcher) {
		this.imageMatcher = imageMatcher;
	}
	
	
}
