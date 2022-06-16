package com.movies.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.management.Attribute;

import com.movies.models.Movie;
import com.movies.utils.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;


@Repository
public class MovieRepository  {
	private static Logger logger = LoggerFactory.getLogger(MovieRepository.class);

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Movie save(Movie book){
        dynamoDBMapper.save(book);
        return book;
    }

    public List<Movie> saveAll(List<Movie> movieList){
        dynamoDBMapper.batchSave(movieList);
        return movieList;
    }
    
    public Movie findById(String id){
       return dynamoDBMapper.load(Movie.class, id);
    }

    public List<Movie> findAll(){
        return dynamoDBMapper.scan(Movie.class, new DynamoDBScanExpression());
    }

    public String update(String id, Movie movie){
        dynamoDBMapper.save(movie,
                new DynamoDBSaveExpression()
        .withExpectedEntry("imdb_title_id",
                new ExpectedAttributeValue(
                        new AttributeValue().withS(id)
                )));
        return id;
    }

    public String delete(String id){
    	Movie movie = dynamoDBMapper.load(Movie.class, id);
        dynamoDBMapper.delete(movie);
        return "Book deleted successfully:: "+id;
    }
    
    public List<Movie> filterByDirectorAndYear(String director, String startYear, String endYear){
    	HashMap<String,AttributeValue> attrValueMap = new HashMap<>();
    	attrValueMap.put(":d", new AttributeValue().withS(director));
    	attrValueMap.put(":sy", new AttributeValue().withS(startYear));
    	attrValueMap.put(":ey", new AttributeValue().withS(endYear));
    	
    	HashMap<String,String> reservedMap = new HashMap<>();
    	reservedMap.put("#year", "year");
    	
    	DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
    			.withFilterExpression("director = :d and #year BETWEEN :sy AND :ey")
    			.withExpressionAttributeValues(attrValueMap)
    			.withExpressionAttributeNames(reservedMap);
    	
    	List<Movie> movies = dynamoDBMapper.scan(Movie.class,dynamoDBScanExpression);
    	return movies;
    }

	public List<Movie> filterByReviews(Integer reviewFromUser) {
		HashMap<String, AttributeValue> attrValueMap = new HashMap<>();
		attrValueMap.put(":ureview", new AttributeValue().withN(String.valueOf(reviewFromUser)));
		
		DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
				.withFilterExpression("reviews_from_users > :ureview")
				.withExpressionAttributeValues(attrValueMap);
		
		List<Movie> movies = dynamoDBMapper.scan(Movie.class,dynamoDBScanExpression);
		
		List<Movie> movieList = new ArrayList<>();
		movieList.addAll(movies);
		
		Collections.sort(movieList,new Movie.OrderByReviews());
		logger.info("filterByReviews->outputSize -> "+movieList.size());
		return movieList;
	}

	public List<Movie> highestBudget(String year, String country) {
		HashMap<String,AttributeValue> attrValueMap = new HashMap<>();
    	attrValueMap.put(":y", new AttributeValue().withS(year));
    	attrValueMap.put(":c", new AttributeValue().withS(country));
    	
		HashMap<String,String> reservedMap = new HashMap<>();
    	reservedMap.put("#year", "year");
    	
    	DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
    			.withFilterExpression("#year = :y and country = :c")
    			.withExpressionAttributeValues(attrValueMap)
    			.withExpressionAttributeNames(reservedMap);
    	
    	
    	List<Movie> movies = dynamoDBMapper.scan(Movie.class,dynamoDBScanExpression);
    	List<Integer> budgetList = new ArrayList<>();
    	
//    	for (Movie movie : movies) {
//			String[] budget = movie.getBudget().trim().split(" ");
//			budgetList.add(Integer.parseInt(budget[1]));
//		}
		logger.info("filterByReviews->outputSize -> "+movies.size());
		return movies;
	}


}
