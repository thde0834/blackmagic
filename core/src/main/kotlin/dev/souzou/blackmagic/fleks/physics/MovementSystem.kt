package dev.souzou.blackmagic.fleks.physics

import com.badlogic.gdx.physics.box2d.World as PhysicsWorld
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import dev.souzou.blackmagic.fleks.position.PositionComponent
import ktx.math.component1
import ktx.math.component2
import ktx.math.minus


class MovementSystem(
    private val physicsWorld: PhysicsWorld = inject()
) : IteratingSystem(
    family = family { all(RigidBodyComponent, ColliderComponent) },
) {
    override fun onUpdate() {
        if (physicsWorld.autoClearForces) {
            physicsWorld.autoClearForces = false
        }
        super.onUpdate()
        physicsWorld.clearForces()
    }

    override fun onTick() {
        super.onTick()
        physicsWorld.step(deltaTime, 6, 2)
    }

    override fun onTickEntity(entity: Entity) {
        val cos = entity[RigidBodyComponent].cos
        val sin = entity[RigidBodyComponent].sin
        val acceleration = entity[RigidBodyComponent].acceleration

        entity[ColliderComponent].applyLinearImpulse(cos, sin, acceleration)

        val offset = entity[ColliderComponent].offset
        val body = entity[ColliderComponent].body

        val (x, y) = body.position - offset

        entity[PositionComponent].setPosition(x, y)
    }

}
