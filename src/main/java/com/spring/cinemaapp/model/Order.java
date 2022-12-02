package com.spring.cinemaapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Date createdDate;

    @Column
    private Double totalPrice;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MovieSeat> movieSeats;


    public Order(){}

    public Long getId() {
        return id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<MovieSeat> getMovieSeats() {
        return movieSeats;
    }

    public void setMovieSeats(List<MovieSeat> movieSeats) {
        this.movieSeats = movieSeats;
    }
}
