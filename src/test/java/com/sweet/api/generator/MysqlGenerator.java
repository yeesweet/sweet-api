package com.sweet.api.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * <p>
 * 代码生成器演示
 * </p>
 * 
 * @author zhang.hp
 * @date 2018-07-14
 */
public class MysqlGenerator {

	/**
	 * <p>
	 * MySQL 生成演示
	 * </p>
	 */
	public static void main(String[] args) {
		/* 获取 JDBC 配置文件 */
		//Properties props = getProperties();
		AutoGenerator mpg = new AutoGenerator();

		String outputDir = "api-api\\src\\main\\java";

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir(outputDir);
		gc.setFileOverride(true);
		gc.setActiveRecord(true);// 开启 activeRecord 模式
		gc.setEnableCache(false);// XML 二级缓存
		gc.setBaseResultMap(true);// XML ResultMap
		gc.setBaseColumnList(true);// XML columList
		gc.setAuthor("zhang.hp");

		// 自定义文件命名，注意 %s 会自动填充表实体属性！
		gc.setMapperName("%sMapper");
		gc.setXmlName("%sMapper");
		gc.setServiceName("I%sService");
		gc.setServiceImplName("%sServiceImpl");
		gc.setControllerName("%sController");
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
		dsc.setTypeConvert(new MySqlTypeConvert());
		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setUsername("root");
		dsc.setPassword("sweet-8888");
		dsc.setUrl("jdbc:mysql://47.95.213.244:3306/sweet?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull");
		mpg.setDataSource(dsc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		// strategy.setCapitalMode(true);// 全局大写命名
		// strategy.setDbColumnUnderline(true);//全局下划线命名
		//strategy.setTablePrefix(new String[] {"tbl_cms"});// 此处可以修改为您的表前缀
		strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		strategy.setInclude(new String[] { "user" }); // 需要生成的表
		strategy.setEntityLombokModel(true);
		// strategy.setExclude(new String[]{"test"}); // 排除生成的表
		// 自定义实体父类
		strategy.setSuperEntityClass("com.sweet.api.entity.SuperEntity");
		// 自定义实体，公共字段
		// strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
		// 自定义 mapper 父类
		strategy.setSuperMapperClass("com.sweet.api.SuperMapper");
		// 自定义 service 父类
		// strategy.setSuperServiceClass("com.baomidou.demo.TestService");
		// 自定义 service 实现类父类
		// strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
		// 自定义 controller 父类
		//strategy.setSuperControllerClass("com.sweet.cms.commons.base.BaseController");
		// 【实体】是否生成字段常量（默认 false）
		// public static final String ID = "test_id";
		// strategy.setEntityColumnConstant(true);
		// 【实体】是否为构建者模型（默认 false）
		// public User setName(String name) {this.name = name; return this;}
		// strategy.setEntityBuliderModel(true);
		mpg.setStrategy(strategy);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent("com.sweet.api"); // 自定义包路径
		pc.setModuleName(null);  //所属模块
		pc.setController("controller"); // 这里是控制器包名，默认 web
		pc.setEntity("entity");
		pc.setMapper("mapper");
		pc.setXml("mapper.xml");
		mpg.setPackageInfo(pc);

		// 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {}
		};
		// 生成的模版路径，不存在时需要先新建
		mpg.setCfg(cfg);
		// 执行生成
		mpg.execute();
	}
	/**
	 * 获取配置文件
	 *
	 * @return 配置Props
	 */
	/*private static Properties getProperties() {
		// 读取配置文件
		Resource resource = new ClassPathResource("/config/application.properties");
		Properties props = new Properties();
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}*/
}