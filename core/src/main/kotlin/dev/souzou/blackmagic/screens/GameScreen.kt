package dev.souzou.blackmagic.screens

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.github.quillraven.fleks.World
import com.github.quillraven.fleks.configureWorld
import dev.souzou.blackmagic.fleks.render.ImageComponent
import dev.souzou.blackmagic.fleks.animation.AnimationComponent
import dev.souzou.blackmagic.fleks.animation.AnimationSystem
import dev.souzou.blackmagic.fleks.animation.AnimationType
import dev.souzou.blackmagic.fleks.debug.DebugSystem
import dev.souzou.blackmagic.fleks.physics.RigidBodyComponent
import dev.souzou.blackmagic.fleks.physics.MovementSystem
import dev.souzou.blackmagic.fleks.physics.ColliderComponent
import dev.souzou.blackmagic.fleks.player.PlayerComponent
import dev.souzou.blackmagic.fleks.position.PositionComponent
import dev.souzou.blackmagic.fleks.render.RenderSystem
import dev.souzou.blackmagic.input.KeyboardProcessor
import ktx.app.KtxScreen
import ktx.assets.disposeSafely
import ktx.box2d.createWorld
import ktx.log.logger
import ktx.math.vec2

class GameScreen : KtxScreen {
    private val stage: Stage = Stage(ExtendViewport(16f, 9f))
    private val textureAtlas: TextureAtlas = TextureAtlas("graphics/mystic_woods.atlas")
    private val physicsWorld = createWorld(gravity = vec2())
        .apply { autoClearForces = false }

    private val world: World = configureWorld {
        injectables {
            add(stage)
            add(textureAtlas)
            add(physicsWorld)
        }
        systems {
            add(RenderSystem())
            add(AnimationSystem())
            add(MovementSystem())
            add(DebugSystem())
        }
    }

    override fun show() {
        log.debug {"GameScreen is shown"}

        world.entity {
            it += PositionComponent(vec2(2f, 7f))

            it += ImageComponent(
                width = 4f,
                height = 4f,
            )

            it += AnimationComponent("player")
                .apply {
                    nextAnimation(AnimationType.RUN)
                }

            it += ColliderComponent(
                vec2(0f, -0.5f),
                1f, 2f,
                BodyDef.BodyType.DynamicBody)

            it += RigidBodyComponent()
            it += PlayerComponent()
        }

        world.entity {
            it += PositionComponent(vec2(7f, 7f))

            it += ImageComponent(
                width = 4f,
                height = 4f,
            )

            it += AnimationComponent("player")
                .apply {
                    nextAnimation(AnimationType.RUN)
                }

            it += ColliderComponent(
                vec2(0f, -0.5f),
                1f, 2f,
                BodyDef.BodyType.DynamicBody)

            it += RigidBodyComponent()
        }

        KeyboardProcessor(world)
    }

    override fun render(delta: Float) {
        world.update(delta)
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        stage.disposeSafely()
        textureAtlas.disposeSafely()
        world.dispose()
        physicsWorld.disposeSafely()
    }

    companion object {
        private val log = logger<GameScreen>()
    }
}
