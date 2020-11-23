package com.example.demo.web;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

public class UserController {
	
	@Autowired
    private UserRepository repo;
	
	/*@PostMapping("/saveUserPic")
	public String saveUser(@ModelAttribute(name = "user")User user, @RequestParam("driverImage") MultipartFile multipartFile) throws IOException{
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		 user.setPhoto(fileName);
		 User savedUser = repo.save(user);
		String uploadDir = "/user-logos/" + savedUser.getId();
		Path uploadPath = Paths.get(uploadDir);
		
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try(InputStream inputStream = multipartFile.getInputStream()){;
		Path filePath = uploadPath.resolve(fileName);
		Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
		}catch(IOException e) {
			throw new IOException("Could not save uploaded file: " + fileName);
		}
		System.out.println("this thing is working");
		return "redirect:/";
	}  */
}
