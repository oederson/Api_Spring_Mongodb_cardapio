package com.example.cardapio.service;

import com.example.cardapio.modelo.FileOb;
import com.example.cardapio.repository.FileObRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileDbService {
    @Autowired
    private FileObRepository fileObRepository;
    public FileOb store(String id, MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        FileOb fileOb = new FileOb(id, fileName, file.getContentType(), file.getBytes());
        return fileObRepository.save(fileOb);
    }
    public FileOb getfileById(String id){
        Optional<FileOb> fileObOptional = fileObRepository.findById(id);
        if(fileObOptional.isPresent()){
            return fileObOptional.get();
        }
        return null;
    }
    public List<FileOb> getFileList(){
        return fileObRepository.findAll();
    }

}

