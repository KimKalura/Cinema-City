package com.spring.cinemaapp;

import com.spring.cinemaapp.dto.AddCinemaRoomDTO;
import com.spring.cinemaapp.dto.ExtraPriceDTO;
import com.spring.cinemaapp.model.CinemaRoom;
import com.spring.cinemaapp.repository.CinemaRoomRepository;
import com.spring.cinemaapp.repository.MovieRepository;
import com.spring.cinemaapp.service.CinemaRoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@SpringBootTest
@ExtendWith({MockitoExtension.class})
class CinemaServiceTest {

    @InjectMocks
    private CinemaRoomService cinemaRoomService;

    @Mock
    private CinemaRoomRepository cinemaRoomRepository;

    @Mock
    private MovieRepository movieRepository;



    @Test
    void contextLoads() {
    }


    @Test
    void testAddCinemaRoom() {
        //testam doar primele 3 randuri din CinemaRoomService, addCinemaRoom

        //given
        ExtraPriceDTO extraPriceDTO = new ExtraPriceDTO(4, 6, 3);
        AddCinemaRoomDTO addCinemaRoomDTO = new AddCinemaRoomDTO(8, 9, Arrays.asList(extraPriceDTO));

        //when
        CinemaRoom cinemaRoomRepositoryObject = new CinemaRoom(1L, 8, 9, null, null);
        when(cinemaRoomRepository.save(any())).thenReturn(cinemaRoomRepositoryObject);

        CinemaRoom result = cinemaRoomService.addCinemaRoom(addCinemaRoomDTO);

        //then
        assertEquals(8, result.getNumberOfRows());
    }
}
