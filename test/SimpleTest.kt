import io.kotlintest.Duration
import io.kotlintest.Eventually
import io.kotlintest.specs.StringSpec
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class StringSpecExample : StringSpec(), Eventually {
    init {
        "should emit an event only once" {
            val count = CountDownLatch(1)
            val ee = EventEmitter()
            var emitted = 0
            ee.once("event1") {
                data: Int ->
                emitted += data
                count.countDown()
            }

            ee.emit("event1", 1)
            ee.emit("event1", 2)

            count.await()

            eventually(Duration(1, TimeUnit.SECONDS)) {
                emitted shouldBe 1
            }
        }

        "should emit an event more than once" {
            val count = CountDownLatch(1)
            val ee = EventEmitter()
            var emitted = 0
            ee.on("event1") {
                data: Int ->
                emitted += data
                count.countDown()
            }

            ee.emit("event1", 1)
            ee.emit("event1", 2)

            count.await()

            eventually(Duration(1, TimeUnit.SECONDS)) {
                emitted shouldBe 3
            }
        }
    }
}