import java.util.*;

public class GenreFilter implements Filter {
    private String genre;
    public GenreFilter(String genre) {
        this.genre = genre;
    }
    
    public boolean satisfies(String id) {
        return MovieDatabase.getGenres(id).contains(genre);
    }
}
