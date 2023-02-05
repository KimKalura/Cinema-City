package com.spring.cinemaapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CinemaRoom {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Integer numberOfRows;

    @Column
    private Integer numberOfCols;

    @OneToMany(mappedBy = "cinemaRoom", cascade = CascadeType.ALL)
    @JsonManagedReference (value = "cinema-movie")
    private List<Movie> movieList;

    @OneToMany(mappedBy = "cinemaRoom", cascade = CascadeType.ALL)
    @JsonManagedReference (value = "cinema-seat")
    private List<Seat> seatList;



    public CinemaRoom(){}

    public CinemaRoom(Long id, Integer numberOfRows, Integer numberOfCols, List<Movie> movieList, List<Seat> seatList) {
        this.id = id;
        this.numberOfRows = numberOfRows;
        this.numberOfCols = numberOfCols;
        this.movieList = movieList;
        this.seatList = seatList;
    }

    public Long getId() {
        return id;
    }

    public Integer getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public Integer getNumberOfCols() {
        return numberOfCols;
    }

    public void setNumberOfCols(int numberOfCols) {
        this.numberOfCols = numberOfCols;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public List<Seat> getSeatList() {
        if(this.seatList == null){
            this.seatList = new ArrayList<>();
        }
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }
}
