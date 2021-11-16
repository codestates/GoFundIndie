package com.IndieAn.GoFundIndie.Repository.JPAInterface;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJPAInterface extends JpaRepository<Board, Long> {
}
