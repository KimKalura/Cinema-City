package com.spring.cinemaapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.cinemaapp.dto.AddMovieDTO;
import com.spring.cinemaapp.model.Movie;
import com.spring.cinemaapp.model.Projection;
import com.spring.cinemaapp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @PostMapping("/add")
    public Movie addMovie(@RequestBody AddMovieDTO addMovieDTO) throws JsonProcessingException {
        return movieService.addMovie(addMovieDTO);
    }

    @GetMapping("/projections-available/{movieId}")
    public List<Projection> getAllProjectionsAvailable(@PathVariable Long movieId) {
        return movieService.getAllProjectionsAvailable(movieId);
    }
}
