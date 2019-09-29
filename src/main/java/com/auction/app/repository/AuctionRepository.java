package com.auction.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.auction.app.model.Auction;

public interface AuctionRepository extends CrudRepository<Auction, Integer> {
	
	List<Auction> findByActive(int active);
}
