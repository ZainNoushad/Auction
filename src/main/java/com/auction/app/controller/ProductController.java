package com.auction.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.auction.app.repository.AuctionRepository;
import com.auction.app.repository.CompletedAuctionRepository;

@Controller
public class ProductController {
	
	@Autowired
	CompletedAuctionRepository completedAuctionRepository;
	
	@Autowired
	AuctionRepository auctionRepository;
	
	@RequestMapping("/auction/{auctionId}")
	public ModelAndView productView(Model model, @PathVariable(value = "auctionId") Integer auctionId) {
		System.out.println(auctionId);
		boolean auctionCompleted=completedAuctionRepository.existsByAuction(auctionRepository.findById(auctionId).get());
		
		ModelAndView view=new ModelAndView();
		view.setViewName("AuctionView");
		view.addObject("winner", auctionCompleted);
		
		return view;
		
		
	}
}
