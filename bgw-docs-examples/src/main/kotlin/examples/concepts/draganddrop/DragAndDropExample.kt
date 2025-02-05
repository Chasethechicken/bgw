package examples.concepts.draganddrop

import tools.aqua.bgw.components.container.Area
import tools.aqua.bgw.components.gamecomponentviews.TokenView
import tools.aqua.bgw.core.BoardGameApplication
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.visual.ColorVisual

fun main() {
    DragAndDropExample()
}

class DragAndDropExample : BoardGameApplication("Drag and drop example") {
    private val gameScene: BoardGameScene = BoardGameScene(background = ColorVisual.LIGHT_GRAY)
    
    private val redToken: TokenView = TokenView(posX = 20, posY = 20, visual = ColorVisual.RED)
    private val greenToken: TokenView = TokenView(posX = 20, posY = 200, visual = ColorVisual.GREEN)
    
    private val redArea: Area<TokenView> = Area(
        height = 50,
        width = 50,
        posX = 200,
        posY = 20,
        visual = ColorVisual(255, 0, 0, 100)
    )
    
    private val greenArea: Area<TokenView> = Area(
        height = 50,
        width = 50,
        posX = 200,
        posY = 200,
        visual = ColorVisual(0, 255, 0, 100)
    )
    
    init {
        redToken.isDraggable = true
        redToken.onDragGestureEnded = { _, success ->
            if (success) {
                redToken.isDraggable = false
            }
        }
        
        greenToken.isDraggable = true
        greenToken.onDragGestureEnded = { _, success ->
            if (success) {
                greenToken.isDraggable = false
            }
        }

        redArea.dropAcceptor = { dragEvent ->
            when (dragEvent.draggedComponent) {
                is TokenView -> dragEvent.draggedComponent == redToken
                else -> false
            }
        }
        redArea.onDragDropped = { dragEvent ->
            redArea.add((dragEvent.draggedComponent as TokenView).apply { reposition(0, 0) })
        }

        greenArea.dropAcceptor = { dragEvent ->
            when (dragEvent.draggedComponent) {
                is TokenView -> dragEvent.draggedComponent == greenToken
                else -> false
            }
        }
        greenArea.onDragDropped = { dragEvent ->
            greenArea.add((dragEvent.draggedComponent as TokenView).apply { reposition(0, 0) })
        }

        gameScene.addComponents(redToken, greenToken, redArea, greenArea)
        showGameScene(gameScene)
        show()
    }
}