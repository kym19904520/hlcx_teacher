package com.up.study.params;

import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;
import com.up.study.model.ImgUrl;
import com.up.study.model.Login;
import com.up.study.model.NameValue;

import java.util.List;

/**
 * TODO:
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/8/6.
 */

public class Params {


    private static int getUserId() {
        return new Login().get().getUser_id();
    }

    /**
     * 试卷错题录入-试卷列表
     *
     * @param courseId  科目ID
     * @param grade     年段
     * @param paperType 试卷类型
     * @param shareType 共享类型
     * @return
     */
    public static HttpParams getTestList(int courseId, int grade, int paperType, int shareType, int pageNo) {
        HttpParams params = new HttpParams();
        params.put("courseId", courseId);
        params.put("grade", grade);
        params.put("paperType", paperType);
        params.put("shareType", shareType);
        params.put("rows", 20);
        params.put("page", pageNo);
        return params;
    }

    /**
     * 获取科目
     *
     * @param classId
     * @return
     */
    public static HttpParams getSubject(Integer classId) {
        HttpParams params = new HttpParams();
        params.put(userId());
        if (null != classId) {
            params.put("classId", classId);
        }
        params.put(userId());
        if (null != classId) {
            params.put("classId", classId);
        }
        return params;
    }

    /**
     * 用户ID
     *
     * @return
     */
    public static HttpParams userId() {
        HttpParams params = new HttpParams();
        params.put("userId", getUserId());
        return params;
    }

    /**
     * 类型
     *
     * @param type shareType 共享类型 papertype 试卷类型
     * @return
     */
    public static HttpParams type(String type) {
        HttpParams params = new HttpParams();
        params.put("type", type);
        return params;
    }

    /**
     * 试卷详情
     *
     * @param id
     * @return
     */
    public static HttpParams testInfo(int id) {
        HttpParams params = new HttpParams();
        params.put("id", id);
        return params;
    }

//    /**
//     * 线下作业通知保存
//     *
//     * @param list
//     * @param content
//     * @param id
//     * @return
//     */
//    public static HttpParams publishTask(List<ImageModel> list, String content, Integer id) {
//        HttpParams params = new HttpParams();
//        if (id != null) {
//            params.put("id", id);
//        }
//        if (list != null) {
//            if (!list.isEmpty()) {
//                params.put("attached ", ImageModel.toJson(list));
//            }
//        }
//        params.put("content", content);
//        return params;
//    }

    /**
     * 线下作业通知保存
     *
     * @param list
     * @param content
     * @param id
     * @return
     */
    public static HttpParams publishTask(List<ImgUrl> list, String content, Integer id) {
        HttpParams params = new HttpParams();
        if (id != null) {
            params.put("id", id);
        }
        if (list != null) {
            if (!list.isEmpty()) {
                params.put("attached", new Gson().toJson(list));
            }
        }
        params.put("content", content);
        return params;
    }

    /**
     * 标准卷上传
     *
     * @param list
     * @param id
     * @return
     */
    public static HttpParams uploadTest(List<ImgUrl> list, int id) {
        HttpParams params = new HttpParams();
        params.put("id", id);
        if (list != null) {
            if (!list.isEmpty()) {
                params.put("attached", new Gson().toJson(list));
            }
        }
        return params;
    }

    /**
     * 出版社筛选
     *
     * @param grade
     * @param courseId
     * @return
     */
    public static HttpParams getPublisher(int grade, int courseId) {
        HttpParams params = new HttpParams();
        params.put("grade", grade);
        params.put("courseId", courseId);
        return params;
    }

    /**
     * 章节筛选
     *
     * @param grade
     * @param courseId
     * @param pressId
     * @return
     */
    public static HttpParams getSection(int grade, int courseId, int pressId) {
        HttpParams params = new HttpParams();
        params.put("grade", grade);
        params.put("courseId", courseId);
        params.put("pressId", pressId);
        return params;
    }

    /**
     * 知识点筛选
     *
     * @param courseId
     * @return
     */
    public static HttpParams getKnowledge(int courseId) {
        HttpParams params = new HttpParams();
        params.put("courseId", courseId);
        return params;
    }

