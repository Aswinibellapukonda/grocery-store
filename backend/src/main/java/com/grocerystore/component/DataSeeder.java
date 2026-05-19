package com.grocerystore.component;

import com.grocerystore.entity.*;
import com.grocerystore.repository.ProductRepository;
import com.grocerystore.repository.RoleRepository;
import com.grocerystore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataSeeder implements CommandLineRunner {
    @Autowired RoleRepository roleRepository;
    @Autowired UserRepository userRepository;
    @Autowired ProductRepository productRepository;
    @Autowired PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(null, ERole.ROLE_USER));
            roleRepository.save(new Role(null, ERole.ROLE_ADMIN));
        }

        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));

            User admin = new User("admin", "admin@grocery.com", encoder.encode("admin123"));
            admin.setRoles(new HashSet<>(Collections.singletonList(adminRole)));
            userRepository.save(admin);

            User user = new User("user", "user@grocery.com", encoder.encode("user123"));
            user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
            userRepository.save(user);
        }

        if (productRepository.count() == 0) {
            Map<String, List<String>> categoryItems = new LinkedHashMap<>();
            categoryItems.put("Fruits", Arrays.asList("Mangoes", "Watermelon", "Apple", "Banana", "Orange"));

categoryItems.put("Vegetables", Arrays.asList("Onions", "Carrots", "Potato", "Tomato", "Cabbage"));

categoryItems.put("Dairy", Arrays.asList("Milk", "Paneer", "Cheese", "Butter Packet", "Yogurt"));

categoryItems.put("Snacks", Arrays.asList("Biscuits", "Popcorn", "Potato Chips", "Chocolate Cookies", "Nachos"));

categoryItems.put("Beverages", Arrays.asList("Tea", "Coffee", "Apple Juice", "Orange Juice", "Coconut Water"));

categoryItems.put("Bakery", Arrays.asList("White Bread", "Donuts", "Brown Bread", "Croissant", "Muffins"));


            Map<String, String> imgMap = new HashMap<>();
        

imgMap.put("Mangoes", "https://upload.wikimedia.org/wikipedia/commons/7/74/Mangos_-_single_and_halved.jpg");
imgMap.put("Watermelon", "https://upload.wikimedia.org/wikipedia/commons/4/47/Taiwan_2009_Tainan_City_Organic_Farm_Watermelon_FRD_7962.jpg");
imgMap.put("Apple", "https://upload.wikimedia.org/wikipedia/commons/1/15/Red_Apple.jpg");
imgMap.put("Banana", "https://upload.wikimedia.org/wikipedia/commons/8/8a/Banana-Single.jpg");
imgMap.put("Orange", "https://upload.wikimedia.org/wikipedia/commons/c/c4/Orange-Fruit-Pieces.jpg");

imgMap.put("Onions", "https://upload.wikimedia.org/wikipedia/commons/2/25/Onion_on_White.JPG");
imgMap.put("Carrots", "https://placehold.co/400x400/f97316/ffffff?text=Carrots");
imgMap.put("Potato", "https://upload.wikimedia.org/wikipedia/commons/a/ab/Patates.jpg");
imgMap.put("Tomato", "https://upload.wikimedia.org/wikipedia/commons/8/89/Tomato_je.jpg");
imgMap.put("Cabbage", "https://upload.wikimedia.org/wikipedia/commons/6/6f/Cabbage_and_cross_section_on_white.jpg");

imgMap.put("Milk", "https://placehold.co/400x400/93c5fd/ffffff?text=Milk");
imgMap.put("Paneer", "https://placehold.co/400x400/fef3c7/111827?text=Paneer");
imgMap.put("Cheese", "https://placehold.co/400x400/facc15/111827?text=Cheese");
imgMap.put("Butter Packet", "https://placehold.co/400x400/fde68a/111827?text=Butter");
imgMap.put("Yogurt", "https://placehold.co/400x400/e0f2fe/111827?text=Yogurt");

imgMap.put("Biscuits", "https://placehold.co/400x400/d6a15d/ffffff?text=Biscuits");
imgMap.put("Potato Chips", "https://placehold.co/400x400/fbbf24/111827?text=Chips");
imgMap.put("Chocolate Cookies", "https://placehold.co/400x400/7c2d12/ffffff?text=Cookies");
imgMap.put("Nachos", "https://placehold.co/400x400/f59e0b/111827?text=Nachos");


imgMap.put("Coffee", "https://upload.wikimedia.org/wikipedia/commons/4/45/A_small_cup_of_coffee.JPG");
imgMap.put("Tea", "https://placehold.co/400x400/92400e/ffffff?text=Tea");
imgMap.put("Apple Juice", "https://placehold.co/400x400/f97316/ffffff?text=Apple+Juice");
imgMap.put("Orange Juice", "https://placehold.co/400x400/f97316/ffffff?text=Orange+Juice");
imgMap.put("Coconut Water", "https://placehold.co/400x400/ecfeff/111827?text=Coconut+Water");





imgMap.put("Donuts", "https://placehold.co/400x400/f3f4f6/111827?text=Donuts");
imgMap.put("White Bread", "https://placehold.co/400x400/fef3c7/111827?text=White+Bread");
imgMap.put("Brown Bread", "https://placehold.co/400x400/92400e/ffffff?text=Brown+Bread");
imgMap.put("Croissant", "https://placehold.co/400x400/d97706/ffffff?text=Croissant");
imgMap.put("Muffins", "https://placehold.co/400x400/b45309/ffffff?text=Muffins");
            List<Product> products = new ArrayList<>();
            for (Map.Entry<String, List<String>> entry : categoryItems.entrySet()) {
                String category = entry.getKey();
                for (String itemName : entry.getValue()) {
                    double price = 20.0 + (Math.random() * 200);
                    int stock = (int) (Math.random() * 100) + 10;
                    String imageUrl = imgMap.getOrDefault(itemName, "");
                    
                    products.add(new Product(
                        null, 
                        itemName, 
                        "Fresh and high quality " + itemName.toLowerCase() + " perfect for your daily needs.", 
                        category, 
                        Math.round(price * 100.0) / 100.0, 
                        stock, 
                        imageUrl
                    ));
                }
            }
            productRepository.saveAll(products);
        }
    }
}
