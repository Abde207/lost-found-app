package org.example.lostandfound.service;

import org.example.lostandfound.dto.ItemRequest;
import org.example.lostandfound.dto.ItemResponse;
import org.example.lostandfound.repository.ItemEntity;
import org.example.lostandfound.repository.ItemRepository;
import org.example.lostandfound.repository.ItemType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public ItemResponse createItem(ItemRequest request, RequestType requestType) {
        ItemEntity itemEntity = itemMapper.mapToEntity(request);
        itemEntity.setItemType(toItemType(requestType));
        itemRepository.save(itemEntity);
        return itemMapper.mapToResponseWithImageUrl(itemEntity);
    }

    @Override
    public List<ItemResponse> getItems(RequestType requestType) {
        return itemRepository.findAllByItemType(toItemType(requestType)).stream()
                .map(itemMapper::mapToResponseWithImageUrl)
                .peek(it -> it.setType(requestType.name()))
                .toList();
    }

    private static ItemType toItemType(RequestType requestType) {
        return ItemType.valueOf(requestType.name());
    }

    @Override
    public String saveImage(MultipartFile file) {
        return "";
    }
}
