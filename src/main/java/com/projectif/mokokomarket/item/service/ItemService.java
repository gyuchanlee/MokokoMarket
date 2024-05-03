package com.projectif.mokokomarket.item.service;

import com.projectif.mokokomarket.item.domain.Item;
import com.projectif.mokokomarket.item.domain.response.ItemResponseDto;
import com.projectif.mokokomarket.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // 상품 전체 조회
    public List<ItemResponseDto> getItemList() {

        List<Item> items = itemRepository.findAll();

        return items.stream().map(item ->
                ItemResponseDto.builder()
                        .itemId(item.getId())
                        .title(item.getTitle())
                        .content(item.getContent())
                        .imageSource(item.getImageSource())
                        .price(item.getPrice())
                        .quantity(item.getQuantity())
                        .brand(item.getBrand())
                        .build()
        ).toList();
    }

    // 상품 한 건 조회
    public ItemResponseDto getItem(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
        return ItemResponseDto.builder()
                .itemId(item.getId())
                .title(item.getTitle())
                .content(item.getContent())
                .imageSource(item.getImageSource())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .brand(item.getBrand())
                .build();
    }


    // 상품 등록

    // 상품 수정

    // 상품 삭제
}
