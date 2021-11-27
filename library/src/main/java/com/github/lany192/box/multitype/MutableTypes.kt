

package com.github.lany192.box.multitype

/**
 * A TypePool implemented by an ArrayList.
 *
 * @author Drakeet Xu
 */
open class MutableTypes constructor(
  open val initialCapacity: Int = 0,
  open val types: MutableList<Type<*>> = ArrayList(initialCapacity)
) : Types {

  override val size: Int get() = types.size

  override fun <T> register(type: Type<T>) {
    types.add(type)
  }

  override fun unregister(clazz: Class<*>): Boolean {
    return types.removeAll { it.clazz == clazz }
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T> getType(index: Int): Type<T> = types[index] as Type<T>

  override fun firstIndexOf(clazz: Class<*>): Int {
    val index = types.indexOfFirst { it.clazz == clazz }
    if (index != -1) {
      return index
    }
    return types.indexOfFirst { it.clazz.isAssignableFrom(clazz) }
  }
}
