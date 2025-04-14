package ru.hendel.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hendel.backend.domain.Artist;
import ru.hendel.backend.domain.Countries;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findByCountry(Countries country);
}