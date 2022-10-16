package com.example.figurereviewsservice;

import com.example.figurereviewsservice.model.FigureReview;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import com.example.figurereviewsservice.repository.FigureReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FigureReviewControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FigureReviewRepository figureReviewRepository;

    private ObjectMapper mapper = new ObjectMapper();

    private String textReview1 = "Stukken passen niet goed";
    private String textReview2 = "Moeilijke stappen";
    private String textReview3 = "Eenvoudige stappen";
    private String textReview4 = "Eenvoudige stappen!";

    private FigureReview reviewStijnDuck = new FigureReview("Duck" ,textReview1, 2, "Stijn");
    private FigureReview reviewMathiasDuck = new FigureReview("Duck" ,textReview2, 3, "Mathias");
    private FigureReview reviewStijnChicken = new FigureReview("Chicken" ,textReview3, 4, "Stijn");
    private FigureReview reviewToBeDeleted = new FigureReview("Car" ,textReview4, 3, "Stijn");


    @Test
    public void givenFigureReviews_whenGetFigureReviewByNameAndUser_thenReturnJsonReview() throws Exception {

        given(figureReviewRepository.findFigureReviewByFigureNameAndUser(reviewStijnDuck.getFigureName(), reviewStijnDuck.getUser())).willReturn(reviewStijnDuck);

        mockMvc.perform(get("/figureReviewByNameAndUser/{figureName}/{user}", reviewStijnDuck.getFigureName(), reviewStijnDuck.getUser()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.figureName", is("Duck")))
                .andExpect(jsonPath("$.textReview", is(textReview1)))
                .andExpect(jsonPath("$.user", is("Stijn")))
                .andExpect(jsonPath("$.stars", is(2)));
    }


    @Test
    public void givenFigureReviews_whenGetFigureReviews_thenReturnJson() throws Exception {

        List<FigureReview> figureReviewList = new ArrayList<>();
        figureReviewList.add(reviewStijnDuck);
        figureReviewList.add(reviewMathiasDuck);
        figureReviewList.add(reviewStijnChicken);
        figureReviewList.add(reviewToBeDeleted);

        given(figureReviewRepository.findAll()).willReturn(figureReviewList);

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
    public void givenFigureReviews_whenGetFigureReviewsByFigureName_thenReturnJson() throws Exception {

        List<FigureReview> figureReviewList = new ArrayList<>();
        figureReviewList.add(reviewStijnDuck);
        figureReviewList.add(reviewMathiasDuck);

        given(figureReviewRepository.findFigureReviewsByFigureName(reviewStijnDuck.getFigureName())).willReturn(figureReviewList);

        mockMvc.perform(get("/figureReviewsByName/{figureName}",reviewStijnDuck.getFigureName() ))
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
    public void givenFigureReviews_whenGetFigureNamesByStars_thenReturnJson() throws Exception {

        List<FigureReview> figureReviewList = new ArrayList<>();

        figureReviewList.add(reviewMathiasDuck);
        figureReviewList.add(reviewToBeDeleted);

        given(figureReviewRepository.findFigureReviewsByStars(3)).willReturn(figureReviewList);

        mockMvc.perform(get("/figureNamesByStars/{stars}",3))
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
    public void whenPostFigureReview_thenReturnJson() throws Exception{

        mockMvc.perform(post("/figureReview")
                        .content(mapper.writeValueAsString(reviewStijnDuck))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.figureName", is("Duck")))
                .andExpect(jsonPath("$.textReview", is(textReview1)))
                .andExpect(jsonPath("$.user", is("Stijn")))
                .andExpect(jsonPath("$.stars", is(2)));
    }


    @Test
    public void givenFigureReview_whenUpdateFigureReview_thenReturnJson() throws Exception{

        given(figureReviewRepository.findFigureReviewByFigureNameAndUser(reviewStijnDuck.getFigureName(), reviewStijnDuck.getUser())).willReturn(reviewStijnDuck);

        FigureReview updatedReviewStijnDuck = new FigureReview("Duck" ,textReview2, 5, "Stijn");

        mockMvc.perform(put("/figureReview")
                        .content(mapper.writeValueAsString(updatedReviewStijnDuck))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.figureName", is("Duck")))
                .andExpect(jsonPath("$.textReview", is(textReview2)))
                .andExpect(jsonPath("$.user", is("Stijn")))
                .andExpect(jsonPath("$.stars", is(5)));
    }

    @Test
    public void givenNoFigureReview_whenDeleteFigureReview_thenStatusNotFound() throws Exception{
        given(figureReviewRepository.findFigureReviewByFigureNameAndUser(reviewStijnDuck.getFigureName(), reviewStijnDuck.getUser())).willReturn(reviewStijnDuck);

        mockMvc.perform(delete("/figureReviews/name/{figureName}/user/{user}",reviewStijnDuck.getFigureName(), reviewStijnDuck.getUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());;
    }





}
