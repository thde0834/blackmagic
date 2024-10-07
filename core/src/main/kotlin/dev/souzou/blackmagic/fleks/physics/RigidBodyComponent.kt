package dev.souzou.blackmagic.fleks.physics

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.World

data class RigidBodyComponent(
    var acceleration: Float = 3f,
    var cos: Float = 0f,
    var sin: Float = 0f,
) : Component<RigidBodyComponent> {
    private lateinit var colliderComponent: ColliderComponent

    override fun World.onAdd(entity: Entity) {
        colliderComponent = entity[ColliderComponent]
    }

    override fun type(): ComponentType<RigidBodyComponent> = RigidBodyComponent

    companion object: ComponentType<RigidBodyComponent>()
}
