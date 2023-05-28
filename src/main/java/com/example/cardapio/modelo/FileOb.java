package com.example.cardapio.modelo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Fotos")
public class FileOb {


    private String id;
    private String nameFile;
    private String type;
    private byte[] data;
    public FileOb(){

    }
    public FileOb(String id, String name, String type, byte[] data) {
        this.id = id;
        this.nameFile = name;
        this.type = type;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


}
