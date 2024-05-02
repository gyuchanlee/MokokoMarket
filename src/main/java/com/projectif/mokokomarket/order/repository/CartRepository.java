package com.projectif.mokokomarket.order.repository;

import com.projectif.mokokomarket.order.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByMemberId(Long memberId);
    List<Cart> findAllByMemberIdAndIsDeletedIs(Long memberId, Boolean isDeleted);
}
