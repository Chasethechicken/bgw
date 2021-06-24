package examples.maumau.view

import examples.main.BG_FILE
import examples.main.CARDS_FILE
import examples.main.IMG_HEIGHT
import examples.main.IMG_WIDTH
import examples.maumau.model.CardSuit
import examples.maumau.model.CardValue
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.core.HorizontalAlignment
import tools.aqua.bgw.core.VerticalAlignment
import tools.aqua.bgw.elements.container.AreaContainerView
import tools.aqua.bgw.elements.container.CardStackView
import tools.aqua.bgw.elements.container.GameElementPoolView
import tools.aqua.bgw.elements.container.LinearLayoutContainer
import tools.aqua.bgw.elements.gameelements.CardView
import tools.aqua.bgw.elements.gameelements.TokenView
import tools.aqua.bgw.elements.uielements.Button
import tools.aqua.bgw.elements.uielements.Label
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.util.FontStyle
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color
import javax.imageio.ImageIO

class GameScene : BoardGameScene() {

    //Player hands
    var otherPlayerHand =
        LinearLayoutContainer<CardView>(height = 220, width = 800, posX = 560, posY = 50, spacing = -50)
    var currentPlayerHand =
        LinearLayoutContainer<CardView>(height = 220, width = 800, posX = 560, posY = 830, spacing = -50)

    //Stacks
    val drawStackView = CardStackView<CardView>(height = 200, width = 130, posX = 750, posY = 440)
    val gameStackView = CardStackView<CardView>(height = 200, width = 130, posX = 1040, posY = 440)
    val drawStackInfo = Label(height = 40, width = 130, posX = 750, posY = 400)
    val gameStackInfo = Label(height = 40, width = 130, posX = 1040, posY = 400)

    //Jack selection
    val buttonClubs = Button(height = 200, width = 130, posX = 820, posY = 330)
    val buttonSpades = Button(height = 200, width = 130, posX = 970, posY = 330)
    val buttonHearts = Button(height = 200, width = 130, posX = 820, posY = 550)
    val buttonDiamonds = Button(height = 200, width = 130, posX = 970, posY = 550)

    //Menu button
    val mainMenuButton = Button(
        height = 100,
        width = 200,
        posX = 20,
        posY = 20,
        label = "Hauptmenü",
        font = Font(20.0, fontStyle = FontStyle.Italic, color = Color.PINK)
    )

    init {
        //Set background of scene
        background = ImageVisual(ImageIO.read(this::class.java.classLoader.getResource(BG_FILE)))

        //Color MenuButton
        mainMenuButton.setVisuals(ColorVisual(180, 0, 0))

        //Set up player hands
        currentPlayerHand.apply {
            setVisuals(ColorVisual(Color(255, 255, 255, 50)))
            horizontalAlignment = HorizontalAlignment.CENTER
            verticalAlignment = VerticalAlignment.CENTER
        }
        otherPlayerHand.apply {
            setVisuals(ColorVisual(Color(255, 255, 255, 50)))
            horizontalAlignment = HorizontalAlignment.CENTER
            verticalAlignment = VerticalAlignment.CENTER
            rotation = 180.0
        }

        //Background color for stack
        drawStackView.setVisuals(ColorVisual(Color(255, 255, 255, 50)))
        gameStackView.setVisuals(ColorVisual(Color(255, 255, 255, 50)))

        //Jack selection visuals
        buttonDiamonds.setVisuals(
            ImageVisual.loadSubImage(
                CARDS_FILE,
                CardValue.ACE.ordinal * IMG_WIDTH,
                CardSuit.DIAMONDS.ordinal * IMG_HEIGHT,
                IMG_WIDTH,
                IMG_HEIGHT
            )
        )
        buttonHearts.setVisuals(
            ImageVisual.loadSubImage(
                CARDS_FILE,
                CardValue.ACE.ordinal * IMG_WIDTH,
                CardSuit.HEARTS.ordinal * IMG_HEIGHT,
                IMG_WIDTH,
                IMG_HEIGHT
            )
        )
        buttonSpades.setVisuals(
            ImageVisual.loadSubImage(
                CARDS_FILE,
                CardValue.ACE.ordinal * IMG_WIDTH,
                CardSuit.SPADES.ordinal * IMG_HEIGHT,
                IMG_WIDTH,
                IMG_HEIGHT
            )

        )
        buttonClubs.setVisuals(
            ImageVisual.loadSubImage(
                CARDS_FILE,
                CardValue.ACE.ordinal * IMG_WIDTH,
                CardSuit.CLUBS.ordinal * IMG_HEIGHT,
                IMG_WIDTH,
                IMG_HEIGHT
            )
        )

        //Hide jack selection
        buttonDiamonds.isVisible = false
        buttonHearts.isVisible = false
        buttonSpades.isVisible = false
        buttonClubs.isVisible = false

        //Add views
        addElements(
            drawStackView,
            gameStackView,
            currentPlayerHand,
            otherPlayerHand,
            drawStackInfo,
            gameStackInfo,
            buttonDiamonds,
            buttonHearts,
            buttonSpades,
            buttonClubs,
            mainMenuButton,
        )
    }
}

