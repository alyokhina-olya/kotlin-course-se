package ru.hse.spb

import org.junit.Assert
import org.junit.Test

class MainTest {

    @Test
    fun testRun1() {
        Assert.assertEquals("YES", run(listOf("petr")))
    }

    @Test
    fun testRun2() {
        Assert.assertEquals("NO", run(listOf("etis", "atis", "animatis", "etis", "atis", "amatis")))
    }
    @Test
    fun testRun3() {
        Assert.assertEquals("YES", run(listOf("nataliala", "kataliala", "vetra", "feinites")))
    }
}