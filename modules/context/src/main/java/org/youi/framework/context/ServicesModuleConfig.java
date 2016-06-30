/**
 * 
 */
package org.youi.framework.context;


//import org.youi.common.component.menu.IMenu;

/**
 * 模块配置文件
 * @author Administrator
 *
 */
public class ServicesModuleConfig {

	private String code;//模型编码
	
	private String name;//模型名
	
	private String caption;//模型描述
	
	private String basePackage;
	
	//代码生成配置项
	private String projectPath;//模块项目路径
	
	private String mainSrcPath;//java源文件路径
	
	private String mainResourcePath;//java配置文件路径
	
	private String testSrcPath;//java源文件路径
	
	private String testResourcePath;//java配置文件路径
	
	private String webAppPath;//web文件路径
	
//	private List<IMenu> menus;//模块菜单

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	

	public String getMainSrcPath() {
		return mainSrcPath;
	}

	public void setMainSrcPath(String mainSrcPath) {
		this.mainSrcPath = mainSrcPath;
	}

	public String getMainResourcePath() {
		return mainResourcePath;
	}

	public void setMainResourcePath(String mainResourcePath) {
		this.mainResourcePath = mainResourcePath;
	}

	public String getWebAppPath() {
		return webAppPath;
	}

	public void setWebAppPath(String webAppPath) {
		this.webAppPath = webAppPath;
	}

	public String getTestSrcPath() {
		return testSrcPath;
	}

	public void setTestSrcPath(String testSrcPath) {
		this.testSrcPath = testSrcPath;
	}

	public String getTestResourcePath() {
		return testResourcePath;
	}

	public void setTestResourcePath(String testResourcePath) {
		this.testResourcePath = testResourcePath;
	}

//	public List<IMenu> getMenus() {
//		return menus;
//	}
//
//	public void setMenus(List<IMenu> menus) {
//		this.menus = menus;
//	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}
	
}
