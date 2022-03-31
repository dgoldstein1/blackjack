package davidgoldstein.blackjack.repository.mongo;

import davidgoldstein.blackjack.beans.GameState;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoGameStateRepository extends MongoRepository<GameState, String> {

}
