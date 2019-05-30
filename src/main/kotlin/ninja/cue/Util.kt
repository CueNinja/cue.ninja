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

fun homePath(): String {
    return when {
        isWindows() -> "${System.getProperty("user.home")}/AppData/Local/"
        isMac() -> "${System.getProperty("user.home")}/Library/Application Support/"
        else -> System.getProperty("user.home")
    }
}

fun configPath(): String {
    return when {
        isLinux() -> "${homePath()}/.config/cue.ninja/"
        else -> "${homePath()}/cue.ninja/"
    }
}

fun mainConfigFile(): String {
    return "${configPath()}config.json"
}