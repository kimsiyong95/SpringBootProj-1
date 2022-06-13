package ksy.webproj.repository;

import ksy.webproj.domain.Order;

import java.util.List;

public interface OrderRepositoryCustom {
    public List<Order> findAll(OrderSearch orderSearch);
}
