package com.spring.cinemaapp.service;

import com.itextpdf.text.DocumentException;
import com.spring.cinemaapp.dto.OrderDTO;
import com.spring.cinemaapp.dto.SeatDTO;
import com.spring.cinemaapp.model.*;
import com.spring.cinemaapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class OrderService {

    public static final String ORDER_MAIL_SUBJECT ="Biletele tale la film ";
    private UserRepository userRepository;
    private CinemaRoomService cinemaRoomService;
    private MovieRepository movieRepository;
    private ProjectionRepository projectionRepository;
    private SeatRepository seatRepository;
    private TicketRepository ticketRepository;
    private OrderRepository orderRepository;
    private MailService mailService;


    @Autowired
    public OrderService(UserRepository userRepository, CinemaRoomService cinemaRoomService, MovieRepository movieRepository, ProjectionRepository projectionRepository, SeatRepository seatRepository, TicketRepository ticketRepository, OrderRepository orderRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.cinemaRoomService = cinemaRoomService;
        this.movieRepository = movieRepository;
        this.projectionRepository = projectionRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
        this.orderRepository = orderRepository;
        this.mailService = mailService;
    }

    //cumparare bilet: idproject, array de col si row,
    //in functie de ce locuri vrem noi trebuie sa punem ticket resp de la locurile resp pe isAvailable=false
    //sa le adaugam in lista de tichete ale unui order
    //si orderul sa il adaugam utilizatorului
    //calculam pretul total= pfilm+extraprice pt fiecare row-col daca exista

    public Order buyTicket(OrderDTO orderDTO) throws MessagingException, DocumentException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User foundUser = userRepository.findUserByUsername(userDetails.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));

        Order newOrder = new Order();
        newOrder.setCreatedDate(new Date());
        newOrder.setUser(foundUser);
        Double totalPriceOrder = 0.0;
        Projection foundProjection = projectionRepository.findById(orderDTO.getProjectionId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "projection not found"));

        for (SeatDTO seatDTO : orderDTO.getSeats()) {
            Seat foundSeat = seatRepository.findBySeatRowAndSeatColAndCinemaRoom(seatDTO.getRow(), seatDTO.getCol(), foundProjection.getMovie().getCinemaRoom());
            Ticket foundTicket = ticketRepository.findByProjectionAndSeat(foundProjection, foundSeat);
            if (!foundTicket.getAvailable()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "the seat at :" + foundSeat.getSeatRow() + "" + foundSeat.getSeatCol() + "" + "is not available");
            }
            totalPriceOrder += foundProjection.getMovie().getPrice() + foundSeat.getExtraPrice();
            foundTicket.setAvailable(false);
            newOrder.getTicketList().add(foundTicket);
            foundTicket.setOrder(newOrder);
        }
        newOrder.setTotalPrice(totalPriceOrder);
        //mailService.sendSimpleMessage("raluca.deftu@yahoo.com", "Merry Christmas", "Christmas is just around the corner!!!");//email+pdf
        mailService.sendOrderConfirmationMessage(foundUser.getEmail(), newOrder);
        return orderRepository.save(newOrder);
    }
}
