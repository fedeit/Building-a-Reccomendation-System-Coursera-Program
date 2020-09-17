import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(String moviefile, String ratingsfile) {
        FirstRatings firstRartings = new FirstRatings();
        myMovies = firstRartings.loadMovies(moviefile);
        myRaters = firstRartings.loadRaters(ratingsfile);
    }
    
    public int getMovieSize() {
        return myMovies.size();
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
        ArrayList<Rating> avgMovieRatings = new ArrayList<Rating>();
        for (Movie movie: myMovies) {
            double average = getAverageByID(movie.getID(), minimalRaters);
            if(average > 0.0){
                Rating rating = new Rating(movie.getID(), average);
                avgMovieRatings.add(rating);
            }    
        }
        return avgMovieRatings;
    }
    
    public String getTitle(String id) {
        for (Movie movie: myMovies) {
            if (movie.getID().equals(id)) {
                return movie.getTitle();
            }
        }
        return "NO SUCH ID.";
    }
    
    public String getID(String title) {
        for (Movie movie: myMovies) {
            if (movie.getTitle().equals(title)) {
                return movie.getID();
            }
        }
        return "NO SUCH TITLE.";
    }   
}
