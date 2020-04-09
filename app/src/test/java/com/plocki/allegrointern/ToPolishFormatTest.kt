package com.plocki.allegrointern

import org.junit.Test

import org.junit.Assert.*


class ToPolishFormatTest {
    @Test
    fun toPolishFormatWithTwoDecimalDigits() {
        assertEquals("12,34", DetailActivity.toPolishFormat(12.34))
    }

    @Test
    fun toPolishFormatWithOneDecimalDigit() {
        assertEquals("4556,70", DetailActivity.toPolishFormat(4556.7))
    }

    @Test
    fun toPolishFormatWithManyDecimalDigits() {
        assertEquals("9,12", DetailActivity.toPolishFormat(9.120000))
    }




}
