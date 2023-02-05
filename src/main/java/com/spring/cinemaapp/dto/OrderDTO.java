package com.spring.cinemaapp.dto;

import java.util.List;

public class OrderDTO {

    private Long projectionId;
    private List<SeatDTO>  seats;

    public OrderDTO(Long projectionId, List<SeatDTO> seats) {
        this.projectionId = projectionId;
        this.seats = seats;
    }

    public Long getProjectionId() {
        return projectionId;
    }

    public void setProjectionId(Long projectionId) {
        this.projectionId = projectionId;
    }

    public List<SeatDTO> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatDTO> seats) {
        this.seats = seats;
    }
}
