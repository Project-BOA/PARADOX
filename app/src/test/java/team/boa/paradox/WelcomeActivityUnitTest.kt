package team.boa.paradox

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import team.boa.paradox.ui.welcome.WelcomeActivity
import java.lang.reflect.Method


/**
 * Local unit test for the WelcomeActivity, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class WelcomeActivityUnitTest {

    // https://stackoverflow.com/questions/58057769/method-getmainlooper-in-android-os-looper-not-mocked-still-occuring-even-after-a
    @Rule  @JvmField
    val rule = InstantTaskExecutorRule()

    @Test
    fun cleanPuzzleId_isCorrect() {

        // get method "cleanPuzzleId" from the class and make it accessable
        val method: Method = WelcomeActivity::class.java.getDeclaredMethod("cleanPuzzleId", String::class.java)
        method.isAccessible = true
        val activity = WelcomeActivity()

        assertEquals("HELLOABD", method.invoke(activity, "HELLOabd"))
        assertEquals("HI THERE 123456789", method.invoke(activity, "Hi thErE 123456789"))
        assertEquals("TEST", method.invoke(activity, "  ><test''[;   "))
    }
}
