package davidgoldstein.blackjack.controller;

import davidgoldstein.blackjack.model.GameStatus;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static davidgoldstein.blackjack.controller.AdminControllerTests.ADMIN_ENDPOINT;
import static davidgoldstein.blackjack.controller.AdminControllerTests.adminToken;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class GameControllerTests {

    static final String GAME_ENDPOINT = "/rest/game";

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void reset() throws Exception {
        this.mockMvc
                .perform(delete(ADMIN_ENDPOINT + "/game").
                        param("token", adminToken))
                .andExpect(status().isOk());
    }



    @Test void returnsEmptyListWithNoGames() throws Exception {
        this.mockMvc
            .perform(get(GAME_ENDPOINT))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(content().string("[]"));
    }

    @Test
    public void createGame() throws Exception {
        String gameId = UUID.randomUUID().toString();
        JSONObject json = new JSONObject();
        json.put("id",gameId);
        this.mockMvc
                .perform(post(GAME_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(gameId)));
    }

    @Test
    public void ableToCreateGameName() throws Exception {
        String gameId = UUID.randomUUID().toString();
        JSONObject json = new JSONObject();
        String gameName = "mytestgame";
        json.put("name", gameName);
        json.put("id",gameId);

        this.mockMvc
                .perform(post(GAME_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(gameId)));
        this.mockMvc
                .perform(get(GAME_ENDPOINT + "/" + gameId).param("gameId", gameId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(gameId)))
                .andExpect(jsonPath("$.name", is(gameName)));
    }

    @Test
    public void retrieveById() throws Exception {
        String gameId = UUID.randomUUID().toString();
        JSONObject json = new JSONObject();
        json.put("id",gameId);
        this.mockMvc
                .perform(post(GAME_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(gameId)));
        this.mockMvc
                .perform(get(GAME_ENDPOINT + "/" + gameId).param("gameId", gameId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(gameId)));
    }

    @Test
    public void joinGame() throws Exception {
        String gameId = UUID.randomUUID().toString();
        JSONObject json = new JSONObject();
        String gameName = "mytestgame2";
        json.put("name", gameName);
        json.put("id", gameId);

        this.mockMvc
                .perform(post(GAME_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(gameId)));
        this.mockMvc
                .perform(get(GAME_ENDPOINT + "/" + gameId).param("gameId", gameId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(gameId)))
                .andExpect(jsonPath("$.name", is(gameName)));

        JSONObject joinGameRequest = new JSONObject();
        String playerID = UUID.randomUUID().toString();
        joinGameRequest.put("name", "davesauce");
        joinGameRequest.put("playerId", playerID);

        this.mockMvc
                .perform(post(GAME_ENDPOINT + "/" + gameId.toString() + "/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(joinGameRequest.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));


        this.mockMvc
                .perform(get(GAME_ENDPOINT + "/" + gameId).param("gameId", gameId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(gameId)))
                .andExpect(jsonPath("$.name", is(gameName)))
                .andExpect(jsonPath("$.players[0].name",is("davesauce")));

    }

    @Test
    public void returnsCreatedGames() throws Exception {
        String gameId = UUID.randomUUID().toString();
        JSONObject json = new JSONObject();
        json.put("id",gameId);
        this.mockMvc
                .perform(post(GAME_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(gameId)));

        this.mockMvc
                .perform(get(GAME_ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.[0]", is(notNullValue())));
    }
}
