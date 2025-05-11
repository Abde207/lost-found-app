package org.example.lostandfound.controller;

import org.example.lostandfound.dto.ItemRequest;
import org.example.lostandfound.dto.ItemResponse;
import org.example.lostandfound.service.ImageService;
import org.example.lostandfound.service.ItemService;
import org.example.lostandfound.service.RequestType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LostAndFoundController {

    private final ItemService itemService;
    private final ImageService imageService;

    public LostAndFoundController(ItemService itemService, ImageService imageService) {
        this.itemService = itemService;
        this.imageService = imageService;
    }

    @GetMapping("/images/{uuid}")
    public ResponseEntity<byte[]> getImage(@PathVariable("uuid") String uuid) {
        byte[] imageData = imageService.getImage(uuid);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }


    @PostMapping("/images")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        return ResponseEntity.ok(imageService.saveImage(file));
    }

    @PostMapping("/items/lost")
    public ResponseEntity<ItemResponse> createLostItem(@RequestBody ItemRequest request) {
        return ResponseEntity.ok(itemService.createItem(request, RequestType.LOST));
    }

    @PostMapping("/items/found")
    public ResponseEntity<ItemResponse> createFoundItem(@RequestBody ItemRequest request) {
        return ResponseEntity.ok(itemService.createItem(request,RequestType.FOUND));
    }

    @GetMapping("/items/lost")
    public ResponseEntity<List<ItemResponse>> getLostItems() {
        return ResponseEntity.ok(itemService.getItems(RequestType.LOST));
    }

    @GetMapping("/items/found")
    public ResponseEntity<List<ItemResponse>> getFoundItems() {
        return ResponseEntity.ok(itemService.getItems(RequestType.FOUND));
    }
}