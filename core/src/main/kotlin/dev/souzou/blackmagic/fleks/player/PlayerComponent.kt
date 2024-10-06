package dev.souzou.blackmagic.fleks.player

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

class PlayerComponent(

) : Component<PlayerComponent> {


    override fun type(): ComponentType<PlayerComponent> = PlayerComponent

    companion object: ComponentType<PlayerComponent>()
}
