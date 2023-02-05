package com.spring.cinemaapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;

import javax.persistence.*;
import java.util.List;

@Entity
public class Seat {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Integer seatRow;

    @Column
    private Integer seatCol;

    @Column
    private Integer extraPrice;

    @OneToMany(mappedBy = "seat", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference(value="seat-ticket")
    private List<Ticket> ticketList;

    @ManyToOne
    @JoinColumn(name = "cinema_room_id")
    @JsonBackReference(value="cinema-seat")
    private CinemaRoom cinemaRoom;



    public Seat(){}

    public Seat(Long id, Integer seatRow, Integer seatCol, Integer extraPrice, List<Ticket> ticketList, CinemaRoom cinemaRoom) {
        this.id = id;
        this.seatRow = seatRow;
        this.seatCol = seatCol;
        this.extraPrice = extraPrice;
        this.ticketList = ticketList;
        this.cinemaRoom = cinemaRoom;
    }

    public Long getId() {
        return id;
    }

    public Integer getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(Integer seatRow) {
        this.seatRow = seatRow;
    }

    public Integer getSeatCol() {
        return seatCol;
    }

    public void setSeatCol(Integer seatCol) {
        this.seatCol = seatCol;
    }

    public Integer getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(Integer extraPrice) {
        this.extraPrice = extraPrice;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public CinemaRoom getCinemaRoom() {
        return cinemaRoom;
    }

    public void setCinemaRoom(CinemaRoom cinemaRoom) {
        this.cinemaRoom = cinemaRoom;
    }
}
