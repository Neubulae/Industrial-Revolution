package me.steven.indrev.armor

import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity

enum class Module(
    val key: String,
    val slots: Array<EquipmentSlot>,
    val maxLevel: Int,
    val hasOverlay: Boolean,
    val apply: (ServerPlayerEntity, Int) -> StatusEffectInstance? = { _, _ -> null }
) {
    NIGHT_VISION("night_vision", arrayOf(EquipmentSlot.HEAD), 1, true, { player, _ ->
        StatusEffectInstance(StatusEffects.NIGHT_VISION, 1000000, 0, false, false)
    }),
    SPEED("speed", arrayOf(EquipmentSlot.LEGS), 3, false, { player, level ->
        StatusEffectInstance(StatusEffects.SPEED, 200, level - 1, false, false)
    }),
    JUMP_BOOST("jump_boost", arrayOf(EquipmentSlot.FEET), 3, false, { player, level ->
        StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, level - 1, false, false)
    }),
    BREATHING("breathing", arrayOf(EquipmentSlot.HEAD), 1, false, { player, level ->
        if (player.isSubmergedInWater)
            StatusEffectInstance(StatusEffects.WATER_BREATHING, 200, level - 1, false, false)
        else
            null
    }),
    FEATHER_FALLING("feather_falling", arrayOf(EquipmentSlot.FEET), 3, false),
    PROTECTION(
        "protection",
        arrayOf(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET),
        3,
        true
    ),
    COLOR("color", arrayOf(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET), -1, false);

    companion object {
        fun getInstalled(stack: ItemStack): Array<Module> {
            val tag = stack.tag ?: return emptyArray()
            return values().filter { module -> module != COLOR }.mapNotNull { module ->
                if (tag.contains(module.key)) module
                else null
            }.toTypedArray()
        }

        fun getLevel(stack: ItemStack, upgrade: Module): Int {
            val tag = stack.tag ?: return 0
            return if (tag.contains(upgrade.key)) tag.getInt(upgrade.key) else 0
        }
    }
}