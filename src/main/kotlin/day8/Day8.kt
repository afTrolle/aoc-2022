package day8

import Day
import ext.merge
import ext.mergeMatrix
import ext.transpose

fun main() {
    Day8("Day08").apply {
        println(part1(parsedInput))
        println(part2(parsedInput))
    }
}

fun <E> List<List<E>>.forEachByRow(
    rowProgression: IntProgression = this.indices,
    colProgression: IntProgression = this.first().indices,
    action: (rowIndex: Int, colIndex: Int, item: E) -> Unit
) {
    for (rowIndex in rowProgression) {
        for (colIndex in colProgression) {
            action(rowIndex, colIndex, this[rowIndex][colIndex])
        }
    }
}

fun <E> List<List<E>>.forEachByCol(
    colProgression: IntProgression = this.first().indices,
    rowProgression: IntProgression = this.indices,
    action: (rowIndex: Int, colIndex: Int, item: E) -> Unit
) {
    for (colIndex in colProgression) {
        for (rowIndex in rowProgression) {
            action(rowIndex, colIndex, this[rowIndex][colIndex])
        }
    }
}


class Day8(input: String) : Day<List<List<Int>>>(input) {

    override fun parseInput(): List<List<Int>> = inputByLines.map { it.map { it.digitToInt() } }

    override fun part1(input: List<List<Int>>): Int {
        fun List<Int>.updateVisible() = runningFold(-1 to true) { (topHeight, _), height ->
            if (height > topHeight) {
                height to true
            } else {
                topHeight to false
            }
        }.drop(1).map { it.second }

        fun List<List<Int>>.getAnswerByRows() = map { row ->
            val leftToRight = row.updateVisible()
            val rightToLeft = row.reversed().updateVisible().reversed()
            leftToRight.merge(rightToLeft) { a: Boolean, b: Boolean -> a || b }
        }

        val answerByRows = input.getAnswerByRows()
        val answerByCols = input.transpose().getAnswerByRows().transpose()

        return answerByRows.mergeMatrix(answerByCols) { a: Boolean, b: Boolean ->
            a || b
        }.flatten().count { it }
    }


    override fun part2(input: List<List<Int>>): Any {
        val rowNum = input.size
        val colNum = input.first().size
        var top = 0;
        fun isEdge(row: Int, col: Int) = row == 0 || col == 0 || rowNum - 1 == row || colNum - 1 == col
        input.forEachByRow { rowIndex: Int, colIndex: Int, item: Int ->
            if (isEdge(rowIndex, colIndex)) {
                return@forEachByRow
            } else {
                // left-to-right
                var leftToRight = 0
                for (col in colIndex + 1 until input[rowIndex].size) {
                    leftToRight += 1
                    if (input[rowIndex][col] >= item) break
                }

                // right-to-left
                var rightToLeft = 0
                for (col in (0 until colIndex).reversed()) {
                    rightToLeft += 1
                    if (input[rowIndex][col] >= item) break
                }

                // top-to-bottom
                var topToBottom = 0
                for (row in (rowIndex + 1 until input.size)) {
                    topToBottom += 1
                    if (input[row][colIndex] >= item) break
                }

                // bottom-to-top
                var bottomToTop = 0
                for (row in (0 until rowIndex).reversed()) {
                    bottomToTop += 1
                    if (input[row][colIndex] >= item) break
                }

                val score = leftToRight * rightToLeft * topToBottom * bottomToTop
                top = top.coerceAtLeast(score)
            }
        }
        return top
    }
}

