package com.projectif.mokokomarket.item.domain;

import com.projectif.mokokomarket.global.auditing.BaseEntity;
import com.projectif.mokokomarket.order.domain.Cart;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    private String title;
    private String content;
    private String imageSource;
    private Integer price;
    private Long quantity;
    private Brand brand;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Cart> cartList = new ArrayList<>();

    // 재고 변동 메서드
    public void addStock(Integer count) {
        this.quantity += count;
    }

    public void removeStock(Integer count) {
        this.quantity -= count;
    }
}
