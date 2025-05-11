package org.example.lostandfound.service;

import org.example.lostandfound.dto.ItemRequest;
import org.example.lostandfound.dto.ItemResponse;
import org.example.lostandfound.repository.ItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemEntity mapToEntity(ItemRequest itemRequest);
    ItemRequest mapToRequest(ItemEntity itemEntity);
    ItemResponse mapToResponse(ItemEntity itemEntity);
    default ItemResponse mapToResponseWithImageUrl(ItemEntity itemEntity){
        ItemResponse itemResponse = mapToResponse(itemEntity);
        itemResponse.setImageUrl("images/"+itemEntity.getImageId());
        return itemResponse;
    }
}
