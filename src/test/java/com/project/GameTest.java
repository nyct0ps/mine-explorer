package com.project;

import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameTest {
  Game game;

  @Mock
  Menu menu;

  @Test
  void loopShouldStopWhenAction0() {
    // given
    Mockito.when(menu.getNbColumn()).thenReturn(3);
    Mockito.when(menu.getNbRow()).thenReturn(3);
    Mockito.when(menu.chooseDirectionToMovePlayer()).thenReturn("z");
    Mockito.when(menu.doAction(ArgumentMatchers.any())).thenReturn(-1).thenReturn(1).thenReturn(0);
    game = new Game(menu, new Player(), new Random(0));

    // when
    game.loop();

    // then
    Assertions.assertEquals(false, game.isGameRunning());
  }

  @Test
  void loopShouldStopWhenPlayerWon() {
    // given
    Mockito.when(menu.getNbColumn()).thenReturn(3);
    Mockito.when(menu.getNbRow()).thenReturn(1);
    Mockito.when(menu.chooseDirectionToMovePlayer()).thenReturn("q");
    Mockito.when(menu.doAction(ArgumentMatchers.any())).thenAnswer(invocation -> {
      Player player = invocation.getArgument(0);
      if (player.getHasWon()) {
        return 0;
      } else {
        return 1;
      }
    });

    game = new Game(menu, new Player(), new Random(0));

    // when
    game.loop();

    // then
    Assertions.assertEquals(false, game.isGameRunning());
  }
}
