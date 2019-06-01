package ninja.cue.util

fun osName(): String {
    return System.getProperty("os.name").toLowerCase()
}

fun isWindows(): Boolean {
    return osName().contains("windows")
}

fun isMac(): Boolean {
    return osName().contains("mac")
}

fun isLinux(): Boolean {
    return osName().contains("nux")
}

fun home(): String {
    return System.getProperty("user.home")
}

fun homePath(): String {
    val home = home()
    return when {
        isWindows() -> "$home/AppData/Local/"
        isMac() -> "$home/Library/Application Support/"
        else -> home
    }
}

fun configPath(): String {
    val homePath = homePath()
    return when {
        isLinux() -> "$homePath/.config/cue.ninja/"
        else -> "$homePath/cue.ninja/"
    }
}

fun mainConfigFile(): String {
    return "${configPath()}config.json"
}
