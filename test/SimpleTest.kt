import com.winterbe.expekt.should
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

/**
 * Created by fernando on 10/10/16.
 */
class SimpleTest : Spek({
    println("AQUI")
    describe("bla") {
        it("ble") {
            1.should.be.equal(42)
        }
    }
})
