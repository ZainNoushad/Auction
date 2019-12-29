package com.auction.app.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@PropertySource("classpath:auction.properties")
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
	
	private static List<String> auctionImages;
	 
	private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

	
	@RequestMapping("/createAuction")
	public String projectPage(Model model) {
		Category ct=new Category();
		ct.setCategoryId(1);
		ct.setCategoryName("Game");
		ct.setActive(1);
		categoryRepository.save(ct);
		model.addAttribute("categories", categoryRepository.findAll());
		return "CreateAuction";
	}
	
	@RequestMapping(path = "/createAuction", method = RequestMethod.POST)
	@ResponseBody
	public String createAuction(CreateAuctionDTO auction,HttpServletRequest request) throws IllegalStateException, IOException {
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		User user=userRepository.findByUsername(username);
		System.out.println(request.getLocalAddr());
		
		if(user!=null) {
			if(auction.isDataValid()) {
				Auction auctionDetails=new Auction(user, auction.getTitle(), auction.getStartingPrice(), LocalDateTime.now(), 
						DateUtil.getDate(auction.getEndingDate()), categoryRepository.findByCategoryId(auction.getCategory()), auction.getDescription(), 1);
				
				Auction createdAuction=auctionRepository.save(auctionDetails);
				
				//Uploading Files
			 	for(MultipartFile file : auction.getMultiPartFiles()) {
					/*
					 ************** Until any storage service is setup file will be randomly choosen
					 ************** from image folder  
						//Uploading Images
						File myFile = new File("auction/" + createdAuction.getAuctionId() );
						myFile.mkdirs();
						File realFile=new File(myFile.getAbsolutePath()+"/"+file.getOriginalFilename());
						file.transferTo(realFile);
						//Adding Image To Database
						AuctionImage image=new AuctionImage(createdAuction, file.getOriginalFilename());
						auctionImageRepository.save(image);
						
						System.out.println(realFile.getAbsolutePath());
					*******************************************************
					*****************************END
					*/
					String fileName=(String) getRandomImage();
					AuctionImage image=new AuctionImage(createdAuction, fileName);
					auctionImageRepository.save(image);
					
					
					
					
				}
				
			}
				
		}
			
		
		return "/";
	}
	private String getRandomImage() {
		if(auctionImages == null) {
			try (Stream<Path> walk = Files.walk(Paths.get("auction/images/"))) {
	
				auctionImages = walk.filter(Files::isRegularFile)
						.map(x -> x.toString()).collect(Collectors.toList());
			}
			catch (IOException e) {
				e.printStackTrace();
			}
				
		}
		int fileIndex = ThreadLocalRandom.current().nextInt(0, auctionImages.size());
		return auctionImages.get(fileIndex);
		
		
	}

	@RequestMapping(value = "/image/{auctionId}")
	@ResponseBody
	public byte[] getImage(@PathVariable(value = "auctionId") int auctionId) throws IOException {
		try {
			Pageable pageable=PageRequest.of(0, 1);
			AuctionImage image=auctionImageRepository.findByProject(auctionRepository.findById(auctionId).get(),pageable).get(0);
		    File serverFile = new File(image.getPath());
	
		    return Files.readAllBytes(serverFile.toPath());
		}catch (Exception e) {
//			e.printStackTrace();
			log.info("-------------------------------START-------------------------");
			log.info(">>>>>>>>>>>>>>>>>>Exception Occured >>> getImage()");
			log.info(">>>>>>>>>>>>>>>>>>Message " + e.getMessage());
			log.info("------------------------------- END -------------------------");
			return null;
		}
		
	}
	
	@RequestMapping(value = "/messageApp")
	public String messageApp() {
		return "Messaging";
	}
	
}
