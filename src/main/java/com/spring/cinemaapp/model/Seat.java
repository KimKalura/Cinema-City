package com.spring.cinemaapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;

import javax.persistence.*;

@Entity
public class Seat {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int numberOfRows;

    @Column
    private int numberOfCols;

    @Column
    private int priceSeat;

    @Column
    private int extraPriceSeat;

    @Column
    private int startingRow;

    @Column
    private int endingRow;

    @Column
    private boolean isAvailable;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cinemaRoom_id")
    private CinemaRoom cinemaRoom;



    public Seat(){}

    public Seat(Long id, int numberOfRows, int numberOfCols, int priceSeat, int extraPriceSeat, int startingRow, int endingRow, boolean isAvailable, CinemaRoom cinemaRoom) {
        this.id = id;
        this.numberOfRows = numberOfRows;
        this.numberOfCols = numberOfCols;
        this.priceSeat = priceSeat;
        this.extraPriceSeat = extraPriceSeat;
        this.startingRow = startingRow;
        this.endingRow = endingRow;
        this.isAvailable = isAvailable;
        this.cinemaRoom = cinemaRoom;
    }

    public Long getId() {
        return id;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfCols() {
        return numberOfCols;
    }

    public void setNumberOfCols(int numberOfCols) {
        this.numberOfCols = numberOfCols;
    }

    public int getPriceSeat() {
        return priceSeat;
    }

    public void setPriceSeat(int priceSeat) {
        this.priceSeat = priceSeat;
    }

    public int getExtraPriceSeat() {
        return extraPriceSeat;
    }

    public void setExtraPriceSeat(int extraPriceSeat) {
        this.extraPriceSeat = extraPriceSeat;
    }

    public int getStartingRow() {
        return startingRow;
    }

    public void setStartingRow(int startingRow) {
        this.startingRow = startingRow;
    }

    public int getEndingRow() {
        return endingRow;
    }

    public void setEndingRow(int endingRow) {
        this.endingRow = endingRow;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public CinemaRoom getCinemaRoom() {
        return cinemaRoom;
    }

    public void setCinemaRoom(CinemaRoom cinemaRoom) {
        this.cinemaRoom = cinemaRoom;
    }
}
