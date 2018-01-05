package com.up.common.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.base.CommonAdapter;
import com.up.common.base.ViewHolder;
import com.up.common.conf.Constants;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.widget.MyListView;
import com.up.study.adapter.RecyclerViewAdapter;
import com.up.study.base.BaseFragment;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.base.CallBack;
import com.up.study.base.ImgCallBack;
import com.up.study.base.ReqImgUrlCallBack;
import com.up.study.model.ClassModel;
import com.up.study.model.EasyModel;
import com.up.study.model.ImgUrl;
import com.up.study.model.Login;
import com.up.study.model.Options;
import com.up.study.model.ReqImgUrl;
import com.up.study.model.SeqModel;
import com.up.study.model.TopicBean;
import com.up.study.weight.tvhtml.RichText;
import com.up.teacher.R;

import java.io.File;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dell on 2017/7/14.
 */

public class StudyUtils {

    public final static int TYPE_KGT = 1;
    public final static int TYPE_ZGT = 2;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    public static void clearOptionList(List<Options> optionsList) {
        if (optionsList == null) {
            return;
        }
        for (int i = 0; i < optionsList.size(); i++) {
            if (TextUtils.isEmpty(optionsList.get(i).getText()) || optionsList.get(i).getText().contains("null")) {
                optionsList.remove(i);
                i--;
            }
        }

    }

    /**
     * 获取科目类型 ，1：客观题：（单选，多选，判断）2： 主观题（简答题、填空题、计算题、应用题）
     *
     * @return
     */
    public static int getSubjectType(String subjectType) {
        if (subjectType.contains("单选") || subjectType.contains("多选") || subjectType.contains("判断")) {
            return 1;
        }
        return 2;

    }

