package com.sportyShoes.controller;

import com.sportyShoes.pojo.Shoe;
import com.sportyShoes.service.ShoeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shoes")
public class ShoeController {
    @Autowired
    ShoeService service;

    @PostMapping("/add")
    public ResponseEntity<String> addShoe(@RequestParam("name") String name,
            @RequestParam("description") String description, @RequestParam("price") BigDecimal price,
            @RequestParam("image") MultipartFile image) throws IOException {
        String response = service.addShoe(name, description, price, image);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getShoe/{id}")
    public Optional<Shoe> getShoe(@PathVariable Long id) {
        return service.getShoe(id);
    }

    @GetMapping("/getAll")
    public List<Shoe> getAll() {
        return service.getAll();
    }

    @PutMapping("/updateShoe/{id}")
    public Object updateShoe(@PathVariable Long id, @RequestParam("name") String name,
            @RequestParam("description") String description, @RequestParam("price") BigDecimal price,
            @RequestParam("image") MultipartFile image) throws IOException {
        return service.updateShoe(id, name, description, price, image);
    }

    @GetMapping("/getImage/{name}")
    public ResponseEntity<byte[]> getShoeImage(@PathVariable String name) throws IOException {
        byte[] imageData = service.getShoeImage(name);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(imageData);
    }

    @DeleteMapping("/deleteShoe/{id}")
    public String deleteShoe(@PathVariable Long id) {
        return service.deleteShoe(id);
    }
}
