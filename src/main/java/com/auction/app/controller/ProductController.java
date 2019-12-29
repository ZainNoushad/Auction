package com.auction.app.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.auction.app.model.Auction;
import com.auction.app.model.AuctionBids;
import com.auction.app.model.AuctionImage;
import com.auction.app.model.CompletedAuction;
import com.auction.app.model.User;
import com.auction.app.repository.AuctionBidsRepository;
import com.auction.app.repository.AuctionImageRepository;
import com.auction.app.repository.AuctionRepository;
import com.auction.app.repository.CompletedAuctionRepository;
import com.auction.app.repository.UserRepository;
import com.auction.app.util.DateUtil;

@Controller
public class ProductController {
	
	@Autowired
	CompletedAuctionRepository completedAuctionRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuctionRepository auctionRepository;
	
	@Autowired
	AuctionBidsRepository auctionBidsRepository;
	
	@Autowired
	AuctionImageRepository auctionImageRepository;
	
	
	@RequestMapping(path = "/auction/{auctionId}", method = RequestMethod.GET)
	public String productView(@PathVariable(value = "auctionId") Integer auctionId,Model model) throws IOException {
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		User user=userRepository.findByUsername(username);
		
		
		Auction auction=auctionRepository.findById(auctionId).get();
		
		if(auction!=null) {
			
			CompletedAuction winner=completedAuctionRepository.findByAuction(auction);
			
			if(auction.getActive()==0 && winner==null)
				return "CustomeError";
				
			
			List<String> images=new ArrayList<String>();
			for(AuctionImage image : auction.getImages()) {
				File file=new File(image.getPath());
				System.out.println(image.getPath());
			 	images.add(image.getImageId()+"");
			}
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm:ss");
			System.out.println(auction.getAuctionEndingDate());
	        String formatDateTime = auction.getAuctionEndingDate().format(formatter);
			System.out.println(formatDateTime);
	
			model.addAttribute("isOpen", winner!=null);
			model.addAttribute("winner",winner);
			model.addAttribute("auction", auction);
			model.addAttribute("images", images);
			model.addAttribute("endDate", formatDateTime);
			model.addAttribute("owner",user.getUserId()==auction.getAuctionBy().getUserId());
			model.addAttribute("bidPlaced", auctionBidsRepository.findByAuction(auction).size());
			return "AuctionView";
				
		}
		

		return "CustomeError";
	}
	@RequestMapping("/auctionImage/{imageId}")
	@ResponseBody
	public byte[] getImageById(@PathVariable(value="imageId") Integer imageId) throws IOException {
		File serverFile = new File(auctionImageRepository.findById(imageId).get().getPath());
		return Files.readAllBytes(serverFile.toPath());
	}
	
	@RequestMapping(path = "/auction/{auctionId}/addBid",method = RequestMethod.POST)
	@ResponseBody
	public String postBid(@PathVariable(value="auctionId") Integer auctionId,Double bidAmount,Model model) {
		System.out.println(model);
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		User whoAmI=userRepository.findByUsername(username);
		Auction auction=auctionRepository.findById(auctionId).get();
		
		if(auction!=null && whoAmI.getUserId() != auction.getAuctionBy().getUserId()) {
			AuctionBids bid=new AuctionBids();
			bid.setAuction(auction);
			bid.setBidOn(LocalDateTime.now());
			bid.setBidPrice(bidAmount);
			bid.setCustomer(whoAmI);
		
			auctionBidsRepository.save(bid);
			return "Bid Posted Successfully";
		}
		return "error,Given parameter are not valid";
		
	}
	
	@RequestMapping("/auction/{auctionId}/bids")
	@ResponseBody
	public String getBidList(@PathVariable(value = "auctionId")Integer auctionId,Model model,String filter) {
		Auction auction=auctionRepository.findById(auctionId).get();
		
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		User whoAmI=userRepository.findByUsername(username);
		String html="";
		if(whoAmI.getUserId() !=null && auction.getAuctionBy().getUserId() != null) {
			
			CompletedAuction winner=completedAuctionRepository.findByAuction(auction);
			List<AuctionBids> auctionBids = null;
			
			if(filter.equals("1"))
				auctionBids=auctionBidsRepository.findByAuctionOrderByBidOnDesc(auction);
			else if(filter.equals("2"))
				auctionBids=auctionBidsRepository.findByAuctionOrderByBidOnDesc(auction);
			else if(filter.equals("3"))
				auctionBids=auctionBidsRepository.findByAuctionOrderByBidPriceAsc(auction);
			
			
			html="";
			int count=1;
			for(AuctionBids bid : auctionBids) {
				html+="								<tr>\n" + 
						"						     	<td>"+(count++)+"</td>\n" + 
						"						     	<td>"+bid.getCustomer().getUsername()+" </td>\n" + 
						"						     	<td>"+bid.getBidOn()+" </td>\n" + 
						"						     	<td>"+bid.getBidPrice()+" </td>\n";
				if(whoAmI.getUserId()==bid.getCustomer().getUserId() && winner==null) {
					html+="<td><input type='button' class='btn btn-success' onclick='delBid("+bid.getBidId()+")' value='Delete Bid'></td>";
				}
				if(whoAmI.getUserId()==auction.getAuctionBy().getUserId() && winner==null) {
					html+="<td><input type='button' class='btn btn-success' onclick='message()' value='Message'>"+
						  "  <input type='button' class='btn btn-success' onclick='awardBid("+bid.getBidId()+")' value='Award Bid'></td>";
				}
				html+="</tr>";
			}
		}
		
		return html;
	}
	
	@RequestMapping("/auction/deleteBid/{bidId}")
	@ResponseBody
	public String deleteBid(@PathVariable(value = "bidId") Integer bidId) {
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		User whoAmI=userRepository.findByUsername(username);
		
		AuctionBids bid=auctionBidsRepository.findById(bidId).get();
		
		if(bid.getCustomer().getUserId() == whoAmI.getUserId()) {
			auctionBidsRepository.delete(bid);
			return "Bid Deleted";
		}
		
		return "Given Parameter are not valid";
	}
	
	@RequestMapping("/auction/{auctionId}/complete/{bidId}")
	@ResponseBody
	public String completeAuction(@PathVariable(value="auctionId") Integer auctionId, @PathVariable(value="bidId") Integer bidId) {
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		User whoAmI=userRepository.findByUsername(username);
		Auction auction=auctionRepository.findById(auctionId).get();
		AuctionBids bid=auctionBidsRepository.findById(bidId).get();
		
		if(whoAmI.getUserId() == auction.getAuctionBy().getUserId() && bid!=null) {
			CompletedAuction winner=new CompletedAuction();
			winner.setAuction(auction);
			winner.setActive(1);
			winner.setAuctionCompletedOn(LocalDateTime.now());
			winner.setAuctionWonBy(bid.getCustomer());
			winner.setWinningBidPrice(bid.getBidPrice());
			
			auction.setActive(0);
			auctionRepository.save(auction);
			completedAuctionRepository.save(winner);
			
		}
		
		
		
		return "/auction/"+auctionId;
	}
}
