package com.gp.socialapp.presentation.material.utils

enum class MimeType(val extension: String, val mimeType: String) {
    PDF("pdf", "application/pdf"),
    TXT("txt", "text/plain"),
    JPG("jpg", "image/jpeg"),
    JPEG(
        "jpeg",
        "image/jpeg"
    ),
    PNG("png", "image/png"), GIF("gif", "image/gif"), BMP("bmp", "image/bmp"), MP3(
        "mp3",
        "audio/mpeg"
    ),
    WAV("wav", "audio/wav"), OGG("ogg", "audio/ogg"), MP4("mp4", "video/mp4"), AVI(
        "avi",
        "video/x-msvideo"
    ),
    MOV("mov", "video/quicktime"), WMV("wmv", "video/x-ms-wmv"), HTML(
        "html",
        "text/html"
    ),
    HTM("htm", "text/html"), CSS("css", "text/css"), JS(
        "js",
        "application/javascript"
    ),
    JSON("json", "application/json"), XML("xml", "application/xml"), ZIP(
        "zip",
        "application/zip"
    ),
    RAR("rar", "application/x-rar-compressed"), TAR("tar", "application/x-tar"), _7Z(
        "7z",
        "application/x-7z-compressed"
    ),
    DOC("doc", "application/msword"), DOCX("docx", "application/msword"), XLS(
        "xls",
        "application/vnd.ms-excel"
    ),
    XLSX("xlsx", "application/vnd.ms-excel"), PPT(
        "ppt",
        "application/vnd.ms-powerpoint"
    ),
    PPTX("pptx", "application/vnd.ms-powerpoint"), CSV("csv", "text/csv"), RTF(
        "rtf",
        "application/rtf"
    ),
    FLAC("flac", "audio/flac"), XLSB(
        "xlsb",
        "application/vnd.ms-excel.sheet.binary.macroEnabled.12"
    ),
    XLSM("xlsm", "application/vnd.ms-excel.sheet.macroEnabled.12"), XLTM(
        "xltm",
        "application/vnd.ms-excel.template.macroEnabled.12"
    ),
    XLTX("xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template"), XLW(
        "xlw",
        "application/vnd.ms-excel"
    ),
    POTX(
        "potx",
        "application/vnd.openxmlformats-officedocument.presentationml.template"
    ),
    PPAM("ppam", "application/vnd.ms-powerpoint.addin.macroEnabled.12"), PPTM(
        "pptm",
        "application/vnd.ms-powerpoint.presentation.macroEnabled.12"
    ),
    SLDM("sldm", "application/vnd.ms-powerpoint.slide.macroEnabled.12"), PPSM(
        "ppsm",
        "application/vnd.ms-powerpoint.slideshow.macroEnabled.12"
    ),
    POTM("potm", "application/vnd.ms-powerpoint.template.macroEnabled.12"), THMX(
        "thmx",
        "application/vnd.ms-officetheme"
    ),
    AI("ai", "application/postscript"), EPS("eps", "application/postscript"), PSD(
        "psd",
        "image/vnd.adobe.photoshop"
    ),
    TIF("tif", "image/tiff"), TIFF("tiff", "image/tiff"), SVG("svg", "image/svg+xml"), MPG(
        "mpg",
        "video/mpeg"
    ),
    MPEG("mpeg", "video/mpeg"), WOFF("woff", "font/woff"), WOFF2("woff2", "font/woff2"), TTF(
        "ttf",
        "font/ttf"
    ),
    OTF("otf", "font/otf"), WEBM("webm", "video/webm"), APK(
        "apk",
        "application/vnd.android.package-archive"
    ),
    EXE("exe", "application/octet-stream"), UNKNOWN("unknown", "application/octet-stream");

    companion object {
        fun getMimeTypeFromFileName(fileName: String): MimeType {
            val extension = fileName.substringAfterLast('.', "")
            val mimeType = values().find { it.extension.equals(extension, ignoreCase = true) }
            return mimeType ?: UNKNOWN
        }
    }
}