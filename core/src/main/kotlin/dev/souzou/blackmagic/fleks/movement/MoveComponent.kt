package dev.souzou.blackmagic.fleks.movement

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

data class MoveComponent(
    var speed: Float = 3f,
    var cos: Float = 0f,
) : Component<MoveComponent> {


    override fun type(): ComponentType<MoveComponent> = MoveComponent

    companion object: ComponentType<MoveComponent>()
}
