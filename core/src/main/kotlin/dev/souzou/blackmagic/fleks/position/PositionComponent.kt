package dev.souzou.blackmagic.fleks.position

import com.badlogic.gdx.math.Vector2
import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

data class PositionComponent(
    val center: Vector2 = Vector2.Zero,
    val z: Float = 0f
) : Component<PositionComponent>, Comparable<PositionComponent> {
    override fun type(): ComponentType<PositionComponent> = PositionComponent

    override fun compareTo(other: PositionComponent): Int {
        return z.compareTo(other.z)
    }

    companion object : ComponentType<PositionComponent>()
}
