@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package tools.aqua.bgw.elements.container

import tools.aqua.bgw.elements.DynamicView
import tools.aqua.bgw.elements.ElementView
import tools.aqua.bgw.elements.gameelements.GameElementView
import tools.aqua.bgw.observable.IObservable
import tools.aqua.bgw.observable.ObservableLinkedList
import tools.aqua.bgw.observable.ObservableList
import tools.aqua.bgw.util.Coordinate
import tools.aqua.bgw.visual.Visual

/**
 * GameElementContainerView is the abstract baseclass for containers that can contain [ElementView]s or its subclasses.
 * It provides the list to store [ElementView]s and some useful methods to work on said list.
 *
 * @param height height for this [GameElementContainerView].
 * @param width width for this [GameElementContainerView].
 * @param posX horizontal coordinate for this [GameElementContainerView].
 * @param posY vertical coordinate for this [GameElementContainerView].
 * @param visual visual for this [GameElementContainerView].
 */
sealed class GameElementContainerView<T : GameElementView>(
	height: Number,
	width: Number,
	posX: Number,
	posY: Number,
	visual: Visual
) : DynamicView(height = height, width = width, visual = visual, posX = posX, posY = posY), Iterable<T> {
	/**
	 * An ObservableList to store the ElementViews that are contained in this GameElementContainerView.
	 * If changes are made to this list, this GameElementContainerView gets re-rendered.
	 */
	internal val observableElements: ObservableList<T> = ObservableLinkedList()
	
	/**
	 * ElementViews that are contained in this GameElementContainerView.
	 * If changes are made to this list, this GameElementContainerView gets re-rendered.
	 */
	var elements: List<T> = observableElements.toList()
		get() = observableElements.toList()
		private set
	
	/**
	 * Adds a listener on the observableElements list.
	 */
	fun addElementsListener(listener: IObservable) {
		observableElements.addListener(listener)
	}
	
	/**
	 * Removes a listener from the observableElements list.
	 */
	fun removeElementsListener(listener: IObservable) {
		observableElements.removeListener(listener)
	}
	
	/**
	 * Removes all listeners from the observableElements list.
	 */
	fun clearElementsListener() {
		observableElements.clearListeners()
	}
	
	/**
	 * Adds an ElementView to this GameElementViewContainer.
	 *
	 * @param element element to add.
	 * @throws IllegalArgumentException when [element] is already contained.
	 * @throws IllegalArgumentException when [index] is out of bounds for [elements].
	 */
	@Synchronized
	open fun add(element: T, index: Int = observableElements.size()) {
		require(!observableElements.contains(element)) {
			"Element $element is already contained in this $this."
		}
		require(element.parent == null) {
			"Element $element is already contained in another container."
		}
		require(index in 0..observableElements.size()) {
			"Index $index is out of list range."
		}
		
		observableElements.add(index, element.apply { parent = this@GameElementContainerView })
	}
	
	/**
	 * Adds all ElementViews passed as varargs to this GameElementContainerView.
	 * Whenever an ElementView is encountered, that is already contained, an
	 * [IllegalArgumentException] is thrown and no further ElementView is added.
	 *
	 * @param elements vararg ElementViews to add.
	 * @throws IllegalArgumentException when an ElementView is already contained.
	 */
	open fun addAll(vararg elements: T) {
		try {
			addAll(elements.toList())
		} catch (e: IllegalArgumentException) {
			throw IllegalArgumentException(e.message)
		}
	}
	
	/**
	 * Adds all ElementViews contained in the passed collection to this GameElementContainerView.
	 * Whenever an ElementView is encountered, that is already contained, an
	 * [IllegalArgumentException] is thrown and no further ElementView is added.
	 *
	 * @param collection collection containing the ElementViews to add.
	 * @throws IllegalArgumentException when an ElementView is already contained.
	 */
	@Synchronized
	open fun addAll(collection: Collection<T>) {
		try {
			collection.forEach { add(it) }
		} catch (e: IllegalArgumentException) {
			throw IllegalArgumentException(e.message)
		}
	}
	
	/**
	 * Removes the element specified by the parameter from this GameElementContainerView.
	 *
	 * @param element the element to remove.
	 */
	@Synchronized
	open fun remove(element: T) {
		observableElements.remove(element.apply { parent = null })
	}
	
	/**
	 * Removes all ElementViews from this GameElementContainerView and returns them.
	 * @return list of all removed Elements
	 */
	@Synchronized
	open fun removeAll(): List<T> {
		val tmp = observableElements.toList()
		observableElements.forEach { it.parent = null }
		observableElements.clear()
		return tmp
	}
	
	/**
	 * Returns the size of the elements list.
	 * @see elements
	 */
	fun numberOfElements(): Int = observableElements.size()
	
	/**
	 * Returns whether the elements list is empty.
	 *
	 * @return `true` if this list contains no elements, `false` otherwise.
	 *
	 * @see isNotEmpty
	 * @see elements
	 */
	fun isEmpty(): Boolean = observableElements.isEmpty()
	
	/**
	 * Returns whether the elements list is not empty.
	 *
	 * @return `true` if this list contains elements, `false` otherwise.
	 *
	 * @see isEmpty
	 * @see elements
	 */
	fun isNotEmpty(): Boolean = !isEmpty()
	
	/**
	 * Method returning a contained child's coordinates within this container.
	 *
	 * @param child child to find.
	 *
	 * @return coordinate of given child in this container relative to containers anchor point.
	 */
	override fun getChildPosition(child: ElementView): Coordinate? = Coordinate(child.posX, child.posY)
	
	/**
	 * Removes element from container's children.
	 *
	 * @param child child to be removed.
	 *
	 * @throws RuntimeException if the child's type is incompatible with container's type.
	 */
	override fun removeChild(child: ElementView) {
		try {
			@Suppress("UNCHECKED_CAST")
			this.remove(child as T)
		} catch (_: ClassCastException) {
			throw RuntimeException("$child type is incompatible with container's type.")
		}
	}
	
	override fun iterator(): Iterator<T> = observableElements.iterator()
	
	/**
	 * Adds the supplied GameElement to this GameElementViewContainer.
	 */
	operator fun T.unaryPlus() {
		add(this)
	}
	
	/**
	 * Adds the supplied GameElements to this GameElementViewContainer.
	 */
	operator fun Collection<T>.unaryPlus() {
		addAll(this)
	}
	
	/**
	 * Removes the supplied GameElement from this GameElementContainerView.
	 */
	operator fun T.unaryMinus() {
		remove(this)
	}
}