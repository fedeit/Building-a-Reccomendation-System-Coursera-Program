import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class MovieRunnerAverage {
    public void printAverageRatings() {
        int minimalRaters = 12;
        SecondRatings sr = new SecondRatings("ratedmoviesfull.csv","ratings.csv");
        System.out.println("Number of rated movie: "+ sr.getMovieSize());
        System.out.println("Number of raters: "+ sr.getRaterSize());
        
        ArrayList<Rating> avgRatingList = sr.getAverageRatings(minimalRaters);
        Collections.sort(avgRatingList);

        for(Rating r:avgRatingList){
            System.out.println(r.getValue()+ " : " + sr.getTitle(r.getItem()));
        }  

        System.out.println("avgRatingList size " + avgRatingList.size());

    }   
    
    public void getAverageRatingOneMovie(){
        int minimalRaters=0;
        
        String title = "No Country for Old Men";
        SecondRatings sr = new SecondRatings("ratedmovies_short.csv","ratings_short.csv");

        ArrayList<Rating> avgRatingList = sr.getAverageRatings(minimalRaters);
        for(Rating rating:avgRatingList){
            if(rating.getItem().equals(sr.getID(title))){
                System.out.println("Average rating for '" + title + "' is: " + rating.getValue());
            }

        }
        System.out.println("There are " + avgRatingList.size() + " movies that has  more than "+ minimalRaters + " ratings");
    }
}
