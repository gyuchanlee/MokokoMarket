package com.projectif.mokokomarket.order.service;

import com.projectif.mokokomarket.item.domain.Item;
import com.projectif.mokokomarket.item.repository.ItemRepository;
import com.projectif.mokokomarket.member.domain.Member;
import com.projectif.mokokomarket.member.repository.MemberRepository;
import com.projectif.mokokomarket.order.domain.Cart;
import com.projectif.mokokomarket.order.domain.CartStatus;
import com.projectif.mokokomarket.order.domain.Order;
import com.projectif.mokokomarket.order.domain.OrderStatus;
import com.projectif.mokokomarket.order.dto.request.CartCreateDto;
import com.projectif.mokokomarket.order.dto.request.OrderCreateDto;
import com.projectif.mokokomarket.order.dto.response.CartListResponseDto;
import com.projectif.mokokomarket.order.dto.response.OrderResponseDto;
import com.projectif.mokokomarket.order.repository.CartRepository;
import com.projectif.mokokomarket.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문 조회 *** 나중에 fetch join
    public List<OrderResponseDto> findAllOrders(Long memberId) {
        List<Order> findOrders = orderRepository.findAllByMemberId(memberId);

        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        for (Order order : findOrders) {

            List<Cart> cartList = order.getCartList();
            List<CartListResponseDto> cartListResponseDtoList = new ArrayList<>();

            for (Cart cart : cartList) {
                CartListResponseDto cartResponseDto = CartListResponseDto.builder()
                        .cartId(cart.getId())
                        .count(cart.getCount())
                        .itemId(cart.getItem().getId())
                        .title(cart.getItem().getTitle())
                        .content(cart.getItem().getContent())
                        .imageSource(cart.getItem().getImageSource())
                        .price(cart.getItem().getPrice())
                        .brand(cart.getItem().getBrand())
                        .build();
                cartListResponseDtoList.add(cartResponseDto);
            }

            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                    .orderId(order.getId())
                    .paymentMethod(order.getPaymentMethod())
                    .totalPrice(order.getTotalPrice())
                    .requests(order.getRequests())
                    .status(order.getStatus())
                    .memberId(order.getMember().getId())
                    .userId(order.getMember().getUserId())
                    .cartList(cartListResponseDtoList)
                    .build();
            orderResponseDtos.add(orderResponseDto);
        }

        return orderResponseDtos;
    }

    // 장바구니 상품 낱개 등록 메서드
    @Transactional
    public Cart createCartItem(CartCreateDto dto) {

        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow(() -> new RuntimeException("Member not found"));
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new RuntimeException("Item not found"));
        if (dto.getCount() > item.getQuantity()) {
            log.info("주문하신 수량보다 재고가 부족합니다.");
            Cart cart = new Cart(dto.getCount(), CartStatus.FAILED, member, item);
            return cartRepository.save(cart);
        }

        Cart cart = Cart.builder()
                .count(dto.getCount())
                .member(member)
                .item(item)
                .build();

        return cartRepository.save(cart);
    }

    // 장바구니의 상품들을 결제 한 후, 주문 내역 생성
    @Transactional
    public boolean createOrder(OrderCreateDto dto) {

        List<Cart> carts = new ArrayList<>();

        boolean check = true;

        for (CartCreateDto cartDto : dto.getCartList()) {
            Cart cartItem = createCartItem(cartDto);
            if (cartItem.getStatus() == CartStatus.FAILED) {
                check = false;
            }
            carts.add(cartItem);
        }

        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow(() -> new RuntimeException("Member not found"));

        // 장바구니 상품 주문에 실패했을 때, 주문의 상태를 실패로 등록
        String status = check ? dto.getOrderStatus() : "FAILED";

        Order order = Order.builder()
                .paymentMethod(dto.getPaymentMethod())
                .totalPrice(dto.getTotalPrice())
                .requests(dto.getRequests())
                .status(OrderStatus.valueOf(status))
                .member(member)
                .build();

        Order saved = orderRepository.save(order);
        // 연관관계 맺어주기 -> cart / order
        for (Cart cart : carts) {
            saved.addCart(cart);
        }
        // 총 가격 재 검산
        saved.calcTotalPrice();

        // 들어간 주문만큼 item들의 수량 줄이기.
        carts.forEach(cart -> {
            if (cart.getStatus() != CartStatus.FAILED) {
                cart.getItem().removeStock(cart.getCount());
            }
        });


        return saved.getId() != null && check;
    }

    // 판매자 주문 확인 및 완료
    @Transactional
    public boolean takeOrder(Long orderId) {
        return false;
    }

    // 주문 취소
    @Transactional
    public boolean cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        // 이미 취소되었는 지 체크
        boolean check = true;
        if (order.getStatus() == OrderStatus.CANCELED) {
            check = false;
        }

        order.cancelOrder();

        // 주문 CANCELED 상태면 주문 취소이므로 true 반환
        return order.getStatus().name().equals("CANCELED") && check;
    }

}
