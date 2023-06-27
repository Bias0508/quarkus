package org.acme.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

@ApplicationScoped
public class WishlistService {
    private final Map<String, Wishlist> wishlists = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path wishlistFile = Path.of("src", "main", "resources", "wishlist.json");

    public WishlistService() {
        loadWishlistFromFile();
    }

    public void addToWishlist(String customerId, UUID eventId) {
        Wishlist wishlist = wishlists.getOrDefault(customerId, new Wishlist(customerId));
        wishlist.addEvent(eventId);
        wishlists.put(customerId, wishlist);
        saveWishlistToFile();
    }

    public void removeFromWishlist(String customerId, UUID eventId) {
        Wishlist wishlist = wishlists.get(customerId);
        if (wishlist != null) {
            wishlist.removeEvent(eventId);
            saveWishlistToFile();
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

    private void loadWishlistFromFile() {
        try {
            if (Files.exists(wishlistFile)) {
                byte[] fileBytes = Files.readAllBytes(wishlistFile);
                List<Wishlist> loadedWishlists = objectMapper.readValue(fileBytes, new TypeReference<List<Wishlist>>() {
                });
                for (Wishlist wishlist : loadedWishlists) {
                    wishlists.put(wishlist.getCustomerId(), wishlist);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception properly
        }
    }

    private void saveWishlistToFile() {
        try {
            List<Wishlist> wishlistList = new ArrayList<>(wishlists.values());
            byte[] fileBytes = objectMapper.writeValueAsBytes(wishlistList);
            Files.write(wishlistFile, fileBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
