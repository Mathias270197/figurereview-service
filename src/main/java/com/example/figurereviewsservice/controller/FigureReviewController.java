package com.example.figurereviewsservice.controller;

import com.example.figurereviewsservice.model.FigureReview;
import com.example.figurereviewsservice.repository.FigureReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@RestController
public class FigureReviewController {

    @Autowired
    private FigureReviewRepository figureReviewRepository;

    @PostConstruct
    public void fillDB(){
        if(figureReviewRepository.count()==0){
            figureReviewRepository.save(new FigureReview("Apple" ,"Leuk figuurtje", 4, "Stijn"));
            figureReviewRepository.save(new FigureReview("Apple" ,"Moeilijk figuurtje", 2, "Mathias"));
            figureReviewRepository.save(new FigureReview("House" ,"Geen leuk figuur", 1,"Stijn"));
            figureReviewRepository.save(new FigureReview("House" ,"Schattig figuur", 3,"Mathias"));
        }
    }


    @GetMapping("/figureReviews")
    public List<FigureReview> getFigureReviews(){
        return figureReviewRepository.findAll();
    }

    @GetMapping("/figureReviewsByName/{figureName}")
    public List<FigureReview> getFigureReviewsByFigureName(@PathVariable String figureName){
        return figureReviewRepository.findFigureReviewsByFigureName(figureName);
    }

    @GetMapping("/figureNamesByStars/{stars}")
    public List<FigureReview> getFigureReviewByStars(@PathVariable Integer stars){
        return figureReviewRepository.findFigureReviewsByStars(stars);
    }

    @GetMapping("/figureReviewByNameAndUser/{figureName}/{user}")
    public FigureReview getFigureReviewByFigureName(@PathVariable String figureName,@PathVariable String user) {
        return figureReviewRepository.findFigureReviewByFigureNameAndUser(figureName,user);
    }

    @PostMapping ("/figureReview")
    public FigureReview addFigureReview(@RequestBody FigureReview figureReview){
        figureReviewRepository.save(figureReview);
        return figureReview;
    }

    @PutMapping("/figureReview")
    public FigureReview updateFigureReview(@RequestBody FigureReview updateFigureReview){
        FigureReview retrievedFigureReview = figureReviewRepository.findFigureReviewByFigureNameAndUser(updateFigureReview.getFigureName(), updateFigureReview.getUser());

//        retrievedFigureReview.setFigureName(updateFigureReview.getFigureName());
        retrievedFigureReview.setTextReview(updateFigureReview.getTextReview());
        retrievedFigureReview.setStars(updateFigureReview.getStars());
        retrievedFigureReview.setDate(updateFigureReview.getDate());

        figureReviewRepository.save(retrievedFigureReview);

        return retrievedFigureReview;
    }

    @DeleteMapping("figureReviews/name/{figureName}/user/{user}")
    public ResponseEntity deleteFigureReview(@PathVariable String figureName, @PathVariable String user){
        FigureReview figureReview = figureReviewRepository.findFigureReviewByFigureNameAndUser(figureName, user);
        if(figureReview!=null){
            figureReviewRepository.delete(figureReview);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }



}

