package icu.sincos.ZhiWeiXiaoYuan_Extra.util

object  AppConstants {
    const val wxOa = "orT8vuPhqrSfM5ylXZWLdLMzwPBU"
    const val getNoAuth = "https://wx.ivxiaoyuan.com/sc/h5/officialAccountLoginManage/getUserRolesNoAuth?ciphertext="
    const val getBalance = "https://wx.ivxiaoyuan.com/sc/consume/h5/query/getMemberBalance?objectUuid=cfb0961b2c844923902ed6544a878467&t=1694329190049"
    const val getAllStudentsOfParentUrl = "https://wx.ivxiaoyuan.com/sc/basic/h5/studentQuery/getAllStudentsOfParent?queryWay=5"
    const val getMemberFlow = "https://wx.ivxiaoyuan.com/sc/consume/h5/query/getMemberFlow"
    const val loginUrl = "https://wx.ivxiaoyuan.com/sc/h5/officialAccountLoginManage/login?appid=wxddbbb3d7ad98c9a4&role=parent"
    const val loginParams = "appid=wxddbbb3d7ad98c9a4&role=parent"
    const val capturePhoto = "https://wx.ivxiaoyuan.com/sc/files/capturePhoto/"
    const val queryUrl = "https://wx.ivxiaoyuan.com/sc/student/h5/dynamicParentViewQuery/query"
    const val copyPersonCode = "cfb0961b2c844923902ed6544a878467"

    //{"objectUuid":"cfb0961b2c844923902ed6544a878467","types":[2,5,6,7],"objectType":7,"page":1,"rows":15,"startTime":"2023-06-24 00:00:00","endTime":"2023-09-24 23:59:59"}
    //String memberFlowJson = "{\"objectUuid\":\"cfb0961b2c844923902ed6544a878467\",\"types\":[2,5,6,7],\"objectType\":7,\"page\":1,\"rows\":15,\"startTime\":\"2023-06-24 00:00:00\",\"endTime\":\"2023-09-24 23:59:59\"}";
    val jsonStr = "{\"appid\": \"wxddbbb3d7ad98c9a4\", \"role\": \"parent\"}"


}