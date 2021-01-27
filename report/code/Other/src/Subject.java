import java.util.Optional;


public interface Subject {

	Path getPath();
	
	Chamber getCurrentChamber();
	
	Optional<Challenge> getCurrentChallenge();
	
}
