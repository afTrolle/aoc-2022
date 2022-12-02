package day2

import Day
import ext.reverse

fun main() {
    Day2("Day02").run()
}

class Day2(input: String) : Day<List<Day2.Round>>(input) {

    enum class Hand(val opponent: Char, val player: Char, val score: Int) {
        Rock('A', 'X', 1), Paper('B', 'Y', 2), Scissor('C', 'Z', 3)
    }

    enum class RoundScore(val score: Int, val input: Char) {
        Win(6, 'Z'), Draw(3, 'Y'), Loss(0, 'X')
    }

    class Round(
        private val opponentHand: Hand,
        private val playerHand: Hand,
        private val outcome: RoundScore
    ) {
        private val roundScore = when {
            opponentHand == playerHand -> RoundScore.Draw
            winMap[opponentHand] == playerHand -> RoundScore.Win
            else -> RoundScore.Loss
        }.score

        private val handNeededOfOutcome = when (outcome) {
            RoundScore.Win -> winMap[opponentHand]!!
            RoundScore.Draw -> opponentHand
            RoundScore.Loss -> lossMap[opponentHand]!!
        }.score

        val scoreByHands = playerHand.score + roundScore
        val scoreByOutcome = handNeededOfOutcome + outcome.score
    }

    override fun parse(): List<Round> = inputByLines.map {
        Round(handMap[it[0]]!!, handMap[it[2]]!!, gameOutComeMap[it[2]]!!)
    }

    override fun partOne(input: List<Round>): Any = input.sumOf { it.scoreByHands }

    override fun partTwo(input: List<Round>): Any = input.sumOf { it.scoreByOutcome }

    companion object {
        val handMap: Map<Char, Hand> = Hand.values().run { associateBy(Hand::opponent) + associateBy(Hand::player) }
        val gameOutComeMap = RoundScore.values().associateBy(RoundScore::input)
        val winMap = listOf(
            Hand.Rock to Hand.Paper,
            Hand.Paper to Hand.Scissor,
            Hand.Scissor to Hand.Rock,
        ).toMap()
        val lossMap = winMap.reverse()
    }
}