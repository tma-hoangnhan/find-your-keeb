package com.nhnhan.find_your_keeb.service;

import com.nhnhan.find_your_keeb.dto.CheckoutRequest;
import com.nhnhan.find_your_keeb.entity.*;
import com.nhnhan.find_your_keeb.repository.OrderRepository;
import com.nhnhan.find_your_keeb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.nhnhan.find_your_keeb.dto.AdminOrderResponse;
import com.nhnhan.find_your_keeb.dto.OrderItemResponse;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final ProductService productService;

    public Page<Order> getUserOrders(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable);
    }

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Page<Order> getOrdersByStatus(OrderStatus status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Transactional
    public Order checkout(Long userId, CheckoutRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Cart cart = cartService.getCartByUserId(userId);
        
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        
        // Create order
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setUser(user);
        order.setShippingAddress(request.getShippingAddress());
        order.setBillingAddress(request.getBillingAddress());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus(OrderStatus.PENDING);
        
        // Convert cart items to order items
        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();
            
            // Check stock availability
            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
            
            // Update product stock
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productService.updateProduct(product.getId(), convertToProductRequest(product));
            
            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            order.addItem(orderItem);
        }
        
        order.calculateTotal();
        Order savedOrder = orderRepository.save(order);
        
        // Clear cart after successful checkout
        cartService.clearCart(userId);
        
        return savedOrder;
    }

    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByCreatedAtBetween(startDate, endDate);
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private com.nhnhan.find_your_keeb.dto.ProductRequest convertToProductRequest(Product product) {
        com.nhnhan.find_your_keeb.dto.ProductRequest request = new com.nhnhan.find_your_keeb.dto.ProductRequest();
        request.setName(product.getName());
        request.setDescription(product.getDescription());
        request.setPrice(product.getPrice());
        request.setBrand(product.getBrand());
        request.setLayout(product.getLayout());
        request.setSwitchType(product.getSwitchType());
        request.setKeycapMaterial(product.getKeycapMaterial());
        request.setCaseMaterial(product.getCaseMaterial());
        request.setRgbSupport(product.getRgbSupport());
        request.setWirelessSupport(product.getWirelessSupport());
        request.setStockQuantity(product.getStockQuantity());
        request.setImageUrl(product.getImageUrl());
        return request;
    }

    public AdminOrderResponse toAdminOrderResponse(Order order) {
        AdminOrderResponse dto = new AdminOrderResponse();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setUsername(order.getUser().getUsername());
        dto.setFirstName(order.getUser().getFirstName());
        dto.setLastName(order.getUser().getLastName());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setBillingAddress(order.getBillingAddress());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setCreatedAt(order.getCreatedAt());
        // Map order items
        List<OrderItemResponse> itemDtos = order.getItems().stream().map(item -> {
            OrderItemResponse itemDto = new OrderItemResponse();
            itemDto.setId(item.getId());
            itemDto.setProductName(item.getProduct().getName());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setUnitPrice(item.getUnitPrice());
            return itemDto;
        }).toList();
        dto.setItems(itemDtos);
        return dto;
    }
} 