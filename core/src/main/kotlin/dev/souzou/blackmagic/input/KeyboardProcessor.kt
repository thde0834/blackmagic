package dev.souzou.blackmagic.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.LEFT
import com.badlogic.gdx.Input.Keys.RIGHT
import com.github.quillraven.fleks.World
import dev.souzou.blackmagic.fleks.physics.RigidBodyComponent
import dev.souzou.blackmagic.fleks.player.PlayerComponent
import ktx.app.KtxInputAdapter

class KeyboardProcessor(
    private val world: World,
) : KtxInputAdapter {
    private val playerEntities = world.family {
        all(PlayerComponent, RigidBodyComponent)
    }
    private var inputCos = 0f

    init {
        Gdx.input.inputProcessor = this
    }

    private fun Int.isMovementKey() : Boolean {
        return this == LEFT || this == RIGHT
    }

    private fun updatePlayerMovement() {
        playerEntities.forEach { entity ->
            with(entity[RigidBodyComponent]) {
                cos = inputCos
            }
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode.isMovementKey()) {
            when(keycode) {
                LEFT -> inputCos = -1f
                RIGHT -> inputCos = 1f
            }
            updatePlayerMovement()
            return true
        }
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        if (keycode.isMovementKey()) {
            when(keycode) {
                LEFT -> inputCos = if(Gdx.input.isKeyPressed(RIGHT)) 1f else 0f
                RIGHT -> inputCos = if(Gdx.input.isKeyPressed(LEFT)) -1f else 0f
            }
            updatePlayerMovement()
            return true
        }
        return false
    }
}
