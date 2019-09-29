package com.auction.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auction.app.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	User findByUsername(String username);
	User findByUsernameAndPassword(String username, String password);
	User findByEmail(String email);
}
