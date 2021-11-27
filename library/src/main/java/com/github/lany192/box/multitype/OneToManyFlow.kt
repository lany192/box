

package com.github.lany192.box.multitype

import androidx.annotation.CheckResult

/**
 * Process and flow operators for one-to-many.
 *
 * @author Drakeet Xu
 */
interface OneToManyFlow<T> {

  /**
   * Sets some item view delegates to the item type.
   *
   * @param delegates the item view delegates
   * @return end flow operator
   */
  @CheckResult
  fun to(vararg delegates: ItemViewDelegate<T, *>): OneToManyEndpoint<T>

  @CheckResult
  fun to(vararg delegates: ItemViewBinder<T, *>): OneToManyEndpoint<T>
}
