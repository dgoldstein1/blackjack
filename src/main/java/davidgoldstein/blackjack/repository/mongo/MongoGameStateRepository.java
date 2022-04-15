package davidgoldstein.blackjack.repository.mongo;

import davidgoldstein.blackjack.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoGameStateRepository extends MongoRepository<Game, String> {

}
