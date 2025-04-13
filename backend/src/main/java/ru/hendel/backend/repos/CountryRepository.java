package ru.hendel.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hendel.backend.domain.Countries;

@Repository
public interface CountryRepository extends JpaRepository<Countries, Long> {
}
