package view

import tools.aqua.bgw.animation.MovementAnimation
import tools.aqua.bgw.components.container.Area
import tools.aqua.bgw.components.gamecomponentviews.TokenView
import tools.aqua.bgw.components.layoutviews.GridPane
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.visual.ColorVisual

class TestScene : BoardGameScene() {
    private val sourceArea = Area<TokenView>(
        posX = 100,
        posY = 100,
        width = 130,
        height = 200,
        visual = ColorVisual.GREEN,
    )

    private val target = GridPane<TokenView>(
        posX = 1000,
        posY = 500,
        columns = 2,
        rows = 2,
        visual = ColorVisual.RED,
    )

    private val tokenView = TokenView(
        width = 130,
        height = 200,
        visual = ColorVisual.BLUE,
    ).apply {
        onMouseClicked = {
            testAnimation(target, 0, 0)
        }
    }

    init {
        sourceArea.add(tokenView)

        target[0, 0] = TokenView(
            width = 130,
            height = 200,
            visual = ColorVisual.LIGHT_GRAY,
        )
        target[1, 0] = TokenView(
            width = 130,
            height = 200,
            visual = ColorVisual.LIGHT_GRAY,
        )
        target[0, 1] = TokenView(
            width = 130,
            height = 200,
            visual = ColorVisual.LIGHT_GRAY,
        )
        target[1, 1] = TokenView(
            width = 130,
            height = 200,
            visual = ColorVisual.LIGHT_GRAY,
        )

        addComponents(sourceArea, target)
    }

    private fun testAnimation(targetGridPane: GridPane<TokenView>, targetX: Int, targetY: Int) {
        val targetView = checkNotNull(targetGridPane[targetX, targetY])
        playAnimation(
            MovementAnimation.toComponentView(
                componentView = tokenView,
                toComponentViewPosition = targetView,
                scene = this,
                duration = 2000,
            ).apply {
                onFinished = {
                    tokenView.removeFromParent()
                    targetGridPane[targetX, targetY] = tokenView
                }
            }
        )
    }
}