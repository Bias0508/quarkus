package org.acme.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class WishlistService {
    private Map<String, Wishlist> wishlists;
    private ObjectMapper objectMapper;
    private Path jsonFilePath;

    public WishlistService() {
        wishlists = new HashMap<>();
        objectMapper = new ObjectMapper();
        jsonFilePath = Paths.get("C:\\Users\\tobia\\Desktop\\FH\\quarkus\\src\\main\\resources\\wishlist.json");

        // loads wishlist at the start
        loadWishlistsFromFile();
    }

    public void addToWishlist(String customerId, UUID eventId) {
        Wishlist wishlist = wishlists.getOrDefault(customerId, new Wishlist(customerId));
        wishlist.addEvent(eventId);
        wishlists.put(customerId, wishlist);
        saveWishlistsToFile();
    }

    public void removeFromWishlist(String customerId, UUID eventId) {
        Wishlist wishlist = wishlists.get(customerId);
        if (wishlist != null) {
            wishlist.removeEvent(eventId);
            saveWishlistsToFile();
        }
    }

    public Wishlist getWishlist(String customerId) {
        return wishlists.getOrDefault(customerId, new Wishlist(customerId));
    }

    public List<UUID> getAllEventsInWishlist(String customerId) {
        Wishlist wishlist = wishlists.get(customerId);
        if (wishlist != null) {
            return wishlist.getEvents();
        }
        return List.of();
    }

    private void loadWishlistsFromFile() {
        try {
            if (Files.exists(jsonFilePath)) {
                String jsonContent = Files.readString(jsonFilePath);
                TypeReference<HashMap<String, Wishlist>> typeReference = new TypeReference<>() {};
                wishlists = objectMapper.readValue(jsonContent, typeReference);
            } else {
                wishlists = new HashMap<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveWishlistsToFile() {
        try {
            String jsonContent = objectMapper.writeValueAsString(wishlists);
            Files.writeString(jsonFilePath, jsonContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