    /**
     * @param ctx
     * @param obj
     * @param seqModel
     * @param from     1:ViewHolder,2:Activity,3:fragment
     */
    public static <T> void initSeq(Context ctx, T obj, SeqModel seqModel, int from) {
        /*final List<String> answerList = null;
        try {
            String answerjson = seqModel.getAnswer();
            Type type1 = new TypeToken<List<String>>() {}.getType();
            answerList = new Gson().fromJson(answerjson, type1);
        }catch (Exception e){
            Logger.e(Logger.TAG,"答案解析异常，为HTML文本");
        }*/

        ViewHolder vh = null;
        BaseFragmentActivity act = null;
        BaseFragment fra = null;

        RecyclerView mRecyclerView = null;//知识点横向列表
        MyListView lv = null;//选择题选项
        RichText webView = null;//解答题题目、内容
        RichText wv_title = null;//选择题题目
        if (from == 1) {
            vh = (ViewHolder) obj;
            mRecyclerView = vh.getView(R.id.recylist);
            lv = vh.getView(R.id.mlv);
            webView = vh.getView(R.id.web_view);
            wv_title = vh.getView(R.id.wv_title);
        } else if (from == 2) {
            act = (BaseFragmentActivity) obj;
            mRecyclerView = act.bind(R.id.recylist);
            lv = act.bind(R.id.mlv);
            webView = act.bind(R.id.web_view);
            wv_title = act.bind(R.id.wv_title);
        } else if (from == 3) {
            fra = (BaseFragment) obj;
            mRecyclerView = fra.bind(R.id.recylist);
            lv = fra.bind(R.id.mlv);
            webView = fra.bind(R.id.web_view);
            wv_title = fra.bind(R.id.wv_title);
        }
        //知识点数据初始化
        List<String> knowNameList = null;
        String knowName = seqModel.getKnowName();
        if (knowName != null) {
            /*Type type1=new TypeToken<List<String>>(){}.getType();
            knowNameList= new Gson().fromJson(knowName,type1);*/
            knowNameList = Arrays.asList(knowName.split(","));

            //设置线性管理器
            LinearLayoutManager ms = new LinearLayoutManager(ctx);
            ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
            mRecyclerView.setLayoutManager(ms);
            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(knowNameList);
            mRecyclerView.setAdapter(myAdapter);
        }


        //客观题初始化选项
        if (StudyUtils.getSubjectType(seqModel.getSubjectTypeName()) == StudyUtils.TYPE_KGT) {

            //WidgetUtils.initWebView(wv_title, seqModel.getTitle());

            /*wv_title.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效
            URLImageGetter ReviewImgGetter = new URLImageGetter(ctx, wv_title);//实例化URLImageGetter类
            wv_title.setText(Html.fromHtml(seqModel.getTitle(),ReviewImgGetter,null));*/

            wv_title.setRichText(seqModel.getTitle());

            String options = seqModel.getOptions();
            Type type = new TypeToken<List<Options>>() {
            }.getType();
            List<Options> optionsList = new Gson().fromJson(options, type);
            StudyUtils.clearOptionList(optionsList);

            CommonAdapter<Options> adapter = new CommonAdapter<Options>(ctx, optionsList, R.layout.item_topic_kgt) {
                @Override
                public void convert(ViewHolder vh, Options item, int position) {
                    TextView tv_answer = vh.getView(R.id.tv_answer);
                    TextView tv_answer_text = vh.getView(R.id.tv_answer_text);
                    tv_answer.setText(item.getOpt());
                    tv_answer_text.setText(item.getText());

                    //选择题答案标记
                    /*if (answerList!=null&&answerList.size()>0){
                        if (seqModel.getSubjectType().contains("单选")||seqModel.getSubjectType().contains("判断")){
                            if (answerList.get(0).equals(item.getOpt())){
                                tv_answer.setBackgroundResource(R.drawable.round_blue_circle_background);
                            }
                            else{
                                tv_answer.setBackgroundResource(R.drawable.round_gray_circle_background);
                            }
                        }
                        else if(seqModel.getSubjectType().contains("多选")){
                            for (int j = 0;j<answerList.size();j++){
                                if (answerList.get(j).equals(item.getOpt())){
                                    tv_answer.setBackgroundResource(R.drawable.round_blue_circle_background);
                                }
                               *//* else{
                                    tv_answer.setBackgroundResource(R.drawable.round_gray_circle_background);
                                }*//*
                            }
                        }
                    }*/

                }
            };
            lv.setAdapter(adapter);

            lv.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
           /* WebView wv_answer = vh.getView(R.id.wv_answer);
            WidgetUtils.initAnswerWebView(wv_answer,seqModel.getAnswer());

            WebView wv_analysis = bind(R.id.wv_analysis);
            WidgetUtils.initWebView(wv_analysis, seqModel.getAnalysis());*/

        }
        //主观题初始化内容
        else if (StudyUtils.getSubjectType(seqModel.getSubjectTypeName()) == StudyUtils.TYPE_ZGT) {

            //WidgetUtils.initWebView(webView, seqModel.getTitle());
            /*webView.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效
            URLImageGetter ReviewImgGetter = new URLImageGetter(ctx, webView);//实例化URLImageGetter类
            webView.setText(Html.fromHtml(seqModel.getTitle(),ReviewImgGetter,null));*/
            webView.setRichText(seqModel.getTitle());

            lv.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
           /* WebView wv_answer = bind(R.id.wv_answer);
            WidgetUtils.initAnswerWebView(wv_answer,seqModel.getAnswer());

            WebView wv_analysis = (WebView) bind(R.id.wv_analysis);
            WidgetUtils.initWebView(wv_analysis, seqModel.getAnalysis());*/


        }

        /*if(seqType==SEQ_TYPE_CTXQ){//错题详情
            StudyUtils.showSeqErrorCauseAndImg(this,seqModel);
        }*/
    }

