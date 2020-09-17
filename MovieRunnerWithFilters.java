import java.util.*;

public class MovieRunnerWithFilters {
    public void printAverageRatings() {
        int minimalRaters = 35;
        ThirdRatings sr = new ThirdRatings("ratings.csv");
        System.out.println("Number of raters: "+ sr.getRaterSize());
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        ArrayList<Rating> avgRatingList = sr.getAverageRatings(minimalRaters);
        Collections.sort(avgRatingList);

        for(Rating r: avgRatingList){
            System.out.println(r.getValue()+ " : " + MovieDatabase.getTitle(r.getItem()));
        }

        System.out.println("found " + avgRatingList.size() + " movies");
    }
    
    public void printAverageRatingsByYear() {
        int minimalRaters = 20;
        ThirdRatings sr = new ThirdRatings("ratings.csv");
        System.out.println("Number of raters: "+ sr.getRaterSize());
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        ArrayList<Rating> avgRatingList = sr.getAverageRatingsByFilter(minimalRaters, new YearAfterFilter(2000));
        Collections.sort(avgRatingList);

        for(Rating r: avgRatingList){
            System.out.println(r.getValue()+ " : " + MovieDatabase.getTitle(r.getItem()));
        }

        System.out.println("found " + avgRatingList.size() + " movies");
    }
    
    public void printAverageRatingsByGenre() {
        int minimalRaters = 20;
        ThirdRatings sr = new ThirdRatings("ratings.csv");
        System.out.println("Number of raters: "+ sr.getRaterSize());
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        ArrayList<Rating> avgRatingList = sr.getAverageRatingsByFilter(minimalRaters, new GenreFilter("Comedy"));
        Collections.sort(avgRatingList);

        for(Rating r: avgRatingList){
            System.out.println(r.getValue()+ " : " + MovieDatabase.getTitle(r.getItem()));
        }

        System.out.println("found " + avgRatingList.size() + " movies");
    }
    
    public void printAverageRatingsByDirectors(){
        int minimalRaters = 4;

        String directors = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";

        ThirdRatings tr = new ThirdRatings("ratings.csv");
        System.out.println("read data for "+ tr.getRaterSize() + " raters");

        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("read data for "+ MovieDatabase.size() + " movies");

        Filter dr = new DirectorsFilter(directors);
        ArrayList<Rating> list = tr.getAverageRatingsByFilter(minimalRaters,dr);
        Collections.sort(list);

        for(Rating r:list){
            String Title = MovieDatabase.getTitle(r.getItem());
            String Directors = MovieDatabase.getDirector(r.getItem());
            System.out.println(r.getValue()+ " " + Title);
            System.out.println("       " + Directors);
        }
        System.out.println("found "+ list.size() + " movies");
    }
    
    public void printAverageRatingsByYearAfterAndGenre(){
        int minimalRaters = 8;
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "ratings.csv";
        int yearCount = 1990;
        String selecGenre = "Drama";

        ThirdRatings tr = new ThirdRatings(ratingsfile);
        System.out.println("read data for "+ tr.getRaterSize() + " raters");

        MovieDatabase.initialize(moviefile);
        System.out.println("read data for "+ MovieDatabase.size() + " movies");

        Filter yf = new YearAfterFilter(yearCount);
        Filter gf = new GenreFilter(selecGenre);
        AllFilters filtersList = new AllFilters();
        filtersList.addFilter(yf);
        filtersList.addFilter(gf);

        ArrayList<Rating> list = tr.getAverageRatingsByFilter(minimalRaters,filtersList);
        Collections.sort(list);

        for(Rating r:list){
            String Title = MovieDatabase.getTitle(r.getItem());
            String Genre = MovieDatabase.getGenres(r.getItem());
            int Year = MovieDatabase.getYear(r.getItem());

            System.out.println(r.getValue()+ " " + Year +" "+ Title );
            System.out.println("       "+ Genre);
        }
        System.out.println(list.size() + " movie " + "matched");
    }
    
    public void printAverageRatingsByDirectorsAndMinutes(){
        int minimalRaters = 3;
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "ratings.csv";

        int minMinutes = 90;
        int maxMinutes = 180;
        String directors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";


        ThirdRatings tr = new ThirdRatings(ratingsfile);
        System.out.println("read data for "+ tr.getRaterSize() + " raters");

        MovieDatabase.initialize(moviefile);
        System.out.println("read data for "+ MovieDatabase.size() + " movies");

        Filter mr = new MinutesFilter(minMinutes,maxMinutes);
        Filter dr = new DirectorsFilter(directors);
        AllFilters filtersList = new AllFilters();
        filtersList.addFilter(mr);
        filtersList.addFilter(dr);

        ArrayList<Rating> list = tr.getAverageRatingsByFilter(minimalRaters,filtersList);
        Collections.sort(list);

        for(Rating r:list){
            String matechedTitle = MovieDatabase.getTitle(r.getItem());
            String matchedDirectors = MovieDatabase.getDirector(r.getItem());
            int matchMinutes = MovieDatabase.getMinutes(r.getItem());
            System.out.println(r.getValue()+ " "+ " Time: "+matchMinutes+ " " +matechedTitle);
            System.out.println("      " + matchedDirectors);
        }
        System.out.println(list.size() + " movie " + "matched");
    }
    
    public void printAverageRatingsByMinutes(){
        int minimalRaters = 5;
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "ratings.csv";
        int minMinutes = 105;
        int maxMinutes = 135;
        ThirdRatings tr = new ThirdRatings(ratingsfile);
        System.out.println("read data for "+ tr.getRaterSize() + " raters");

        MovieDatabase.initialize(moviefile);
        System.out.println("read data for "+ MovieDatabase.size() + " movies");

        Filter mr = new MinutesFilter(minMinutes,maxMinutes);
        ArrayList<Rating> list = tr.getAverageRatingsByFilter(minimalRaters,mr);
        Collections.sort(list);

        for(Rating r:list){
            String Title = MovieDatabase.getTitle(r.getItem());
            int Time = MovieDatabase.getMinutes(r.getItem());
            System.out.println(r.getValue() + " " + "Time: "+ Time+ " "+ Title);
        }
        System.out.println("found "+ list.size() + " movies");
    }
}
