package ksy.webproj.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ksy.webproj.domain.Order;
import ksy.webproj.domain.QMember;
import ksy.webproj.domain.QOrder;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class OrderRepositoryCustomImpl extends QuerydslRepositorySupport implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QOrder qOrder;
    private final QMember qMember;

    public OrderRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        super(Order.class);
        this.queryFactory = queryFactory;
        this.qMember = QMember.member;
        this.qOrder = QOrder.order;
    }


    @Override
    public List<Order> findAll(OrderSearch orderSearch) {
        return queryFactory
                .select(qOrder)
                .from(qOrder)
                .join(qOrder.member, qMember)
                .where(
                        eqOrderStatus(orderSearch),
                        likeMemberName(orderSearch)
                )
                .fetch();
    }



    public BooleanExpression likeMemberName(OrderSearch orderSearch){
        return StringUtils
                .isNullOrEmpty(orderSearch.getMemberName()) ? null : qOrder.member.name.like(orderSearch.getMemberName());
    }

    public BooleanExpression eqOrderStatus(OrderSearch orderSearch){
        return orderSearch.getOrderStatus() != null ? qOrder.status.eq(orderSearch.getOrderStatus()) : null;
    }

}
