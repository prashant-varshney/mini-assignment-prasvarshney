package com.movies.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.movies.models.Movie;
import com.movies.services.MovieService;

public class Utils {
	private static Logger logger = LoggerFactory.getLogger(Utils.class);
	
	   public static List<Movie> readMoviesFromCSV() {
	        List<Movie> movies = new ArrayList<>();       
	        try {
	        	logger.info("Reading the CSV file from directory");
	        	BufferedReader br = new BufferedReader(new FileReader("src/main/resources/movies.csv"));
	            String line = br.readLine();
	            logger.info("Splitting the records based on regex for each line");
	            while ((line = br.readLine()) != null) {
	                String[] attributes =  line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);// use comma as separator
	                
	                Movie movie = createMovie(attributes);

	                movies.add(movie);
	               
	            }

	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	        }
	        logger.info("Data Mapped successfully from csv to Model");
	        return movies;
	    }



	
    private static Movie createMovie(String[] metadata) {
    	// create and return movie of this metadata;
        return new Movie(metadata[0], metadata[1], metadata[2],metadata[3],metadata[4],metadata[5],metadata[6],metadata[7],metadata[8],metadata[9],metadata[10],metadata[11],metadata[12],metadata[13],metadata[14],metadata[15],metadata[16],metadata[17],metadata[18],metadata[19],metadata[20] != ""?Integer.parseInt(metadata[20]):0,metadata[21]);

}




}
