package dev.souzou.blackmagic.fleks.animation

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Array
import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.World
import ktx.app.gdxError

data class AnimationComponent(
    private val atlasKey: String,
    private val playMode: PlayMode = PlayMode.LOOP,
) : Component<AnimationComponent> {
    private lateinit var animation: Animation<TextureRegionDrawable>
    private lateinit var animationMap: Map<String, Animation<TextureRegionDrawable>>
    private var stateTime: Float = 0f

    private var nextAnimation: String = NONE

    fun nextAnimation(type: AnimationType) {
        nextAnimation = "$atlasKey/${type.atlasKey}"
    }

    private fun getAtlasKey(type: AnimationType): String {
        return "$atlasKey/${type.atlasKey}"
    }

    override fun World.onAdd(entity: Entity) {
        animationMap = AnimationType.entries.associate {
            getAtlasKey(it) to inject<TextureAtlas>()
                .findRegions("$atlasKey/${it.atlasKey}")
                .toList()
                .ifEmpty {
                    gdxError("No Texture Regions found for: $it")
                }
                .let {
                    Animation<TextureRegionDrawable>(
                        DEFAULT_FRAME_DURATION,
                        Array(
                            it
                                .map { TextureRegionDrawable(it) }
                                .toTypedArray()
                        )
                    )
                }
        }
    }

    fun getKeyFrame(deltaTime: Float): TextureRegionDrawable {
        if (nextAnimation == NONE) {
            stateTime += deltaTime
        } else {
            animation = animationMap.getOrElse(nextAnimation) {
                gdxError("No Animation found for: $nextAnimation")
            }
            stateTime = 0f
            nextAnimation = NONE
        }
        animation.playMode = playMode

        return animation.getKeyFrame(stateTime)
    }

    override fun type(): ComponentType<AnimationComponent> = AnimationComponent

    companion object : ComponentType<AnimationComponent>() {
        const val NONE = ""
        private const val DEFAULT_FRAME_DURATION: Float = 1/8f
    }
}
