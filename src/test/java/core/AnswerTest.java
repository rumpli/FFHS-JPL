package core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {

    char charTest = 'A';

    Player player = new Player() {
        @Override
        public void addPoints(int pointsToAdd) {
            super.addPoints(pointsToAdd);
        }
    };
    Answer answer = new Answer(charTest, player);
    @Test
    void answer() {
        assertEquals(charTest, answer.answer());
    }

    @Test
    void player() {
        assertEquals(player, answer.player());
    }
}