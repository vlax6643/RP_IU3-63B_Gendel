package ru.hendel.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hendel.backend.domain.Artist;
import ru.hendel.backend.domain.Museum;
import ru.hendel.backend.domain.Painting;

import java.util.List;
import java.util.Optional;


@Repository
public interface PaintingRepository extends JpaRepository<Painting, Long> {
    List<Painting> findByArtist(Artist artist);
    List<Painting> findByMuseum(Museum museum);
}
