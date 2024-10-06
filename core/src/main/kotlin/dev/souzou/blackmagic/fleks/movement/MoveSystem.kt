package dev.souzou.blackmagic.fleks.movement

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import dev.souzou.blackmagic.fleks.physics.CollisionComponent
import ktx.math.component1
import ktx.math.component2

class MoveSystem(

) : IteratingSystem(
    family = family { all(MoveComponent, CollisionComponent) },
) {
    override fun onTickEntity(entity: Entity) {
        val moveComponent = entity[MoveComponent]
        val collisionComponent = entity[CollisionComponent]
        val mass = collisionComponent.body.mass
        val (velX, velY) = collisionComponent.body.linearVelocity

        if (moveComponent.cos == 0f) {
            collisionComponent.impulse.set(
                mass * (0f - velX),
                mass * (0f - velY),
            )
            return
        }

        collisionComponent.impulse.set(
            mass * (moveComponent.speed * moveComponent.cos - velX),
            0f
        )
    }

}
