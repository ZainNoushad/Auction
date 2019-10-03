package com.auction.app.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.auction.app.dto.CreateAuctionDTO;
import com.auction.app.model.Auction;
import com.auction.app.model.AuctionImage;
import com.auction.app.model.Category;
import com.auction.app.model.User;
import com.auction.app.repository.AuctionImageRepository;
import com.auction.app.repository.AuctionRepository;
import com.auction.app.repository.CategoryRepository;
import com.auction.app.repository.UserRepository;
import com.auction.app.util.DateUtil;

@Controller
@PropertySource("auction.properties")
public class ProjectController {
	
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuctionRepository auctionRepository;
	
	@Autowired
	AuctionImageRepository auctionImageRepository;
	
	@Value("${auction.fileUpload}")
	String fileUploadURL;
	
	@RequestMapping("/createAuction")
	public String projectPage(Model model) {
		
		model.addAttribute("categories", categoryRepository.findAll());
		return "CreateAuction";
	}
	
	@RequestMapping(path = "/createAuction", method = RequestMethod.POST)
	@ResponseBody
	public String createAuction(CreateAuctionDTO auction,HttpServletRequest request) throws IllegalStateException, IOException {
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		User user=userRepository.findByUsername(username);
		System.out.println(request.getLocalAddr());
		Category ct=new Category();
		ct.setCategoryId(1);
		ct.setCategoryName("Game");
		ct.setActive(1);
		categoryRepository.save(ct);
		
		if(user!=null) {
			if(auction.isDataValid()) {
				Auction auctionDetails=new Auction(user, auction.getTitle(), auction.getStartingPrice(), LocalDateTime.now(), 
						DateUtil.getDate(auction.getEndingDate()), categoryRepository.findByCategoryId(auction.getCategory()), auction.getDescription(), 1);
				
				Auction createdAuction=auctionRepository.save(auctionDetails);
				
				for(MultipartFile file : auction.getMultiPartFiles()) {
					
					//Uploading Images
					File myFile = new File("auction/" + createdAuction.getAuctionId() );
					myFile.mkdirs();
					File realFile=new File(myFile.getAbsolutePath()+"/"+file.getOriginalFilename());
					file.transferTo(realFile);
					//Adding Image To Database
					AuctionImage image=new AuctionImage(createdAuction, file.getOriginalFilename());
					auctionImageRepository.save(image);
					
					System.out.println(realFile.getAbsolutePath());
					
				}
				
			}
				
		}
			
		System.out.println("hei");
		
		return "/";
	}
	@RequestMapping(value = "/image/{auctionId}")
	@ResponseBody
	public byte[] getImage(@PathVariable(value = "auctionId") int auctionId) throws IOException {
		System.out.println(auctionId);
		AuctionImage image=auctionImageRepository.findOneImage(auctionId);
	    File serverFile = new File("auction/" + auctionId + "/" +image.getPath());

	    return Files.readAllBytes(serverFile.toPath());
	}
}
