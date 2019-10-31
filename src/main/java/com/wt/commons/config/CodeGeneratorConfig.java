package com.wt.commons.config;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 代码生成器 配置类
 *
 * @author wtao
 * @date 2019-10-20 21:19
 */
@Component
@Data
public class CodeGeneratorConfig {

    /**
     * 执行代码生成
     *
     * @author wtao
     * @date 2019-10-20 21:20
     */
    public void doGenerate() {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String path = System.getProperty(projectPath);
        gc.setOutputDir(path + outPut);
        gc.setAuthor(author);
        gc.setOpen(false);
        // 实体属性 Swagger2 注解
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dataSourceUrl);
        dsc.setDriverName(dataSourceDriver);
        dsc.setUsername(dataSourceUsername);
        dsc.setPassword(dataSourcePassword);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent(parentPackage);
        if (null != entityPackage && !"".equals(entityPackage)) {
            pc.setEntity(entityPackage);
        }
        if (null != mapperPackage && !"".equals(mapperPackage)) {
            pc.setMapper(mapperPackage);
        }
        if (null != servicePackage && !"".equals(servicePackage)) {
            pc.setService(servicePackage);
        }
        if (null != serviceImplPackage && !"".equals(serviceImplPackage)) {
            pc.setServiceImpl(serviceImplPackage);
        }
        if (null != controllerPackage && !"".equals(controllerPackage)) {
            pc.setController(controllerPackage);
        }
        if (null != xmlPackage && !"".equals(xmlPackage)) {
            pc.setXml(xmlPackage);
        }
        mpg.setPackageInfo(pc);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        if (null != superEntity && !"".equals(superEntity)) {
            strategy.setSuperEntityClass(superEntity);
        }
        if (null != superMapper && !"".equals(superMapper)) {
            strategy.setSuperMapperClass(superMapper);
        }
        if (null != superService && !"".equals(superService)) {
            strategy.setSuperServiceClass(superService);
        }
        if (null != superServiceImpl && !"".equals(superServiceImpl)) {
            strategy.setSuperServiceImplClass(superServiceImpl);
        }
        if (null != superController && !"".equals(superController)) {
            strategy.setSuperControllerClass(superController);
        }
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        if (null != superEntityColumns && !"".equals(superEntityColumns)) {
            strategy.setSuperEntityColumns(superEntityColumns);
        }
        strategy.setInclude(tables.split(","));
        strategy.setControllerMappingHyphenStyle(true);
        if (null != tablePrefix && !"".equals(tablePrefix)) {
            strategy.setTablePrefix(tablePrefix);
        }
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }

    @Value("${generator.author}")
    private String author;
    @Value("${generator.projectPath}")
    private String projectPath;
    @Value("${generator.outPut}")
    private String outPut;
    @Value("${generator.dataSource.url}")
    private String dataSourceUrl;
    @Value("${generator.dataSource.driver}")
    private String dataSourceDriver;
    @Value("${generator.dataSource.username}")
    private String dataSourceUsername;
    @Value("${generator.dataSource.password}")
    private String dataSourcePassword;
    @Value("${generator.package.parentPackage}")
    private String parentPackage;
    @Value("${generator.package.entityPackage}")
    private String entityPackage;
    @Value("${generator.package.mapperPackage}")
    private String mapperPackage;
    @Value("${generator.package.servicePackage}")
    private String servicePackage;
    @Value("${generator.package.serviceImplPackage}")
    private String serviceImplPackage;
    @Value("${generator.package.controllerPackage}")
    private String controllerPackage;
    @Value("${generator.package.xmlPackage}")
    private String xmlPackage;
    @Value("${generator.package.moduleName}")
    private String moduleName;
    @Value("${generator.dataSource.tablePrefix}")
    private String tablePrefix;
    @Value("${generator.dataSource.tables}")
    private String tables;
    @Value("${generator.strategy.superEntity}")
    private String superEntity;
    @Value("${generator.strategy.superMapper}")
    private String superMapper;
    @Value("${generator.strategy.superService}")
    private String superService;
    @Value("${generator.strategy.superServiceImpl}")
    private String superServiceImpl;
    @Value("${generator.strategy.superController}")
    private String superController;
    @Value("${generator.strategy.superEntityColumns}")
    private String superEntityColumns;

     /*@Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        //yaml.setResources(new FileSystemResource("config.yml"));//File引入
		yaml.setResources(new ClassPathResource("generator.yml"));//class引入
        configurer.setProperties(yaml.getObject());
        return configurer;
    }*/
}
