package com.spring.cinemaapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private LocalDateTime date;

    @Column
    private int priceFilm;

    @Column
    private int points;

    @ManyToOne
    @JsonIgnore
    //@JsonBackReference
    @JoinColumn(name = "cinemaRoom_id")
    private CinemaRoom cinemaRoom;

    @OneToMany(mappedBy = "movie", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<WatchingTime> watchingTimes;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="user_id")
    private User user;



    public Movie(){}

    public Movie(Long id, String name, LocalDateTime date, int priceFilm, int points, CinemaRoom cinemaRoom, List<WatchingTime> watchingTimes, User user) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.priceFilm = priceFilm;
        this.points = points;
        this.cinemaRoom = cinemaRoom;
        this.watchingTimes = watchingTimes;
        this.user = user;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getPriceFilm() {
        return priceFilm;
    }

    public void setPriceFilm(int priceFilm) {
        this.priceFilm = priceFilm;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public CinemaRoom getCinemaRoom() {
        return cinemaRoom;
    }

    public void setCinemaRoom(CinemaRoom cinemaRoom) {
        this.cinemaRoom = cinemaRoom;
    }

    public List<WatchingTime> getWatchingTimes() {
        return watchingTimes;
    }

    public void setWatchingTimes(List<WatchingTime> watchingTimes) {
        this.watchingTimes = watchingTimes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
