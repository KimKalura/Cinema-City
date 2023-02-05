package com.spring.cinemaapp.repository;

import com.spring.cinemaapp.model.Projection;
import com.spring.cinemaapp.model.Seat;
import com.spring.cinemaapp.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository  extends JpaRepository<Ticket, Long> {
    Ticket findByProjectionAndSeat(Projection projection, Seat seat);

}
