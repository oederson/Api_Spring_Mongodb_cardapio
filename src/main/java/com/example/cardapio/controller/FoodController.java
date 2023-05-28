package com.example.cardapio.controller;

import com.example.cardapio.modelo.FileOb;
import com.example.cardapio.modelo.Food;
import com.example.cardapio.repository.FileObRepository;
import com.example.cardapio.repository.FoodRepository;
import com.example.cardapio.service.FileDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import resources.FoodRequest;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;


@RestController
public class FoodController {
    private final FoodRepository foodRepository;
    private final FileObRepository fileObRepository;
    @Autowired
    private FileDbService fileDbService;

    public FoodController(FoodRepository foodRepository,FileObRepository fileObRepository) {
        this.foodRepository = foodRepository;
        this.fileObRepository = fileObRepository;
    }
    private List<Map<String, Object>> mergeLists(List<Food> posts, List<FileOb> image) {
        List<Map<String, Object>> mergedList = new ArrayList<>();
        for (FileOb inage : image) {
            for (Food post : posts) {
                if (post.getId().equals(inage.getId())) {
                    Map<String, Object> mergedItem = new HashMap<>();
                    mergedItem.put("id", post.getId());
                    mergedItem.put("name", post.getName());
                    mergedItem.put("descriptor", post.getDescriptor());
                    mergedItem.put("price", post.getPrice());
                    mergedItem.put("type", inage.getType());
                    mergedItem.put("data", inage.getData());
                    mergedList.add(mergedItem);
                    break;
                }
            }
        }
        return mergedList;
    }
    public static double formatarPreco(double numero) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        String numeroFormatado = decimalFormat.format(numero);
        return Double.parseDouble(numeroFormatado.replace(",", "."));
    }

    @GetMapping("/food/retornatudo")
    public ResponseEntity<List<Map<String, Object>>> getTudo() {
        List<Food> posts = this.foodRepository.findAll();
        List<FileOb> image = this.fileDbService.getFileList();
        List<Map<String, Object>> mergedList = mergeLists(posts, image);
        return ResponseEntity.ok(mergedList);
    }
    @PostMapping("/food")
    public ResponseEntity<Food> createFood(@RequestBody FoodRequest foodRequest ){
        if (foodRequest.getName() == null && foodRequest.getDescriptor() == null && foodRequest.getPrice() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (foodRequest.getName().isEmpty() && foodRequest.getDescriptor().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Food food = new Food();
        food.setName(foodRequest.getName());
        food.setDescriptor(foodRequest.getDescriptor());
        food.setPrice(formatarPreco(foodRequest.getPrice()));

        return ResponseEntity.status(201).body(this.foodRepository.save(food));
    }
    @PostMapping("/food/foto/{id}")
    public FileOb uploadfile(@PathVariable String id,
                             @RequestParam("file") MultipartFile file) throws IOException {
        return fileDbService.store(id, file);
    }
    @GetMapping("/food/foto/{id}")
    public FileOb getFile(@PathVariable String id){
        return fileDbService.getfileById(id);
    }
    @GetMapping("/food/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable String id) {
        return this.foodRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/food/{id}")
    public ResponseEntity<String> deleteFoodsById(@PathVariable String id) {
        Optional<FileOb> arquivo = this.fileObRepository.findById(id);
        Optional<Food> food = this.foodRepository.findById(id);
        if (food.isPresent() && arquivo.isPresent()) {
            this.fileObRepository.deleteById(id);
            this.foodRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/food/{id}")
    public ResponseEntity<Food> updateFoodById(@PathVariable("id") String id,
                                               @RequestBody FoodRequest foodRequest) {
         if (foodRequest.getName() == null || foodRequest.getName().isEmpty()) {
                   return ResponseEntity.badRequest().build();
         }
        Optional<Food> optionalFood = this.foodRepository.findById(id);
        if (optionalFood.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Food food = optionalFood.get();

        if (foodRequest.getDescriptor() == null || foodRequest.getDescriptor().isEmpty()){
            food.setName(foodRequest.getName());

            foodRepository.save(food);
            return ResponseEntity.ok(food);
        }

            food.setName(foodRequest.getName());
            food.setDescriptor(foodRequest.getDescriptor());
        food.setPrice(formatarPreco(foodRequest.getPrice()));
            foodRepository.save(food);
            return ResponseEntity.ok(food);
        }
    @PutMapping("/food/foto/{id}")
    public FileOb atualizaFile(@PathVariable String id,
                               @RequestParam("file") MultipartFile file) throws IOException {
        Optional<FileOb> optionalFileOb = this.fileObRepository.findById(id);
        FileOb arquivo = optionalFileOb.get();
        arquivo.setNameFile(file.getName());
        arquivo.setType(file.getContentType());
        arquivo.setData(file.getBytes());
        return fileObRepository.save(arquivo);
    }
}

