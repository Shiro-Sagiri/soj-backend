package com.shiro.soj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiro.soj.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.shiro.soj.model.entity.QuestionSubmit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionSubmitMapper extends BaseMapper<QuestionSubmit> {

    /**
     * 分页查询
     * @param questionSubmitQueryRequest 查询条件
     */
    List<QuestionSubmit> pageQuery(@Param("questionSubmitQueryRequest") QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取分页查询的记录数
     * @return 记录数
     */
    Long getTotal(@Param("questionSubmitQueryRequest") QuestionSubmitQueryRequest questionSubmitQueryRequest);
}
