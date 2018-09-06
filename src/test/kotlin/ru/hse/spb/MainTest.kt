package ru.hse.spb

import org.junit.Test

import org.junit.Assert.*

class MainTest {

    @Test
    fun testRun1() {
        assertEquals("YES", ru.hse.spb.run(listOf("petr")))
    }

    @Test
    fun testRun2() {
        assertEquals("NO", ru.hse.spb.run(listOf("etis atis animatis etis atis amatis")))
    }
    @Test
    fun testRun3() {
        assertEquals("YES", ru.hse.spb.run(listOf("nataliala kataliala vetra feinites")))
    }
}