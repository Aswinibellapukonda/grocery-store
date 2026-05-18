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
            categoryItems.put("Fruits", Arrays.asList("Mangoes", "Watermelon", "Pineapple", "Grapes", "Strawberries", "Apple", "Banana", "Orange", "Papaya", "Kiwi", "Pomegranate", "Guava", "Cherry", "Peach", "Pear"));
            categoryItems.put("Vegetables", Arrays.asList("Onions", "Carrots", "Broccoli", "Cauliflower", "Capsicum", "Potato", "Tomato", "Cabbage", "Spinach", "Garlic", "Ginger", "Cucumber", "Green Chili", "Eggplant", "Lady Finger"));
            categoryItems.put("Dairy", Arrays.asList("Curd Cup", "Butter Packet", "Paneer", "Yogurt", "Fresh Cream", "Milk", "Cheese", "Ghee", "Condensed Milk", "Buttermilk", "Flavored Milk", "Margarine", "Cheddar", "Mozzarella", "Ice Cream"));
            categoryItems.put("Snacks", Arrays.asList("Biscuits", "Nachos", "Chocolate Cookies", "Popcorn", "Salted Peanuts", "Potato Chips", "Pretzels", "Mixture", "Bhujia", "Chocolate Bar", "Wafers", "Crackers", "Granola Bar", "Makhana", "Puffs"));
            categoryItems.put("Beverages", Arrays.asList("Soft Drinks", "Cold Coffee", "Green Tea", "Energy Drink", "Mineral Water", "Tea", "Coffee", "Apple Juice", "Orange Juice", "Cola", "Lemonade", "Soda", "Coconut Water", "Milkshake", "Tomato Juice"));
            categoryItems.put("Bakery", Arrays.asList("White Bread", "Cupcakes", "Donuts", "Brown Bread", "Croissant", "Muffins", "Buns", "Bagel", "Pound Cake", "Pizza Base", "Burger Buns", "Toast", "Garlic Bread", "Pastry", "Baguette"));
            categoryItems.put("Personal Care", Arrays.asList("Shampoo", "Conditioner", "Soap", "Body Wash", "Toothpaste", "Toothbrush", "Face Wash", "Moisturizer", "Deodorant", "Hair Oil", "Shaving Cream", "Razor", "Hand Wash", "Lotion", "Sunscreen"));
            categoryItems.put("Household Essentials", Arrays.asList("Dish Wash Liquid", "Detergent Powder", "Garbage Bags", "Floor Cleaner", "Tissue Rolls", "Toilet Cleaner", "Room Freshener", "Sponge", "Mop", "Broom", "Glass Cleaner", "Insect Repellent", "Matches", "Batteries", "Fabric Softener"));
            categoryItems.put("Frozen Foods", Arrays.asList("Frozen Fries", "Frozen Pizza", "Nuggets", "Ice Cream Tub", "Frozen Peas", "Frozen Corn", "Frozen Desserts", "Frozen Paratha", "Frozen Samosa", "Frozen Mixed Veggies", "Frozen Waffles", "Frozen Paneer", "Frozen Berries", "Frozen Meat", "Frozen Fish"));
            categoryItems.put("Rice & Grains", Arrays.asList("Basmati Rice", "Wheat Flour", "Oats", "Toor Dal", "Quinoa", "Brown Rice", "Moong Dal", "Chana Dal", "Urad Dal", "Rajma", "Chickpeas", "Millet", "Cornmeal", "Barley", "Poha"));

            Map<String, String> imgMap = new HashMap<>();
            imgMap.put("Mangoes", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Mangos_-_single_and_halved.jpg/500px-Mangos_-_single_and_halved.jpg");
            imgMap.put("Watermelon", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Taiwan_2009_Tainan_City_Organic_Farm_Watermelon_FRD_7962.jpg/500px-Taiwan_2009_Tainan_City_Organic_Farm_Watermelon_FRD_7962.jpg");
            imgMap.put("Pineapple", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/%E0%B4%95%E0%B5%88%E0%B4%A4%E0%B4%9A%E0%B5%8D%E0%B4%9A%E0%B4%95%E0%B5%8D%E0%B4%95.jpg/500px-%E0%B4%95%E0%B5%88%E0%B4%A4%E0%B4%9A%E0%B5%8D%E0%B4%9A%E0%B4%95%E0%B5%8D%E0%B4%95.jpg");
            imgMap.put("Grapes", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Grapes%2C_Rostov-on-Don%2C_Russia.jpg/500px-Grapes%2C_Rostov-on-Don%2C_Russia.jpg");
            imgMap.put("Strawberries", "https://placehold.co/400x400/e2e8f0/1e293b?text=Strawberries");
            imgMap.put("Apple", "https://placehold.co/400x400/e2e8f0/1e293b?text=Apple");
            imgMap.put("Banana", "https://placehold.co/400x400/e2e8f0/1e293b?text=Banana");
            imgMap.put("Orange", "https://placehold.co/400x400/e2e8f0/1e293b?text=Orange");
            imgMap.put("Papaya", "https://placehold.co/400x400/e2e8f0/1e293b?text=Papaya");
            imgMap.put("Kiwi", "https://placehold.co/400x400/e2e8f0/1e293b?text=Kiwi");
            imgMap.put("Pomegranate", "https://placehold.co/400x400/e2e8f0/1e293b?text=Pomegranate");
            imgMap.put("Guava", "https://placehold.co/400x400/e2e8f0/1e293b?text=Guava");
            imgMap.put("Cherry", "https://placehold.co/400x400/e2e8f0/1e293b?text=Cherry");
            imgMap.put("Peach", "https://placehold.co/400x400/e2e8f0/1e293b?text=Peach");
            imgMap.put("Pear", "https://placehold.co/400x400/e2e8f0/1e293b?text=Pear");
            imgMap.put("Onions", "https://placehold.co/400x400/e2e8f0/1e293b?text=Onions");
            imgMap.put("Carrots", "https://placehold.co/400x400/e2e8f0/1e293b?text=Carrots");
            imgMap.put("Broccoli", "https://placehold.co/400x400/e2e8f0/1e293b?text=Broccoli");
            imgMap.put("Cauliflower", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Chou-fleur_02.jpg/500px-Chou-fleur_02.jpg");
            imgMap.put("Capsicum", "https://placehold.co/400x400/e2e8f0/1e293b?text=Capsicum");
            imgMap.put("Potato", "https://placehold.co/400x400/e2e8f0/1e293b?text=Potato");
            imgMap.put("Tomato", "https://placehold.co/400x400/e2e8f0/1e293b?text=Tomato");
            imgMap.put("Cabbage", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6f/Cabbage_and_cross_section_on_white.jpg/500px-Cabbage_and_cross_section_on_white.jpg");
            imgMap.put("Spinach", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/37/Spinacia_oleracea_Spinazie_bloeiend.jpg/500px-Spinacia_oleracea_Spinazie_bloeiend.jpg");
            imgMap.put("Garlic", "https://upload.wikimedia.org/wikipedia/commons/3/39/Allium_sativum_Woodwill_1793.jpg");
            imgMap.put("Ginger", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/18/Koeh-146-no_text.jpg/500px-Koeh-146-no_text.jpg");
            imgMap.put("Cucumber", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/96/ARS_cucumber.jpg/500px-ARS_cucumber.jpg");
            imgMap.put("Green Chili", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Madame_Jeanette_and_other_chillies.jpg/500px-Madame_Jeanette_and_other_chillies.jpg");
            imgMap.put("Eggplant", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/76/Solanum_melongena_24_08_2012_%281%29.JPG/500px-Solanum_melongena_24_08_2012_%281%29.JPG");
            imgMap.put("Lady Finger", "https://placehold.co/400x400/e2e8f0/1e293b?text=Lady%20Finger");
            imgMap.put("Curd", "https://placehold.co/400x400/e2e8f0/1e293b?text=Curd");
            imgMap.put("Butter", "https://placehold.co/400x400/e2e8f0/1e293b?text=Butter");
            imgMap.put("Paneer", "https://placehold.co/400x400/e2e8f0/1e293b?text=Paneer");
            imgMap.put("Yogurt", "https://placehold.co/400x400/e2e8f0/1e293b?text=Yogurt");
            imgMap.put("Fresh Cream", "https://placehold.co/400x400/e2e8f0/1e293b?text=Fresh%20Cream");
            imgMap.put("Milk", "https://placehold.co/400x400/e2e8f0/1e293b?text=Milk");
            imgMap.put("Cheese", "https://placehold.co/400x400/e2e8f0/1e293b?text=Cheese");
            imgMap.put("Ghee", "https://placehold.co/400x400/e2e8f0/1e293b?text=Ghee");
            imgMap.put("Condensed Milk", "https://placehold.co/400x400/e2e8f0/1e293b?text=Condensed%20Milk");
            imgMap.put("Buttermilk", "https://placehold.co/400x400/e2e8f0/1e293b?text=Buttermilk");
            imgMap.put("Flavored Milk", "https://placehold.co/400x400/e2e8f0/1e293b?text=Flavored%20Milk");
            imgMap.put("Margarine", "https://placehold.co/400x400/e2e8f0/1e293b?text=Margarine");
            imgMap.put("Cheddar", "https://placehold.co/400x400/e2e8f0/1e293b?text=Cheddar");
            imgMap.put("Mozzarella", "https://placehold.co/400x400/e2e8f0/1e293b?text=Mozzarella");
            imgMap.put("Ice Cream", "https://placehold.co/400x400/e2e8f0/1e293b?text=Ice%20Cream");
            imgMap.put("Biscuits", "https://placehold.co/400x400/e2e8f0/1e293b?text=Biscuits");
            imgMap.put("Nachos", "https://placehold.co/400x400/e2e8f0/1e293b?text=Nachos");
            imgMap.put("Chocolate Cookies", "https://placehold.co/400x400/e2e8f0/1e293b?text=Chocolate%20Cookies");
            imgMap.put("Popcorn", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d6/Popcorn_-_Studio_-_2011.jpg/500px-Popcorn_-_Studio_-_2011.jpg");
            imgMap.put("Salted Peanuts", "https://placehold.co/400x400/e2e8f0/1e293b?text=Salted%20Peanuts");
            imgMap.put("Potato Chips", "https://placehold.co/400x400/e2e8f0/1e293b?text=Potato%20Chips");
            imgMap.put("Pretzels", "https://placehold.co/400x400/e2e8f0/1e293b?text=Pretzels");
            imgMap.put("Mixture", "https://placehold.co/400x400/e2e8f0/1e293b?text=Mixture");
            imgMap.put("Bhujia", "https://placehold.co/400x400/e2e8f0/1e293b?text=Bhujia");
            imgMap.put("Chocolate Bar", "https://placehold.co/400x400/e2e8f0/1e293b?text=Chocolate%20Bar");
            imgMap.put("Wafers", "https://placehold.co/400x400/e2e8f0/1e293b?text=Wafers");
            imgMap.put("Crackers", "https://placehold.co/400x400/e2e8f0/1e293b?text=Crackers");
            imgMap.put("Granola Bar", "https://placehold.co/400x400/e2e8f0/1e293b?text=Granola%20Bar");
            imgMap.put("Makhana", "https://placehold.co/400x400/e2e8f0/1e293b?text=Makhana");
            imgMap.put("Puffs", "https://placehold.co/400x400/e2e8f0/1e293b?text=Puffs");
            imgMap.put("Soft Drinks", "https://placehold.co/400x400/e2e8f0/1e293b?text=Soft%20Drinks");
            imgMap.put("Cold Coffee", "https://placehold.co/400x400/e2e8f0/1e293b?text=Cold%20Coffee");
            imgMap.put("Green Tea", "https://placehold.co/400x400/e2e8f0/1e293b?text=Green%20Tea");
            imgMap.put("Energy Drink", "https://placehold.co/400x400/e2e8f0/1e293b?text=Energy%20Drink");
            imgMap.put("Mineral Water", "https://placehold.co/400x400/e2e8f0/1e293b?text=Mineral%20Water");
            imgMap.put("Tea", "https://placehold.co/400x400/e2e8f0/1e293b?text=Tea");
            imgMap.put("Coffee", "https://placehold.co/400x400/e2e8f0/1e293b?text=Coffee");
            imgMap.put("Apple Juice", "https://placehold.co/400x400/e2e8f0/1e293b?text=Apple%20Juice");
            imgMap.put("Orange Juice", "https://placehold.co/400x400/e2e8f0/1e293b?text=Orange%20Juice");
            imgMap.put("Cola", "https://placehold.co/400x400/e2e8f0/1e293b?text=Cola");
            imgMap.put("Lemonade", "https://placehold.co/400x400/e2e8f0/1e293b?text=Lemonade");
            imgMap.put("Soda", "https://placehold.co/400x400/e2e8f0/1e293b?text=Soda");
            imgMap.put("Coconut Water", "https://placehold.co/400x400/e2e8f0/1e293b?text=Coconut%20Water");
            imgMap.put("Milkshake", "https://placehold.co/400x400/e2e8f0/1e293b?text=Milkshake");
            imgMap.put("Tomato Juice", "https://placehold.co/400x400/e2e8f0/1e293b?text=Tomato%20Juice");
            imgMap.put("White Bread", "https://placehold.co/400x400/e2e8f0/1e293b?text=White%20Bread");
            imgMap.put("Cupcakes", "https://placehold.co/400x400/e2e8f0/1e293b?text=Cupcakes");
            imgMap.put("Donuts", "https://placehold.co/400x400/e2e8f0/1e293b?text=Donuts");
            imgMap.put("Brown Bread", "https://placehold.co/400x400/e2e8f0/1e293b?text=Brown%20Bread");
            imgMap.put("Croissant", "https://placehold.co/400x400/e2e8f0/1e293b?text=Croissant");
            imgMap.put("Muffins", "https://placehold.co/400x400/e2e8f0/1e293b?text=Muffins");
            imgMap.put("Buns", "https://placehold.co/400x400/e2e8f0/1e293b?text=Buns");
            imgMap.put("Bagel", "https://placehold.co/400x400/e2e8f0/1e293b?text=Bagel");
            imgMap.put("Pound Cake", "https://placehold.co/400x400/e2e8f0/1e293b?text=Pound%20Cake");
            imgMap.put("Pizza Base", "https://placehold.co/400x400/e2e8f0/1e293b?text=Pizza%20Base");
            imgMap.put("Burger Buns", "https://placehold.co/400x400/e2e8f0/1e293b?text=Burger%20Buns");
            imgMap.put("Toast", "https://placehold.co/400x400/e2e8f0/1e293b?text=Toast");
            imgMap.put("Garlic Bread", "https://placehold.co/400x400/e2e8f0/1e293b?text=Garlic%20Bread");
            imgMap.put("Pastry", "https://placehold.co/400x400/e2e8f0/1e293b?text=Pastry");
            imgMap.put("Baguette", "https://placehold.co/400x400/e2e8f0/1e293b?text=Baguette");
            imgMap.put("Shampoo", "https://placehold.co/400x400/e2e8f0/1e293b?text=Shampoo");
            imgMap.put("Conditioner", "https://placehold.co/400x400/e2e8f0/1e293b?text=Conditioner");
            imgMap.put("Soap", "https://placehold.co/400x400/e2e8f0/1e293b?text=Soap");
            imgMap.put("Body Wash", "https://placehold.co/400x400/e2e8f0/1e293b?text=Body%20Wash");
            imgMap.put("Toothpaste", "https://placehold.co/400x400/e2e8f0/1e293b?text=Toothpaste");
            imgMap.put("Toothbrush", "https://placehold.co/400x400/e2e8f0/1e293b?text=Toothbrush");
            imgMap.put("Face Wash", "https://placehold.co/400x400/e2e8f0/1e293b?text=Face%20Wash");
            imgMap.put("Moisturizer", "https://placehold.co/400x400/e2e8f0/1e293b?text=Moisturizer");
            imgMap.put("Deodorant", "https://placehold.co/400x400/e2e8f0/1e293b?text=Deodorant");
            imgMap.put("Hair Oil", "https://placehold.co/400x400/e2e8f0/1e293b?text=Hair%20Oil");
            imgMap.put("Shaving Cream", "https://placehold.co/400x400/e2e8f0/1e293b?text=Shaving%20Cream");
            imgMap.put("Razor", "https://placehold.co/400x400/e2e8f0/1e293b?text=Razor");
            imgMap.put("Hand Wash", "https://placehold.co/400x400/e2e8f0/1e293b?text=Hand%20Wash");
            imgMap.put("Lotion", "https://placehold.co/400x400/e2e8f0/1e293b?text=Lotion");
            imgMap.put("Sunscreen", "https://placehold.co/400x400/e2e8f0/1e293b?text=Sunscreen");
            imgMap.put("Dish Wash Liquid", "https://placehold.co/400x400/e2e8f0/1e293b?text=Dish%20Wash%20Liquid");
            imgMap.put("Detergent Powder", "https://placehold.co/400x400/e2e8f0/1e293b?text=Detergent%20Powder");
            imgMap.put("Garbage Bags", "https://placehold.co/400x400/e2e8f0/1e293b?text=Garbage%20Bags");
            imgMap.put("Floor Cleaner", "https://placehold.co/400x400/e2e8f0/1e293b?text=Floor%20Cleaner");
            imgMap.put("Tissue Rolls", "https://placehold.co/400x400/e2e8f0/1e293b?text=Tissue%20Rolls");
            imgMap.put("Toilet Cleaner", "https://placehold.co/400x400/e2e8f0/1e293b?text=Toilet%20Cleaner");
            imgMap.put("Room Freshener", "https://placehold.co/400x400/e2e8f0/1e293b?text=Room%20Freshener");
            imgMap.put("Sponge", "https://placehold.co/400x400/e2e8f0/1e293b?text=Sponge");
            imgMap.put("Mop", "https://placehold.co/400x400/e2e8f0/1e293b?text=Mop");
            imgMap.put("Broom", "https://placehold.co/400x400/e2e8f0/1e293b?text=Broom");
            imgMap.put("Glass Cleaner", "https://placehold.co/400x400/e2e8f0/1e293b?text=Glass%20Cleaner");
            imgMap.put("Insect Repellent", "https://placehold.co/400x400/e2e8f0/1e293b?text=Insect%20Repellent");
            imgMap.put("Matches", "https://placehold.co/400x400/e2e8f0/1e293b?text=Matches");
            imgMap.put("Batteries", "https://placehold.co/400x400/e2e8f0/1e293b?text=Batteries");
            imgMap.put("Fabric Softener", "https://placehold.co/400x400/e2e8f0/1e293b?text=Fabric%20Softener");
            imgMap.put("Frozen Fries", "https://placehold.co/400x400/e2e8f0/1e293b?text=Frozen%20Fries");
            imgMap.put("Frozen Pizza", "https://placehold.co/400x400/e2e8f0/1e293b?text=Frozen%20Pizza");
            imgMap.put("Nuggets", "https://placehold.co/400x400/e2e8f0/1e293b?text=Nuggets");
            imgMap.put("Ice Cream Tub", "https://placehold.co/400x400/e2e8f0/1e293b?text=Ice%20Cream%20Tub");
            imgMap.put("Frozen Peas", "https://placehold.co/400x400/e2e8f0/1e293b?text=Frozen%20Peas");
            imgMap.put("Frozen Corn", "https://placehold.co/400x400/e2e8f0/1e293b?text=Frozen%20Corn");
            imgMap.put("Frozen Desserts", "https://placehold.co/400x400/e2e8f0/1e293b?text=Frozen%20Desserts");
            imgMap.put("Frozen Paratha", "https://placehold.co/400x400/e2e8f0/1e293b?text=Frozen%20Paratha");
            imgMap.put("Frozen Samosa", "https://placehold.co/400x400/e2e8f0/1e293b?text=Frozen%20Samosa");
            imgMap.put("Frozen Mixed Veggies", "https://placehold.co/400x400/e2e8f0/1e293b?text=Frozen%20Mixed%20Veggies");
            imgMap.put("Frozen Waffles", "https://placehold.co/400x400/e2e8f0/1e293b?text=Frozen%20Waffles");
            imgMap.put("Frozen Paneer", "https://placehold.co/400x400/e2e8f0/1e293b?text=Frozen%20Paneer");
            imgMap.put("Frozen Berries", "https://placehold.co/400x400/e2e8f0/1e293b?text=Frozen%20Berries");
            imgMap.put("Frozen Meat", "https://placehold.co/400x400/e2e8f0/1e293b?text=Frozen%20Meat");
            imgMap.put("Frozen Fish", "https://placehold.co/400x400/e2e8f0/1e293b?text=Frozen%20Fish");
            imgMap.put("Basmati Rice", "https://placehold.co/400x400/e2e8f0/1e293b?text=Basmati%20Rice");
            imgMap.put("Wheat Flour", "https://placehold.co/400x400/e2e8f0/1e293b?text=Wheat%20Flour");
            imgMap.put("Oats", "https://placehold.co/400x400/e2e8f0/1e293b?text=Oats");
            imgMap.put("Toor Dal", "https://placehold.co/400x400/e2e8f0/1e293b?text=Toor%20Dal");
            imgMap.put("Quinoa", "https://placehold.co/400x400/e2e8f0/1e293b?text=Quinoa");
            imgMap.put("Brown Rice", "https://placehold.co/400x400/e2e8f0/1e293b?text=Brown%20Rice");
            imgMap.put("Moong Dal", "https://placehold.co/400x400/e2e8f0/1e293b?text=Moong%20Dal");
            imgMap.put("Chana Dal", "https://placehold.co/400x400/e2e8f0/1e293b?text=Chana%20Dal");
            imgMap.put("Urad Dal", "https://placehold.co/400x400/e2e8f0/1e293b?text=Urad%20Dal");
            imgMap.put("Rajma", "https://placehold.co/400x400/e2e8f0/1e293b?text=Rajma");
            imgMap.put("Chickpeas", "https://placehold.co/400x400/e2e8f0/1e293b?text=Chickpeas");
            imgMap.put("Millet", "https://placehold.co/400x400/e2e8f0/1e293b?text=Millet");
            imgMap.put("Cornmeal", "https://placehold.co/400x400/e2e8f0/1e293b?text=Cornmeal");
            imgMap.put("Barley", "https://placehold.co/400x400/e2e8f0/1e293b?text=Barley");
            imgMap.put("Poha", "https://placehold.co/400x400/e2e8f0/1e293b?text=Poha");
            List<Product> products = new ArrayList<>();
            for (Map.Entry<String, List<String>> entry : categoryItems.entrySet()) {
                String category = entry.getKey();
                for (String itemName : entry.getValue()) {
                    double price = 20.0 + (Math.random() * 200);
                    int stock = (int) (Math.random() * 100) + 10;
                    String imageUrl = imgMap.getOrDefault(itemName, "https://placehold.co/400x400/e2e8f0/1e293b?text=" + itemName.replace(" ", "%20"));
                    
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
