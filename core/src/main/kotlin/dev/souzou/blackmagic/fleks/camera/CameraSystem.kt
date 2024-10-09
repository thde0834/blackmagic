package dev.souzou.blackmagic.fleks.camera

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import dev.souzou.blackmagic.fleks.player.PlayerComponent
import dev.souzou.blackmagic.fleks.render.RenderComponent
import ktx.math.component1
import ktx.math.component2

class CameraSystem(
    stage: Stage = inject()
) : IteratingSystem(
    family = family { all(PlayerComponent, RenderComponent) }
) {
    private val camera: Camera = stage.camera

    override fun onTickEntity(entity: Entity) {
        val (x, y) = entity[RenderComponent].position
        camera.position.set(x, y, camera.position.z)
    }

}
