package me.pluie.aoc.c2017

import me.pluie.aoc.*
import kotlin.math.*

fun main() = challenge(2017, 3) {

    //37  36  35  34  33  32  31
    //38  17  16  15  14  13  30
    //39  18   5   4   3  12  29
    //40  19   6   1   2  11  28
    //41  20   7   8   9  10  27
    //42  21  22  23  24  25  26
    //43  44  45  46  47  48  49


    //12  13  14  15  16  17  18
    //11                      19
    //10                      20
    // 9                      21
    // 8                      22
    // 7                      23
    // 6   5   4   3   2   1   0

    // nSq = 7

    // 48
    // pOS = 1
    // manhattan(49) = sqrt(49) - 1
    // manhattan(48) = sqrt(49) - 1 - 1
    // manhattan(47) = sqrt(49) - 1 - 2
    // manhattan(46) = sqrt(49) - 1 - 3
    // manhattan(45) = sqrt(49) - 1 - 4

    val input = int()

    submit {
        val nearestSqrt = ceil(sqrt(input.toFloat())).roundToInt()
        val nsq = nearestSqrt + 1 - nearestSqrt % 2

        val hnsq = nsq / 2
        val positionOnSpiral = nsq * nsq - input
        hnsq + abs(positionOnSpiral % nearestSqrt - hnsq)
    }

    // n
    submit {0

    }

}