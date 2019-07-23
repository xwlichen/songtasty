package com.song.tasty.common.core.binding.command;

/**
 * @date : 2019-07-23 17:03
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public interface BindingConsumer<T> {
    void call(T t);
}
