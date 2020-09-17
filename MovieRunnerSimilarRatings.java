import java.util.*;

public class MovieRunnerSimilarRatings {
    public void printAverageRatings() {
        int minimalRaters = 35;
        FourthRatings ratings = new FourthRatings();
        RaterDatabase.initialize("ratings.csv");
        System.out.println("Number of raters: "+ RaterDatabase.size());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        ArrayList<Rating> avgRatingList = ratings.getAverageRatings(minimalRaters);
        Collections.sort(avgRatingList);

        for(Rating r: avgRatingList){
            System.out.println(r.getValue()+ " : " + MovieDatabase.getTitle(r.getItem()));
        }

        System.out.println("found " + avgRatingList.size() + " movies");
    }
    
    public void printAverageRatingsByYearAfterAndGenre(){
        int minimalRaters = 8;
        int yearCount = 1990;
        String selecGenre = "Drama";

        FourthRatings ratings = new FourthRatings();
        RaterDatabase.initialize("ratings.csv");
        System.out.println("read data for "+ RaterDatabase.size() + " raters");

        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("read data for "+ MovieDatabase.size() + " movies");

        Filter yf = new YearAfterFilter(yearCount);
        Filter gf = new GenreFilter(selecGenre);
        AllFilters filtersList = new AllFilters();
        filtersList.addFilter(yf);
        filtersList.addFilter(gf);

        ArrayList<Rating> list = ratings.getAverageRatingsByFilter(minimalRaters,filtersList);
        Collections.sort(list);

        for(Rating r: list){
            String Title = MovieDatabase.getTitle(r.getItem());
            String Genre = MovieDatabase.getGenres(r.getItem());
            int Year = MovieDatabase.getYear(r.getItem());

            System.out.println(r.getValue()+ " " + Year +" "+ Title );
            System.out.println("       "+ Genre);
        }
        System.out.println(list.size() + " movie " + "matched");
    }
    
    public void printSimilarRatings() {
        FourthRatings ratings = new FourthRatings();
        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        ArrayList<Rating> similarRatings = ratings.getSimilarRatings("71", 20, 5);
        String topID = similarRatings.get(0).getItem();
        System.out.println("Top movie: " + MovieDatabase.getMovie(topID));
        
    }
    
    private void printSimilarRatingsByFilter(Filter filter, String id, int minimalRaters, int numSimilarRaters) {
        FourthRatings ratings = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        ArrayList<Rating> similarRatings = ratings.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, filter);
        Rating topID = similarRatings.get(0);
        System.out.println("Top movie: " + MovieDatabase.getMovie(topID.getItem()));
    }
    
    public void printSimilarRatingsByGenre() {
        System.out.println("Looking up by genre");
        printSimilarRatingsByFilter(new GenreFilter("Mystery"), "964", 5, 20);
    }
    
    public void printSimilarRatingsByDirector() {
        System.out.println("Looking up by director");
        printSimilarRatingsByFilter(new DirectorsFilter("Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh"), "120", 2, 10);
    }
    
    public void printSimilarRatingsByGenreAndMinutes() {
        System.out.println("Looking up by genre and minutes");
        AllFilters filtersList = new AllFilters();
        filtersList.addFilter(new MinutesFilter(80, 160));
        filtersList.addFilter(new GenreFilter("Drama"));
        printSimilarRatingsByFilter(filtersList, "168", 3, 10);
    }
    
    public void printSimilarRatingsByYearAndMinutes() {
        System.out.println("Looking up by genre and minutes");
        AllFilters filtersList = new AllFilters();
        filtersList.addFilter(new MinutesFilter(70, 200));
        filtersList.addFilter(new YearAfterFilter(1975));
        printSimilarRatingsByFilter(filtersList, "314",5,10);
    }
}
