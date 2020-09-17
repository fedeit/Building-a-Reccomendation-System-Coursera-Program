import java.util.*;

public class FourthRatings {        
    private double getAverageByID(String id, int minimalRaters) {
        double total = 0.0;
        int ratings = 0;
        for (Rater rater: RaterDatabase.getRaters()) {
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
        for (String movie: MovieDatabase.filterBy(new TrueFilter())) {
            double average = getAverageByID(movie, minimalRaters);
            if(average > 0.0){
                Rating rating = new Rating(movie, average);
                avgMovieRatings.add(rating);
            }    
        }
        return avgMovieRatings;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> avgMovieRatings = new ArrayList<Rating>();
        for (String movie: MovieDatabase.filterBy(filterCriteria)) {
            double average = getAverageByID(movie, minimalRaters);
            if(average > 0.0){
                Rating rating = new Rating(movie, average);
                avgMovieRatings.add(rating);
            }    
        }
        return avgMovieRatings;
    }
    
    private double dotProduct(Rater me, Rater r) {
        double dotProd = 0.0;
        ArrayList<String> movieIDs = me.getItemsRated();
        for (String id: movieIDs) {
            double rawDotProd = (r.getRating(id) - 5.0) * (me.getRating(id) - 5.0);
            if (r.hasRating(id) && rawDotProd > 0.0) {
                dotProd += rawDotProd;
            }
        }
        return dotProd;
    }
    
    public ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> results = new ArrayList<Rating>();
        Rater me = RaterDatabase.getRater(id);
        for (Rater rater: RaterDatabase.getRaters()) {
            double dotProd = dotProduct(me, rater);
            if (!rater.getID().equals(id) && dotProd >= 0) { 
                results.add(new Rating(rater.getID(), dotProd));
            }
        }
        Collections.sort(results, Collections.reverseOrder());
        return results;
    }
    
    private HashMap<String, Double> ratingsArrayToMap(ArrayList<Rating> ratingsArray) {
        HashMap<String, Double> map = new HashMap<String, Double>();
        for (Rating rating: ratingsArray) {
            map.put(rating.getItem(), rating.getValue());
        }
        return map;
    }
    
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) {
        return getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, new TrueFilter());
    }
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> weightedRatings = new ArrayList<Rating> ();
        ArrayList<Rating> similarRatings = getSimilarities(id);

        for (String movieID : MovieDatabase.filterBy(filterCriteria)) {
            double movieRating = 0.0, counter = 0.0;
            for (int k=0; k < numSimilarRaters; k++) {
                Rating rating = similarRatings.get(k);
                Rater rater = RaterDatabase.getRater(rating.getItem());
                if (rater.hasRating(movieID)) {
                    movieRating += rater.getRating(movieID) * rating.getValue();
                    counter += 1;
                }
            }
            if (counter >= minimalRaters) {
                weightedRatings.add(new Rating (movieID, (Math.round((movieRating / counter) * 100.0) / 100.0)));
            }
        }
        
        Collections.sort(weightedRatings, Collections.reverseOrder());
        return weightedRatings;
    }
}