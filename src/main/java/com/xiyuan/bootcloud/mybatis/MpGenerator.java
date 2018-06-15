package com.xiyuan.bootcloud.mybatis;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

public class MpGenerator {

    private static final String author = "xiyuan";

    private static final String databasePropertiesPath = "application.yml";

    private static final String[] tablePrefixes = new String[]{"tb_"};

    private static final String[] includeTables = {};//要生成的table列表，如果生成所有的table，这里保持为空数组

    private static final String[] excludeTables = {};//要跳过的table列表，如果生成所有的table，这里保持为空数组

    private static final NamingStrategy namingStrategy = NamingStrategy.underline_to_camel;//表字段命名方式，采用下划线转驼峰的格式

    private static final String basePackage = "com.xiyuan.bootcloud.mybatis";

    private static final String javaPath = "src/main/java";

    private static final String resourcesPath = "src/main/resources";

    private static final String mapperXmlDirPath = resourcesPath + "/mapper";

    private static final Charset charset = StandardCharsets.UTF_8;

    public static void main(String[] args) throws IOException {
        String tempProjectDir = new File(".").getAbsolutePath().replace('\\', '/');
        tempProjectDir = tempProjectDir.substring(0, tempProjectDir.length() - 1);
        final String curProjectDir = tempProjectDir;

        YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
        List<PropertySource<?>> propertySources = yamlPropertySourceLoader.load("application", new ClassPathResource("application.yml"));
        PropertySource<?> propertySource = propertySources.get(0);

        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(
                // 全局配置
                new GlobalConfig()
                        .setOutputDir(curProjectDir + javaPath)//输出目录
                        .setFileOverride(true)// 是否覆盖文件
                        .setActiveRecord(true)// 开启 activeRecord 模式
                        .setEnableCache(false)// XML 二级缓存
                        .setBaseResultMap(true)// XML ResultMap
                        .setBaseColumnList(true)// XML columList
                        .setAuthor(author)
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                // .setMapperName("%sDao")
                // .setXmlName("%sDao")
                // .setServiceName("MP%sService")
                // .setServiceImplName("%sServiceDiy")
                // .setControllerName("%sAction")
        ).setDataSource(
                // 数据源配置
                new DataSourceConfig()
                        .setDbType(DbType.MYSQL)// 数据库类型
                        .setTypeConvert(new MySqlTypeConvert() {
                            // 自定义数据库表字段类型转换【可选】
                            @Override
                            public DbColumnType processTypeConvert(String fieldType) {
                                return super.processTypeConvert(fieldType);
                            }
                        })
                        .setDriverName("" + propertySource.getProperty("spring.datasource.driver-class-name"))
                        .setUsername("" + propertySource.getProperty("spring.datasource.username"))
                        .setPassword("" + propertySource.getProperty("spring.datasource.password"))
                        .setUrl("" + propertySource.getProperty("spring.datasource.url"))
        ).setStrategy(
                // 策略配置
                new StrategyConfig()
                        // .setCapitalMode(true)// 全局大写命名
                        // .setDbColumnUnderline(true)//全局下划线命名
                        .setTablePrefix(tablePrefixes)// 此处可以修改为您的表前缀
                        .setNaming(namingStrategy)// 表字段生成策略
                         .setInclude(includeTables) // 需要生成的表
                         .setExclude(excludeTables) // 排除生成的表
                        // 自定义实体父类
                        // .setSuperEntityClass("com.baomidou.demo.TestEntity")
                        // 自定义实体，公共字段
                        //.setSuperEntityColumns(new String[]{"test_id"})
                // 自定义 mapper 父类
                // .setSuperMapperClass("com.baomidou.demo.TestMapper")
                // 自定义 service 父类
                // .setSuperServiceClass("com.baomidou.demo.TestService")
                // 自定义 service 实现类父类
                // .setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl")
                // 自定义 controller 父类
                // .setSuperControllerClass("com.baomidou.demo.TestController")
                // 【实体】是否生成字段常量（默认 false）
                // public static final String ID = "test_id";
                // .setEntityColumnConstant(true)
                // 【实体】是否为构建者模型（默认 false）
                // public User setName(String name) {this.name = name; return this;}
                // .setEntityBuilderModel(true)
                // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                // .setEntityLombokModel(true)
                // Boolean类型字段是否移除is前缀处理
                // .setEntityBooleanColumnRemoveIsPrefix(true)
                // .setRestControllerStyle(true)
                // .setControllerMappingHyphenStyle(true)
        ).setPackageInfo(
                // 包配置
                new PackageConfig()
                        .setParent(basePackage)
        ).setCfg(
            null
        ).setTemplate(
                // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                //设置null关闭默认生成
                new TemplateConfig()
                        .setController(null)
                        .setService(null)
                        .setServiceImpl(null)
                        // .setMapper("...");
        );

        // 执行生成
        mpg.execute();


        // 对于mapper 下的 mapper接口生成对应的 dao 以及 mapper文件
        File mapperClassDir = new File(curProjectDir + javaPath + "/" + basePackage.replace('.', '/') + "/mapper");
        File mapperDaoDir = new File(curProjectDir + javaPath + "/" + basePackage.replace('.', '/') + "/dao");
        File mapperXmlDir = new File(curProjectDir + mapperXmlDirPath);
        if (mapperClassDir.exists()
                && (mapperDaoDir.exists() || mapperDaoDir.mkdirs())
                && (mapperXmlDir.exists() || mapperXmlDir.mkdirs())) {
            //将 自动生成的 xml 文件夹 移动到 mapperXmlDir目录下
            File mapperXmlGenerateDir = new File(curProjectDir + javaPath + "/" + basePackage.replace('.', '/') + "/mapper/xml");
            File mapperXmlMoveToDir = new File(curProjectDir + mapperXmlDirPath + "/xml");
            if (mapperXmlGenerateDir.exists()) {
                if (mapperXmlMoveToDir.exists()) {
                    deleteFile(mapperXmlMoveToDir);
                }
                mapperXmlGenerateDir.renameTo(mapperXmlMoveToDir);
            }

            File[] mapperClassFiles = mapperClassDir.listFiles();
            if (mapperClassFiles != null) {
                VelocityEngine ve = new VelocityEngine();
                ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
                ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
                ve.init();

                Template daoJavaVm = ve.getTemplate("template/daoJava.vm");
                Template daoXmlVm = ve.getTemplate("template/daoXml.vm");

                for (File mapperClassFile : mapperClassFiles) {
                    if (mapperClassFile.isFile() && mapperClassFile.getName().endsWith(".java")) {
                        String mapperSimpleName = mapperClassFile.getName().split("\\.")[0];
                        String mapperName = basePackage + ".mapper." + mapperSimpleName;
                        String packageStr = "package " + basePackage + ".dao;";
                        String daoSimpleName = mapperSimpleName.replace("Mapper", "Dao");
                        String daoName = basePackage + ".dao." + daoSimpleName;

                        VelocityContext context = new VelocityContext();
                        context.put("mapperSimpleName", mapperSimpleName);
                        context.put("mapperName", mapperName);
                        context.put("packageStr", packageStr);
                        context.put("daoSimpleName", daoSimpleName);
                        context.put("daoName", daoName);

                        File daoClassFile = new File(curProjectDir + javaPath + "/" + daoName.replace('.', '/') + ".java");
                        if (!daoClassFile.exists()) {
                            StringWriter writer = new StringWriter();
                            daoJavaVm.merge(context, writer);
                            try (FileOutputStream out = new FileOutputStream(daoClassFile)) {
                                out.write(writer.toString().getBytes(charset));
                            }
                            catch (Exception e) {
                                System.err.println("文件保存失败：" + daoClassFile);
                                e.printStackTrace();
                            }
                        }

                        File daoXmlFile = new File(curProjectDir + mapperXmlDirPath + "/" + daoSimpleName + ".xml");
                        if (!daoXmlFile.exists()) {
                            StringWriter writer = new StringWriter();
                            daoXmlVm.merge(context, writer);
                            try (FileOutputStream out = new FileOutputStream(daoXmlFile)) {
                                out.write(writer.toString().getBytes(charset));
                            }
                            catch (Exception e) {
                                System.err.println("文件保存失败：" + daoXmlFile);
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }
        }
    }

    private static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                for (File subFile : subFiles) {
                    deleteFile(subFile);
                }
            }
        }
        file.delete();
    }

}
