package com.spring.cinemaapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class MovieSeat {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="watchingTime_id")
    private WatchingTime watchingTime;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="order_id")
    private Order order;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;



    public MovieSeat(){}

    public MovieSeat(Long id, WatchingTime watchingTime, Order order, User user) {
        this.id = id;
        this.watchingTime = watchingTime;
        this.order = order;
        this.user = user;
    }

    public Long getId() {
        return id;
    }


    public WatchingTime getWatchingTime() {
        return watchingTime;
    }

    public void setWatchingTime(WatchingTime watchingTime) {
        this.watchingTime = watchingTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
