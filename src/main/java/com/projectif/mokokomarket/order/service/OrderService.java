package com.projectif.mokokomarket.order.service;

import com.projectif.mokokomarket.order.domain.Cart;
import com.projectif.mokokomarket.order.domain.Order;
import com.projectif.mokokomarket.order.domain.OrderStatus;
import com.projectif.mokokomarket.order.dto.request.OrderCreateDto;
import com.projectif.mokokomarket.order.dto.response.CartListResponseDto;
import com.projectif.mokokomarket.order.dto.response.OrderResponseDto;
import com.projectif.mokokomarket.order.repository.CartRepository;
import com.projectif.mokokomarket.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    // 주문 조회
    public List<OrderResponseDto> findAllOrders(Long memberId) {
        List<Order> findOrders = orderRepository.findAllByMemberId(memberId);

        return null;
    }

    // 장바구니의 상품들을 결제 한 후, 주문 내역 생성
    public boolean createOrder(OrderCreateDto dto, Long memberId) {
        List<Cart> findCartItem = cartRepository.findAllByMemberIdAndIsDeletedIs(memberId, false);

        Order order = Order.builder()
                .paymentMethod(dto.getPaymentMethod())
                .totalPrice(dto.getTotalPrice())
                .requests(dto.getRequests())
                .status(OrderStatus.valueOf(dto.getOrderStatus()))
                .cartList(findCartItem)
                .build();

        Order saved = orderRepository.save(order);

        return saved.getId() != null;
    }

    // 주문 수정

    // 주문 취소

}
