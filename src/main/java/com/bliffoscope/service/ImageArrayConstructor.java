package com.bliffoscope.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

import com.bliffoscope.commons.BliffoscopeObjectType;
import com.bliffoscope.model.BliffoscopeImage;

@Component
public class ImageArrayConstructor {

	
	public BliffoscopeImage constructImageObject(InputStream inputStream, Integer imageHeight, BliffoscopeObjectType imageType) throws Exception{
			
			char[][] imagePixelArray = new char[imageHeight][];
			
			int rowCount = 0;
			String line = null;
			
			
			try(BufferedReader	bufferedReader = new BufferedReader(new InputStreamReader(inputStream))){
				while((line = bufferedReader.readLine()) != null) {
					char[] chars = line.toCharArray();
					imagePixelArray[rowCount++] = chars;
		        }
			}catch (ArrayIndexOutOfBoundsException e) {
				throw new ArrayIndexOutOfBoundsException("Acctual height should be more than "+imageHeight +" for :"+imageType.name());
			}catch (IOException e) {				
				throw new Exception("Error While Reading "+imageType);
			} 
			
			//If image height is mentioned more then exact rowcount is taken as image height
			imageHeight = rowCount;
			return new BliffoscopeImage(imageHeight, imagePixelArray, imageType);
		}
	
	
}
