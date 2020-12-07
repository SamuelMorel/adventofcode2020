package org.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class Puzzle {

    private static final Logger LOGGER = Logger.getLogger(Puzzle.class.getName());

    /**
     * Solve the puzzle by counting how many password complies with given the policy
     *
     * @param fileName        the file to read puzzle from
     * @param policyValidator a method reference to validate a puzzle
     * @return how many puzzle are compliant with policy
     * @throws IOException in case of impossibility to read {@code fileName}
     */
    public Long solve(String fileName, Predicate<PuzzleLine> policyValidator) throws IOException {
        return Files.readAllLines(Paths.get(fileName))
                .stream()
                .map(PuzzleLine::new)
                .filter(policyValidator)
                .count();
    }

    /**
     * First policy implementation,
     *
     * @param puzzle the puzzle to check
     * @return {@code true} if puzzle complies with policy {@code false} otherwise
     */
    private boolean isValidFirstPolicy(PuzzleLine puzzle) {
        // count char
        long count = puzzle.password
                // convert to char array
                .chars()
                // filter to keep only specified
                .filter(ch -> ch == puzzle.character)
                .count();

        return count >= puzzle.min && count <= puzzle.max;
    }

    /**
     * Second policy implementation,
     *
     * @param puzzle the puzzle to check
     * @return {@code true} if puzzle complies with policy {@code false} otherwise
     */
    private boolean isValidSecondPolicy(PuzzleLine puzzle) {
        // XOR
        return puzzle.password.charAt(puzzle.min - 1) == puzzle.character
                ^
                puzzle.password.charAt(puzzle.max - 1) == puzzle.character;
    }


    public static void main(String[] args) throws IOException {
        Puzzle puzzle = new Puzzle();
        LOGGER.info(String.valueOf(puzzle.solve("src/main/resources/input.txt", puzzle::isValidFirstPolicy)));
        LOGGER.info(String.valueOf(puzzle.solve("src/main/resources/input.txt", puzzle::isValidSecondPolicy)));
    }
}