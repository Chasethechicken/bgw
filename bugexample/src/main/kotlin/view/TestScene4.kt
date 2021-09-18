package view

import entity.CardImageLoader
import entity.CardSuit
import entity.CardValue
import tools.aqua.bgw.animation.MovementAnimation
import tools.aqua.bgw.components.container.Area
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.layoutviews.GridPane
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual

class TestScene4 : BoardGameScene() {
    private val cardImageLoader = CardImageLoader()

    private val gridPane1 = GridPane<CardView>(
        posX = 100,
        posY = 100,
        columns = 5,
        rows = 1,
        spacing = 25,
        layoutFromCenter = false,
        visual = ColorVisual.ORANGE,
    )

    private val cardView = CardView(
        width = 130,
        height = 200,
        front = ImageVisual(cardImageLoader.frontImageFor(CardSuit.HEARTS, CardValue.ACE)),
        back = ImageVisual(cardImageLoader.backImage),
    ).apply {
        isDraggable = true
    }

    init {
        gridPane1[0, 0] = cardView

        addComponents(gridPane1)
    }
}