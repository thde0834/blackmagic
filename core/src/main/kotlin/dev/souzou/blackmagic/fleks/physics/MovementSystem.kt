package dev.souzou.blackmagic.fleks.physics

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.World as PhysicsWorld
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.Fixed
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import dev.souzou.blackmagic.fleks.render.RenderComponent
import ktx.math.component1
import ktx.math.component2
import ktx.math.minus


class MovementSystem(
    private val physicsWorld: PhysicsWorld = inject()
) : IteratingSystem(
    interval = Fixed(1/60f),
    family = family { all(RigidBodyComponent, ColliderComponent, RenderComponent) },
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
        with(entity[RenderComponent]) {
            previousPosition.set(position)
        }

        with(entity[RigidBodyComponent]) {
            entity[ColliderComponent].applyLinearImpulse(cos, sin, acceleration)
        }
    }

    override fun onAlphaEntity(entity: Entity, alpha: Float) {
        val (bodyX, bodyY) = with(entity[ColliderComponent]) {
            body.position - offset
        }

        with(entity[RenderComponent]) {
            setPosition(
                MathUtils.lerp(previousPosition.x, bodyX, alpha),
                MathUtils.lerp(previousPosition.y, bodyY, alpha),
            )
        }
    }

}
