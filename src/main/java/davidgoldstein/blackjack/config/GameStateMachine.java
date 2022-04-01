package davidgoldstein.blackjack.config;

import davidgoldstein.blackjack.model.Action;
import davidgoldstein.blackjack.model.GameState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

public class GameStateMachine {
    @Configuration
    @EnableStateMachine
    static class StateMachineConfig
            extends EnumStateMachineConfigurerAdapter<GameState, Action> {

        @Override
        public void configure(StateMachineStateConfigurer<GameState, Action> states)
                throws Exception {
            states
                    .withStates()
                    .initial(GameState.STARTED)
                    .states(EnumSet.allOf(GameState.class));
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<GameState, Action> transitions)
                throws Exception {
            transitions
                    .withExternal()
                    .source(GameState.STARTED)
                    .target(GameState.WAITING_FOR_BET)
                    .event(Action.DEAL_CARDS)
                .and()
                    .withExternal()
                    .source(GameState.WAITING_FOR_BET)
                    .target(GameState.WAITING_FOR_PLAYER_TO_PLAY_CARD)
                    .event(Action.COLLECT_BETS);
        }

    }
}
