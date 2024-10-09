package dev.souzou.blackmagic.fleks.render

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Scaling
import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.World
import dev.souzou.blackmagic.fleks.animation.AnimationSystem
import ktx.log.logger

data class RenderComponent(
    val position: Vector2,
    val width: Float,
    val height: Float,
    val z: Float = 0f,
    val image: Image = Image(),
    val scaling: Scaling = Scaling.fill
) : Component<RenderComponent>, Comparable<RenderComponent> {
    val previousPosition: Vector2 = position

    init {
        setPosition(position.x, position.y)
        image.setSize(width, height)
        image.setScaling(scaling)
    }

    fun setPosition(x: Float, y: Float) {
        position.set(x, y)
        image.setPosition(
            x - 0.5f * width,
            y - 0.5f * height
        )
    }

    override fun World.onAdd(entity: Entity) {
        inject<Stage>().addActor(image)
    }

    override fun compareTo(other: RenderComponent): Int {
        return z.compareTo(other.z)
    }

    override fun type(): ComponentType<RenderComponent> = RenderComponent

    companion object : ComponentType<RenderComponent>() {
        private val log = logger<AnimationSystem>()
    }
}
