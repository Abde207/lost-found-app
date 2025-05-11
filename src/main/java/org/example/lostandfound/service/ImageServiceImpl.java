package org.example.lostandfound.service;

import org.example.lostandfound.repository.ImageEntity;
import org.example.lostandfound.repository.ImageRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public String saveImage(MultipartFile file) throws IOException {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setFile(file.getBytes());
        imageRepository.save(imageEntity);
        return imageEntity.getId();
    }

    @Override
    public byte[] getImage(String uuid) {
        return imageRepository.findById(uuid).map(ImageEntity::getFile).orElseThrow(()->new IllegalStateException("image not exist"));
    }
}
