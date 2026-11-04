package edu.course.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EvenNumberCheckerTest {
    private final EvenNumberChecker checker = new EvenNumberChecker();

    @Test
    void returnsTrueForEvenNumber() {
        assertTrue(checker.isEven(4));
    }

    @ParameterizedTest
    @CsvSource({"1", "3", "5"})
    void returnsFalseForOddNumbers(int value) {
        assertFalse(checker.isEven(value));
    }
}
