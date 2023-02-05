package com.spring.cinemaapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference(value="user-order")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference(value="order-ticket")
    private List<Ticket> ticketList;


    public Order(){}

    public Order(Long id, Date createdDate, Double totalPrice, User user, List<Ticket> ticketList) {
        this.id = id;
        this.createdDate = createdDate;
        this.totalPrice = totalPrice;
        this.user = user;
        this.ticketList = ticketList;
    }

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

    public List<Ticket> getTicketList() {
        if (this.ticketList == null) {
            this.ticketList = new ArrayList<>();
        }
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

}
