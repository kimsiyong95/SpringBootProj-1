package ksy.webproj.service;

import ksy.webproj.domain.Address;
import ksy.webproj.domain.Member;
import ksy.webproj.domain.Order;
import ksy.webproj.domain.OrderStatus;
import ksy.webproj.domain.item.Book;
import ksy.webproj.domain.item.Item;
import ksy.webproj.exception.NotEnoughStockException;
import ksy.webproj.repository.ItemRepository;
import ksy.webproj.repository.MemberRepository;
import ksy.webproj.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Transactional
class OrderServiceTest {


    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @DisplayName("상품주문")
    @Test
    public void orderItem() throws Exception {
        //given
        Member member = createMember();

        Book book = createBook("Real Mysql", 10000, 10);
        //when
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);


        //then
        Order getOrder = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("조회된 주문이 없습니다."));


        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER).as("상품 주문시 상태는 ORDER");
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1).as("주문한 상품 종류 수가 정확해야 한다.");
        assertThat(getOrder.getTotalPrice()).isEqualTo(10000*orderCount).as("주문 가격은 가격 * 수량이다.");
        assertThat(book.getStockQuantity()).isEqualTo(8).as("주문 수량만큼 재고가 줄어야 한다.");

    }




    @DisplayName("주문취소")
    @Test
    public void orderCancel() throws Exception {
        //given
        Member member = createMember();
        Book item = createBook("오브젝트", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findById(orderId).orElseThrow(()-> new IllegalArgumentException("조회된 주문이 없습니다."));

        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCEL).as("주문 취소시 상태는 CANCEL이 된다.");
        assertThat(item.getStockQuantity()).isEqualTo(10).as("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");
    }

    @DisplayName("상품주문_재고수량초과")
    @Test
    public void orderItemStockCountExcess() throws Exception {
        //given
        Member member = createMember();

        Item item = createBook("Clean Code", 10000, 10);

        int orderCount = 11;
        
        //when
        NotEnoughStockException thrown = assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), item.getId(), orderCount));


        //then
        assertThat(thrown.getMessage()).isEqualTo("need more stock");
        
    }


    private Member createMember() {
        Member member = Member.builder()
                .name("kimsiyong95")
                .address(new Address("대전", "중리동", "123-123"))
                .build();

        memberRepository.save(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = Book.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity).build();

        itemRepository.save(book);
        return book;
    }
}