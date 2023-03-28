package davidgoldstein.blackjack.controller;

import davidgoldstein.blackjack.api.JoinGameRequest;
import davidgoldstein.blackjack.model.Game;
import davidgoldstein.blackjack.exceptions.GameAlreadyExistsException;
import davidgoldstein.blackjack.exceptions.GameNotFoundException;
import davidgoldstein.blackjack.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
     * @return gameState
     */
    @PostMapping("/rest/game")
    @ResponseBody
    public Game createGame(@RequestBody Game game) throws GameAlreadyExistsException {
        return gameService.createGame(game.getId(), game.getName());
    }


    // join an existing game
    @PostMapping("/rest/game/{id}/join")
    @ResponseBody
    public Game joinGame(@PathVariable String id, @RequestBody JoinGameRequest jgr) throws GameNotFoundException {
        return gameService.processAction(id,jgr);
    }
}
