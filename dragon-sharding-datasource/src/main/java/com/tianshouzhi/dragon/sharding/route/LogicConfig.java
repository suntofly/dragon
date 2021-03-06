package com.tianshouzhi.dragon.sharding.route;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

/**
 * Created by TIANSHOUZHI336 on 2017/2/23.
 */
public abstract class LogicConfig {
    //route rule中变量的命名规则
    public static final Pattern routeRuleVariablePattern =Pattern.compile("(\\$\\{.+?\\})",Pattern.CASE_INSENSITIVE);
    private String namePattern;
    protected MessageFormat messageFormat;//eg table_{00}

    public LogicConfig(String namePattern) {
        if(StringUtils.isBlank(namePattern)){
            throw new RuntimeException("namePattern can't be blank!!!");
        }
        this.namePattern = namePattern;
        this.messageFormat = new MessageFormat(namePattern);
    }

    public String getNamePattern(){
        return namePattern;
    }

    public String format(Long caculatedIndex){
        return messageFormat.format(new Object[]{caculatedIndex});
    }

    public Long parseIndex(String realName){
        try {
            return (Long) messageFormat.parse(realName)[0];
        } catch (ParseException e) {
            throw new RuntimeException(realName);
        }
    }
}
