package day2

import Day
import day2.Day2.Hand.*
import day2.Day2.Score.*
import ext.reverse

fun main() {
    Day2("Day02").solve()
}

class Day2(input: String) : Day<List<Day2.Round>>(input) {

    enum class Hand(val opponent: Char, val player: Char, val score: Int) {
        Rock('A', 'X', 1),
        Paper('B', 'Y', 2),
        Scissor('C', 'Z', 3)
    }

    enum class Score(val score: Int, val input: Char) {
        Win(6, 'Z'),
        Draw(3, 'Y'),
        Loss(0, 'X')
    }

    class Round(
        opponentHand: Hand,
        playerHand: Hand,
        outcome: Score
    ) {
        private val roundScore = when {
            opponentHand == playerHand -> Draw
            winMap[opponentHand] == playerHand -> Win
            else -> Loss
        }.score

        private val handNeededOfOutcome = when (outcome) {
            Win -> winMap[opponentHand]!!
            Draw -> opponentHand
            Loss -> lossMap[opponentHand]!!
        }.score

        val scoreByHands = playerHand.score + roundScore
        val scoreByOutcome = handNeededOfOutcome + outcome.score
    }

    override fun parse(): List<Round> = inputByLines.map {
        Round(handMap[it[0]]!!, handMap[it[2]]!!, gameOutComeMap[it[2]]!!)
    }

    override fun part1(input: List<Round>): Any = input.sumOf { it.scoreByHands }
    override fun part2(input: List<Round>): Any = input.sumOf { it.scoreByOutcome }

    companion object {
        val handMap: Map<Char, Hand> = Hand.values().run { associateBy(Hand::opponent) + associateBy(Hand::player) }
        val gameOutComeMap = Score.values().associateBy(Score::input)
        val winMap = listOf(
            Rock to Paper,
            Paper to Scissor,
            Scissor to Rock,
        ).toMap()
        val lossMap = winMap.reverse()
    }
}