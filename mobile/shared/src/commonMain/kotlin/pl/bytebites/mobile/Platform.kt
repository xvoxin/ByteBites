package pl.bytebites.mobile

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform