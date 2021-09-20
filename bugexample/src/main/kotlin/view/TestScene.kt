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

    private val tokenView = TokenView(
        width = 130,
        height = 200,
        visual = ColorVisual.BLUE,
    ).apply {
        onMouseClicked = {
            animate()
        }
    }

    init {
        sourceArea.add(tokenView)

        addComponents(sourceArea)

        println("Expected: 0 / 0")
        println(tokenView.posX)
        println(tokenView.posY)
        println("Expected: 50 / 50")
        tokenView.reposition(50, 50)
        println(tokenView.posX)
        println(tokenView.posY)
        println("Expected: 0 / 0")
        tokenView.posX = 0.0
        tokenView.posY = 0.0
        println(tokenView.posX)
        println(tokenView.posY)
        println()
    }

    private fun animate() {
        playAnimation(
            MovementAnimation(
                tokenView,
                200,
                200,
                2000
            ).apply {
                onFinished = {
                    println("Expected: 200 / 200")
                    println(tokenView.posX)
                    println(tokenView.posY)
                    println("Expected: 0 / 0")
                    tokenView.posX = 0.0
                    tokenView.posY = 0.0
                    println(tokenView.posX)
                    println(tokenView.posY)
                    println()
                }
            }
        )
    }
}