

package com.github.lany192.box.multitype


internal class DefaultLinker<T> : Linker<T> {
  override fun index(position: Int, item: T): Int = 0
}
