import java.util.concurrent.CountDownLatch

/**
 * Created by fernando on 08/10/16.
 */

fun main(args: Array<String>) {
    val counter = CountDownLatch(11)
    val ee = EventEmitter()
    ee.on("bla") {
        msg: String ->
        println("on: $msg")
        counter.countDown()
    }
    ee.once("bla") {
        msg: String ->
        println("once: $msg")
        counter.countDown()
    }
    ee.once("ble") {
        msg: Int ->
        println("once Int: $msg")
    }
    for(c: Int in 1 .. 10) {
        ee.emit("bla", "test $c")
        ee.emit("bla", c)
    }

    counter.await()
}
