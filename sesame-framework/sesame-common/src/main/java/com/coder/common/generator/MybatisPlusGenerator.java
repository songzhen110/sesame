package com.coder.common.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.function.Function;

@Slf4j
public class MybatisPlusGenerator {

    private static final String BASE = "com.saic.howcool";

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/d_how_cool_0?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC&allowMultiQueries=true&useSSL=false";

    private static final String USER_NAME = "root";

    private static final String PASSWORD = "root1234";

    private static final String PROJECT_PATH = "/Users/lucas/Downloads/test";

    public static void main(String[] args) {

        FastAutoGenerator.create(initDataSourceConfig())
                .globalConfig(MybatisPlusGenerator::initGlobalConfig)
                .packageConfig(MybatisPlusGenerator::initPackageConfig)
                .strategyConfig(MybatisPlusGenerator::initStrategyConfig)
                //.templateConfig()
                //.injectionConfig()
                //.templateEngine(new FreemarkerTemplateEngine())
                .execute();

        log.info("文件生成完成，路径为：{}",PROJECT_PATH);
    }

    /**
     * 初始化数据源
     */
    private static DataSourceConfig.Builder initDataSourceConfig() {
        return new DataSourceConfig.Builder(URL, USER_NAME, PASSWORD)
                .dbQuery(new MySqlQuery())
                .schema("")
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler());
    }

    /**
     * 初始化包配置
     */
    private static void initPackageConfig(Function<String, String> scanner, PackageConfig.Builder builder) {
        builder.parent(BASE)
                //.moduleName(scanner.apply("请输入模块名:"))
                .entity("entity")
                .service("service")
                .serviceImpl("service.impl")
                .mapper("mapper")
                .xml("mapper.xml")
                //.controller("controller")
                //.other("other")
                .pathInfo(Collections.singletonMap(OutputFile.xml, PROJECT_PATH + "/src/main/resources/mapper/"));
    }

    /**
     * 初始化策略配置
     */
    private static void initStrategyConfig(Function<String, String> scanner, StrategyConfig.Builder builder) {
        // 策略表配置
        builder.addInclude(scanner.apply("请输入表名，多个英文逗号分割:"))
                .enableCapitalMode()
                .enableSkipView()
                .disableSqlFilter();
                //.enableSchema();

        // 策略mapper配置
        builder.mapperBuilder()
                .superClass(BaseMapper.class)
                .enableMapperAnnotation()
                .enableBaseResultMap()
                .enableBaseColumnList()
                //.cache(MyMapperCache.class)
                .formatMapperFileName("%sDao")
                //.formatXmlFileName("%sXml")
                //.convertXmlFileName()
                .build();
    }

    /**
     * 初始化公共配置
     */
    private static void initGlobalConfig(Function<String, String> scanner, GlobalConfig.Builder builder) {
        builder.author(scanner.apply("请输入作者:"))
                //.enableKotlin()
                //.enableSwagger()
                .outputDir(PROJECT_PATH + "/src/main/java")
                .dateType(DateType.TIME_PACK)
                // 文件头时间
                .commentDate("yyyy-MM-dd HH:mm:ss")
                // 是否打开文件
                .disableOpenDir();
    }
}