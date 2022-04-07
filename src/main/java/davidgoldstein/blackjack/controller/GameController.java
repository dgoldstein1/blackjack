package davidgoldstein.blackjack.controller;

import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.repository.GameAlreadyExistsException;
import davidgoldstein.blackjack.repository.GameNotFoundException;
import davidgoldstein.blackjack.repository.GameRepository;
import davidgoldstein.blackjack.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GameController {
    @Autowired
    GameRepository gameRepository;
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
     * retrieve game by id
     * @return
     */
    @GetMapping("/rest/game/{id}")
    @ResponseBody
    public GameState retrieveGame(@PathVariable String id) throws GameNotFoundException {
        return gameRepository.retrieveById(id);
    }

    /**
     * create new game
     * @param gameId unique Id
     * @return gameState
     */
    @PostMapping("/rest/game")
    @ResponseBody
    public GameState createGame(@RequestParam(value = "gameId",name = "gameId") String gameId) throws GameAlreadyExistsException {
        return gameRepository.createGame(gameId);
    }


}
