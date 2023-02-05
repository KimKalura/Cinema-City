package com.spring.cinemaapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.spring.cinemaapp.dto.AddMovieDTO;
import com.spring.cinemaapp.dto.ProjectionDTO;
import com.spring.cinemaapp.model.*;
import com.spring.cinemaapp.repository.CinemaRoomRepository;
import com.spring.cinemaapp.repository.MovieRepository;
import com.spring.cinemaapp.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private MovieRepository movieRepository;
    private CinemaRoomRepository cinemaRoomRepository;
    private RestTemplate restTemplate;

    private static final String FIND_MOVIE_BY_NAME_URL = "https://api.themoviedb.org/3/search/movie?api_key={APIkey}&language=en-US&query={movieName}&page=1&include_adult=false";
    //https://api.themoviedb.org/3/search/movie?api_key=72eafaa6ebf17e6a58481763927a8a27&language=en-US&query=Harry%20Potter&page=1&include_adult=false
    private static final String FIND_MOVIE_DETAILS_BY_ID_URL = "https://api.themoviedb.org/3/movie/{movieId}?api_key={APIkey}&language=en-US";
    //https://api.themoviedb.org/3/movie/671?api_key=72eafaa6ebf17e6a58481763927a8a27&language=en-US


    @Value("${api.themoviedb.key}")
    private String apiKey;


    @Autowired
    public MovieService(MovieRepository movieRepository, CinemaRoomRepository cinemaRoomRepository, RestTemplate restTemplate) {
        this.movieRepository = movieRepository;
        this.cinemaRoomRepository = cinemaRoomRepository;
        this.restTemplate = restTemplate;
    }

    public Movie addMovie(AddMovieDTO addMovieDTO) throws JsonProcessingException {
        //gasim CinemaRoom
        //cream Movie - setam atributele
        //EXC Optional: daca MovieName exista
        //generateProjections

        CinemaRoom foundCinemaRoom = cinemaRoomRepository.findById(addMovieDTO.getCinemaRoomId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the cinema was not found"));
        Optional<Movie> foundMovie = movieRepository.findByMovieName(addMovieDTO.getMovieName());
        if (foundMovie.isPresent()) {
            throw new ResponseStatusException((HttpStatus.ALREADY_REPORTED), "Movie name already exist");
        }
        Movie movie = new Movie();
        //movie.setMovieName(addMovieDTO.getMovieName());
        movie.setPrice(addMovieDTO.getPrice());
        movie.setCinemaRoom(foundCinemaRoom);
        //
        JsonNode responseMovieBody = getResponseBodyJson(FIND_MOVIE_BY_NAME_URL, addMovieDTO.getMovieName());
        addMovieDetails(movie, responseMovieBody);
        JsonNode responseMovieDetailsBody = getResponseBodyJson(FIND_MOVIE_DETAILS_BY_ID_URL, responseMovieBody.path("results").get(0).path("id").asInt());
        addMovieGenres(movie, responseMovieDetailsBody);
        generateProjections(addMovieDTO, movie, foundCinemaRoom);
        return movieRepository.save(movie);
    }
    private void addMovieGenres(Movie movie, JsonNode responseMovieDetailsBody) {
        List<String> genres = new ArrayList<>();
        ArrayNode genresJson = (ArrayNode) responseMovieDetailsBody.path("genres");
        for(JsonNode genreJson : genresJson){
            genres.add(genreJson.path("name").asText());
        }
        movie.setGenres(genres);
    }

    private void addMovieDetails(Movie movie, JsonNode responseMovieBody)  {
        if (responseMovieBody.path("results").isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't add a movie with this name");
        }

        JsonNode firstResult = responseMovieBody.path("results").get(0);

        String releaseDateText = firstResult.path("release_date").asText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate releaseDate = LocalDate.parse(releaseDateText, formatter);
        movie.setOverview(firstResult.path("overview").asText());
        movie.setLanguage(firstResult.path("original_language").asText());
        movie.setVoteAverage(firstResult.path("vote_average").asDouble());
        movie.setReleaseDate(releaseDate);
        movie.setMovieName(firstResult.path("original_title").asText());

        /*String overview = firstResult.path("overview").asText();
        String language = firstResult.path("original_language").asText();
        Double voteAverage = firstResult.path("vote_Average").asDouble();
        String originalTitle = firstResult.path("original_title").asText();
        movie.setOverview(overview);
        movie.setLanguage(language);
        movie.setVoteAverage(voteAverage);
        movie.setReleaseDate(releaseDate);
        movie.setMovieName(originalTitle);*/
    }
    //OR ELSE THROW VERIFICA daca e true
    public JsonNode getResponseBodyJson(String requestBaseUrl, String movieName) throws JsonProcessingException {
        URI url = new UriTemplate(requestBaseUrl).expand(apiKey, movieName);
        return makeAPICall(url);
    }

    public JsonNode getResponseBodyJson(String requestBaseUrl, Integer movieId) throws JsonProcessingException {
        URI url = new UriTemplate(requestBaseUrl).expand(movieId, apiKey);
        return makeAPICall(url);
    }

    private JsonNode makeAPICall(URI url) throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(response.getBody());
    }

    private void generateProjections(AddMovieDTO addMovieDTO, Movie movie, CinemaRoom foundCinemaRoom) {
        //addMovieDTO - date - forEach - projectionDTO
        //EXC Optional: metoda canProjectionBeAdded
        //cream un obiect Projection - setam atributele
        //relatie BIdirectionala
        addMovieDTO.getDates().forEach(projectionDTO -> {
            Optional<Projection> interferingProjection = canProjectionBeAdded(foundCinemaRoom, projectionDTO);
            if (interferingProjection.isPresent()) {
                throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, "there is already a projection between following dates" + " " + interferingProjection.get().getStartTime() + " " + interferingProjection.get().getEndTime());
            }
            // addMovieDTO.getDates().forEach(projectionDTO -> {
            Projection projection = new Projection();
            projection.setStartTime(projectionDTO.getStartTime());
            projection.setEndTime(projectionDTO.getEndTime());
            projection.setMovie(movie);
            movie.getProjectionList().add(projection);
            generateTicketsForProjection(foundCinemaRoom, projection);
        });
    }

    private Optional<Projection> canProjectionBeAdded(CinemaRoom foundCinemaRoom, ProjectionDTO projection) {
        for (Movie movie : foundCinemaRoom.getMovieList()) {
            for (Projection existingProjection : movie.getProjectionList()) {
                if (!(projection.getEndTime().isBefore(existingProjection.getStartTime()) || projection.getStartTime().isAfter(existingProjection.getEndTime()))) {
                    return Optional.of(existingProjection);
                }
            }
        }
        return Optional.empty();
    }

    public void generateTicketsForProjection(CinemaRoom foundCinemaRoom, Projection projection) {
        for (Seat seat : foundCinemaRoom.getSeatList()) {//int i = 0; i < foundCinemaRoom.getSeatList().size(); i++
            Ticket ticket = new Ticket();
            ticket.setAvailable(true);
            ticket.setProjection(projection);
            projection.getTicketList().add(ticket);
            ticket.setSeat(seat); //foundCinemaRoom.getSeatList().get(i)
        }
    }


    //Vad lista de locuri disponibile la un film
    //Hint - asta numai daca filmul are date la care se va difuza in viitor
    public List<Projection> getAllProjectionsAvailable(Long movieId) {
        //1.daca projection este din viitor
        //2.daca are locuri disponibile
        Movie foundMovie = movieRepository.findById(movieId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the movie was not found"));
//        List<Projection> projectionsAvailable = new ArrayList<>();
//        for (Projection projection : foundMovie.getProjectionList()) {
//            if (projection.getStartTime().isAfter(LocalDateTime.now())) {
//                boolean hasProjectionAvailableTickets = projection.getTicketList().stream()
//                        .anyMatch(ticket -> ticket.getAvailable().equals(true));
//                if (hasProjectionAvailableTickets){
//                    projectionsAvailable.add(projection);
//                }
//            }
//        }
//        return projectionsAvailable;

        return foundMovie.getProjectionList().stream()
                .filter(projection -> projection.getStartTime().isAfter(LocalDateTime.now()))
                .filter(projection -> hasProjectionAvailableTickets(projection))
                .collect(Collectors.toList());
    }

    public boolean hasProjectionAvailableTickets(Projection projection) {
        return projection.getTicketList().stream()
                .anyMatch(ticket -> ticket.getAvailable());
    }
}
