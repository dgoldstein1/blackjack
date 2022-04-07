package davidgoldstein.blackjack.controller;

import davidgoldstein.blackjack.repository.GameRepository;
import davidgoldstein.blackjack.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.AuthenticationException;

@Controller
public class AdminController {

    @Autowired
    AuthService authService;

    @Autowired
    GameRepository gameRepository;

    /**
     * Delete all games in repo
     * @param token
     * @return boolean
     */
    @DeleteMapping("/admin/game")
    public void deleteAll(@RequestParam(value = "token",name = "token") String token) throws AuthenticationException {
        authService.validateAdminToken(token);
        gameRepository.deleteAll();
    }
}
