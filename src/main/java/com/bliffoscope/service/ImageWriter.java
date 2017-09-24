package com.bliffoscope.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bliffoscope.commons.BliffoscopeConstants;
import com.bliffoscope.commons.BliffoscopeObjectType;
import com.bliffoscope.model.BliffoscopeImage;
import com.bliffoscope.model.ResultData;

@Component
public class ImageWriter {

	// This method writes the best matched images in a text file
	public String writeToFile(BliffoscopeImage targetImage, Map<BliffoscopeObjectType, BliffoscopeImage> sourceImageMap, List<ResultData> resultList) throws IOException{
			
			int sourceHeight = 0;
			int sourceWeidth = 0;
			
			String fileName = BliffoscopeConstants.RESULT_FILE_NAME;
			
			File myFile = new File(fileName);
			
			FileWriter writer = new FileWriter(myFile);
			for(ResultData resultData : resultList){
				sourceHeight = sourceImageMap.get(resultData.getObjectType()).getImageHeight();
				sourceWeidth = sourceImageMap.get(resultData.getObjectType()).getImagePixel()[0].length;
				int x = resultData.getxCoordinate();
				int y = resultData.getyCoordinate();
				writer.write("-------Coordnate :("+x+","+y+"),  Match:"+resultData.getMatchPercentage()+", Source_Type :"+resultData.getObjectType()+"----------");
				writer.write("\n");
				for(int row=x; row<x+sourceHeight; row++ ){
					StringBuffer sb = new StringBuffer("");
					for(int column=y; column<y+sourceWeidth; column++){
						sb.append(targetImage.getImagePixel()[row][column]);
					}
					
					writer.write(sb.toString());
					writer.write("\n");
				}
				writer.write("\n");
			}
			writer.close();
			
			return fileName;
		}
}
