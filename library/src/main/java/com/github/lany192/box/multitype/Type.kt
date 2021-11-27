

package com.github.lany192.box.multitype


data class Type<T>(
  val clazz: Class<out T>,
  val delegate: ItemViewDelegate<T, *>,
  val linker: Linker<T>
)
