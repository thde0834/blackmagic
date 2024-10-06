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

data class ImageComponent(
    val width: Float,
    val height: Float,
    val image: Image = Image(),
    val scaling: Scaling = Scaling.fill
) : Component<ImageComponent> {
    init {
        image.setSize(width, height)
        image.setScaling(scaling)
    }

    fun setPosition(x: Float, y: Float) {
        image.setPosition(
            x - 0.5f * width,
            y - 0.5f * height
        )
    }

    override fun World.onAdd(entity: Entity) {
        inject<Stage>().addActor(image)
    }

    override fun type(): ComponentType<ImageComponent> = ImageComponent

    companion object : ComponentType<ImageComponent>() {
        private val log = logger<AnimationSystem>()
    }
}
