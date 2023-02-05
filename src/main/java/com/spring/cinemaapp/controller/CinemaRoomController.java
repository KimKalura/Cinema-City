package com.spring.cinemaapp.controller;

import com.spring.cinemaapp.dto.AddCinemaRoomDTO;
import com.spring.cinemaapp.model.CinemaRoom;
import com.spring.cinemaapp.service.CinemaRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinema")
public class CinemaRoomController {

    private CinemaRoomService cinemaRoomService;

    @Autowired
    public CinemaRoomController(CinemaRoomService cinemaRoomService) {
        this.cinemaRoomService = cinemaRoomService;
    }

    @PostMapping("/add")
    public CinemaRoom addCinemaRoom(@RequestBody AddCinemaRoomDTO addCinemaRoomDTO){
        return cinemaRoomService.addCinemaRoom(addCinemaRoomDTO);
    }
}
