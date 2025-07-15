package com.nhnhan.find_your_keeb.controller;

import com.nhnhan.find_your_keeb.dto.CartItemRequest;
import com.nhnhan.find_your_keeb.entity.Cart;
import com.nhnhan.find_your_keeb.service.CartService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Shopping Cart", description = "Shopping cart management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public class CartController {

    private final CartService cartService;

    @GetMapping
    @Operation(summary = "Get user's cart", description = "Retrieve the current user's shopping cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cart retrieved successfully",
                content = @Content(schema = @Schema(implementation = Cart.class))),
        @ApiResponse(responseCode = "401", description = "User not authenticated"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<Cart> getCart() {
        Long userId = getCurrentUserId();
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/items")
    @Operation(summary = "Add item to cart", description = "Add a product to the user's shopping cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item added to cart successfully",
                content = @Content(schema = @Schema(implementation = Cart.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "User not authenticated"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Cart> addItemToCart(@Valid @RequestBody CartItemRequest request) {
        Long userId = getCurrentUserId();
        Cart cart = cartService.addItemToCart(userId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/items/{productId}")
    @Operation(summary = "Update cart item quantity", description = "Update the quantity of an item in the cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cart item updated successfully",
                content = @Content(schema = @Schema(implementation = Cart.class))),
        @ApiResponse(responseCode = "400", description = "Invalid quantity"),
        @ApiResponse(responseCode = "401", description = "User not authenticated"),
        @ApiResponse(responseCode = "404", description = "Item not found in cart")
    })
    public ResponseEntity<Cart> updateCartItemQuantity(
            @Parameter(description = "Product ID") @PathVariable Long productId,
            @Parameter(description = "New quantity") @RequestParam Integer quantity) {
        Long userId = getCurrentUserId();
        Cart cart = cartService.updateCartItemQuantity(userId, productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/items/{productId}")
    @Operation(summary = "Remove item from cart", description = "Remove a product from the user's shopping cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item removed from cart successfully",
                content = @Content(schema = @Schema(implementation = Cart.class))),
        @ApiResponse(responseCode = "401", description = "User not authenticated"),
        @ApiResponse(responseCode = "404", description = "Item not found in cart")
    })
    public ResponseEntity<Cart> removeItemFromCart(
            @Parameter(description = "Product ID") @PathVariable Long productId) {
        Long userId = getCurrentUserId();
        Cart cart = cartService.removeItemFromCart(userId, productId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping
    @Operation(summary = "Clear cart", description = "Remove all items from the user's shopping cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cart cleared successfully",
                content = @Content(schema = @Schema(implementation = Cart.class))),
        @ApiResponse(responseCode = "401", description = "User not authenticated")
    })
    public ResponseEntity<Cart> clearCart() {
        Long userId = getCurrentUserId();
        Cart cart = cartService.clearCart(userId);
        return ResponseEntity.ok(cart);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof com.nhnhan.find_your_keeb.entity.User) {
            return ((com.nhnhan.find_your_keeb.entity.User) authentication.getPrincipal()).getId();
        }
        throw new RuntimeException("User not authenticated");
    }
} 