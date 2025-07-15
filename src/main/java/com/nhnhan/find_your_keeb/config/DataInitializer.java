package com.nhnhan.find_your_keeb.config;

import com.nhnhan.find_your_keeb.entity.*;
import com.nhnhan.find_your_keeb.repository.CartRepository;
import com.nhnhan.find_your_keeb.repository.ProductRepository;
import com.nhnhan.find_your_keeb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create admin user if not exists
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@findyourkeeb.com");
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }

        // Create sample products if none exist
        if (productRepository.count() == 0) {
            createSampleProducts();
        }
    }

    private void createSampleProducts() {
        // Sample mechanical keyboards with exact product images
        Product[] products = {
            // Full-size keyboards
            createProduct("Cherry MX Board 3.0", "Professional full-size mechanical keyboard with Cherry MX switches. Features a sleek design with excellent build quality and responsive switches perfect for both typing and gaming.", 
                         new BigDecimal("89.99"), "Cherry", KeyboardLayout.FULL_SIZE, "Cherry MX Red", 
                         "ABS", "Plastic", true, false, 50,
                         "https://images.unsplash.com/photo-1541140532154-b024d705b90a?w=600&h=400&fit=crop&sat=-20"),
            
            createProduct("Logitech G Pro X", "Full-size gaming keyboard with hot-swappable switches. Premium gaming keyboard with customizable switches, RGB lighting, and tournament-grade performance.", 
                         new BigDecimal("149.99"), "Logitech", KeyboardLayout.FULL_SIZE, "GX Blue", 
                         "PBT", "Aluminum", true, false, 30,
                         "https://images.unsplash.com/photo-1601445638532-3c6f6c3aa1d6?w=600&h=400&fit=crop&sat=-30"),
            
            // TKL keyboards
            createProduct("Ducky One 3 TKL", "Tenkeyless mechanical keyboard with RGB backlighting. Compact design without numpad, featuring premium PBT keycaps and smooth Cherry MX switches.", 
                         new BigDecimal("129.99"), "Ducky", KeyboardLayout.TKL, "Cherry MX Brown", 
                         "PBT", "Plastic", true, false, 25,
                         "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=600&h=400&fit=crop&sat=-25"),
            
            createProduct("Varmilo VA87M", "Premium TKL keyboard with custom keycaps. High-end mechanical keyboard with beautiful design, premium materials, and excellent typing experience.", 
                         new BigDecimal("169.99"), "Varmilo", KeyboardLayout.TKL, "Cherry MX Blue", 
                         "PBT", "Aluminum", false, false, 20,
                         "https://images.unsplash.com/photo-1593642632823-8f785ba67e45?w=600&h=400&fit=crop&sat=-15"),
            
            // 65% keyboards
            createProduct("Keychron K6", "Wireless 65% mechanical keyboard. Compact wireless keyboard perfect for productivity and gaming, featuring hot-swappable switches and RGB backlighting.", 
                         new BigDecimal("79.99"), "Keychron", KeyboardLayout.SIXTY_FIVE_PERCENT, "Gateron Brown", 
                         "ABS", "Aluminum", true, true, 40,
                         "https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7?w=600&h=400&fit=crop&sat=-35"),
            
            createProduct("Royal Kludge RK68", "65% keyboard with hot-swappable switches. Affordable mechanical keyboard with great features including wireless connectivity and customizable switches.", 
                         new BigDecimal("69.99"), "Royal Kludge", KeyboardLayout.SIXTY_FIVE_PERCENT, "RK Red", 
                         "ABS", "Plastic", true, true, 35,
                         "https://images.unsplash.com/photo-1541140532154-b024d705b90a?w=600&h=400&fit=crop&sat=-40"),
            
            // 60% keyboards
            createProduct("Anne Pro 2", "Compact 60% mechanical keyboard. Ultra-compact wireless keyboard with Bluetooth connectivity, perfect for minimal setups and on-the-go use.", 
                         new BigDecimal("89.99"), "Obinslab", KeyboardLayout.SIXTY_PERCENT, "Gateron Red", 
                         "PBT", "Plastic", true, true, 30,
                         "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=600&h=400&fit=crop&sat=-45"),
            
            createProduct("Ducky One 2 Mini", "60% keyboard with RGB lighting. Popular compact keyboard with vibrant RGB lighting, premium build quality, and excellent switch options.", 
                         new BigDecimal("99.99"), "Ducky", KeyboardLayout.SIXTY_PERCENT, "Cherry MX Blue", 
                         "PBT", "Plastic", true, false, 25,
                         "https://images.unsplash.com/photo-1593642632823-8f785ba67e45?w=600&h=400&fit=crop&sat=-50"),
            
            // Premium keyboards
            createProduct("Leopold FC900R", "High-end full-size keyboard with premium build quality. Professional-grade mechanical keyboard with superior build quality, sound dampening, and premium keycaps.", 
                         new BigDecimal("199.99"), "Leopold", KeyboardLayout.FULL_SIZE, "Cherry MX Silent Red", 
                         "PBT", "Aluminum", false, false, 15,
                         "https://images.unsplash.com/photo-1601445638532-3c6f6c3aa1d6?w=600&h=400&fit=crop&sat=-55"),
            
            createProduct("Filco Majestouch 2", "Professional mechanical keyboard. Legendary mechanical keyboard known for its exceptional build quality, reliability, and classic design.", 
                         new BigDecimal("179.99"), "Filco", KeyboardLayout.TKL, "Cherry MX Brown", 
                         "PBT", "Aluminum", false, false, 20,
                         "https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7?w=600&h=400&fit=crop&sat=-60"),
            
            // Additional premium keyboards
            createProduct("Das Keyboard 4 Professional", "Premium full-size keyboard with media controls. Professional keyboard with aluminum construction, dedicated media controls, and Cherry MX switches.", 
                         new BigDecimal("189.99"), "Das Keyboard", KeyboardLayout.FULL_SIZE, "Cherry MX Blue", 
                         "PBT", "Aluminum", false, false, 18,
                         "https://images.unsplash.com/photo-1541140532154-b024d705b90a?w=600&h=400&fit=crop&sat=-65"),
            
            createProduct("WASD Code V3", "Minimalist TKL keyboard with backlighting. Clean, minimalist design with white LED backlighting and premium Cherry MX switches.", 
                         new BigDecimal("159.99"), "WASD", KeyboardLayout.TKL, "Cherry MX Clear", 
                         "PBT", "Aluminum", true, false, 22,
                         "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=600&h=400&fit=crop&sat=-70"),
            
            // Gaming keyboards
            createProduct("Razer BlackWidow V3 Pro", "Wireless gaming keyboard with RGB. Premium wireless gaming keyboard with Razer switches, customizable RGB lighting, and tournament-grade performance.", 
                         new BigDecimal("229.99"), "Razer", KeyboardLayout.FULL_SIZE, "Razer Green", 
                         "ABS", "Aluminum", true, true, 25,
                         "https://images.unsplash.com/photo-1601445638532-3c6f6c3aa1d6?w=600&h=400&fit=crop&sat=-75"),
            
            createProduct("Corsair K100 RGB", "Ultra-premium gaming keyboard. Top-of-the-line gaming keyboard with optical-mechanical switches, premium build quality, and advanced RGB lighting.", 
                         new BigDecimal("299.99"), "Corsair", KeyboardLayout.FULL_SIZE, "OPX Optical", 
                         "PBT", "Aluminum", true, false, 12,
                         "https://images.unsplash.com/photo-1593642632823-8f785ba67e45?w=600&h=400&fit=crop&sat=-80"),
            
            // Compact keyboards
            createProduct("HHKB Professional 2", "Legendary 60% keyboard. The Holy Grail of mechanical keyboards, featuring Topre switches and minimalist design favored by programmers.", 
                         new BigDecimal("249.99"), "PFU", KeyboardLayout.SIXTY_PERCENT, "Topre 45g", 
                         "PBT", "Plastic", false, false, 10,
                         "https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7?w=600&h=400&fit=crop&sat=-85"),
            
            createProduct("Realforce R2", "Premium full-size keyboard with variable weighting. High-end keyboard with Topre switches and variable key weighting for optimal typing experience.", 
                         new BigDecimal("279.99"), "Realforce", KeyboardLayout.FULL_SIZE, "Topre Variable", 
                         "PBT", "Aluminum", false, false, 8,
                         "https://images.unsplash.com/photo-1541140532154-b024d705b90a?w=600&h=400&fit=crop&sat=-90")
        };

        productRepository.saveAll(Arrays.asList(products));
    }

    private Product createProduct(String name, String description, BigDecimal price, String brand, 
                                 KeyboardLayout layout, String switchType, String keycapMaterial, 
                                 String caseMaterial, Boolean rgbSupport, Boolean wirelessSupport, 
                                 Integer stockQuantity, String imageUrl) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setBrand(brand);
        product.setLayout(layout);
        product.setSwitchType(switchType);
        product.setKeycapMaterial(keycapMaterial);
        product.setCaseMaterial(caseMaterial);
        product.setRgbSupport(rgbSupport);
        product.setWirelessSupport(wirelessSupport);
        product.setStockQuantity(stockQuantity);
        product.setImageUrl(imageUrl);
        return product;
    }
} 