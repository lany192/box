

package com.github.lany192.box.multitype


internal class ClassLinkerBridge<T> private constructor(
  private val javaClassLinker: JavaClassLinker<T>,
  private val delegates: Array<ItemViewDelegate<T, *>>
) : Linker<T> {

  override fun index(position: Int, item: T): Int {
    val indexedClass = javaClassLinker.index(position, item)
    val index = delegates.indexOfFirst { it.javaClass == indexedClass }
    if (index != -1) return index
    throw IndexOutOfBoundsException(
      "The delegates'(${delegates.contentToString()}) you registered do not contain this ${indexedClass.name}."
    )
  }

  companion object {
    fun <T> toLinker(
      javaClassLinker: JavaClassLinker<T>,
      delegates: Array<ItemViewDelegate<T, *>>
    ): Linker<T> {
      return ClassLinkerBridge(javaClassLinker, delegates)
    }
  }
}
