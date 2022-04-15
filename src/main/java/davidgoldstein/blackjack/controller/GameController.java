package davidgoldstein.blackjack.controller;

import davidgoldstein.blackjack.model.Game;
import davidgoldstein.blackjack.exceptions.GameAlreadyExistsException;
import davidgoldstein.blackjack.exceptions.GameNotFoundException;
import davidgoldstein.blackjack.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GameController {
    @Autowired
    GameService gameService;
    /**
     * list all games
     * @return
     */
    @GetMapping("/rest/game")
    @ResponseBody
    public List<Game> listGames() {
        return gameService.listGames();
    }

    /**
     * retrieve game by id
     * @return
     */
    @GetMapping("/rest/game/{id}")
    @ResponseBody
    public Game retrieveGame(@PathVariable String id) throws GameNotFoundException {
        return gameService.retrieveById(id);
    }

    /**
     * create new game
     * @param gameId unique Id
     * @return gameState
     */
    @PostMapping("/rest/game")
    @ResponseBody
    public Game createGame(@RequestParam(value = "gameId",name = "gameId") String gameId) throws GameAlreadyExistsException {
        return gameService.createGame(gameId);
    }


}
