import kotlin.concurrent.thread

/**
 * Created by fernando on 08/10/16.
 */


open class EventEmitter {
    class Listeners(val once: Boolean = false) {
        val listeners:  MutableMap<String, MutableMap<String, MutableSet<(Any) -> Unit>>> = mutableMapOf()

        inline fun <reified T : Any> addListener(event: String, noinline cb: (T) -> Unit) {
            val className = T::class.java.simpleName
            listeners.get(event) ?: listeners.put(event, mutableMapOf())
            listeners.get(event)?.get(className) ?: listeners.get(event)?.put(className, mutableSetOf())

            listeners.get(event)?.get(className)?.add(cb as (Any) -> Unit)
        }

        fun <T : Any> getListeners(event: String, obj: T): Set<(T) -> Unit> {
            val className = obj.javaClass.simpleName
            val ret = listeners.get(event)?.get(className)?.toSet() ?: setOf()
            if(once) {
                listeners.get(event)?.get(className)?.clear()
            }
            return ret
        }
    }

    val listeners =  Listeners()
    val listOnce  =  Listeners(once = true)

    inline fun <reified T : Any> on(event: String, noinline cb: (T) -> Unit) {
        listeners.addListener<T>(event, cb)
    }

    inline fun <reified T : Any> once(event: String, noinline cb: (T) -> Unit) {
        listOnce.addListener<T>(event, cb)
    }

    fun <T : Any> emit(event: String, data: T) {
        val cbs = listeners.getListeners(event, data) + listOnce.getListeners(event, data)
        thread(start = true) {
            synchronized(event) {
                for (cb in cbs) {
                    thread(start = true) {
                        synchronized(event) {
                            cb(data)
                        }
                    }
                }
            }
        }
    }
}