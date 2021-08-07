package com.changgyu.my_annotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class MyAnnotation(
    val name: String,
    val age: Int,
    val job: String
)
