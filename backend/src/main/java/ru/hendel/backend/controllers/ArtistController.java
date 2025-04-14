package ru.hendel.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.hendel.backend.domain.Artist;
import ru.hendel.backend.domain.Countries;
import ru.hendel.backend.repos.ArtistRepository;
import ru.hendel.backend.repos.CountryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private CountryRepository countryRepository;

    @GetMapping("/artists")
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable(value = "id") Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found"));
        return ResponseEntity.ok(artist);
    }

    @PostMapping("/artists")
    public ResponseEntity<Object> createArtist(@RequestBody Artist artist) {
        try {

            if (artist.getCountry() != null && artist.getCountry().getId() != null) {
                Optional<Countries> country = countryRepository.findById(artist.getCountry().getId());
                if (!country.isPresent()) {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "countrynotfound");
                    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
                }
                artist.setCountry(country.get());
            }

            Artist newArtist = artistRepository.save(artist);
            return new ResponseEntity<>(newArtist, HttpStatus.OK);
        } catch (Exception ex) {
            String error;
            if (ex.getMessage().contains("artists.name_UNIQUE")) {
                error = "artistalreadyexists";
            } else {
                error = "undefinederror";
            }
            Map<String, String> map = new HashMap<>();
            map.put("error", error);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @PutMapping("/artists/{id}")
    public ResponseEntity<Object> updateArtist(@PathVariable(value = "id") Long artistId,
                                               @RequestBody Artist artistDetails) {
        Optional<Artist> artistOpt = artistRepository.findById(artistId);

        if (!artistOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found");
        }

        Artist artist = artistOpt.get();


        artist.setName(artistDetails.getName());
        artist.setAge(artistDetails.getAge());


        if (artistDetails.getCountry() != null && artistDetails.getCountry().getId() != null) {
            Optional<Countries> country = countryRepository.findById(artistDetails.getCountry().getId());
            if (!country.isPresent()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "countrynotfound");
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
            artist.setCountry(country.get());
        }

        Artist updatedArtist = artistRepository.save(artist);
        return ResponseEntity.ok(updatedArtist);
    }

    @DeleteMapping("/artists/{id}")
    public ResponseEntity<Object> deleteArtist(@PathVariable(value = "id") Long artistId) {
        Optional<Artist> artist = artistRepository.findById(artistId);
        Map<String, Boolean> resp = new HashMap<>();

        if (artist.isPresent()) {
            artistRepository.delete(artist.get());
            resp.put("deleted", Boolean.TRUE);
        } else {
            resp.put("deleted", Boolean.FALSE);
        }

        return ResponseEntity.ok(resp);
    }


    @GetMapping("/countries/{countryId}/artists")
    public List<Artist> getArtistsByCountry(@PathVariable(value = "countryId") Long countryId) {
        Countries country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found"));
        return artistRepository.findByCountry(country);
    }
}