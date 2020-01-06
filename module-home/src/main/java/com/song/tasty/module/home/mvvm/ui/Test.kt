package com.song.tasty.module.home.mvvm.ui

/**
 * @date : 2020-01-06 18:17
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
data class Person(val age: Int) {

    constructor() : this() {}

    fun Person.test() {
        val jack = Person(1);
        val olderJack = jack.copy(2)
        println(jack)
        println(olderJack)
    }

}