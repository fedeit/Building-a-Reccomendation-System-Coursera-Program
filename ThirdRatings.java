import java.util.*;

public class ThirdRatings {
    private ArrayList<Rater> myRaters;
    
    public ThirdRatings() {
        this("ratings.csv");
    }
    
    public ThirdRatings(String ratingsfile) {
        FirstRatings firstRartings = new FirstRatings();
        myRaters = firstRartings.loadRaters(ratingsfile);
    }
    
    public int getRaterSize() {
        return myRaters.size();
    }
    
    private double getAverageByID(String id, int minimalRaters) {
        double total = 0.0;
        int ratings = 0;
        for (Rater rater: myRaters) {
            if (rater.hasRating(id)) {
                total += rater.getRating(id);
                ratings += 1;
            }
        }
        if (ratings >= minimalRaters && ratings != 0) {
            return total / ratings;
        }
        return 0.0;
    }
    
    public ArrayList getAverageRatings(int minimalRaters) {
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> avgMovieRatings = new ArrayList<Rating>();
        for (String movie: movies) {
            double average = getAverageByID(movie, minimalRaters);
            if(average > 0.0){
                Rating rating = new Rating(movie, average);
                avgMovieRatings.add(rating);
            }    
        }
        return avgMovieRatings;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> avgMovieRatings = new ArrayList<Rating>();
        for (String movie: movies) {
            double average = getAverageByID(movie, minimalRaters);
            if(average > 0.0){
                Rating rating = new Rating(movie, average);
                avgMovieRatings.add(rating);
            }    
        }
        return avgMovieRatings;
    }
}
