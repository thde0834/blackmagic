package dev.souzou.blackmagic.fleks.animation

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import dev.souzou.blackmagic.fleks.render.RenderComponent
import ktx.log.logger

class AnimationSystem: IteratingSystem(
    family { all(AnimationComponent, RenderComponent) }
) {
    override fun onTickEntity(entity: Entity) {
        entity[RenderComponent].image.drawable =
            entity[AnimationComponent].getKeyFrame(deltaTime)
    }

    companion object {
        private val log = logger<AnimationSystem>()
    }

}
