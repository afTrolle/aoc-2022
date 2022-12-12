package day11

import Day
import java.math.BigInteger

fun main() {
    Day11("Day11").solve()
}

class Day11(input: String) : Day<List<Day11.Monkey>>(input) {

    operator fun List<String>.component6(): String = get(5)

    data class Monkey(
        val name: Int,
        val items: MutableList<BigInteger> = mutableListOf(),
        val operation: (old: BigInteger) -> BigInteger,
        val divisible: BigInteger,
        val onTrue: Int,
        val onFalse: Int,
        var inspectedItems: Long = 0
    )

    fun String.getValue(old: BigInteger): BigInteger = when (this) {
        "old" -> old
        else -> this.toInt().toBigInteger()
    }

    fun String.getOperationBig(): (BigInteger, BigInteger) -> BigInteger = when (this) {
        "+" -> BigInteger::plus
        "-" -> BigInteger::minus
        "/" -> BigInteger::div
        "*" -> BigInteger::times
        else -> error("unhandled op")
    }

    override fun parseInput(): List<Monkey> {
        return inputByGroups.mapIndexed { index, (_, items, operation, test, ifTrue, ifFalse) ->
            val (constOrVar, op, constOrVarSecond) = operation.trim().removePrefix("Operation: new = ").split(" ")
            val opLambda: (old: BigInteger) -> BigInteger = { old ->
                val a = constOrVar.getValue(old)
                val b = constOrVarSecond.getValue(old)
                op.getOperationBig().invoke(a, b)
            }

            val divisible = test.trim().removePrefix("Test: divisible by ").toInt()

            val onTrue = ifTrue.trim().removePrefix("If true: throw to monkey ").toInt()
            val onFalse = ifFalse.trim().removePrefix("If false: throw to monkey ").toInt()

            Monkey(
                index,
                items = items.trim().removePrefix("Starting items: ").split(", ").map { it.toInt().toBigInteger() }
                    .toMutableList(),
                operation = opLambda,
                divisible = divisible.toBigInteger(),
                onTrue = onTrue,
                onFalse = onFalse
            )
        }
    }

    override fun part1(input: List<Monkey>): Any {
        val three = BigInteger.valueOf(3)
        repeat(20) {
            input.forEach { monkey ->
                repeat(monkey.items.size) {
                    val item = monkey.items.removeFirst()
                    val updatedItem = monkey.operation.invoke(item).div(three)
                    val nextMonkey = if (updatedItem.mod(monkey.divisible) == BigInteger.ZERO) {
                        monkey.onTrue
                    } else {
                        monkey.onFalse
                    }
                    monkey.inspectedItems += 1
                    input[nextMonkey].items.add(updatedItem)
                }
            }
        }

        val (a, b) = input.map { it.inspectedItems }.sortedDescending().take(2)
        return a * b
    }

    override fun part2(input: List<Monkey>): Any? {
        val worry = input.map { it.divisible }.reduce { acc, bigInteger ->
            acc * bigInteger
        }

        repeat(10000) {
            input.forEach { monkey ->
                repeat(monkey.items.size) {
                    val item = monkey.items.removeFirst()
                    val updatedItem = monkey.operation.invoke(item).mod(worry)
                    val nextMonkey = if (updatedItem.mod(monkey.divisible) == BigInteger.ZERO) {
                        monkey.onTrue
                    } else {
                        monkey.onFalse
                    }
                    monkey.inspectedItems += 1
                    input[nextMonkey].items.add(updatedItem)
                }
            }
        }

        val (a, b) = input.map { it.inspectedItems }.sortedDescending().take(2)
        return a * b
    }
}




