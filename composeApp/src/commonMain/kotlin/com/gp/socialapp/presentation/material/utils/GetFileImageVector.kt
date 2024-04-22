package com.gp.socialapp.presentation.material.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
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
        MimeType.JPEG -> MaterialIcon.Jpg
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