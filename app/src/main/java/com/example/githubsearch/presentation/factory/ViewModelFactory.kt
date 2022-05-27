package com.example.githubsearch.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.MapKey
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

class ViewModelFactory @Inject constructor(
    private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        var creator = creators[modelClass]
        if (creator == null) {
            for ((k, v) in creators) {
                if (k.isAssignableFrom(modelClass)) {
                    creator = v
                    break;
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("IllegalArgumentException: $modelClass")
        }

        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch(e: Exception) {
            throw RuntimeException(e)
        }
    }
}

@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val key: KClass<out ViewModel>)