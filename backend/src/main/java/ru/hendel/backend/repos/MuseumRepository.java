package ru.hendel.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hendel.backend.domain.Museum;

import java.util.List;

@Repository
public interface MuseumRepository extends JpaRepository<Museum, Long> {


    Museum findByName(String name);


}