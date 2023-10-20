package icu.sincos.ZhiWeiXiaoYuan_Extra.bean

data class NotificationData(
    val total: Int,
    val page: Int,
    val rows: Int,
    val datas: List<Notification>
)

data class Notification(
    val id: Long,
    val tenantId: Int,
    val originId: Long,
    val typeCode: Int,
    val typeName: String,
    val title: String,
    val publisherName: String,
    val publisherCode: String?,
    val receiverName: String?,
    val receiverCode: String?,
    val copyPersonName: String,
    val copyPersonCode: String,
    val content: String,
    val picture: String,
    val publishTime: String,
    val status: Int,
    val isRead: Int,
    val reserver1: String,
    val reserver2: String,
    val reserver3: String?,
    val reserver4: String,
    val reserver5: String?
)
