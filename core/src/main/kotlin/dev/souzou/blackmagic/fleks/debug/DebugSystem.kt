package dev.souzou.blackmagic.fleks.debug

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import com.github.quillraven.fleks.IntervalSystem
import com.github.quillraven.fleks.World.Companion.inject
import ktx.assets.disposeSafely

class DebugSystem(
    private val physicsWorld: World = inject(),
    private val stage: Stage = inject()
) : IntervalSystem(enabled = false) {
    private lateinit var debugRenderer: Box2DDebugRenderer

    init {
        if (enabled) {
            debugRenderer = Box2DDebugRenderer()
        }
    }

    override fun onTick() {
        debugRenderer.render(physicsWorld, stage.camera.combined)
    }

    override fun onDispose() {
        if (enabled) {
            debugRenderer.disposeSafely()
        }
    }

}
