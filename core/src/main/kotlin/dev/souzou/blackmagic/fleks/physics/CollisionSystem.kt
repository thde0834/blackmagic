package dev.souzou.blackmagic.fleks.physics

import com.badlogic.gdx.physics.box2d.World as PhysicsWorld
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.Fixed
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import dev.souzou.blackmagic.fleks.animation.AnimationSystem
import dev.souzou.blackmagic.fleks.position.PositionComponent
import ktx.log.logger
import ktx.math.component1
import ktx.math.component2
import ktx.math.plus

class CollisionSystem(
    private val physicsWorld: PhysicsWorld = inject()
) : IteratingSystem(
    interval = Fixed(1/60f),
    family = family {any(CollisionComponent, PositionComponent)}
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
        val (x, y) = entity[PositionComponent].position + entity[CollisionComponent].center
        entity[CollisionComponent].setPosition(x, y)
    }

    companion object {
        private val log = logger<AnimationSystem>()
    }
}
