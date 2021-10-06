package view

import tools.aqua.bgw.components.gamecomponentviews.TokenView
import tools.aqua.bgw.components.layoutviews.GridPane
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.visual.ColorVisual

class TestScene : BoardGameScene() {
    private val testGrid = GridPane<TokenView>(
        columns = 4,
        rows = 1,
        posX = 500,
        posY = 500,
    ).apply {
        scale = 0.5
        rotation = 270.0
    }

    private val tokenView = TokenView(
        width = 130,
        height = 200,
        visual = ColorVisual.BLUE,
    ).apply {
        isDraggable = true
        rotation = 90.0
    }

    init {
        testGrid[0, 0] = tokenView

        addComponents(testGrid)
    }
}