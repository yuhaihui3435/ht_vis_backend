package com.ht.vis.validator.sp;

import cn.hutool.core.util.StrUtil;
import com.jfinal.core.Controller;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreValidator;
import com.ht.vis.model.SpAssessmentCriteria;
import com.jfinal.kit.Kv;

import java.util.List;


public class SpAssessmentCriteriaValidator extends CoreValidator {
    @Override
    protected void validate(Controller controller) {
        SpAssessmentCriteria spAssessmentCriteria = controller.getModel(SpAssessmentCriteria.class, "", true);
        String ak = getActionKey();
        List<SpAssessmentCriteria> list = null;
        if (ak.equals("/spAssessmentCriteria/save") || ak.equals("/spAssessmentCriteria/update")) {

            if (spAssessmentCriteria.getNorm() == null) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "指标值不能为空");
                return;
            }

            if (spAssessmentCriteria.getTitle() == null) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "标题值不能为空");
                return;
            }

            if (StrUtil.isBlank(spAssessmentCriteria.getEssential())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "考评要点值不能为空");
                return;
            }

            if (StrUtil.isBlank(spAssessmentCriteria.getPoint())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "分值值不能为空");
                return;
            }

            if (StrUtil.isNotBlank(spAssessmentCriteria.getPoint())) {
                int len = StrUtil.totalLength(spAssessmentCriteria.getPoint());
                if (len > 10) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "分值长度不能大于10");
                    return;
                }
            }
            if (StrUtil.isBlank(spAssessmentCriteria.getGrading())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "评分标准值不能为空");
                return;
            }


        } else if (ak.equals("/spAssessmentCriteria/del") || ak.contains("/spAssessmentCriteria/logicDel")) {
            String ids = controller.getPara("ids");
            if (StrUtil.isBlank(ids)) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "缺少删除数据的关键数据");
                return;
            }
        }
        if (ak.equals("/spAssessmentCriteria/save")) {
            Kv kv= Kv.create();
            kv.put("norm=",spAssessmentCriteria.getNorm());
            kv.put("title=",spAssessmentCriteria.getTitle());
            kv.put("essential=",spAssessmentCriteria.getEssential());
            kv=SpAssessmentCriteria.buildParamMap(SpAssessmentCriteria.class,kv);
            list=SpAssessmentCriteria.dao.findByAndCond(kv);
            if(!list.isEmpty()){
                addError(Consts.REQ_JSON_CODE.fail.name(), "考评标准重复");
                return;
            }
        } else if (ak.equals("/spAssessmentCriteria/update")) {
            Kv kv= Kv.create();
            kv.put("norm=",spAssessmentCriteria.getNorm());
            kv.put("title=",spAssessmentCriteria.getTitle());
            kv.put("essential=",spAssessmentCriteria.getEssential());
            kv.put("id<>",spAssessmentCriteria.getId());
            kv=SpAssessmentCriteria.buildParamMap(SpAssessmentCriteria.class,kv);
            list=SpAssessmentCriteria.dao.findByAndCond(kv);
            if(!list.isEmpty()){
                addError(Consts.REQ_JSON_CODE.fail.name(), "考评标准重复");
                return;
            }

        }
    }

    @Override
    protected void handleError(Controller controller) {
        controller.renderJson(getErrorJSON());
    }

}
