package org.example.lostandfound.service;

import org.example.lostandfound.dto.ItemRequest;
import org.example.lostandfound.dto.ItemResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
    ItemResponse createItem(ItemRequest request,RequestType requestType);
    List<ItemResponse> getItems(RequestType requestType);
    String saveImage(MultipartFile file);
}
