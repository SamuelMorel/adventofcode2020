package org.adventofcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Puzzle line implementation
 */
class PuzzleLine {
    // this regex captures input from the line
    static final String REGEX_PATTERN = "(?<min>[0-9]*)-(?<max>[0-9]*) (?<character>[a-z]): (?<password>.*)";
    static final Pattern pattern = Pattern.compile(REGEX_PATTERN);

    Integer min;
    Integer max;
    Character character;
    String password;

    public PuzzleLine(String line) {
        Matcher m = pattern.matcher(line);

        if (!m.matches()) {
            throw new IllegalArgumentException("Does not match model !");
        }

        min = Integer.parseInt(m.group("min"));
        max = Integer.parseInt(m.group("max"));
        character = m.group("character").toCharArray()[0];
        password = m.group("password");
    }
}