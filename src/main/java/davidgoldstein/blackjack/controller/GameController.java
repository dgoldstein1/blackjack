package davidgoldstein.blackjack.controller;

import davidgoldstein.blackjack.beans.ActionRequest;
import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.service.GameAlreadyExistsException;
import davidgoldstein.blackjack.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
    private GameService gameService;

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * creates a new game and sends gamestate to the topic/table/id subscription endpoint
     * @param gameId ID for the game
     * @return
     */
    @MessageMapping("/create/{gameId}")
    @SendTo("/topic/table/{gameId}")
    public GameState createGame(@DestinationVariable String gameId) throws GameAlreadyExistsException {
        return gameService.createGame(gameId);
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
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
    @SendTo("/topic/action/{gameId}")
    public GameState makeMove(@DestinationVariable String gameId, ActionRequest req) {
        GameState gameState = gameService.move(gameId, req);

        return gameState;
    }
}
