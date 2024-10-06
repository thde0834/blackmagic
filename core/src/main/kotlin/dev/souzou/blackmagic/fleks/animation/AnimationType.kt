package dev.souzou.blackmagic.fleks.animation

enum class AnimationType {
    IDLE, RUN, ATTACK, DEATH;

    val atlasKey: String = this.toString().lowercase()
}
