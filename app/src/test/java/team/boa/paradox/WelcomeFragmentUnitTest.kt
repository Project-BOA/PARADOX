package team.boa.paradox

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import team.boa.paradox.ui.home.WelcomeFragment
import java.lang.reflect.Method


/**
 * Local unit test for the WelcomeFragment, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class WelcomeFragmentUnitTest {

    // https://stackoverflow.com/questions/58057769/method-getmainlooper-in-android-os-looper-not-mocked-still-occuring-even-after-a
    @Rule  @JvmField
    val rule = InstantTaskExecutorRule()

    @Test
    fun cleanPuzzleId_isCorrect() {

        // get method "cleanPuzzleId" from the class and make it accessible
        val method: Method = WelcomeFragment::class.java.getDeclaredMethod("cleanPuzzleId", String::class.java)
        method.isAccessible = true
        val fragment = WelcomeFragment()

        assertEquals("HI THERE 123456789", method.invoke(fragment, "Hi thErE 123456789"))
        assertEquals("HELLOABD", method.invoke(fragment, "HELLOabd"))
        assertEquals("TEST", method.invoke(fragment, "  ><test''[;   "))
    }


    @Test
    fun morseConvert_isCorrect() {


        assertEquals(5, 2+2)
    }
}