    /**
     * 初始化题目(废弃)
     *
     * @param ctx
     * @param vh
     * @param seqModel
     */
    @Deprecated
    public static void initSeq(Context ctx, ViewHolder vh, SeqModel seqModel) {
        /*final List<String> answerList = null;
        try {
            String answerjson = seqModel.getAnswer();
            Type type1 = new TypeToken<List<String>>() {}.getType();
            answerList = new Gson().fromJson(answerjson, type1);
        }catch (Exception e){
            Logger.e(Logger.TAG,"答案解析异常，为HTML文本");
        }*/

        //知识点数据初始化
        List<String> knowNameList = null;
        String knowName = seqModel.getKnowName();
        if (knowName != null) {
            /*Type type1=new TypeToken<List<String>>(){}.getType();
            knowNameList= new Gson().fromJson(knowName,type1);*/
            knowNameList = Arrays.asList(knowName.split(","));

            //找到这个Listview
            RecyclerView mRecyclerView = vh.getView(R.id.recylist);
            //设置线性管理器
            LinearLayoutManager ms = new LinearLayoutManager(ctx);
            ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
            mRecyclerView.setLayoutManager(ms);
            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(knowNameList);
            mRecyclerView.setAdapter(myAdapter);
        }

        MyListView lv = vh.getView(R.id.mlv);
        WebView webView = vh.getView(R.id.web_view);//解答题内容
        //客观题初始化选项
        if (StudyUtils.getSubjectType(seqModel.getSubjectTypeName()) == StudyUtils.TYPE_KGT) {

            WebView wv_title = vh.getView(R.id.wv_title);
            WidgetUtils.initWebView(wv_title, seqModel.getTitle());

            String options = seqModel.getOptions();
            Type type = new TypeToken<List<Options>>() {
            }.getType();
            List<Options> optionsList = new Gson().fromJson(options, type);
            StudyUtils.clearOptionList(optionsList);

            CommonAdapter<Options> adapter = new CommonAdapter<Options>(ctx, optionsList, R.layout.item_topic_kgt) {
                @Override
                public void convert(ViewHolder vh, Options item, int position) {
                    TextView tv_answer = vh.getView(R.id.tv_answer);
                    TextView tv_answer_text = vh.getView(R.id.tv_answer_text);
                    tv_answer.setText(item.getOpt());
                    tv_answer_text.setText(item.getText());

                    //选择题答案标记
                    /*if (answerList!=null&&answerList.size()>0){
                        if (seqModel.getSubjectType().contains("单选")||seqModel.getSubjectType().contains("判断")){
                            if (answerList.get(0).equals(item.getOpt())){
                                tv_answer.setBackgroundResource(R.drawable.round_blue_circle_background);
                            }
                            else{
                                tv_answer.setBackgroundResource(R.drawable.round_gray_circle_background);
                            }
                        }
                        else if(seqModel.getSubjectType().contains("多选")){
                            for (int j = 0;j<answerList.size();j++){
                                if (answerList.get(j).equals(item.getOpt())){
                                    tv_answer.setBackgroundResource(R.drawable.round_blue_circle_background);
                                }
                               *//* else{
                                    tv_answer.setBackgroundResource(R.drawable.round_gray_circle_background);
                                }*//*
                            }
                        }
                    }*/

                }
            };
            lv.setAdapter(adapter);

            lv.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
           /* WebView wv_answer = vh.getView(R.id.wv_answer);
            WidgetUtils.initAnswerWebView(wv_answer,seqModel.getAnswer());

            WebView wv_analysis = bind(R.id.wv_analysis);
            WidgetUtils.initWebView(wv_analysis, seqModel.getAnalysis());*/

        }
        //主观题初始化内容
        else if (StudyUtils.getSubjectType(seqModel.getSubjectTypeName()) == StudyUtils.TYPE_ZGT) {

            WidgetUtils.initWebView(webView, seqModel.getTitle());
            lv.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
           /* WebView wv_answer = bind(R.id.wv_answer);
            WidgetUtils.initAnswerWebView(wv_answer,seqModel.getAnswer());

            WebView wv_analysis = (WebView) bind(R.id.wv_analysis);
            WidgetUtils.initWebView(wv_analysis, seqModel.getAnalysis());*/


        }

        /*if(seqType==SEQ_TYPE_CTXQ){//错题详情
            StudyUtils.showSeqErrorCauseAndImg(this,seqModel);
        }*/
    }

