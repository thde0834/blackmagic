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
import dev.souzou.blackmagic.fleks.position.PositionComponent
import ktx.box2d.box
import ktx.math.plus
import ktx.math.vec2

data class CollisionComponent(
    val offset: Vector2,
    private val width: Float,
    private val height: Float,
    private val bodyType: BodyType,
) : Component<CollisionComponent> {
    val impulse = vec2()
    lateinit var body: Body

    fun setPosition(x: Float, y: Float) {
        body.position.x = x
        body.position.y = y
    }

    override fun World.onAdd(entity: Entity) {
        body = inject<PhysicsWorld>().createBody(
            BodyDef().apply {
                type = bodyType
                position.set(entity[PositionComponent].center + offset)
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

    override fun type(): ComponentType<CollisionComponent> = CollisionComponent

    companion object: ComponentType<CollisionComponent>()
}
