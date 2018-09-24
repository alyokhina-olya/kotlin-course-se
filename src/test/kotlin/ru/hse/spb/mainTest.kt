package ru.hse.spb

import org.junit.Assert
import org.junit.Test

class MainTest {

    @Test
    fun testRun1() {
        Assert.assertEquals(true, run(listOf("petr")))
    }

    @Test
    fun testRun2() {
        Assert.assertEquals(false, run(listOf("etis", "atis", "animatis", "etis", "atis", "amatis")))
    }
    @Test
    fun testRun3() {
        Assert.assertEquals(true, run(listOf("nataliala", "kataliala", "vetra", "feinites")))
    }
}