    public static void uploadImgUrl(List<String> localImgList, final Context ctx, final CallBack<List<ImgUrl>> callBack) {
        final List<String> strList = new ArrayList<>();
        strList.addAll(localImgList);
        final List<ImgUrl> urls = new ArrayList<>();//上传到阿里云的图片地址
        onlyGetImgRequsetUrl(ctx, new ReqImgUrlCallBack() {
            @Override
            public void suc(ReqImgUrl reqImgUrl) {
                for (int i = 0; i < strList.size(); i++) {
                    final int index = i;
                    uploadImg(ctx, reqImgUrl, strList.get(i), "img.png", new ImgCallBack() {
                        @Override
                        public void suc(String imgPath) {
                            //返回的是阿里云的图片地址
                            Logger.i(Logger.TAG, "上传到阿里云的第" + (index + 1) + "张图片地址：" + imgPath);
                            ImgUrl imgUrl = new ImgUrl();
                            imgUrl.setUrl(imgPath);
                            urls.add(imgUrl);
                            if (urls.size() == strList.size()) {//最后一个图片
                                callBack.suc(urls);
                            }
                        }
                    });
                }
            }
        });

    }

    public static void onlyGetImgRequsetUrl(Context ctx, final ReqImgUrlCallBack callBack) {
        HttpParams params = new HttpParams();
        J.http().post(Urls.GET_IMG_REQUEST_URL, ctx, params, new HttpCallback<Respond<String>>(null) {
            @Override
            public void success(Respond<String> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {

                    String options = res.getData();
                    Type type = new TypeToken<ReqImgUrl>() {
                    }.getType();
                    ReqImgUrl reqImgUrl = new Gson().fromJson(options, type);
                    Logger.i(Logger.TAG, reqImgUrl.getDir());
                    callBack.suc(reqImgUrl);
                }
            }
        });
    }

