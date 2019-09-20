package com.yoj.web.bean;

import com.yoj.nuts.judge.bean.TestResult;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Solution {
    private Integer solutionId;

    private Integer problemId;

    private Integer userId;
    //	Languages
    private Integer language;

    private String code;
    //	Results
    private Integer result;
    //ms
    private Integer runtime;
    //memoryInfo = this.memory / 10 + "KB"
    private Integer memory;

    private String errorMessage;

    private Date submitTime;

    //非表格字段
    private String languageStr;

    private String resultStr;

    private User user;

    private List<TestResult> testResults;
}
