package main.kotlin

import java.io.File

class Puzzle
{
    /**
     * Found two complementary numbers which sum equals to 2020
     *
     * @param fileName the input file to read integers from
     * @return the product of such integers if they are found
     */
    fun solvePuzzleFirstStar(fileName: String): Int? {
        var result: Int? = null
        val map: MutableMap<Int, Boolean> = HashMap()

        // read each line of input
        File(Puzzle::class.java.getResource(fileName).toURI()).forEachLine {
            val current: Int = it.toInt()

            // Did we already saw the complementary ?
            if (map[2020-current] == true) {
                result = current*(2020-current)
                return@forEachLine
            } else {
                // flag current as read
                map[current] = true
            }
        }

        return result
    }

    /**
     * Found three complementary numbers which sum equals to 2020
     *
     * @param fileName the input file to read integers from
     * @return the product of such integers if they are found
     */
    fun solvePuzzleSecondStar(fileName: String): Int? {
        var result: Int? = null
        val keys: MutableList<Int> = ArrayList()
        val intermediateMap: MutableMap<Int, Int> = HashMap()

        // read each line of input
        File(Puzzle::class.java.getResource(fileName).toURI()).forEachLine { line ->
            val current: Int = line.toInt()

            // If a previous pair + current = 2020 we're good !
            // just get the product calculated before, and multiply with current
            if (intermediateMap.containsKey(2020-current)) {
                result = current* intermediateMap[2020-current]!!
                return@forEachLine
            }

            // build an intermediate array
            // sum previously read int and sum them with current
            // also store the product for later
            keys.forEach { key ->
                if (key+current < 2020) intermediateMap[key+current] = key*current
            }

            // add the current to read index
            keys.add(current)
        }

        return result
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println(Puzzle().solvePuzzleFirstStar("input.txt"))
            println(Puzzle().solvePuzzleSecondStar("input.txt"))
        }
    }
}