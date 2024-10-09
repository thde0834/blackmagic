package dev.souzou.blackmagic.fleks.render

import com.badlogic.gdx.scenes.scene2d.Stage
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import com.github.quillraven.fleks.collection.compareEntityBy
import ktx.log.logger

class RenderSystem(
    private val stage: Stage = inject()
) : IteratingSystem(
    comparator = compareEntityBy(RenderComponent),
    family = family { all(RenderComponent) }
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
        entity[RenderComponent].image.toFront()
    }

    companion object {
        private val log = logger<RenderSystem>()
    }
}
