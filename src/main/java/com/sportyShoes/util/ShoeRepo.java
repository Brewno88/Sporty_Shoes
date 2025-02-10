package com.sportyShoes.util;

import com.sportyShoes.pojo.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShoeRepo extends JpaRepository<Shoe, Long> {
    String findNameByEmpNo="select s from Shoe s where s.name=?1";
    @Query(findNameByEmpNo)
    public Optional<Shoe> findShoeByName(String name);
}
