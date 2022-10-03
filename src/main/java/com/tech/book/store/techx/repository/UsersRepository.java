package com.tech.book.store.techx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tech.book.store.techx.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
	Users findByUsername(String username);
	
	boolean existsByUsername(String username);
	
	@Query(value = "delete from Users u where u.username = ?1", nativeQuery = true)
    void deleteUsersByUserName(String username);
	

	@Query(value = "select * from Users u where u.username = ?1", nativeQuery = true)
	Users findUserIdByName(String username);
}