    public static void uploadImg(Context ctx, final ReqImgUrl reqImgUrl, String filePath, String fileName, final ImgCallBack imgCallBack) {
        final String key = reqImgUrl.getDir() + "/" + sdf.format(new Date()) + new Random().nextInt(100) + fileName;
        Logger.i(Logger.TAG, "key=" + key);
        OkGo.post("http://" + reqImgUrl.getReadhost())//
                .tag(ctx)//
                // .isMultipart(true)       // 强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .params("OSSAccessKeyId", reqImgUrl.getAccessid())
                .params("policy", reqImgUrl.getPolicy())
                .params("signature", reqImgUrl.getSignature())
                .params("key", key)
                .params("success_action_status", 200)
                .params("file", new File(filePath))   // 可以添加文件上传
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        Logger.i(Logger.TAG, "图片上传到阿里云成功！图片地址：" + reqImgUrl.getReadhost() + "/" + key);
                        Logger.i(Logger.TAG, response.message());
//                        imgCallBack.suc("http://" + reqImgUrl.getReadhost() + "/" + key);
                        imgCallBack.suc("/" + key);
                        //未返回图片地址

                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                    }
                });
    }

    /**
     * 班级列表
     *
     * @param ctx
     * @param callBack
     */
    public static void getClass(Context ctx, final CallBack<List<ClassModel>> callBack) {
        HttpParams params = new HttpParams();
        J.http().post(Urls.GET_CLASS, ctx, params, new HttpCallback<Respond<List<ClassModel>>>(ctx, true, true) {
            @Override
            public void success(Respond<List<ClassModel>> res, Call call, Response response, boolean isCache) {
                callBack.suc(res.getData());
            }
        });
    }

    /**
     * 作业类型筛选
     *
     * @param ctx
     * @param callBack
     */
    public static void getWorkType(Context ctx, final CallBack<List<EasyModel>> callBack) {
        HttpParams params = new HttpParams();
        params.put("type", "relationType");
        J.http().post(Urls.GET_WORK_TYPE, ctx, params, new HttpCallback<Respond<List<EasyModel>>>(ctx, true, true) {
            @Override
            public void success(Respond<List<EasyModel>> res, Call call, Response response, boolean isCache) {
                callBack.suc(res.getData());
            }
        });
    }

    /**
     * 作业状态筛选
     *
     * @param ctx
     * @param callBack
     */
    public static void getWorkStatus(Context ctx, final CallBack<List<EasyModel>> callBack) {
        HttpParams params = new HttpParams();
        params.put("type", "workType");
        J.http().post(Urls.GET_WORK_TYPE, ctx, params, new HttpCallback<Respond<List<EasyModel>>>(ctx, true, true) {
            @Override
            public void success(Respond<List<EasyModel>> res, Call call, Response response, boolean isCache) {
                callBack.suc(res.getData());
            }
        });
    }

    /* public static void getLoginInfo(Context ctx){
         String userId= SPUtil.getString(ctx, Constants.SP_USER_ID,"");
         String classId =  SPUtil.getString(ctx,Constants.SP_CLASS_ID,"");
         String studentId =  SPUtil.getString(ctx,Constants.SP_STUDENT_ID,"");
         String gradeId =  SPUtil.getString(ctx,Constants.SP_GRADE_ID,"");

         TApplication.getInstant().setPhone(SPUtil.getString(ctx, Constants.SP_USER_PHONE,""));
         TApplication.getInstant().setUserName(SPUtil.getString(ctx,Constants.SP_USER_NAME,""));
     }*/
    public static void saveLoginInfo(Context ctx, Login loginData, String userPassword) {
        SPUtil.putString(ctx, Constants.SP_USER_ID, "1");
        /*if (loginData.getUser()!=null) {
            SPUtil.putString(ctx, Constants.SP_USER_PHONE, loginData.getUser().getAccount());
            SPUtil.putString(ctx,Constants.SP_USER_NAME,loginData.getUser().getName());
            SPUtil.putString(ctx,Constants.SP_USER_ID,loginData.getUser().getId()+"");

            TApplication.getInstant().setPhone(loginData.getUser().getAccount());
            TApplication.getInstant().setUserId(loginData.getUser().getId());
            TApplication.getInstant().setUserName(loginData.getUser().getName());
        }

        if (loginData.getClasss()!=null){
            SPUtil.putString(ctx,Constants.SP_CLASS_ID,loginData.getClasss().getId()+"");
            SPUtil.putString(ctx,Constants.SP_CLASS_NAME,loginData.getClasss().getName()+"");
            SPUtil.putString(ctx,Constants.SP_GRADE_ID,loginData.getClasss().getGrade()+"");
            TApplication.getInstant().setClassId(loginData.getClasss().getId());
            TApplication.getInstant().setClassName(loginData.getClasss().getName());
            TApplication.getInstant().setGradeId(loginData.getClasss().getGrade());
        }

        if (loginData.getStudent()!=null) {
            SPUtil.putString(ctx, Constants.SP_STUDENT_ID, loginData.getStudent().getId() + "");
            SPUtil.putString(ctx, Constants.SP_STUDENT_NAME, loginData.getStudent().getName() + "");
            SPUtil.putString(ctx, Constants.SP_STUDENT_NUM, loginData.getStudent().getCode() + "");
            TApplication.getInstant().setStudentId(loginData.getStudent().getId());
            TApplication.getInstant().setStudentName(loginData.getStudent().getName());
            TApplication.getInstant().setStudentNum(loginData.getStudent().getCode());
        }
        SPUtil.putString(ctx, Constants.SP_USER_PSW, userPassword);*/
    }

    /**
     * 初始化题目页面
     */
    public static void initSeqView(View view, TopicBean.SubListBean seqModel, Activity ctx) {
        WebView wv_title = (WebView) view.findViewById(R.id.wv_title);
        TextView tv_subject_type = (TextView) view.findViewById(R.id.tv_subject_type);
        tv_subject_type.setText(seqModel.getSubjectTypeName());
        WidgetUtils.initWebView(wv_title, seqModel.getTitle());
        if (seqModel.getOptions() != null) {
            final String options = seqModel.getOptions();
            final Type type = new TypeToken<List<Options>>() {
            }.getType();
            final List<Options> optionsList = new Gson().fromJson(options, type);
            StudyUtils.clearOptionList(optionsList);
            if (optionsList != null) {
                final MyListView lv = (MyListView) view.findViewById(R.id.lv);
                final CommonAdapter<Options> adapter = new CommonAdapter<Options>(ctx, optionsList, R.layout.item_topic_kgt) {
                    @Override
                    public void convert(ViewHolder vh, Options item, int position) {
                        TextView tv_answer = vh.getView(R.id.tv_answer);
                        TextView tv_answer_text = vh.getView(R.id.tv_answer_text);
                        tv_answer.setText(item.getOpt());
                        tv_answer_text.setText(item.getText());
                    }
                };
                lv.setAdapter(adapter);
            }
        }
    }
}
