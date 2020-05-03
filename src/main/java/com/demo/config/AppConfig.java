package com.demo.config;

import com.demo.controller.IndexController;
import com.demo.model._MappingKit;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;

/**
 * API引导式配置
 * @author guoyongkui
 */
public class AppConfig extends JFinalConfig {

    /**
     * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
     * <p>
     * 使用本方法启动过第一次以后，会在开发工具的 debug、run config 中自动生成
     * 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM argument 可配置为：
     * -XX:PermSize=64M -XX:MaxPermSize=256M
     */
    public static void main(String[] args) {
        JFinal.start("src/main/webapp", 80, "/", 5);
    }

    /**
     * 配置常量
     */
    @Override
    public void configConstant(Constants me) {
        // 加载少量必要配置，随后可用PropKit.get(...)获取值
        PropKit.use("config.properties");
        me.setDevMode(PropKit.getBoolean("devMode", false));
        me.setBaseDownloadPath("upload");
    }

    /**
     * 配置路由
     */
    @Override
    public void configRoute(Routes me) {
        // 第三个参数为该Controller的视图存放路径
        me.setBaseViewPath("/app");
        me.add("/", IndexController.class);
//        me.add("/blog", BlogController.class);
//        me.add("/user", UserController.class);
//        me.add("/file", FileController.class);
    }

    @Override
    public void configEngine(Engine me) {
//        me.addSharedFunction("/common/_admin_layout.html");
//        me.addSharedFunction("/common/_layout.html");
    }

    /**
     * 配置插件
     */
    @Override
    public void configPlugin(Plugins me) {
        // 配置C3p0数据库连接池插件
        DruidPlugin druidPlugin = createDruidPlugin();
        me.add(druidPlugin);

        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        // 所有映射在 MappingKit 中自动化搞定
        _MappingKit.mapping(arp);
        me.add(arp);

        // 配置Ecache
//		me.add(new EhCachePlugin());
    }

    public static DruidPlugin createDruidPlugin() {
        return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
    }

    /**
     * 配置全局拦截器
     */
    @Override
    public void configInterceptor(Interceptors me) {
//        me.add(new ParameterInterceptor());
    }

    /**
     * 配置处理器
     */
    @Override
    public void configHandler(Handlers me) {
        me.add(new ContextPathHandler("BASE_PATH"));

    }
}
