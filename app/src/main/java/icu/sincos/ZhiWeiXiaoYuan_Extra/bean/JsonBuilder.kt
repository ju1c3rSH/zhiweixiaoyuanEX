package icu.sincos.ZhiWeiXiaoYuan_Extra.bean

data class MemberFlowJsonBuilder(
    val objectUuid: String,
    val types: List<Int>,
    val objectType: Int,
    val page: Int,
    val rows: Int,
    val startTime: String,
    val endTime: String
)

data class QueryJsonBuilder(
    val page: Int,
    val rows: Int,
    val copyPersonCode: String,
    val typeCode: List<Int>
)