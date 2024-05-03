package com.projectif.mokokomarket.order.repository;

import com.projectif.mokokomarket.order.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByMemberId(Long memberId);

    @Query("select c from Cart c where c.order.id = :memberId and c.isDeleted = :isDeleted")
    List<Cart> findCartListByMemberId(Long memberId, Boolean isDeleted);
}
