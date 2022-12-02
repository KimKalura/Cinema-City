package com.spring.cinemaapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.cinemaapp.model.Movie;
import com.spring.cinemaapp.model.MovieSeat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class WatchingTime {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "watchingTime", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MovieSeat> movieSeat;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "movie_id")
    private Movie movie;



    public WatchingTime(){}

    public WatchingTime(Long id, LocalDateTime startTime, LocalDateTime endTime, List<MovieSeat> movieSeat, Movie movie) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.movieSeat = movieSeat;
        this.movie = movie;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<MovieSeat> getMovieSeat() {
        return movieSeat;
    }

    public void setMovieSeat(List<MovieSeat> movieSeat) {
        this.movieSeat = movieSeat;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
