package com.projectif.mokokomarket.item.repository;

import com.projectif.mokokomarket.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
