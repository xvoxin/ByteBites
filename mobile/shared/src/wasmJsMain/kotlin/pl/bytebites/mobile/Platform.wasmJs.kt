package pl.bytebites.mobile

class WasmPlatform : Platform {
    override val name: String = "WebAssembly (Wasm/JS)"
}

actual fun getPlatform(): Platform = WasmPlatform()
