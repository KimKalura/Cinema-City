package com.spring.cinemaapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;

import javax.persistence.*;
import java.util.List;

@Entity
public class CinemaRoom {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "cinemaRoom", cascade = CascadeType.ALL)
    //@JsonManagedReference
    private List<Movie> movieList;

    @OneToMany(mappedBy = "cinemaRoom", cascade = CascadeType.ALL)
    private List<Seat> seatList;



    public CinemaRoom(){}

    public CinemaRoom(Long id, String name, List<Movie> movieList, List<Seat> seatList) {
        this.id = id;
        this.name = name;
        this.movieList = movieList;
        this.seatList = seatList;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }
}
