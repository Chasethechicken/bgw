@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package tools.aqua.bgw.elements.container

import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.HorizontalAlignment
import tools.aqua.bgw.core.VerticalAlignment
import tools.aqua.bgw.elements.gameelements.GameElementView
import tools.aqua.bgw.elements.uielements.Orientation
import tools.aqua.bgw.observable.DoubleProperty
import tools.aqua.bgw.observable.ObjectProperty
import tools.aqua.bgw.observable.Property
import tools.aqua.bgw.visual.Visual

/**
 * A [LinearLayoutContainer] may be used to visualize a zone containing [GameElementView]s.
 * [GameElementView]s inside the container get placed according to the specified [Orientation] and [Alignment].
 * A [spacing] between Elements may be specified which may also be negative
 * e.g. Elements like playing cards should overlap.
 *
 * Visualization:
 * The [Visual] is used to visualize a background.
 * If all Elements are still within bounds with the user defined spacing,
 * the user defined spacing gets used to space the Elements.
 * Otherwise the biggest possible spacing is used
 * so that all Elements are still withing bounds of the [LinearLayoutContainer].
 *
 * @param height height for this [LinearLayoutContainer]. Default: 0.
 * @param width width for this [LinearLayoutContainer]. Default: 0.
 * @param posX horizontal coordinate for this [LinearLayoutContainer]. Default: 0.
 * @param posY vertical coordinate for this [LinearLayoutContainer]. Default: 0.
 * @param spacing spacing between contained [GameElementView]s. Default: 0.
 * @param orientation orientation for this [LinearLayoutContainer]. Default: [Orientation.HORIZONTAL].
 * @param alignment specifies how the contained Elements should be aligned.
 * Default: [Alignment.TOP_LEFT].
 */
