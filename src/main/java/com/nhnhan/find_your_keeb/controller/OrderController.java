package com.nhnhan.find_your_keeb.controller;

import com.nhnhan.find_your_keeb.dto.CheckoutRequest;
import com.nhnhan.find_your_keeb.entity.Order;
import com.nhnhan.find_your_keeb.entity.OrderStatus;
import com.nhnhan.find_your_keeb.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Orders", description = "Order management and checkout endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "Get user's orders", description = "Retrieve all orders for the current user with pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully",
                content = @Content(schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "401", description = "User not authenticated")
    })
    public ResponseEntity<Page<Order>> getUserOrders(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        Long userId = getCurrentUserId();
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderService.getUserOrders(userId, pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Retrieve a specific order by its ID (user can only access their own orders)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order found",
                content = @Content(schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "401", description = "User not authenticated"),
        @ApiResponse(responseCode = "403", description = "Access denied - Order belongs to another user"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<Order> getOrderById(
            @Parameter(description = "Order ID") @PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        // Check if the order belongs to the current user
        Long userId = getCurrentUserId();
        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }
        return ResponseEntity.ok(order);
    }

    @PostMapping("/checkout")
    @Operation(summary = "Checkout cart", description = "Process checkout of the user's shopping cart and create an order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Checkout successful - Order created",
                content = @Content(schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "400", description = "Invalid checkout data"),
        @ApiResponse(responseCode = "401", description = "User not authenticated"),
        @ApiResponse(responseCode = "404", description = "Cart is empty or product not found"),
        @ApiResponse(responseCode = "409", description = "Insufficient stock")
    })
    public ResponseEntity<Order> checkout(@Valid @RequestBody CheckoutRequest request) {
        Long userId = getCurrentUserId();
        Order order = orderService.checkout(userId, request);
        return ResponseEntity.ok(order);
    }

    // Admin endpoints
    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all orders (Admin)", description = "Retrieve all orders in the system (Admin only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully",
                content = @Content(schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin role required")
    })
    public ResponseEntity<Page<Order>> getAllOrders(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get order by ID (Admin)", description = "Retrieve any order by its ID (Admin only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order found",
                content = @Content(schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin role required"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<Order> getOrderByIdAdmin(
            @Parameter(description = "Order ID") @PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update order status (Admin)", description = "Update the status of an order (Admin only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order status updated successfully",
                content = @Content(schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "400", description = "Invalid status"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin role required"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<Order> updateOrderStatus(
            @Parameter(description = "Order ID") @PathVariable Long id,
            @Parameter(description = "New order status") @RequestBody OrderStatusUpdateRequest request) {
        Order updatedOrder = orderService.updateOrderStatus(id, request.getStatus());
        return ResponseEntity.ok(updatedOrder);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof com.nhnhan.find_your_keeb.entity.User) {
            return ((com.nhnhan.find_your_keeb.entity.User) authentication.getPrincipal()).getId();
        }
        throw new RuntimeException("User not authenticated");
    }

    // DTO for order status update
    public static class OrderStatusUpdateRequest {
        private OrderStatus status;

        public OrderStatus getStatus() {
            return status;
        }

        public void setStatus(OrderStatus status) {
            this.status = status;
        }
    }
} 