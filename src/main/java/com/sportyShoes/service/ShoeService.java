package com.sportyShoes.service;

import com.sportyShoes.pojo.Shoe;
import com.sportyShoes.util.ShoeRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class ShoeService {
    @Autowired
    private ShoeRepo repo;

    private static final String FOLDER_PATH = "/Users/contev/Projects/JAVA course/Sporty Shoes/src/main/resources/static/images/";

    public String addShoe(String name, String description, BigDecimal price, MultipartFile image) throws IOException {
        File directory = new File(FOLDER_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File destinationFile = new File(directory, image.getOriginalFilename());
        image.transferTo(destinationFile);

        Shoe shoe = new Shoe();
        shoe.setName(name);
        shoe.setDescription(description);
        shoe.setPrice(price);
        shoe.setImage(image.getOriginalFilename());

        repo.save(shoe);
        return "Shoe uploaded successfully!";
    }

    public Optional<Shoe> getShoe(Long id) {
        return repo.findById(id);
    }

    public @NotNull List<Shoe> getAll() {
        return repo.findAll();
    }

    public Object updateShoe(Long id, String name, String description, BigDecimal price, MultipartFile image)
            throws IOException {
        File directory = new File(FOLDER_PATH);
        File destinationFile = new File(directory, image.getOriginalFilename());
        image.transferTo(destinationFile);

        // Retrieve the user from the repository
        Optional<Shoe> opShoe = repo.findById(id);

        if (opShoe.isPresent()) {
            Shoe shoe = opShoe.get();
            shoe.setName(name);
            shoe.setDescription(description);
            shoe.setPrice(price);

            if (!shoe.getImage().equals(image.getOriginalFilename())) {
                File oldImage = new File(FOLDER_PATH + shoe.getImage());
                oldImage.delete();
                shoe.setImage(image.getOriginalFilename());
            }

            repo.save(shoe);
            return shoe;
        }
        return "Shoe not found";
    }

    public byte[] getShoeImage(String name) throws IOException {
        File imageFile = new File(FOLDER_PATH + name);
        if (!imageFile.exists()) {
            throw new IOException(FOLDER_PATH + name);
        }
        return Files.readAllBytes(imageFile.toPath());
    }

    public String deleteShoe(Long id) {
        Optional<Shoe> opShoe = repo.findById(id);
        if (opShoe.isPresent()) {
            Shoe shoe = opShoe.get();
            File imageFile = new File(FOLDER_PATH + shoe.getImage());
            imageFile.delete();
            repo.deleteById(id);
        }
        return "Shoe with id: " + id + " deleted successfully!";
    }
}
