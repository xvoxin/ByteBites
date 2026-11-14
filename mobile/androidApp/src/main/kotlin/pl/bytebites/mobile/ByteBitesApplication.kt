package pl.bytebites.mobile

import android.app.Application
import org.koin.core.context.GlobalContext
import pl.bytebites.mobile.presentation.di.initKoin

class ByteBitesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (GlobalContext.getOrNull() == null) {
            initKoin()
        }
    }

}
