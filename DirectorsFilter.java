import java.util.*;

public  class DirectorsFilter implements Filter{
	private String directors;

	public DirectorsFilter(String directors){
		this.directors = directors;
	}
	
	public boolean satisfies(String id){
		ArrayList<String> dirList= new ArrayList(Arrays.asList(directors.split(",")));
		for(String dir:dirList){
			if(MovieDatabase.getDirector(id).contains(dir)){
				return true;
			}
		}
		return false;
	}
}
