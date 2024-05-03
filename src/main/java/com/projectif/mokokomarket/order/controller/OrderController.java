package com.projectif.mokokomarket.order.controller;

import com.projectif.mokokomarket.order.dto.request.OrderCreateDto;
import com.projectif.mokokomarket.order.dto.response.OrderResponseDto;
import com.projectif.mokokomarket.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 한 멤버의 주문 리스트 조회
    @GetMapping("/{memberId}")
    public List<OrderResponseDto> getOrder(@PathVariable("memberId") Long memberId) {
        return orderService.findAllOrders(memberId);
    }

    // 한 회원의 주문 한 건 생성
    @PostMapping("")
    public boolean createOrder(@RequestBody OrderCreateDto orderCreateDto) {
        return orderService.createOrder(orderCreateDto);
    }

    // 회원의 주문 전체 취소
    @DeleteMapping("/{orderId}")
    public boolean cancelOrder(@PathVariable("orderId") Long orderId) {
        return orderService.cancelOrder(orderId);
    }
}
