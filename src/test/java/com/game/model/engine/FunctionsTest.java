package com.game.model.engine;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FunctionsTest {

    @Test
    public void testChanceForAction_shouldReturnTrue_whenRandGreaterThanChance() {
        int min = 5;
        int max = 10;
        assertTrue(Functions.chanceForAction(min, max, min));
    }

    @Test
    public void testChanceForAction_shouldReturnFalse_whenRandLessThanChance() {
        int min = 5;
        int max = 10;
        assertFalse(Functions.chanceForAction(min, max, max));
    }

    @Test
    public void testGetRandomNumber_passSuccessfully_whenGivenNumInRange() {
        int min = 1;
        int max = 10;
        int rand = Functions.getRandomNumber(min, max);
        assertTrue(min <= rand);
        assertTrue(max >= rand);
    }

    @Test
    public void testGetRandomNumber_failSuccessfully_whenGivenNumOutOfRange() {
        int min = 1;
        int max = 10;
        int outOfRangeLowerBound = -1;
        int outOfRangeMaxBound = 11;
        int rand = Functions.getRandomNumber(min, max);
        assertFalse(outOfRangeLowerBound >= rand);
        assertFalse(outOfRangeMaxBound <= rand);
    }
}