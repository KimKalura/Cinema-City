package com.spring.cinemaapp.dto;

import java.util.Date;
import java.util.List;

public class AddMovieDTO {

   private String movieName;
   private Double price;
   private Long cinemaRoomId;
   private List<ProjectionDTO> dates;


   public AddMovieDTO(String movieName,Double price, Long cinemaRoomId, List<ProjectionDTO> dates) {
      this.movieName = movieName;
      this.price = price;
      this.cinemaRoomId = cinemaRoomId;
      this.dates = dates;
   }

   public String getMovieName() {
      return movieName;
   }

   public void setMovieName(String movieName) {
      this.movieName = movieName;
   }

   public Double getPrice() {
      return price;
   }

   public void setPrice(Double price) {
      this.price = price;
   }

   public Long getCinemaRoomId() {
      return cinemaRoomId;
   }

   public void setCinemaRoomId(Long cinemaRoomId) {
      this.cinemaRoomId = cinemaRoomId;
   }

   public List<ProjectionDTO> getDates() {
      return dates;
   }

   public void setDates(List<ProjectionDTO> dates) {
      this.dates = dates;
   }
}
