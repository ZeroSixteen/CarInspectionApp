package com.example.demo.web;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserServiceImpl;



@Controller
public class MainController {
	
	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	
	@GetMapping("/")
	public String home() {
		return"index";
	}
	
	
	@GetMapping("/carregistration")
	public String carRegistrationForm(Model model) {
		return "carregistration";
	}
	
	@GetMapping("/uploadimage")
	public String uploadImageForm(Model model) {
		return "uploadimage";
	}
	
	// Waiver Method START 
	@GetMapping("/waiver")
	public String waiverForm(Model model) {
		
		return "waiver";
	}
	
	@PostMapping("/waiver")
	public void waiverSubmit(@RequestParam(value="waiverVerify",required =false) String checkboxValue,Model model) {
		boolean waiverVerify = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("waiverVerify", waiverVerify);
		if (checkboxValue != null) {
			waiverVerify = true;
		}
		else {
			waiverVerify = false;
		}
		
		System.out.println(waiverVerify);
		String Email = auth.getName();
		System.out.println(Email);
		User user = userRepository.findByEmail(Email);
		user.setSigned(waiverVerify);
		userRepository.save(user);
		
		
	}
	@PostMapping("/saveUserPic")
	public String saveUser(@ModelAttribute(name = "user")User user, @RequestParam("driverImage") MultipartFile multipartFile) throws IOException{
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		 user.setPhoto(fileName);
		 User savedUser = userRepository.save(user);
		String uploadDir = "./user-logos/" + savedUser.getId();
		Path uploadPath = Paths.get(uploadDir);
		
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try(InputStream inputStream = multipartFile.getInputStream()){;
		Path filePath = uploadPath.resolve(fileName);
		System.out.println(filePath.toString());
		Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
		}catch(IOException e) {
			throw new IOException("Could not save uploaded file: " + fileName);
		}
		
		System.out.println("this thing is working");
		return "redirect:/uploadimage?success";
	}
	
}
