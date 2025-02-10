package com.sportyShoes.util;

import com.sportyShoes.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
  User findUserByEmailAndPassword(String email, String password);
}
