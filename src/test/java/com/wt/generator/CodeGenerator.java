package com.wt.generator;

import com.wt.commons.config.CodeGeneratorConfig;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


/**
 * @author wtao
 * @date 2019-10-19 22:35
 */
@SpringBootTest
public class CodeGenerator {

    @Resource
    private CodeGeneratorConfig codeGeneratorConfig;


    /**
     * 生成代码
     *
     * @author wtao
     * @date 2019-10-20 21:22
     */
    @Test
    public void codeGenerator() {

        codeGeneratorConfig.doGenerate();
    }
}