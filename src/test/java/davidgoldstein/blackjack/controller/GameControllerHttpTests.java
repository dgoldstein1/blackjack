package davidgoldstein.blackjack.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class GameControllerHttpTests {

    String LIST_GAMES_ENDPOINT = "/rest/game";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void endpointExists() throws Exception {
        this.mockMvc.perform(get(LIST_GAMES_ENDPOINT))
            .andExpect(status().isOk());
    }

    @Test void returnsEmptyListWithNoGames() throws Exception {
        this.mockMvc
            .perform(get(LIST_GAMES_ENDPOINT))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(content().string("[]"));
    }

    @Test
    public void returnsCreatedGames() throws Exception {
        this.mockMvc
                .perform(get(LIST_GAMES_ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.[0]", is(notNullValue())));
    }
}
