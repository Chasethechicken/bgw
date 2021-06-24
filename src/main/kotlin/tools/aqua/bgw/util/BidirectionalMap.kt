@file:Suppress("MemberVisibilityCanBePrivate")

package tools.aqua.bgw.util

/**
 * A bidirectional map.
 * Each key-value-pair gets mapped in both directions.
 * Keys and values must be unique in the sense that there must not be a duplicate key in the domain,
 * nor a duplicate value in the coDomain, but the same element may appear once as key and once as value.
 *
 * Example:
 *
 * A -> B
 *
 * A -> C
 *
 * is invalid because A is contained twice in the Domain.<br>
 *
 * A -> B
 *
 * C -> B
 *
 * is invalid because B is contained twice in the coDomain.<br>
 *
 * A -> B
 *
 * C -> A
 *
 * is valid because A is only contained once in the domain and in the coDomain.
 *
 * @constructor Creates a map with the given set of elements mapping pair.first -> pair.second.
 *
 * @param elements Elements to be initialized in the map.
 */
class BidirectionalMap<T : Any, R : Any>(vararg elements: Pair<T, R>) {
	private val map: MutableList<Pair<T, R>> = mutableListOf()
	
	init {
		for (element in elements) {
			add(element.first, element.second)
		}
	}
	
	/**
	 * Adds a relation A -> B if domain does not contain A and coDomain does not contain B.
	 * Returns `false` if the relation could not be added.
	 *
	 * @return `true` if the element was added to the map, `false` otherwise.
	 *
	 * @see add
	 */
	fun add(entity: T, elementView: R): Boolean {
		if (contains(entity, elementView))
			return false
		
		map.add(Pair(entity, elementView))
		return true
	}
	
	/**
	 * Forward lookup for entry.
	 *
	 * @param entity Relation key.
	 *
	 * @return B for query A if relation A -> B exists.
	 *
	 * @throws NoSuchElementException If no such element is found.
	 */
	fun forward(entity: T): R = map.first { t -> t.first == entity }.second
	
	/**
	 * Forward lookup for entry.
	 *
	 * @param entity Relation key.
	 *
	 * @return B for query A if relation A -> B exists. Null otherwise.
	 */
	fun forwardOrNull(entity: T): R? = map.firstOrNull { t -> t.first == entity }?.second
	
	/**
	 * Backward lookup for entry.
	 *
	 * @param value Relation key.
	 *
	 * @return A for query B if relation A -> B exists.
	 *
	 * @throws NoSuchElementException If no such element is found.
	 */
	fun backward(value: R): T = map.first { t -> t.second == value }.first
	
	/**
	 * Backward lookup for entry.
	 *
	 * @param value Relation key.
	 *
	 * @return A for query B if relation A -> B exists. Null otherwise.
	 */
	fun backwardOrNull(value: R): T? = map.firstOrNull { t -> t.second == value }?.first
	
	/**
	 * Removes relation A -> B if it exists.
	 *
	 * @param entity Relation key A.
	 * @param value Relation value B.
	 *
	 * @return `true` if the element was removed, `false` if the element was not found.
	 *
	 * @see removeForward
	 * @see removeBackward
	 */
	fun remove(entity: T, value: R): Boolean = map.removeIf { t -> t.first == entity && t.second == value }
	
	/**
	 * Removes relation A -> B if it exists.
	 *
	 * @param pair (Relation key A, Relation value B)
	 *
	 * @return `true` if the element was removed, `false` if the element was not found.
	 *
	 * @see removeForward
	 * @see removeBackward
	 */
	fun remove(pair: Pair<T, R>): Boolean = remove(pair.first, pair.second)
	
	/**
	 * Removes by forward lookup.
	 * Removes relation A -> * if it exists.
	 *
	 * @param entity Relation key A.
	 *
	 * @return `true` if the element was removed, `false` if the element was not found.
	 *
	 * @see remove
	 * @see removeBackward
	 */
	fun removeForward(entity: T): Boolean = map.removeIf { t -> t.first == entity }
	
	/**
	 * Removes by backward lookup.
	 * Removes relation * -> B.
	 *
	 * @param value Relation value B.
	 *
	 * @return `true` if the element was removed, `false` if the element was not found.
	 *
	 * @see remove
	 * @see removeForward
	 */
	fun removeBackward(value: R): Boolean = map.removeIf { t -> t.second == value }
	
	/**
	 * Returns whether relation A -> B exists in this map.
	 *
	 * @param entity Relation key A.
	 * @param value Relation value B.
	 *
	 * @return `true` if the relation exists in this map, `false` otherwise.
	 *
	 * @see containsForward
	 * @see containsBackward
	 */
	fun contains(entity: T, value: R): Boolean = containsForward(entity) && containsBackward(value)
	
	/**
	 * Returns whether a relation A -> * exists.
	 *
	 * @param entity Relation key A.
	 *
	 * @return `true` if the relation exists in this map, `false` otherwise.
	 *
	 * @see contains
	 * @see containsBackward
	 */
	fun containsForward(entity: T): Boolean = map.any { t -> t.first == entity }
	
	/**
	 * Returns whether a relation * -> B exists.
	 *
	 * @param value Relation value B.
	 *
	 * @return `true` if the relation exists in this map, `false` otherwise.
	 *
	 * @see contains
	 * @see containsForward
	 */
	fun containsBackward(value: R): Boolean = map.any { t -> t.second == value }
	
	/**
	 * Returns the domain of this map as a set.
	 *
	 * @see getCoDomain
	 */
	fun getDomain(): Set<T> = map.map { t -> t.first }.toSet()
	
	/**
	 * Returns the coDomain of this map as a set.
	 *
	 * @see getDomain
	 */
	fun getCoDomain(): Set<R> = map.map { t -> t.second }.toSet()
	
	/**
	 * Clears the map.
	 */
	fun clear() {
		map.clear()
	}
}