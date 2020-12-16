package main

import (
	"bufio"
	"fmt"
	"os"
)

/**
	Main function
 */
func main() {
	fmt.Println(solveOneStar("input.txt"))
	fmt.Println(solveSecondStar("input.txt"))
}

/**
	Function that count common positive answers in a group and sum them
 */
func solveSecondStar(path string) int {
	// init counters
	overallCount := 0
	nbPeopleInGroup := 0

	// this var will be used to concat answers of a group
	var answers string

	// create file from path
	file, _ := os.Open(path)

	// ask for file closing after we're done
	defer file.Close()

	// create a scanner to read line by line
	scanner := bufio.NewScanner(file)

	// for each line
	for scanner.Scan() {
		// read line
		answer := scanner.Text()

		// if we are not at the end of a group
		if answer != "" {
			// concat answers
			answers += answer
			// count people
			nbPeopleInGroup++
		} else {
			// map a letter to its occurrence counter
			m := make(map[rune]int)

			// split on letters
			for _, letter := range answers {
				// increment letter occurrence
				m[letter]++

				// did we reach nbPeopleInGroup ?
				// take as hypothesis that we cannot go over nbPeopleInGroup
				if m[letter] == nbPeopleInGroup {
					overallCount++
				}
			}

			// reset group count and answers
			answers = ""
			nbPeopleInGroup = 0
		}
	}

	return overallCount
}

/**
	Function returns a count of distinct letters
 */
func solveOneStar(path string) int {
	// init counters
	overallCount := 0

	// this var will be used to concat answers of a group
	var answers string

	// create file from path
	file, _ := os.Open(path)

	// ask for file closing after we're done
	defer file.Close()

	// create a scanner to read line by line
	scanner := bufio.NewScanner(file)

	// for each line
	for scanner.Scan() {
		// read line
		answer := scanner.Text()

		// concat answers
		answers += answer

		// if we read the end of the group
		if answer == "" {
			// map a letter to its presence flag
			m := map[rune]bool{}

			// split on letters
			for _, letter := range answers {
				m[letter] = true
			}

			// number of distinct letter is map length
			overallCount += len(m)

			// reset string
			answers = ""
		}

	}

	return overallCount
}
