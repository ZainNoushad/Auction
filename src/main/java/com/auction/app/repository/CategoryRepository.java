package com.auction.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.auction.app.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
	public Category findByCategoryId(int categoryId);
}
