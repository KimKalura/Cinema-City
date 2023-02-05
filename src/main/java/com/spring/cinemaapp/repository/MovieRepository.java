package com.spring.cinemaapp.repository;

import com.spring.cinemaapp.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
        Optional<Movie> findByMovieName(String movieName);
}
