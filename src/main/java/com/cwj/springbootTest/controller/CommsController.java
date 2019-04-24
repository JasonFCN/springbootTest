package com.cwj.springbootTest.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("comms")
public class CommsController {
	
	private static final String UPLOADED_FOLDER="F:/图片/";
	
	@GetMapping("uploadPage")
	public String uploadPage(){
		return "uploadFile";
	}
	@PostMapping("uploadFile")
	public String uploadFile(HttpServletRequest request,@RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes){
		if(file.isEmpty()){
			redirectAttributes.addFlashAttribute("message","请选择您要上传的文件！");
			return "redirect:uploadPage";
		}
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			redirectAttributes.addFlashAttribute("message","上传成功！"+file.getOriginalFilename());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:uploadPage";
	}
}
