package davidgoldstein.blackjack.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class AdminControllerTests {
    @Autowired
    private MockMvc mockMvc;

    static final String ADMIN_ENDPOINT = "/admin/" ;
    static final String adminToken = "changeme";

    @Test
    void deleteAllGames() throws Exception {
        this.mockMvc
                .perform(delete(ADMIN_ENDPOINT + "/game").
                        param("token", adminToken))
                .andExpect(status().isOk());
    }
}
