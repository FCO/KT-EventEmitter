import java.util.concurrent.CountDownLatch

/**
 * Created by fernando on 08/10/16.
 */

fun main(args: Array<String>) {
    val counter = CountDownLatch(11)
    val ee = EventEmitter()
    ee.on("event1") {
        msg: String ->
        println("on: $msg")
        counter.countDown()
    }
    ee.once("event1") {
        msg: String ->
        println("once: $msg")
        counter.countDown()
    }
    ee.once("event1") {
        msg: Int ->
        println("once Integer: $msg")
    }

    for(c in 1 .. 10) {
        ee.emit("event1", "test $c")
        ee.emit("event1", c)
    }

    counter.await()
}
