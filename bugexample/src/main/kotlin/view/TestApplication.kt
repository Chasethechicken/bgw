package view

import tools.aqua.bgw.core.BoardGameApplication

class TestApplication : BoardGameApplication("") {
    private val testScene = TestScene()

    init {
        showGameScene(testScene)
    }
}