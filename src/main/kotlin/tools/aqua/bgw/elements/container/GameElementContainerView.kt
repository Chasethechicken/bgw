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
 * Baseclass for containers that can contain [ElementView]s or its subclasses.
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
	 * An [ObservableList] to store the [ElementView]s that are contained in this [GameElementContainerView].
	 * If changes are made to this list, this [GameElementContainerView] gets re-rendered.
	 */
	internal val observableElements: ObservableList<T> = ObservableLinkedList()
	
	/**
	 * [ElementView]s that are contained in this [GameElementContainerView].
	 * If changes are made to this list, this [GameElementContainerView] gets re-rendered.
	 */
	var elements: List<T> = observableElements.toList()
		get() = observableElements.toList()
		private set
	
	/**
	 * Adds a [listener] on the [observableElements] list.
	 */
	fun addElementsListener(listener: IObservable) {
		observableElements.addListener(listener)
	}
	
	/**
	 * Removes a [listener] from the [observableElements] list.
	 */
	fun removeElementsListener(listener: IObservable) {
		observableElements.removeListener(listener)
	}
	
	/**
	 * Removes all [listeners] from the [observableElements] list.
	 */
	fun clearElementsListener() {
		observableElements.clearListeners()
	}
	
	/**
	 * Adds an [ElementView] to this [GameElementContainerView].
	 *
	 * @param element element to add.
	 */
	@Synchronized
	open fun addElement(element: T, index: Int = observableElements.size) {
		require(!observableElements.contains(element)) {
			"Element $element is already contained in this $this."
		}
		require(element.parent == null) {
			"Element $element is already contained in another container."
		}
		require(index in 0..observableElements.size) {
			"Index $index is out of list range."
		}
		
		observableElements.add(index, element.apply { parent = this@GameElementContainerView })
	}
	
	/**
	 * Adds all [ElementView]s passed as varargs to this [GameElementContainerView].
	 *
	 * @param elements vararg ElementViews to add.
	 */
	open fun addAllElements(vararg elements: T) {
		addAllElements(elements.toList())
	}
	
	/**
	 * Adds all [ElementView]s contained in the passed collection to this [GameElementContainerView].
	 *
	 * @param collection collection containing the ElementViews to add.
	 */
	@Synchronized
	open fun addAllElements(collection: Collection<T>) {
		collection.forEach { addElement(it) }
	}
	
	/**
	 * Removes the [element] specified by the parameter from this [GameElementContainerView].
	 *
	 * @param element the element to remove.
	 */
	@Synchronized
	open fun removeElement(element: T) {
		observableElements.remove(element.apply { parent = null })
	}
	
	/**
	 * Removes all [ElementView]s from this [GameElementContainerView].
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
	fun numberOfElements(): Int = observableElements.size
	
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
	 * Returning a contained child's coordinates within this container.
	 *
	 * @param child child to find.
	 *
	 * @return coordinate of given child in this container relative to containers anchor point.
	 */
	override fun getChildPosition(child: ElementView): Coordinate? = Coordinate(child.posX, child.posY)
	
	/**
	 * Removes [child] from container's children.
	 *
	 * @param child child to be removed.
	 *
	 * @throws RuntimeException if the child's type is incompatible with container's type.
	 */
	override fun removeChild(child: ElementView) {
		try {
			@Suppress("UNCHECKED_CAST")
			this.removeElement(child as T)
		} catch (_: ClassCastException) {
			throw RuntimeException("$child type is incompatible with container's type.")
		}
	}
	
	override fun iterator(): Iterator<T> = observableElements.iterator()
	
	/**
	 * Adds the supplied [GameElementView] to this [GameElementContainerView].
	 */
	operator fun T.unaryPlus() {
		addElement(this)
	}
	
	/**
	 * Adds the supplied [GameElementView]s to this [GameElementContainerView].
	 */
	operator fun Collection<T>.unaryPlus() {
		addAllElements(this)
	}
	
	/**
	 * Removes the supplied [GameElementView] from this [GameElementContainerView].
	 */
	operator fun T.unaryMinus() {
		removeElement(this)
	}
}