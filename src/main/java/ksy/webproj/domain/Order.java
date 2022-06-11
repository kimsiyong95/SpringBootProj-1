package ksy.webproj.domain;

import lombok.*;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    //연관관계 메서드
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // == 생성 메서드 == //
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = Order.builder()
                .member(member)
                .delivery(delivery)
                .status(OrderStatus.ORDER)
                .orderDate(LocalDateTime.now())
                .build();

        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        return order;
    }

    // == 비지니스 로직 == //
    /**
     * 주문 취소
     */

    public void cancel(){
        if(delivery.getDeliveryStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.status = OrderStatus.CANCEL;

        orderItems.forEach(OrderItem::cancel);
    }


    // == 조회 로직 == //
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}