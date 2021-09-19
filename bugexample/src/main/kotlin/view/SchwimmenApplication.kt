package view

import tools.aqua.bgw.core.BoardGameApplication

class SchwimmenApplication : BoardGameApplication("") {
    private val testScene = TestScene()

    init {
        showGameScene(testScene)
    }
}