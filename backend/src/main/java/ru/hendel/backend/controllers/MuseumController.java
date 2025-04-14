package ru.hendel.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.hendel.backend.domain.Museum;
import ru.hendel.backend.repos.MuseumRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class MuseumController {

    @Autowired
    private MuseumRepository museumRepository;

    @GetMapping("/museums")
    public List<Museum> getAllMuseums() {
        return museumRepository.findAll();
    }

    @GetMapping("/museums/{id}")
    public ResponseEntity<Museum> getMuseumById(@PathVariable(value = "id") Long museumId) {
        Museum museum = museumRepository.findById(museumId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Museum not found"));
        return ResponseEntity.ok(museum);
    }

    @PostMapping("/museums")
    public ResponseEntity<Object> createMuseum(@RequestBody Museum museum) {
        try {
            Museum newMuseum = museumRepository.save(museum);
            return new ResponseEntity<>(newMuseum, HttpStatus.OK);
        } catch (Exception ex) {
            String error;
            if (ex.getMessage().contains("museums.name_UNIQUE")) {
                error = "museumnamealreadyexists";
            } else {
                error = "undefinederror";
            }
            Map<String, String> map = new HashMap<>();
            map.put("error", error);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @PutMapping("/museums/{id}")
    public ResponseEntity<Object> updateMuseum(@PathVariable(value = "id") Long museumId,
                                               @RequestBody Museum museumDetails) {
        Optional<Museum> museumOpt = museumRepository.findById(museumId);

        if (!museumOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Museum not found");
        }

        Museum museum = museumOpt.get();
        museum.setName(museumDetails.getName());
        museum.setLocation(museumDetails.getLocation());

        try {
            Museum updatedMuseum = museumRepository.save(museum);
            return ResponseEntity.ok(updatedMuseum);
        } catch (Exception ex) {
            String error;
            if (ex.getMessage().contains("museums.name_UNIQUE")) {
                error = "museumnamealreadyexists";
            } else {
                error = "undefinederror";
            }
            Map<String, String> map = new HashMap<>();
            map.put("error", error);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @DeleteMapping("/museums/{id}")
    public ResponseEntity<Object> deleteMuseum(@PathVariable(value = "id") Long museumId) {
        Optional<Museum> museum = museumRepository.findById(museumId);
        Map<String, Boolean> resp = new HashMap<>();

        if (museum.isPresent()) {
            museumRepository.delete(museum.get());
            resp.put("deleted", Boolean.TRUE);
        } else {
            resp.put("deleted", Boolean.FALSE);
        }

        return ResponseEntity.ok(resp);
    }


}