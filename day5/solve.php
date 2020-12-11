<?php

    // in boarding passes, columns starts to 7
    define("COLUMN_INDEX_IN_BOARDINGPASS", 7);
    define("NB_COLUMNS", 8);
    define("NB_ROWS", 128);

    /**
     * This function compares to boarding passes.
     * Based on the fact that B < F and R > L.
     * 
     * @param $bp1 first boarding pass
     * @param $bp2 second boarding pass
     * 
     * @return -1 if $bp1 < $bp2 ; 1 other
     */
    function sortBoardingPass($bp1, $bp2)
    {
        $rows1 = substr($bp1, 0, COLUMN_INDEX_IN_BOARDINGPASS);
        $rows2 = substr($bp2, 0, COLUMN_INDEX_IN_BOARDINGPASS);
        
        if ($rows1 == $rows2) {
            $columns1 = substr($bp1, COLUMN_INDEX_IN_BOARDINGPASS);
            $columns2 = substr($bp2, COLUMN_INDEX_IN_BOARDINGPASS);
            return ($columns1 < $columns2) ? -1 : 1;
        }
        return ($rows1 > $rows2) ? -1 : 1;
    }

    /**
     * compute the site id from the boarding pass.
     * 
     * @param $boardingPass the boarding pass to compute
     * 
     * @return the corresponding site id
     */
    function computeSeatId($boardingPass) {
        // split string in chars
        $chars = str_split($boardingPass);
        
        $rowStart = 0;
        $rowEnd = NB_ROWS;
        $columnStart = 0;
        $columnEnd = NB_COLUMNS;

        foreach ($chars as $char) {
            // apply dichotomy
            if ($char == "F") $rowEnd = ($rowStart + $rowEnd) / 2;
            if ($char == "B") $rowStart = ($rowStart + $rowEnd) / 2;

            if ($char == "R") $columnStart = ($columnStart + $columnEnd) / 2;
            if ($char == "L") $columnEnd = ($columnStart + $columnEnd) / 2;
        }
        
        return $rowStart * NB_COLUMNS + $columnStart;
    }

    /**
     * This function look for an empty seat in a list of boarding pass, 
     * considering that the plane is full.
     * 
     * @param $boardingPasses the boarding pass list
     * 
     * @return the empty seat id
     */
    function findMissingSeat($boardingPasses) {
        // init previous seat id with the first boarding pass
        $previousSeatId = computeSeatId($boardingPasses[0]);

        // for every other boarding pass
        foreach (array_slice($boardingPasses, 1) as $boardingPass) {
            $currentSeatId = computeSeatId($boardingPass);
            if ($currentSeatId != $previousSeatId+1) {
                    echo $previousSeatId+1;
                    break;
            } 
            $previousSeatId = $currentSeatId;
        }
    }

    // Read the inpout file
    $boardingPasses = file('inpux.txt', FILE_IGNORE_NEW_LINES);


    // sort array by boarding pass desc
    usort($boardingPasses, "sortBoardingPass");

    // step 1 -> compute the highest seat id (last in list)
    $higherBoardingPass = $boardingPasses[count($boardingPasses)-1];

    echo "Step 1 -> ";
    echo computeSeatId($higherBoardingPass);
    echo "\n";

    echo "Step 2 -> ";
    echo findMissingSeat($boardingPasses);
    echo "\n";
?>