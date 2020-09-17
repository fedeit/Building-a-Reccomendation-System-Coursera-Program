import java.util.*;

public class RecommendationRunner implements Recommender {
    public ArrayList<String> getItemsToRate () {
        ArrayList<String> results = new ArrayList<String> ();
        ArrayList<String> movieIDs = MovieDatabase.filterBy(new TrueFilter());
        for (int i=0; i < 20; i++) {
            Random rand = new Random();
            int randomMovie = rand.nextInt(movieIDs.size());
            if (!results.contains(movieIDs.get(randomMovie))) {
                results.add(movieIDs.get(randomMovie));
            }
        }
        return results;
    }
    
    public void printRecommendationsFor (String webRaterID) {
        FourthRatings fourthRatings = new FourthRatings ();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        ArrayList<Rating> ratings = fourthRatings.getSimilarRatings(webRaterID, 20, 5);
        
        String html = "";
        if (ratings.size() == 0) {
            html += "<p>No movies found<p>";
        } else {
           html += "<table><tr><th>Movie Title</th><th>Rating</th><th>Genres</th></tr>";
           int limit = 20;
           for (Rating rating: ratings) {
               if (limit == 0) {
                   break;
               }
               html += "<tr><td>" + MovieDatabase.getTitle(rating.getItem()) + "</td><td>" + Double.toString(rating.getValue()) + "</td><td>" + MovieDatabase.getGenres(rating.getItem()) + "</td></tr>";
               limit -= 1;
            }
           html += "</table>";
        }
        System.out.print(html);
    }

}
