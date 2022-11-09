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
        String[] reviewArray={"Mooi figuurtje","Tof speelgoed","Stukken passen niet goed","Blokken zijn heel klein", "Leuk", "Duur maar leuk"};

        if(figureReviewRepository.count()==0){
            figureReviewRepository.save(new FigureReview("Apple" ,reviewArray[0], 5, "Stijn"));
            figureReviewRepository.save(new FigureReview("Apple" ,reviewArray[1], 4, "Mathias"));
            figureReviewRepository.save(new FigureReview("Apple" ,reviewArray[2], 2, "Stijn"));
            figureReviewRepository.save(new FigureReview("House" ,reviewArray[3], 3,"Mathias"));
            figureReviewRepository.save(new FigureReview("House" ,reviewArray[4], 5, "Stijn"));
            figureReviewRepository.save(new FigureReview("Robot" ,reviewArray[5], 5, "Mathias"));
            figureReviewRepository.save(new FigureReview("Robot" ,reviewArray[6], 4, "Stijn"));
            figureReviewRepository.save(new FigureReview("Robot" ,reviewArray[0], 3,"Stijn"));
        }
    };


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

