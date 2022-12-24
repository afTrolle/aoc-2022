package day13

import Day
import kotlin.math.max

fun main() {
    Day13("Day13").solve()
}

class ValueOrList(
    val parent: ValueOrList? = null,
    val list: MutableList<ValueOrList>? = null,
    val value: Int? = null
) {
    val isValue = value != null
}

class Day13(input: String) : Day<List<Pair<ValueOrList, ValueOrList>>>(input) {

    override fun parseInput(): List<Pair<ValueOrList, ValueOrList>> {
        return inputByGroups.map { (left, right) ->
            parseToValueOrList(left) to parseToValueOrList(right)
        }
    }

    private fun parseToValueOrList(left: String): ValueOrList {
        val root = ValueOrList(list = mutableListOf())
        var current = root
        "(\\d+)|(\\[)|(\\])".toRegex().findAll(left).toList().drop(1).dropLast(1).forEach { match ->
            val c = match.value
            val maybeInt = c.toIntOrNull()
            when {
                maybeInt != null -> {
                    current.list!!.add(ValueOrList(parent = current, value = maybeInt))
                }

                c == "[" -> {
                    val prev = current
                    current = ValueOrList(parent = prev, list = mutableListOf())
                    prev.list?.add(current)
                }

                c == "]" -> {
                    current = current.parent!!
                }
            }
        }

        return root
    }

    private fun isInRightOrder(left: ValueOrList, right: ValueOrList): Boolean? {
        return if (left.isValue && right.isValue) {
            val leftV = left.value!!
            val rightV = right.value!!
            return if (leftV == rightV) {
                null
            } else {
                leftV < rightV
            }
        } else if (left.isValue && !right.isValue) {
            // mixed
            val leftList = ValueOrList(parent = left, mutableListOf(left))
            isInRightOrder(leftList, right)
        } else if (!left.isValue && right.isValue) {
            // mixed
            val rightList = ValueOrList(parent = right, mutableListOf(right))
            isInRightOrder(left, rightList)
        } else {
            // both lists
            val size = max(left.list!!.size, right.list!!.size)
            if (left.list.isEmpty() && right.list.isNotEmpty()) {
                return true
            } else if (left.list.isNotEmpty() && right.list.isEmpty()) {
                return false
            } else {
                for (x in 0 until size) {
                    val leftV = left.list.getOrNull(x)
                    val rightV = right.list.getOrNull(x)
                    if (leftV != null && rightV != null) {
                        val result = isInRightOrder(leftV, rightV)
                        if (result != null) {
                            return result
                        }
                    } else if (leftV == null && rightV != null) {
                        // mixed
                        return true
                    } else if (leftV != null && rightV == null) {
                        // mixed
                        return false
                    } else {
                        // both null
                        return null
                    }
                }
            }
            return null
        }
    }

    override fun part1(input: List<Pair<ValueOrList, ValueOrList>>): Any? {
        return input.mapIndexedNotNull { index, (left, right) ->
            val at = isInRightOrder(left, right)!!
            if (at) index + 1 else null
        }.sum()
    }

    override fun part2(input: List<Pair<ValueOrList, ValueOrList>>): Any? {
        val two = ValueOrList(list = mutableListOf()).also {
            it.list!!.add(ValueOrList(parent = it, value = 2))
        }
        val six = ValueOrList(list = mutableListOf()).also {
            it.list!!.add(ValueOrList(parent = it, value = 6))
        }
        val list = input.map { (left, right) ->
            listOf(left, right)
        }.flatten().plus(two).plus(six).sortedWith { o1, o2 ->
            if (isInRightOrder(o1!!, o2!!)!!) {
                1
            } else {
                -1
            }
        }
      return  list.indexOf(six) * list.indexOf(two)
    }
}




