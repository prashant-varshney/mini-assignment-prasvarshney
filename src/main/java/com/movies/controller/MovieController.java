package com.movies.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.movies.models.Movie;
import com.movies.services.MovieService;


@RestController
@RequestMapping("/movies")
public class MovieController {
	private static Logger logger = LoggerFactory.getLogger(MovieService.class);

	@Autowired
    private MovieService movieService;
	
	 @PostMapping
	    public Movie save(@RequestBody Movie movie){
	        logger.info("save movie " + this.getClass().getName());
	        return movieService.save(movie);
	    }
	 
	@GetMapping
	    public List<Movie> findAll(){
	        logger.info("findAll movies " + this.getClass().getName());
	        return movieService.findAll();
	    }
	 
	@GetMapping("/query/filter_by_director_year")
	@ResponseBody
    public List<Movie> filterByDirectorYear(@RequestParam("director") String director,@RequestParam("start_year") String startYear,@RequestParam("end_year") String endYear) {
        logger.info("***filterByDirectorYear movies**** " + director+" "+startYear+" "+endYear);
        return movieService.filterByDirectorYear(director,startYear,endYear);
    }
	
	@GetMapping("/query/filter_by_reviews")
	@ResponseBody
    public List<Movie> filterByReviews(@RequestParam("user_reviews") Integer reviewFromUser){
		logger.info("filterByReviews query param -> " + reviewFromUser);
		return movieService.filterByReviews(reviewFromUser);}
	
	@GetMapping("/query/highest_budget")
	@ResponseBody
    public List<Movie> highestBudget(@RequestParam("year") String year, @RequestParam("country") String country){
		logger.info("highestBudget query param -> " + year+" "+country);
		return movieService.highestBudget(year, country);}
	
    @GetMapping("/{id}")
    public Movie findById(@PathVariable(value = "id") String id){
        logger.info("find movie by id" + this.getClass().getName());
        return movieService.findById(id);
    }

}
