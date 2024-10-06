package dev.souzou.blackmagic

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import dev.souzou.blackmagic.screens.GameScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.async.KtxAsync

class MainApplication : KtxGame<KtxScreen>() {
    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        KtxAsync.initiate()

        addScreen(GameScreen())
        setScreen<GameScreen>()
    }
}
