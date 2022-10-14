package com.example.figurereviewsservice;


import com.example.figurereviewsservice.repository.FigureReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class FigureReviewControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FigureReviewRepository reviewRepository;

    private ObjectMapper mapper = new ObjectMapper();


}
