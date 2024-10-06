package dev.souzou.blackmagic.fleks.animation

// TODO: Each entity should have their own unique AnimationType
enum class AnimationType {
    IDLE, RUN, ATTACK, DEATH;

    val atlasKey: String = this.toString().lowercase()
}
