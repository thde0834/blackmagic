package dev.souzou.blackmagic.fleks.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.World as PhysicsWorld
import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.World
import dev.souzou.blackmagic.fleks.render.RenderComponent
import ktx.box2d.box
import ktx.math.component1
import ktx.math.component2
import ktx.math.plus
import ktx.math.vec2

data class ColliderComponent(
    val offset: Vector2,
    private val width: Float,
    private val height: Float,
    private val bodyType: BodyType,
) : Component<ColliderComponent> {
    lateinit var body: Body

    override fun World.onAdd(entity: Entity) {
        body = inject<PhysicsWorld>().createBody(
            BodyDef().apply {
                type = bodyType
                position.set(entity[RenderComponent].position + offset)
                fixedRotation = true
                allowSleep = false
            }
        )
        body.box(width, height) {
            isSensor = false
        }
        body.userData = entity
    }

    override fun World.onRemove(entity: Entity) {
        body.world.destroyBody(body)
        body.userData = null
    }

    fun applyLinearImpulse(cos: Float, sin: Float, acceleration: Float) {
        val (velX, velY) = body.linearVelocity

        val impulse = vec2(
            body.mass * (acceleration * cos - velX),
            body.mass * (acceleration * sin - velY)
        )

        if (!impulse.isZero) {
            body.applyLinearImpulse(
                impulse,
                body.worldCenter,
                true
            )
            impulse.setZero()
        }
    }

    override fun type(): ComponentType<ColliderComponent> = ColliderComponent

    companion object: ComponentType<ColliderComponent>()
}