    /**
     * @param courseId
     * @return
     */
    public static HttpParams getYear(int courseId) {
        HttpParams params = new HttpParams();
        params.put("courseId", courseId);
        return params;
    }


    /**
     * 章节筛选参数
     *
     * @param press       出版社1
     * @param grade       年段1
     * @param relativeId  章节Id2
     * @param subjectType 题型2
     * @param difficulty  难度2
     * @param uploadType  类型2
     * @param source      来源1
     * @return
     */
    public static HttpParams getSectionParams(int courseId, NameValue press, NameValue grade, NameValue relativeId, NameValue subjectType, NameValue difficulty, NameValue uploadType, NameValue source) {
        HttpParams params = new HttpParams();
        params.put("queryType", "structure");//
        params.put("courseId", courseId);//
        params.put("press", press.getValue());//出版社
        params.put("grade", grade.getValue());//年段ID

//        if (null != relativeId) {
        params.put("relativeId", relativeId.getValue());//章节
//        }
        if (null != subjectType) {
            params.put("subjectType", subjectType.getValue());//题型
        }
        if (null != difficulty) {
            params.put("difficulty", difficulty.getValue());//难度
        }
        if (null != uploadType) {
            params.put("uploadType", uploadType.getValue());//类型
        }
        params.put("source", source.getValue());
        params.put(userId());
        return params;
    }

    /**
     * 只是点筛选参数
     *
     * @param relativeId  知识点
     * @param subjectType 题型
     * @param difficulty  难度
     * @param source      来源
     * @return
     */
    public static HttpParams getKnowledgeParams(int courseId, NameValue relativeId, NameValue subjectType, NameValue difficulty, NameValue source) {
        HttpParams params = new HttpParams();
        params.put("queryType", "know");
        params.put("courseId", courseId);//
        params.put("relativeId", relativeId.getValue());
        if (null != subjectType) {
            params.put("subjectType", subjectType.getValue());//题型
        }
        if (null != difficulty) {
            params.put("difficulty", difficulty.getValue());//难度
        }
        params.put("source", source.getValue());
        return params;

    }

    /**
     * 练习卷筛选参数
     *
     * @param courseId   科目
     * @param year       年份
     * @param grade      年段
     * @param uploadType 类型1
     * @param difficulty 难度1
     * @param source     来源
     * @return
     */
    public static HttpParams getExerciseParams(int courseId, NameValue year, NameValue grade, NameValue uploadType, NameValue difficulty, NameValue source) {
        HttpParams params = new HttpParams();
        params.put("courseId", courseId);
        params.put("year", year.getValue());//年份
        params.put("grade", grade.getValue());//年级
        if (null != uploadType) {
            params.put("uploadType", uploadType.getValue());//类型
        }
        if (null != difficulty) {
            params.put("difficulty", difficulty.getValue());//难度
        }
        params.put("source", source.getValue());
        return params;
    }

    /**
     * 获取班级
     *
     * @param paperId  试卷ID
     * @param grade    年段 知识点时无此条件
     * @param courseId 科目
     * @param isSection 是否章节tab
     * @return
     */
    public static HttpParams getTeacherClass(int paperId, Integer grade, int courseId,boolean isSection) {
        HttpParams params = new HttpParams();
        if(isSection){
            params.put("type",1);
        }
        params.put("userId", getUserId());
        params.put("paperId", paperId);
        if (null != grade) {
            params.put("grade", grade);
        }
        params.put("courseId", courseId);

        return params;
    }

    /**
     * 发布
     *
     * @param paperId 试卷ID
     * @param classId 班级ID 多个以 @分割 ，例 1@2
     * @param day     日期
     * @return
     */
    public static HttpParams publish(int paperId, String classId, int day,int relationType) {
        HttpParams params = new HttpParams();
        params.put("userId", getUserId());
        params.put("paperId", paperId);
        params.put("classId", classId);
        params.put("day", day);
        params.put("relationType", relationType);
        return params;

    }
}
