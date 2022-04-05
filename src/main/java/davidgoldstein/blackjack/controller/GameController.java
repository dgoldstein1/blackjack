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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GameController {
    // TODO: conditionally set type of gameRespository
    @Autowired
    GameRepository gameRepository;

    @MessageExceptionHandler
    @SendToUser("/queue/error")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }


    /**
     * list all games
     * @return
     */
    @GetMapping("/rest/game")
    @ResponseBody
    public List<GameState> listGames() {
        return gameRepository.listGames();
    }

    /**
     * create new game
     * @param gameId unique Id
     * @return
     */
    @PostMapping("/rest/game")
    @ResponseBody
    public GameState createGame(@RequestParam(value = "gameId",name = "gameId",required = true) String gameId) throws GameAlreadyExistsException {
        return gameRepository.createGame(gameId);
    }
}
