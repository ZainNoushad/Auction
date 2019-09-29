package com.auction.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.auction.app.model.AuctionImage;

public interface AuctionImageRepository extends CrudRepository<AuctionImage, Integer> {
	
	@Query(value = "select * from auction_images where auction_id = :auctionId LIMIT 0,1", nativeQuery = true)
	AuctionImage findOneImage(@Param("auctionId") Integer auctionId);
}
 