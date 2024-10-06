package dev.souzou.blackmagic.fleks.render

import com.badlogic.gdx.scenes.scene2d.Stage
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import com.github.quillraven.fleks.collection.compareEntityBy
import dev.souzou.blackmagic.fleks.animation.AnimationSystem
import dev.souzou.blackmagic.fleks.position.PositionComponent
import ktx.log.logger

class RenderSystem(
    private val stage: Stage = inject()
) : IteratingSystem(
    comparator = compareEntityBy(PositionComponent),
    family = family { all(ImageComponent, PositionComponent) }
) {
    override fun onTick() {
        super.onTick()

        with(stage) {
            viewport.apply()
            act(deltaTime)
            draw()
        }
    }

    override fun onTickEntity(entity: Entity) {
        val imageComponent = entity[ImageComponent]
        val position = entity[PositionComponent].position

        imageComponent.setPosition(position.x, position.y)
        imageComponent.image.toFront()
    }

    companion object {
        private val log = logger<AnimationSystem>()
    }
}
