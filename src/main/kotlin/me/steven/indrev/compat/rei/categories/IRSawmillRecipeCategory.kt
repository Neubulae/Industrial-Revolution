package me.steven.indrev.compat.rei.categories

import me.shedaniel.math.Point
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.EntryStack
import me.shedaniel.rei.api.widgets.Widgets
import me.shedaniel.rei.gui.widget.Widget
import me.steven.indrev.compat.rei.plugins.IRMachinePlugin
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

class IRSawmillRecipeCategory(
    identifier: Identifier,
    logo: EntryStack,
    categoryName: String
) : IRMachineRecipeCategory(identifier, logo, categoryName) {

    override fun setupDisplay(recipeDisplay: IRMachinePlugin, bounds: Rectangle): MutableList<Widget> {
        val recipe = recipeDisplay.recipe
        val startPoint = Point(bounds.centerX - 41, bounds.centerY - 27)
        val widgets: MutableList<Widget> = listOf(Widgets.createCategoryBase(bounds)).toMutableList()
        widgets.add(Widgets.createArrow(Point(startPoint.x + 24, startPoint.y + 18)))
        val input = recipeDisplay.inputEntries
        widgets.add(Widgets.createSlot(Point(startPoint.x + 1, startPoint.y + 19)).entries(input[0]))
        val outputs = recipe.outputs.map { EntryStack.create(it.stack) }
        widgets.add(
            Widgets.createSlot(Point(startPoint.x + 61, startPoint.y + 6))
                .entry(outputs.getOrElse(0) { EntryStack.create(ItemStack.EMPTY) })
        )
        widgets.add(
            Widgets.createSlot(Point(startPoint.x + 61, startPoint.y + 24))
                .entry(outputs.getOrElse(1) { EntryStack.create(ItemStack.EMPTY) })
        )
        widgets.add(
            Widgets.createSlot(Point(startPoint.x + 79, startPoint.y + 6))
                .entry(outputs.getOrElse(2) { EntryStack.create(ItemStack.EMPTY) })
        )
        widgets.add(
            Widgets.createSlot(Point(startPoint.x + 79, startPoint.y + 24))
                .entry(outputs.getOrElse(3) { EntryStack.create(ItemStack.EMPTY) })
        )
        return widgets
    }

}