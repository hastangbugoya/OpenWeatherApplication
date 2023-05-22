import com.example.openweatherapplication.network.MyRetrofit
import org.junit.Assert.assertNotNull
import org.junit.Test

class MyRetrofitTest {
    @Test
    fun testGetRetrofit() {
        val myRetrofit = MyRetrofit.getRetrofit()
        assertNotNull(myRetrofit)
    }

    @Test
    fun testGetService() {
        val myService = MyRetrofit.getService()
        assertNotNull(myService)
    }
}