package com.up.common.conf;

/**
 * TODO:
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/3/16.
 */

public class Urls {

    //public static String HTTP_HEAD = "http://149v448g72.iask.in/easylearn/api/";//接口工程地址
//    public static String HTTP_HEAD = "http://121.196.192.233:8099/easylearn/api/";//接口工程地址
    public static String HTTP_HEAD = "http://120.27.224.231:8081/easylearn/api/";//测试接口工程地址
//    public static String HTTP_HEAD = "http://116.62.243.203:8081/easylearn/api/";//开发接口工程地址
//    public static String HTTP_HEAD = "http://ycss.tunnel.qydev.com/easylearn/api/";//接口工程地址
//    public static String HTTP_HEAD = "http://huangyouli.ngrok.cc/easylearn/api/";//接口工程地址

    /**
     * 平台滚动公告
     */
    public static String BANNER = HTTP_HEAD + "home/bannerList";
    //登陆
    public static String LOGIN = HTTP_HEAD + "user/login";
    //注册
    public static String REGISTER = HTTP_HEAD + "user/registered";
    //注册获取验证码
    public static String GET_CODE = HTTP_HEAD + "user/getphonecode";
    //忘记密码获取验证码
    public static String GET_CODE_FORGET_PSW = HTTP_HEAD + "user/resetpassword";
    //忘记密码验证验证码
    public static String VERIFICATION_PHONE = HTTP_HEAD + "user/setnewpassword";
    //绑定学生
    public static String BINDING_STU = HTTP_HEAD + "parent/bindingstudent";
    //修改密码
    public static String UPDATE_PSW = HTTP_HEAD + "user/saveuserpass";
    //获取省数据
    public static String GET_REGION_LIST = HTTP_HEAD + "subject/getRegionList";
    //获取区县数据
    public static String GET_CITY_REGION_LIST = HTTP_HEAD + "subject/getCityRegionList";
    //获取学校
    public static String GET_SCHOOL = HTTP_HEAD + "school/getregisteredlist";
    //获取班级
    public static String GET_CLASS = HTTP_HEAD + "teacher/analysis/queryClass";
    //获取作业类型
    public static String GET_WORK_TYPE = HTTP_HEAD + "teacher/task/queryCodes";
    //获取首页spinner的数据
    public static String GET_HOME_SPINNER_DATA = HTTP_HEAD + "teacher/task/queryOffTaskCondition";
    //首页-线上作业&试卷录入列表
    public static String GET_HOME_TASK_LIST = HTTP_HEAD + "taskonlie/getTeacherTaskListByPage";
    //首页-线下作业列表
    public static String GET_HOME_XX_TASK_LIST = HTTP_HEAD + "teacher/task/queryOfflineTask";
    //作业详情-试卷录入
    public static String ZYXQ_SJLUR = HTTP_HEAD + "taskonlie/getSubmitAndOrderListByPage";
    //作业详情-试卷录入-试卷统计
    public static String ZYXQ_SJTj = HTTP_HEAD + "taskonlie/getPapersStatistics";
    //作业详情-试卷录入-试卷统计-试卷详情
    public static String ZYXQ_SJXX = HTTP_HEAD + "taskonlie/getSubjectDetails";
    //知识薄弱点分析
    public static String ZSBRD_FX = HTTP_HEAD + "taskonlie/getStuWeakKnowledgeAnalysisList";
    //作业详情-家庭作业
    public static String ZYXQ_JTZY = HTTP_HEAD + "taskonlie/getStuOnlineAnalysisList";
    //消息列表
    public static String GET_MES = HTTP_HEAD + "schoolbulletin/getlist";
    //获取banner和最近的三条消息
    public static String GET_BANNER = HTTP_HEAD + "schoolbulletin/getcarousel";
    //删除任务
    public static String DEL_TASK = HTTP_HEAD + "taskonlie/deleteTaskById";
    //在线练习转试卷录入
    public static String SJLR = HTTP_HEAD + "taskonlie/copyRelationTaskById";
    //获取上传图片的接口地址
    public static String GET_IMG_REQUEST_URL = HTTP_HEAD + "postfile/img";
    //--------------------------YCSS-----------------------------
    //布置作业
    //试卷错题录入-试卷列表
    public static String TEST_LIST = HTTP_HEAD + "teacher/task/queryPars";
    //获取学科
    public static String GET_SUBJECT = HTTP_HEAD + "teacher/analysis/queryCourse";
    //获取年级
    public static String GET_GRADES = HTTP_HEAD + "teacher/task/queryGades";
    //试卷类型
    public static String GET_TEST_TYPE = HTTP_HEAD + "teacher/task/queryCodes";
    //共享类型
    public static String GET_SHARE_TYPE = HTTP_HEAD + "teacher/task/queryCodes";

    //试卷错题录入-试卷详情
    public static String TEST_INFO = HTTP_HEAD + "teacher/task/findParsItem";
    public static String QUERY_SUBUJECT = HTTP_HEAD + "teacher/task/querySubject";
    public static String QUERY_SUBUJECT_E = HTTP_HEAD + "teacher/task/queryPapers";

    //在线练习-章节-选择试题保存
    public static String SAVE_PARS = HTTP_HEAD + "teacher/task/savePars";

    //线下作业通知
    public static String PUBLISH_TASK = HTTP_HEAD + "teacher/task/addOrUpdateOfflineTask";
    //标准卷上传
    public static String UPLOAD_TEST = HTTP_HEAD + "teacher/task/updatePars";

