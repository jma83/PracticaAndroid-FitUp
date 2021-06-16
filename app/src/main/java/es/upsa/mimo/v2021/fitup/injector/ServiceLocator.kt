package es.upsa.mimo.v2021.fitup.injector

import java.lang.reflect.InvocationTargetException
import java.util.*

class ServiceLocator {
    val INSTANCE: ServiceLocator = ServiceLocator()

    private val maps: MutableMap<Class<*>, Class<*>> = HashMap()
    private val instances: MutableMap<Class<*>, Any?> = HashMap()

    private fun ServiceLocalor() {}

    fun <T> init(baseClass: Class<T>, implementationClass: Class<out T>) {
        maps[baseClass] = implementationClass
    }

    fun <T> getImplementation(type: Class<T>): T? {
        return try {
            var implementation = instances[type]
            if (implementation == null) {
                val implementationClass = maps[type] ?: throw IllegalArgumentException()
                implementation = implementationClass.getConstructor().newInstance()
                instances[type] = implementation
            }
            implementation as T?
        } catch (e: InstantiationException) {
            throw IllegalArgumentException()
        } catch (e: InvocationTargetException) {
            throw IllegalArgumentException()
        } catch (e: NoSuchMethodException) {
            throw IllegalArgumentException()
        } catch (e: IllegalAccessException) {
            throw IllegalArgumentException()
        }
    }
}