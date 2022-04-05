package davidgoldstein.blackjack.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void reset() {

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
        this.mockMvc
            .perform(post(GAME_ENDPOINT).param("gameId", gameId))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id", is(gameId)));
    }

    @Test
    public void retrieveById() throws Exception {
        String gameId = UUID.randomUUID().toString();
        this.mockMvc
                .perform(post(GAME_ENDPOINT).param("gameId", gameId))
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
    public void returnsCreatedGames() throws Exception {
        String gameId = UUID.randomUUID().toString();
        this.mockMvc
                .perform(post(GAME_ENDPOINT).param("gameId", gameId))
                .andExpect(status().isOk());

        this.mockMvc
                .perform(get(GAME_ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.[0]", is(notNullValue())));
    }
}
