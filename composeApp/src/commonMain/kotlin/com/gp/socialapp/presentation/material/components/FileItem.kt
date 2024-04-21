package com.gp.socialapp.presentation.material.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.data.material.model.MaterialFile
import com.gp.socialapp.presentation.material.MaterialAction
import com.gp.socialapp.presentation.material.components.imageVectors.MaterialIcon
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Ai
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Avi
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Bmp
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Csv
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Doc
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Docx
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Eps
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Exe
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.File
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Giff
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Html
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Jpg
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Mov
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Mp3
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Mp4
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Mpeg
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Pdf
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Png
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Ppt
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Psd
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Rar
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Svg
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Tiff
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Txt
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Wav
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Xml
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Xsl
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Zip

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FileItem(
    file: MaterialFile, action: (MaterialAction) -> Unit
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    Card(modifier = Modifier.fillMaxWidth().padding(4.dp).combinedClickable(
        onClick = {
            action(MaterialAction.OnFileClicked(file.id, file.url, file.name))
        },
        onLongClick = {
            isMenuExpanded = true
        }

    )) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Image(
                imageVector = getFileImageVector(
                    MimeType.getMimeTypeFromFileName(
                        file.name
                    )
                ),
                contentDescription = "File",
                modifier = Modifier.fillMaxWidth().size(100.dp)
            )
            Text(
                text = "Name: " + file.name,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                softWrap = false
            )
            if (isMenuExpanded) {
                MoreOptionsMenu(isExpanded = true,
                    onCloseMenu = { isMenuExpanded = false },
                    onDelete = { action(MaterialAction.OnDeleteFileClicked(file.id)) },
                    onOpenFile = {
                        action(
                            MaterialAction.OnFileClicked(
                                file.id,
                                file.url,
                                file.name
                            )
                        )
                    },
                    onDownload = {
                        action(
                            MaterialAction.OnDownloadFileClicked(
                                file.url,
                                file.name
                            )
                        )
                    },
                    onDetails = {
                        action(MaterialAction.OnDetailsClicked(file))
                    }
                )
            }
        }
    }
}


@Composable
fun getFileImageVector(type: MimeType): ImageVector {
    return when (type) {
        MimeType.AI -> MaterialIcon.Ai
        MimeType.AVI -> MaterialIcon.Avi
        MimeType.BMP -> MaterialIcon.Bmp
        MimeType.CSV -> MaterialIcon.Csv
        MimeType.DOC -> MaterialIcon.Doc
        MimeType.DOCX -> MaterialIcon.Docx
        MimeType.EPS -> MaterialIcon.Eps
        MimeType.EXE -> MaterialIcon.Exe
        MimeType.GIF -> MaterialIcon.Giff
        MimeType.HTML -> MaterialIcon.Html
        MimeType.JPG -> MaterialIcon.Jpg
        MimeType.MOV -> MaterialIcon.Mov
        MimeType.MP3 -> MaterialIcon.Mp3
        MimeType.MP4 -> MaterialIcon.Mp4
        MimeType.MPEG -> MaterialIcon.Mpeg
        MimeType.PDF -> MaterialIcon.Pdf
        MimeType.PNG -> MaterialIcon.Png
        MimeType.PPT -> MaterialIcon.Ppt
        MimeType.PPTX -> MaterialIcon.Ppt
        MimeType.PSD -> MaterialIcon.Psd
        MimeType.RAR -> MaterialIcon.Rar
        MimeType.SVG -> MaterialIcon.Svg
        MimeType.TIFF -> MaterialIcon.Tiff
        MimeType.TXT -> MaterialIcon.Txt
        MimeType.WAV -> MaterialIcon.Wav
        MimeType.XML -> MaterialIcon.Xml
        MimeType.ZIP -> MaterialIcon.Zip
        MimeType.XLS -> MaterialIcon.Xsl
        MimeType.XLSX -> MaterialIcon.Xsl
        else -> MaterialIcon.File
    }
}

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

