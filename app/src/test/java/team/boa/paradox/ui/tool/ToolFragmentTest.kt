package team.boa.paradox.ui.tool

import org.junit.Assert.*
import org.junit.Test
import java.lang.reflect.Method


internal class ToolFragmentTest {

    @Test
    fun binToStringTest() {

        val method: Method = ConverterToolFragment::class.java.getDeclaredMethod("binToString",String::class.java)

        method.isAccessible = true
        val fragment = ConverterToolFragment()

        assertEquals("test",method.invoke(fragment,"01110100 01100101 01110011 01110100"));
    }

    @Test
    fun octToStringTest() {

        val method: Method = ConverterToolFragment::class.java.getDeclaredMethod("octToString",String::class.java)

        method.isAccessible = true
        val fragment = ConverterToolFragment()

        assertEquals("test",method.invoke(fragment,"164 145 163 164"));
    }

    @Test
    fun hexToStringTest() {

        val method: Method = ConverterToolFragment::class.java.getDeclaredMethod("hexToString",String::class.java)

        method.isAccessible = true
        val fragment = ConverterToolFragment()

        assertEquals("test",method.invoke(fragment,"74 65 73 74"));
    }

    @Test
    fun hexToStringTestFail() {

        val method: Method = ConverterToolFragment::class.java.getDeclaredMethod("hexToString",String::class.java)

        method.isAccessible = true
        val fragment = ConverterToolFragment()

        assertNotEquals("test",method.invoke(fragment,"74 73 73 74"));
    }

    @Test
    fun cipherTest() {

        val method: Method = CipherToolFragment::class.java.getDeclaredMethod("shiftText",String::class.java ,Int::class.java)

        method.isAccessible = true
        val fragment = CipherToolFragment()

        assertEquals("CIPHER",method.invoke(fragment,"jpwoly",7));
    }


    @Test
    fun cipherTestFail() {

        val method: Method = CipherToolFragment::class.java.getDeclaredMethod("shiftText",String::class.java ,Int::class.java)

        method.isAccessible = true
        val fragment = CipherToolFragment()

        assertNotEquals("CIPHER",method.invoke(fragment,"jpwoly",6));
    }



}