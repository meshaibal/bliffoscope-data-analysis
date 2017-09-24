package com.bliffoscope.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bliffoscope.commons.BliffoscopeObjectType;
import com.bliffoscope.service.BliffoscopeService;
import com.google.gson.Gson;

@Controller
@RequestMapping(value="/target-finder")
public class BliffoscopeController {

	private static final Gson GSON = new Gson();
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showWelcomePage(){
		ModelAndView modelAndView = new ModelAndView("bliffoscope");
			
		modelAndView.addObject("sources", Arrays.asList(BliffoscopeObjectType.values()));
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/find-target")
	public @ResponseBody String upload(@RequestParam("sourceFile1") MultipartFile source1File, @RequestParam("sourcePixelHeight1") int source1VerticalLength, @RequestParam("type1") String soureType1,
									   @RequestParam("sourceFile2") MultipartFile source2File, @RequestParam("sourcePixelHeight2") int source2VerticalLength, @RequestParam("type2") String soureType2,
									   @RequestParam("targetFile") MultipartFile targetFile, @RequestParam("targetPixelHeight") int targetVerticalLength, @RequestParam("type") String type 
			                            
			                           ) throws Exception{

		if(!source1File.isEmpty() && !targetFile.isEmpty() && !source2File.isEmpty()){
			

			BliffoscopeObjectType source1Type = BliffoscopeObjectType.valueOf(soureType1);
			BliffoscopeObjectType source2Type = BliffoscopeObjectType.valueOf(soureType2);
			
					
			return GSON.toJson(bliffoscopeService.run(source1File.getInputStream(), source2File.getInputStream(), source1VerticalLength, 
					source2VerticalLength, source1Type, source2Type, targetFile.getInputStream(), targetVerticalLength));
		}
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/download/{fileName}")
	public void downloadFile(HttpServletResponse response, @PathVariable("fileName") String fileName) throws Exception{
		
		try {
	        fileName = fileName+".txt";
	        response.setContentType("application/x-download"); 
	        response.setHeader("Content-disposition", "attachment; filename=result");
	        
	        FileInputStream in = new FileInputStream(fileName);
	        OutputStream out = response.getOutputStream();

	         byte[] buf = new byte[1024];
	         int count = 0;
	         while ((count = in.read(buf)) >= 0) {
	           out.write(buf, 0, count);
	        }
	      out.close();
	      in.close();
	        
	    } catch (IOException ex) {
	        throw new Exception("IOError writing file to output stream");
	    }
	}
	
	@ExceptionHandler(ArrayIndexOutOfBoundsException.class)
	public @ResponseBody String handelExceptionLessImageHeight(Exception ex){
		
		Map<String, Object> errorMap = new HashMap<>();
		errorMap.put("isError", true);
		errorMap.put("ErrorMessage", "<strong>ERROR! </strong>"+ex.getMessage());
		
		return GSON.toJson(errorMap);
	}
	
	@ExceptionHandler(Exception.class)
	public @ResponseBody String handelException(Exception ex){
		
		Map<String, Object> errorMap = new HashMap<>();
		errorMap.put("isError", true);
		errorMap.put("ErrorMessage", "<strong>ERROR! </strong>"+ex.getMessage());
		
		return GSON.toJson(errorMap);
	}
	
	private BliffoscopeService bliffoscopeService;

	@Autowired
	public void setBliffoscopeService(BliffoscopeService bliffoscopeService) {
		this.bliffoscopeService = bliffoscopeService;
	}
	
	
}
