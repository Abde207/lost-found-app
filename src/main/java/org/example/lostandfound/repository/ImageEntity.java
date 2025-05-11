package org.example.lostandfound.repository;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Lob
    @Column(name = "file", columnDefinition = "BLOB")
    private byte[] file;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
