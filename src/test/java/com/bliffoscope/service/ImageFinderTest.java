package com.bliffoscope.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


import com.bliffoscope.model.BliffoscopeImage;
import com.bliffoscope.model.ResultData;

import static org.junit.Assert.assertEquals;


public class ImageFinderTest {

	private ImageMatcher imageMatcher ;
	private ImageArrayConstructor imageArrayConstructor ;
	private ImageFinder imageFinder ;
	
	@Before
	public void setUp(){
		imageMatcher = new ImageMatcher();
		imageFinder = new ImageFinder();
		imageFinder.setImageMatcher(imageMatcher);
		imageArrayConstructor = new ImageArrayConstructor();
	}
	
	@Test
	public void findInputImageInTargetImage() throws Exception{
		ClassLoader classLoader = getClass().getClassLoader();
		File sourceFile = new File(classLoader.getResource("input.txt").getFile());
		File targetFile = new File(classLoader.getResource("target.txt").getFile());
		
		BliffoscopeImage sourceImage = imageArrayConstructor.constructImageObject(new FileInputStream(sourceFile), 4, null);
		BliffoscopeImage targetImage = imageArrayConstructor.constructImageObject(new FileInputStream(targetFile), 22, null);
		
		
		List<ResultData> list = imageFinder.findImage(targetImage, sourceImage, null, 1.0f);

		assertEquals("Expect 5 figures in target file", 5, list.size());
	}
	
	
}
