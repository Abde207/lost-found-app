package org.example.lostandfound.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity,Long> {
    List<ItemEntity> findAllByItemType(ItemType itemType);
}
