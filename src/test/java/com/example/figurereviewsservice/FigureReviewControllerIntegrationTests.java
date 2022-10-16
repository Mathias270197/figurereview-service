package com.example.figurereviewsservice;

import com.example.figurereviewsservice.model.FigureReview;
import com.example.figurereviewsservice.repository.FigureReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FigureReviewControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FigureReviewRepository figureReviewRepository;

    private String textReview1 = "Stukken passen niet goed";
    private String textReview2 = "Moeilijke stappen";
    private String textReview3 = "Eenvoudige stappen";
    private String textReview4 = "Eenvoudige stappen!";


    private FigureReview reviewStijnDuck = new FigureReview("Duck" ,textReview1, 2, "Stijn");
    private FigureReview reviewMathiasDuck = new FigureReview("Duck" ,textReview2, 3, "Mathias");
    private FigureReview reviewStijnChicken = new FigureReview("Chicken" ,textReview3, 4, "Stijn");
    private FigureReview reviewToBeDeleted = new FigureReview("Car" ,textReview4, 3, "Stijn");

    @BeforeEach
    public void beforeAllTests() {
        figureReviewRepository.deleteAll();
        figureReviewRepository.save(reviewStijnDuck);
        figureReviewRepository.save(reviewMathiasDuck);
        figureReviewRepository.save(reviewStijnChicken);
        figureReviewRepository.save(reviewToBeDeleted);
    }

    @AfterEach
    public void afterAllTests() {
        //Watch out with deleteAll() methods when you have other data in the test database!
        figureReviewRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenFigureReview_whenGetFigureReviews_thenReturnJson() throws Exception {

        mockMvc.perform(get("/figureReviews"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].figureName", is("Duck")))
                .andExpect(jsonPath("$[1].figureName", is("Duck")))
                .andExpect(jsonPath("$[2].figureName", is("Chicken")))
                .andExpect(jsonPath("$[3].figureName", is("Car")))
                .andExpect(jsonPath("$[0].textReview", is(textReview1)))
                .andExpect(jsonPath("$[1].textReview", is(textReview2)))
                .andExpect(jsonPath("$[2].textReview", is(textReview3)))
                .andExpect(jsonPath("$[3].textReview", is(textReview4)))
                .andExpect(jsonPath("$[0].user", is("Stijn")))
                .andExpect(jsonPath("$[1].user", is("Mathias")))
                .andExpect(jsonPath("$[2].user", is("Stijn")))
                .andExpect(jsonPath("$[3].user", is("Stijn")))
                .andExpect(jsonPath("$[0].stars", is(2)))
                .andExpect(jsonPath("$[1].stars", is(3)))
                .andExpect(jsonPath("$[2].stars", is(4)))
                .andExpect(jsonPath("$[3].stars", is(3)));
    }


    @Test
    public void givenFigureReview_whenGetFigureReviewsByName_thenReturnJson() throws Exception {

        mockMvc.perform(get("/figureReviewsByName/{figureName}", "Duck"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].figureName", is("Duck")))
                .andExpect(jsonPath("$[1].figureName", is("Duck")))
                .andExpect(jsonPath("$[0].textReview", is(textReview1)))
                .andExpect(jsonPath("$[1].textReview", is(textReview2)))
                .andExpect(jsonPath("$[0].user", is("Stijn")))
                .andExpect(jsonPath("$[1].user", is("Mathias")))
                .andExpect(jsonPath("$[0].stars", is(2)))
                .andExpect(jsonPath("$[1].stars", is(3)));
    }

    @Test
    public void givenFigureReview_whenGetFigureReviewByStars_thenReturnJson() throws Exception {

        mockMvc.perform(get("/figureNamesByStars/{stars}", "3"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].figureName", is("Duck")))
                .andExpect(jsonPath("$[1].figureName", is("Car")))
                .andExpect(jsonPath("$[0].textReview", is(textReview2)))
                .andExpect(jsonPath("$[1].textReview", is(textReview4)))
                .andExpect(jsonPath("$[0].user", is("Mathias")))
                .andExpect(jsonPath("$[1].user", is("Stijn")))
                .andExpect(jsonPath("$[0].stars", is(3)))
                .andExpect(jsonPath("$[1].stars", is(3)));
    }

    @Test
    public void givenFigureReview_whenGetFigureReviewByFigureName_thenReturnJson() throws Exception {

        mockMvc.perform(get("/figureReviewByNameAndUser/{figureName}/{user}", "Duck", "Stijn"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.figureName", is("Duck")))
                .andExpect(jsonPath("$.textReview", is(textReview1)))
                .andExpect(jsonPath("$.user", is("Stijn")))
                .andExpect(jsonPath("$.stars", is(2)));
    }


    @Test
    public void whenPostFigureReview_thenReturnJson() throws Exception {
        FigureReview reviewMathiasChicken = new FigureReview("Chicken" ,textReview3, 1, "Mathias");

        mockMvc.perform(post("/figureReview")
                        .content(mapper.writeValueAsString(reviewMathiasChicken))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.figureName", is("Chicken")))
                .andExpect(jsonPath("$.textReview", is(textReview3)))
                .andExpect(jsonPath("$.user", is("Mathias")))
                .andExpect(jsonPath("$.stars", is(1)));
    }

    @Test
    public void givenFigureReview_whenPutFigureReview_thenReturnJson() throws Exception {

        FigureReview updateReviewMathiasChicken = new FigureReview("Duck" ,textReview2, 3, "Stijn");

        mockMvc.perform(put("/figureReview")
                        .content(mapper.writeValueAsString(updateReviewMathiasChicken))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.figureName", is("Duck")))
                .andExpect(jsonPath("$.textReview", is(textReview2)))
                .andExpect(jsonPath("$.user", is("Stijn")))
                .andExpect(jsonPath("$.stars", is(3)));
    }

    @Test
    public void givenNoFigureReview_whenDeleteReview_thenStatusNotFound() throws Exception {

        mockMvc.perform(delete("/figureReviews/name/{figureName}/user/{user}", "Car","Stijn")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }



}
