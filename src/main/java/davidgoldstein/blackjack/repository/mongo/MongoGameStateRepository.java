package davidgoldstein.blackjack.repository.mongo;

import davidgoldstein.blackjack.model.GameState;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoGameStateRepository extends MongoRepository<GameState, String> {

}
