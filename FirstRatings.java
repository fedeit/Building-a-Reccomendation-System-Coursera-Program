import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {
    public ArrayList<Movie>loadMovies(String filename) {
        ArrayList<Movie> list = new ArrayList<Movie>();
        FileResource csvData = new FileResource(filename);
         for (CSVRecord csvRecord : csvData.getCSVParser()) {
            Movie movie = new Movie(csvRecord.get("id"),csvRecord.get("title"),csvRecord.get("year"),csvRecord.get("genre"),csvRecord.get("director"),csvRecord.get("country"),csvRecord.get("poster"),Integer.parseInt(csvRecord.get("minutes")));
            list.add(movie);
        }
        System.out.println("Movies: " + list.size());
        return list;
    }
    
    public ArrayList<Rater>loadRaters(String filename) {
        HashMap<String,Rater> list = new HashMap<String, Rater>();
        FileResource csvData = new FileResource(filename);
        for (CSVRecord csvRecord : csvData.getCSVParser()) {
            String rater_id = csvRecord.get("rater_id");
            String movie_id = csvRecord.get("movie_id");
            double rating = Double.parseDouble(csvRecord.get("rating"));
            if (list.containsKey(rater_id)) {
                Rater copy = list.get(rater_id);
                copy.addRating(movie_id, rating);
                list.put(rater_id, copy);
            } else {
                Rater newRater = new EfficientRater(rater_id);
                newRater.addRating(movie_id, rating);
                list.put(rater_id, newRater);
            }
        }
        ArrayList<Rater> listarr = new ArrayList<Rater>(list.values());
        System.out.println("Raters: " + listarr.size());
        return listarr;
    }
    
    public void testLoadMovies() {
        ArrayList<Movie> movies = loadMovies("ratedmoviesfull.csv");
        System.out.println(movies.size());
        HashMap<String, Integer> directors = new HashMap<String, Integer>(); 
        int comedy = 0, over150 = 0;
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            if (movie.getGenres().contains("Comedy")) {
                comedy += 1;
            }
            if (movie.getMinutes() > 150) {
                over150 += 1;
            }

            String director = movie.getDirector();
            if(directors.containsKey(director)){
                directors.put(director, directors.get(director)+1);
            } else{
                directors.put(director,1);
            }
        }
        System.out.println("Comedy: " + comedy);
        System.out.println("over150: " + over150);
        System.out.println("Directors count " + directors.size());
        int maxValueInMap=(Collections.max(directors.values()));
        System.out.println("Max movies for one director " + maxValueInMap);
        String key = Collections.max(directors.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println("Director max " + key);
    }
    
    public void testLoadRaters() {
        ArrayList<Rater> raters = loadRaters("ratings.csv");
        System.out.println(raters.size());
        Rater maxRater = new EfficientRater("");
        HashSet<String> allMovies= new HashSet<String>();
        int found = 0;
        for (int i = 0; i < raters.size(); i++) {
            Rater rater = raters.get(i);
            if (rater.getID().equals("193")) {
                System.out.println("Rater" + rater.getID() + " has " + rater.numRatings() + " ratings");
            }
            if (rater.numRatings() > maxRater.numRatings()) {
                maxRater = rater;
            }
            for (String rating: rater.getItemsRated()) {
                allMovies.add(rating);
            }
            if (rater.hasRating("1798709")) {
                found += 1;
            }
        }
        System.out.println("Movie 1798709 has " + found + " reviews: ");
        System.out.println("Total number of unique movies that have been rated: " + allMovies.size());
        System.out.println("Rater: " + maxRater.getID() + " has the max number of ratings: " + maxRater.numRatings() + " ");
    }
}