open class LinearLayoutContainer<T : GameElementView>(
	height: Number = 0,
	width: Number = 0,
	posX: Number = 0,
	posY: Number = 0,
	spacing: Number = 0,
	visual: Visual = Visual.EMPTY,
	orientation: Orientation = Orientation.HORIZONTAL,
	alignment: Alignment = Alignment.TOP_LEFT,
) : GameElementContainerView<T>(height = height, width = width, posX = posX, posY = posY, visual = visual) {
	
	/**
	 * Secondary constructor taking separate alignment components.
	 *
	 * @param height height for this [LinearLayoutContainer]. Default: 0.
	 * @param width width for this [LinearLayoutContainer]. Default: 0.
	 * @param posX horizontal coordinate for this [LinearLayoutContainer]. Default: 0.
	 * @param posY vertical coordinate for this [LinearLayoutContainer]. Default: 0.
	 * @param spacing spacing between contained [GameElementView]s. Default: 0.
	 * @param orientation orientation for this [LinearLayoutContainer]. Default: [Orientation.HORIZONTAL].
	 * @param verticalAlignment specifies how the contained Elements should be aligned vertically.
	 * Default: [VerticalAlignment.TOP].
	 * @param horizontalAlignment specifies how the contained Elements should be aligned horizontally.
	 * Default: [HorizontalAlignment.LEFT].
	 */
	constructor(
		height: Number = 0,
		width: Number = 0,
		posX: Number = 0,
		posY: Number = 0,
		spacing: Number = 0,
		visual: Visual = Visual.EMPTY,
		orientation: Orientation = Orientation.HORIZONTAL,
		verticalAlignment: VerticalAlignment = VerticalAlignment.TOP,
		horizontalAlignment: HorizontalAlignment = HorizontalAlignment.LEFT
	) : this(
		height, width, posX, posY, spacing, visual, orientation, Alignment.of(verticalAlignment, horizontalAlignment)
	)
	
	/**
	 * [Property] for the spacing of [GameElementView]s in this [LinearLayoutContainer].
	 */
	val spacingProperty: DoubleProperty = DoubleProperty(spacing.toDouble())
	
	/**
	 * Spacing for this [LinearLayoutContainer].
	 */
	var spacing: Double
		get() = spacingProperty.value
		set(value) {
			spacingProperty.value = value
		}
	
	/**
	 * [Property] for the orientation of [GameElementView]s in this [LinearLayoutContainer].
	 * @see Orientation
	 */
	val orientationProperty: ObjectProperty<Orientation> = ObjectProperty(orientation)
	
	/**
	 * [Orientation] for this [LinearLayoutContainer].
	 * @see Orientation
	 * @see orientationProperty
	 */
	var orientation: Orientation
		get() = orientationProperty.value
		set(value) {
			orientationProperty.value = value
		}
	
	/**
	 * [Property] for the [Alignment] of [GameElementView]s in this [LinearLayoutContainer].
	 * @see VerticalAlignment
	 */
	val alignmentProperty: ObjectProperty<Alignment> = ObjectProperty(alignment)
	
	/**
	 * [Alignment] for this [LinearLayoutContainer].
	 *
	 * @see Alignment
	 * @see VerticalAlignment
	 * @see verticalAlignment
	 * @see HorizontalAlignment
	 * @see horizontalAlignment
	 */
	var alignment: Alignment
		get() = alignmentProperty.value
		set(value) {
			alignmentProperty.value = value
		}
	
	/**
	 * [VerticalAlignment] for this [LinearLayoutContainer].
	 *
	 * @see Alignment
	 * @see alignment
	 * @see VerticalAlignment
	 * @see HorizontalAlignment
	 * @see horizontalAlignment
	 */
	var verticalAlignment: VerticalAlignment
		get() = alignmentProperty.value.verticalAlignment
		set(value) {
			alignmentProperty.value = Alignment.of(value, alignmentProperty.value.horizontalAlignment)
		}
	
	/**
	 * [HorizontalAlignment] for this [LinearLayoutContainer].
	 *
	 * @see Alignment
	 * @see alignment
	 * @see VerticalAlignment
	 * @see verticalAlignment
	 * @see HorizontalAlignment
	 */
	var horizontalAlignment: HorizontalAlignment
		get() = alignmentProperty.value.horizontalAlignment
		set(value) {
			alignmentProperty.value = Alignment.of(alignmentProperty.value.verticalAlignment, value)
		}
	
	init {
		observableElements.setInternalListenerAndInvoke {
			layout()
		}
		spacingProperty.internalListener = { _, _ -> layout() }
		orientationProperty.internalListener = { _, _ -> layout() }
		alignmentProperty.internalListener = { _, _ -> layout() }
	}
	
	override fun add(element: T, index: Int) {
		super.add(element, index)
		element.apply { addPosListeners() }
	}
	
	override fun addAll(collection: Collection<T>) {
		super.addAll(collection)
		collection.forEach { it.addPosListeners() }
	}
	
	override fun addAll(vararg elements: T) {
		addAll(elements.toList())
	}
	
	override fun remove(element: T) {
		super.remove(element.apply { removePosListeners() })
	}
	
	override fun removeAll(): List<T> {
		return super.removeAll().onEach {
			it.removePosListeners()
		}
	}
	
	private fun T.addPosListeners() {
		posXProperty.internalListener = { _, _ -> observableElements.internalListener?.invoke() }
		posYProperty.internalListener = { _, _ -> observableElements.internalListener?.invoke() }
	}
	
	private fun T.removePosListeners() {
		posXProperty.internalListener = null
		posYProperty.internalListener = null
	}
	
	private fun layoutHorizontal() {
		val totalContentWidth: Double = observableElements.sumOf { it.width }
		val totalContentWidthWithSpacing = totalContentWidth + (observableElements.size - 1) * spacing
		val newSpacing: Double = if (totalContentWidthWithSpacing > width) {
			-((totalContentWidth - width) / (observableElements.size - 1)) //ignore user defined spacing
		} else {
			spacing //use user defined spacing
		}
		val newTotalContentWidth = totalContentWidth + (observableElements.size - 1) * newSpacing
		val initial = when (horizontalAlignment) {
			HorizontalAlignment.LEFT -> 0.0
			HorizontalAlignment.CENTER -> (width - newTotalContentWidth) / 2
			HorizontalAlignment.RIGHT -> width - newTotalContentWidth
		}
		
		observableElements.fold(initial) { acc: Double, element: T ->
			element.posYProperty.setSilent(
				when (verticalAlignment) {
					VerticalAlignment.TOP -> 0.0
					VerticalAlignment.CENTER -> (height - element.height) / 2
					VerticalAlignment.BOTTOM -> height - element.height
				}
			)
			element.posXProperty.setSilent(acc)
			acc + element.width + newSpacing
		}
	}
	
	private fun layoutVertical() {
		val totalContentHeight: Double = observableElements.sumOf { it.height }
		val totalContentHeightWithSpacing = totalContentHeight + (observableElements.size - 1) * spacing
		val newSpacing: Double = if (totalContentHeightWithSpacing > height) {
			-((totalContentHeight - height) / (observableElements.size - 1)) //ignore user defined spacing
		} else {
			spacing //use user defined spacing
		}
		val newTotalContentHeight = totalContentHeight + (observableElements.size - 1) * newSpacing
		val initial = when (verticalAlignment) {
			VerticalAlignment.TOP -> 0.0
			VerticalAlignment.CENTER -> (width - newTotalContentHeight) / 2
			VerticalAlignment.BOTTOM -> width - newTotalContentHeight
		}
		observableElements.fold(initial) { acc: Double, element: T ->
			element.posYProperty.setSilent(acc)
			element.posXProperty.setSilent(
				when (horizontalAlignment) {
					HorizontalAlignment.LEFT -> 0.0
					HorizontalAlignment.CENTER -> (width - element.width) / 2
					HorizontalAlignment.RIGHT -> width - element.width
				}
			)
			acc + element.height + newSpacing
		}
	}
	
	private fun layout() {
		when (orientation) {
			Orientation.HORIZONTAL -> layoutHorizontal()
			Orientation.VERTICAL -> layoutVertical()
		}
	}
}