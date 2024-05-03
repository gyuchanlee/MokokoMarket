package com.projectif.mokokomarket.item.controller;

import com.projectif.mokokomarket.item.domain.response.ItemResponseDto;
import com.projectif.mokokomarket.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("")
    public List<ItemResponseDto> getItems() {
        return itemService.getItemList();
    }

    @GetMapping("/{itemId}")
    public ItemResponseDto getItem(@PathVariable Long itemId) {
        return itemService.getItem(itemId);
    }
}
