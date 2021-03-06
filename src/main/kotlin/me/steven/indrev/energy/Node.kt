package me.steven.indrev.energy

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

data class Node(val origin: BlockPos, val target: BlockPos, val dist: Short, val direction: Direction) : Comparable<Node> {
    override fun compareTo(other: Node): Int {
        return when {
            this.dist > other.dist -> 1
            this.dist < other.dist -> -1
            else -> 0
        }
    }
}