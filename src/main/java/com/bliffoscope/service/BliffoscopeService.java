package com.bliffoscope.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bliffoscope.commons.BliffoscopeConstants;
import com.bliffoscope.commons.BliffoscopeObjectType;
import com.bliffoscope.model.BliffoscopeImage;
import com.bliffoscope.model.Result;
import com.bliffoscope.model.ResultData;

@Component
public class BliffoscopeService {

	public Result run(InputStream source1FilePath, InputStream source2FilePath, 
			               int source1ImageHeight, int source2ImageHeight, 
			               BliffoscopeObjectType source1Type, BliffoscopeObjectType source2Type, InputStream targetFilePath, int targetImageHeight) throws Exception{
	//public void run() throws IOException{
		Map<BliffoscopeObjectType, BliffoscopeImage> sourceImageMap = new HashMap<>(2);
		
		BliffoscopeImage source1 = imageArrayConstructor.constructImageObject(source1FilePath, source1ImageHeight,source1Type);
		BliffoscopeImage source2 = imageArrayConstructor.constructImageObject(source2FilePath, source2ImageHeight,source2Type);
		BliffoscopeImage target = imageArrayConstructor.constructImageObject(targetFilePath, targetImageHeight, null);
		
		sourceImageMap.put(source1.getBliffoscopeObject(), source1);
		sourceImageMap.put(source2.getBliffoscopeObject(), source2);
		
		List<ResultData> resultList = new ArrayList<>();
		resultList.addAll(imageFinder.findImage(target, source1, source1Type, BliffoscopeConstants.MINIMUM_MATCH_PERCENTAGE));
		resultList.addAll(imageFinder.findImage(target, source2, source2Type, BliffoscopeConstants.MINIMUM_MATCH_PERCENTAGE));
		
		return new Result(imageWriter.writeToFile(target, sourceImageMap, resultList), resultList);
	}
	
	
	/*public List<ResultData> run2() throws IOException{
		//public void run() throws IOException{
		//BliffoscopeImage source1 = new ImageArrayConstructor().constructImageObject("D:\\Shaibal\\personal\\MyWork\\SlimeTorpedo.txt", 13,null);
		//BliffoscopeImage target = new ImageArrayConstructor().constructImageObject("D:\\Shaibal\\personal\\MyWork\\TestData.txt", 100, null);
		
		List<ResultData> resultList = new ArrayList<>();
		resultList.addAll(imageFinder.findImage(target, source1, null, BliffoscopeConstants.MINIMUM_MATCH_PERCENTAGE));
		
		return resultList;
}*/
	
	private ImageArrayConstructor imageArrayConstructor;	
	private ImageWriter imageWriter;
	private ImageFinder imageFinder;
	
	@Autowired
	public void setImageArrayConstructor(ImageArrayConstructor imageArrayConstructor) {
		this.imageArrayConstructor = imageArrayConstructor;
	}
	
	@Autowired
	public void setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
	}

	@Autowired
	public void setImageFinder(ImageFinder imageFinder) {
		this.imageFinder = imageFinder;
	}


	public static void main(String[] args) throws IOException {
		//new BliffoscopeService().run2();
	}
	
	
}
