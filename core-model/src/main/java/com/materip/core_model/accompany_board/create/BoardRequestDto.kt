package com.materip.core_model.accompany_board.create

import com.materip.core_model.ui_model.BoardStatus
import com.materip.core_model.ui_model.Category
import com.materip.core_model.ui_model.PreferredAge
import com.materip.core_model.ui_model.PreferredGender
import com.materip.core_model.ui_model.Region
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


// 동행글 생성 /api/v1/accompany/boards
@Serializable
data class BoardRequestDto(
    val title: String,
    val content: String,
    val region: Region,
    @Serializable(with = LocalDateTimeSerializer::class)
    val startDate: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val endDate: LocalDateTime,
    val capacity: Int,
    val boardStatus: BoardStatus,
    val categories: List<Category>,
    val preferredAge: PreferredAge,
    val preferredGender: PreferredGender,
    val imageUrls: List<String>,
    val tagNames: List<String>
)

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = LocalDateTime::class)
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    private val formatter = DateTimeFormatter.ISO_DATE_TIME

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString(), formatter)
    }
}