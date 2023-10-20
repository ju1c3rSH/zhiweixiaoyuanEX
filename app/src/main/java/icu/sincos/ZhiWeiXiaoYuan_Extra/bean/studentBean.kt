package icu.sincos.ZhiWeiXiaoYuan_Extra.bean

data class Student(
    val studentId: Int,
    val tenantId: Int,
    val uuid: String,
    val tuid: Int,
    val studentCode: String?,
    val campusCode: String?,
    val studentNumber: String?,
    val classes: Classes,
    val schoolYear: Int,
    val studentName: String,
    val studentNamePinyin: String,
    val headSculpture: String,
    val photo: String,
    val sex: String,
    val height: Double?,
    val weight: Double?,
    val hobby: String?,
    val birthday: String?,
    val idcardType: Int,
    val idcard: String,
    val censusRegister: String,
    val addr: String,
    val dataOrigin: Int,
    val studyStatus: Int,
    val registrationStatus: Int,
    val graduatedSchool: String?,
    val operator: String?,
    val status: Int,
    val accommodationType: String,
    val icCard1: String,
    val icCard2: String,
    val icCard3: String,
    val subServStatus: Any?,  // 这里使用 Any? 表示可空的字段
    val subServContractName: String?,
    val subServLastValidTime: String?,
    val description: String,
    val creator: String,
    val createTime: String,
    val modifier: String,
    val modifyTime: String,
    val cardNumber: String?,
    val roomId: String?,
    val parentStudents: Any?,  // 这里使用 Any? 表示可空的字段
    val studentFaces: List<StudentFace>,
    val schoolName: String,
    val subTelServiceStatus: Any?  // 这里使用 Any? 表示可空的字段
)

data class Classes(
    val classId: Int,
    val tenantId: Int,
    val parentId: Int,
    val className: String,
    val classCode: String?,
    val campusCode: String?,
    val schoolYear: Int,
    val deptType: Int,
    val graduateStatus: Int,
    val graduateTime: String?,
    val classOrder: Int,
    val description: String?,
    val classesWorkers: Any?  // 这里使用 Any? 表示可空的字段
)

data class StudentFace(
    val faceId: Int,
    val tenantId: Int,
    val faceLoadStatus: Int,
    val photo: String,
    val orderNumber: Int,
    val photoOrigin: Int,
    val originFaceUrl: String?,
    val status: Int,
    val createTime: String,
    val modifyTime: String
)
