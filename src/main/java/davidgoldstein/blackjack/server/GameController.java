package davidgoldstein.blackjack.server;

import davidgoldstein.blackjack.beans.ActionRequest;
import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.model.Action;
import davidgoldstein.blackjack.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class GameController {
    private GameService gameService;

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @MessageMapping("/create/{uuid}")
    @SendTo("/topic/table/{uuid}")
    public GameState createGame(@DestinationVariable String uuid) {
        GameState gameState = gameService.createGame(UUID.fromString(uuid));

        return gameState;
    }

    @MessageMapping("/action/{uuid}")
    @SendTo("/topic/action/{uuid}")
    public GameState makeMove(@DestinationVariable String uuid, ActionRequest req) {
        GameState gameState = gameService.move(UUID.fromString(uuid), req);

        return gameState;
    }
}
