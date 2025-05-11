package org.example.lostandfound;

import jakarta.annotation.PostConstruct;
import org.example.lostandfound.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.IntStream;

@Component
public class TestMockData {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ImageRepository imageRepository;
    private final PasswordEncoder passwordEncoder;

    public TestMockData(UserRepository userRepository, ItemRepository itemRepository, ImageRepository imageRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.imageRepository = imageRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void createInitialData() throws IOException {
        UserEntity user = new UserEntity();
        user.setEmail("user@user.com");
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setRole("USER");
        userRepository.save(user);

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setFile(this.getClass().getResourceAsStream("/images/image.png").readAllBytes());
        imageRepository.save(imageEntity);

        IntStream.range(0, 10)
                .mapToObj(i -> itemEntity(i, ItemType.LOST, imageEntity.getId()))
                .forEach(itemRepository::save);
        IntStream.range(0, 10)
                .mapToObj(i -> itemEntity(i, ItemType.FOUND, imageEntity.getId()))
                .forEach(itemRepository::save);
    }

    private static ItemEntity itemEntity(int id, ItemType itemType, String uuid) {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setItemType(itemType);
        itemEntity.setName("itemName " + id);
        itemEntity.setImageId("imageId" + id);
        itemEntity.setDate(LocalDate.now());
        itemEntity.setImageId(uuid);
        itemEntity.setCategory("itemCategory" + id);
        itemEntity.setDescription("itemDescription" + id);
        return itemEntity;
    }
}
