package davidgoldstein.blackjack.controller;

import davidgoldstein.blackjack.beans.ActionRequest;
import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.repository.GameAlreadyExistsException;
import davidgoldstein.blackjack.repository.GameNotFoundException;
import davidgoldstein.blackjack.repository.GameRepository;
import davidgoldstein.blackjack.repository.mongo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
    // TODO: conditionally set type of gameRespository
    @Autowired
    GameRepository gameRepository;

    /**
     * creates a new game and sends gamestate to the topic/table/id subscription endpoint
     * @param gameId ID for the game
     * @return
     */
    @MessageMapping("/create/{gameId}")
    @SendTo("/topic/table/{gameId}")
    public GameState createGame(@DestinationVariable String gameId) throws GameAlreadyExistsException {
        return gameRepository.createGame(gameId);
    }

    @MessageExceptionHandler
    @SendToUser("/queue/error")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }

    /**
     * listens on actions to games, sends updates to /topics/actions/gameID
     * @param gameId
     * @param req
     * @return
     */
    @MessageMapping("/action/{gameId}")
    @SendTo("/topic/table/{gameId}")
    public GameState makeMove(@DestinationVariable String gameId, ActionRequest req) throws GameNotFoundException {
        // TODO: validate request
        // TODO: convert to move
        System.out.println(req.getUserId());
        return new GameState();
        // TODO: set in repository
//        return gameRepository.setGameState(gameId, newGameState);
    }
}
