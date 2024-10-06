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
import ktx.box2d.box

data class CollisionComponent(
    val center: Vector2,
    val width: Float,
    val height: Float,
    val bodyType: BodyType,
) : Component<CollisionComponent> {
    lateinit var body: Body

    fun setPosition(x: Float, y: Float) {
        body.setTransform(x, y, 0f)
    }

    override fun World.onAdd(entity: Entity) {
        body = inject<PhysicsWorld>().createBody(
            BodyDef().apply {
                type = bodyType
                position.set(center)
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
