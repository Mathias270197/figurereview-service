package com.example.figurereviewsservice;

import com.example.figurereviewsservice.model.FigureReview;
import com.example.figurereviewsservice.repository.FigureReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FigureReviewControllerIntegrationTests {

    /*@Autowired
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
    private FigureReview reviewToBeDeleted = new FigureReview("Car" ,textReview4, 5, "Stijn");

    @BeforeEach
    public void beforeAllTests() {
        FigureReviewRepository.deleteAll();
        FigureReviewRepository.save(reviewUser1Book1);
        FigureReviewRepository.save(reviewUser1Book2);
        FigureReviewRepository.save(reviewUser2Book1);
        FigureReviewRepository.save(reviewToBeDeleted);
    }

    @AfterEach
    public void afterAllTests() {
        //Watch out with deleteAll() methods when you have other data in the test database!
        FigureReviewRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenFigureReview_whenGetFigureReviews_thenReturnJsonFigureReview() throws Exception {

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
                .andExpect(jsonPath("$[3].user", is("Stijn")));
    }*/


}
