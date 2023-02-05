package com.spring.cinemaapp.repository;

import com.spring.cinemaapp.model.CinemaRoom;
import com.spring.cinemaapp.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
   Seat findBySeatRowAndSeatColAndCinemaRoom(Integer row, Integer col, CinemaRoom cinemaRoom);
}
