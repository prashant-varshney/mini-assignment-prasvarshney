package com.movies.services;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movies.models.Movie;
import com.movies.repository.MovieRepository;
import com.movies.utils.Utils;

@Service
public class MovieService {
	
	private static Logger logger = LoggerFactory.getLogger(MovieService.class);
	
	@Autowired
    private MovieRepository movieRepository;
	
	@PostConstruct
	public void init() throws IOException{
		List<Movie> movies = Utils.readMoviesFromCSV();
		logger.info("-----------"+movies.get(0).getActors());
	    movieRepository.saveAll(movies);	        
	}

	public Movie findById(String id) {
		logger.info("find movie by id" + this.getClass().getName());
	    return movieRepository.findById(id);
	}


	public Movie save(Movie movie) {
        logger.info("save movie " + this.getClass().getName());
        return movieRepository.save(movie);
	}


	public List<Movie> findAll() {
        logger.info("findAll movies " + this.getClass().getName());
        return movieRepository.findAll();
	}

	public List<Movie> filterByDirectorYear(String director, String startYear, String endYear) {
		logger.info("filter movies by director and years" + this.getClass().getName());
        return movieRepository.filterByDirectorAndYear(director,startYear,endYear);
	}

	public List<Movie> filterByReviews(Integer reviewFromUser) {
		logger.info("filterByReviews" + this.getClass().getName());
		return movieRepository.filterByReviews(reviewFromUser);
	}

	public List<Movie> highestBudget(String year, String country) {
		logger.info("highestBudget" + this.getClass().getName());
		return movieRepository.highestBudget(year, country);
	}
	

}
