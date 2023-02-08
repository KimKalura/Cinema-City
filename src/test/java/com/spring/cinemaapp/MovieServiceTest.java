package com.spring.cinemaapp;

import com.spring.cinemaapp.dto.AddMovieDTO;
import com.spring.cinemaapp.model.CinemaRoom;
import com.spring.cinemaapp.model.Movie;
import com.spring.cinemaapp.repository.CinemaRoomRepository;
import com.spring.cinemaapp.repository.MovieRepository;
import com.spring.cinemaapp.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private CinemaRoomRepository cinemaRoomRepository;



    @Test
    void testAddMovieShouldThrowException() {
        //given
        AddMovieDTO addMovieDTO = new AddMovieDTO("The Grinch", 0.0, null, null);

        //when
        when(cinemaRoomRepository.findById(any())).thenReturn(Optional.of(new CinemaRoom()));
        when(movieRepository.findByMovieName(addMovieDTO.getMovieName())).thenReturn(Optional.of(new Movie(null, addMovieDTO.getMovieName(), 0.0, null, null, null, null, null, null, null)));

        assertThrows(ResponseStatusException.class, () -> movieService.addMovie(addMovieDTO));
    }
}
