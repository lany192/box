package com.lany.box.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 网络事件
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NetWorkEvent {
    private boolean available;
}