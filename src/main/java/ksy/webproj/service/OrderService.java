package ksy.webproj.service;


import ksy.webproj.domain.Delivery;
import ksy.webproj.domain.Member;
import ksy.webproj.domain.Order;
import ksy.webproj.domain.OrderItem;
import ksy.webproj.domain.item.Item;
import ksy.webproj.repository.ItemRepository;
import ksy.webproj.repository.MemberRepository;
import ksy.webproj.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */

    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("조회된 회원이 없습니다."));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("조회된 상품이 없습니다."));

        // 배송정보 생성
        Delivery delivery = Delivery.builder()
                                    .address(member.getAddress())
                                    .build();

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */

    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("조회된 상품이 없습니다."));
        order.cancel();
    }


    /**
     * 검색
     */
    public List<Order> findOrders(){
        return orderRepository.findAll();
    }

}