    //筛选
    //出版社
    public static String GET_PUBLISHER = HTTP_HEAD + "teacher/task/queryPressTypes";
    //章节
    public static String GET_SECTION = HTTP_HEAD + "teacher/task/queryStructures";
    //题型
    public static String GET_PRO_TYPE = HTTP_HEAD + "teacher/task/queryCodes?type=subjectType";
    //难度
    public static String GET_DIFFICULTY = HTTP_HEAD + "teacher/task/queryCodes?type=difficultyType";
    //类型
    public static String GET_TYPE = HTTP_HEAD + "teacher/task/queryCodes?type=uploadType";
    //来源
    public static String GET_SOURCE = HTTP_HEAD + "teacher/task/queryCodes?type=papersource";
    //知识点
    public static String GET_KNOWLEDGE = HTTP_HEAD + "teacher/task/queryKonws";
    //年份获取
    public static String GET_YEAR = HTTP_HEAD + "teacher/task/queryYears";
    //学期
    public static String GET_TERM = HTTP_HEAD + "teacher/task/queryCodes?type=term";
    //获取班级
    public static String GET_CLASS_SCREEN = HTTP_HEAD + "teacher/task/queryTaskClass";
    //发布通用
    public static String NORMAL_PUBLISH = HTTP_HEAD + "teacher/task/saveReleasePars";
    //图片下载
    public static String IMAGE_LOAD = HTTP_HEAD + "teacher/task/downImage";
    public static String DOC_LOAD = HTTP_HEAD + "teacher/task/downDoc";
    //--------------------------YCSS-----------------------------

    //获取教辅
    public static String NORISUKE_URL = HTTP_HEAD + "classs/getauxiliarybymajorcode";
    //添加班级----获取年级
    public static String ADD_CLASS_GRADE_URL = HTTP_HEAD + "classs/getdictionariesbytype";
    //添加班级---获取班级
    public static String ADD_CLASS_NUMBER_URL = HTTP_HEAD + "classs/getdictionariesbytype";
    //添加班级---获取学科
    public static String ADD_CLASS_SUBJECTS_URL = HTTP_HEAD + "classs/getdictionariesbytype";
    //添加班级---获取教辅
    public static String ADD_CLASS_MAJOR_URL = HTTP_HEAD + "classs/getauxiliarybymajorcode";
    //创建班级
    public static String ADD_CLASS_URL = HTTP_HEAD + "classs/add";
    //获取班级列表
    public static String CLASS_LIST_URL = HTTP_HEAD + "user/getclassbyuid";
    //获取学生列表
    public static String CLASS_STUDENT_URL = HTTP_HEAD + "classs/getstudents";
    //获取老师列表
    public static String CLASS_TEACHER_URL = HTTP_HEAD + "user/getuserbycid";
    //获取家长列表
    public static String CLASS_PATRIARCH_URL = HTTP_HEAD + "classs/getparents";
    //移除家长
    public static String CLASS_REMOVE_PATRIARCH_URL = HTTP_HEAD + "classs/delparent";
    //老师管理---教师禁用。启用
    public static String TEACHER_MANAGE = HTTP_HEAD + "user/setactivatebytccid";
    //班主任转让列表
    public static String CLASS_TEACHER_MAKE_OVER_URL = HTTP_HEAD + "classs/getClassTeachers";
    //提交班主任转让
    public static String SUBMIT_CLASS_TEACHER_URL = HTTP_HEAD + "classs/editHeadTeacher";
    //获取班级信息
    public static String CLASS_MESSAGE_URL = HTTP_HEAD + "classs/getClassAllInfo";
    //学生管理---移除学生
    public static String REMOVE_STUDENT_URL = HTTP_HEAD + "classs/delstudent";
    //修改班级信息
    public static String MODIFICATION_CLASS_MESSAGE_URL = HTTP_HEAD + "classs/edit";
    //班级审核
    public static String CLASS_AUDIT_URL = HTTP_HEAD + "user/getheadmasterclassteachers";
    //我的---获取班级
    public static String GET_CLASS_URL = HTTP_HEAD + "teacher/analysis/queryClass";
    //我的---获取科目
    public static String GET_SUBJECT_URL = HTTP_HEAD + "teacher/analysis/queryCourse";
    //我的---获取学情分析
    public static String STUDENT_ANALYSIS_URL = HTTP_HEAD + "teacher/analysis/queryAnalysis";
    //我的---获取学生列表
    public static String STUDENT_LIST_URL = HTTP_HEAD + "teacher/analysis/queryStudentInfo";
    //我的---获取学生分析详情
    public static String STUDENT_ANALYZE_DETAILS = HTTP_HEAD + "teacher/analysis/queryStudentInfoDetail";
    //我的---用户信息
    public static String MY_MESSAGE_URL = HTTP_HEAD + "user/getuserinfo";
    //我的---保存用户信息
    public static String SAVE_MESSAGE_URL = HTTP_HEAD + "user/saveuserinfo";
    //我的---反馈
    public static String MY_FEEDBACK_URL = HTTP_HEAD + "feedback/submit";
    //我的---知识点学生分析
    public static String STUDENT_ANALYZE_URL = HTTP_HEAD + "teacher/analysis/queryClassStudentKnowInfo";
    //智能组卷
    public static String CAPACITY_ZJ_URL = HTTP_HEAD + "intelligentPapers/querySubjectData";
    //智能组卷详情
    public static String CAPACITY_ZJ_DETAILS_URL = HTTP_HEAD + "intelligentPapers/getPapersList";
    //班级详情---老师列表
    public static String CLASS_TEACHER_LIST = HTTP_HEAD + "user/getuserbycidteacherlist";
    //智能组卷---替换
    public static String REPLACE_TOPIC_URL = HTTP_HEAD + "intelligentPapers/getSimilarSubjectByTeacher";
}
