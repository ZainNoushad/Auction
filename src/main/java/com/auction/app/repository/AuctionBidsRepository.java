package com.auction.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.auction.app.model.Auction;
import com.auction.app.model.AuctionBids;

public interface AuctionBidsRepository extends CrudRepository<AuctionBids, Integer> {
	
	List<AuctionBids> findByAuction(Auction auction);
	List<AuctionBids> findByAuctionOrderByBidOnDesc(Auction auction);
	List<AuctionBids> findByAuctionOrderByBidPriceDesc(Auction auction);
	List<AuctionBids> findByAuctionOrderByBidPriceAsc(Auction auction);
	
}
