package ksy.webproj.domain;


import ksy.webproj.domain.item.Item;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    public void setOrder(Order order){
        this.order = order;
    }

    // == 생성 메서드 == //
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        item.removeStock(count);
        return OrderItem.builder()
                        .item(item)
                        .orderPrice(orderPrice)
                        .count(count)
                        .build();
    }


    // == 비지니스 로직 == //
    public void cancel() {
        getItem().addStock(count);
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
