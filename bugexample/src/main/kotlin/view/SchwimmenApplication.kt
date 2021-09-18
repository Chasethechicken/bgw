package view

import tools.aqua.bgw.core.BoardGameApplication

class SchwimmenApplication : BoardGameApplication("Schwimmen") {
    private val testScene1 = TestScene1()
    private val testScene2 = TestScene2()
    private val testScene3 = TestScene3()
    private val testScene4 = TestScene4()

    init {
        showGameScene(testScene1)
        // showGameScene(testScene2)
        // showGameScene(testScene3)
        // showGameScene(testScene4)
    }
}