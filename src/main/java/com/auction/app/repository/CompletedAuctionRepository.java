package com.auction.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.auction.app.model.Auction;
import com.auction.app.model.CompletedAuction;

public interface CompletedAuctionRepository extends CrudRepository<CompletedAuction, Integer>{
	
	boolean existsByAuction(Auction auction);
	CompletedAuction findByAuction(Auction auction);
}
