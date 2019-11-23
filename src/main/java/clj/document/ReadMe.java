package clj.document;

public class ReadMe {

	public static void main(String[] args) {
		/**
		 * 
		 * -----------------------环境配置-----------------------<br>
		 * 1. 在桌面新建一个文件夹a，用来maven项目的工作空间；<br>
		 * 2. 打开eclipse，选择工作空间a，打开；<br>
		 * 3. eclipse -> preferences -> java -> installed jres ,修改jdk版本 -> apply <br>
		 * 4 修改本地maven的 ->conf -> settings 的 localRepository
		 * 为本地的maven仓库路径（随便创建一个文件夹都可以当maven仓库）<br>
		 * 5. eclipse -> preferences -> maven -> user settings -> 修改为本地安装的maven -> conf
		 * -> settings.xml , -> apply <br>
		 * 6. eclipse -> preferences -> server -> runtime,选择本地安装的tomcat7的路径, ->apply
		 * close <br>
		 * -----------------------------------------------------
		 * 
		 * -----------------------创建maven-web项目----------------------- <br>
		 * 1. new ->project -> maven -> maven project -> next -> 在filter中搜索
		 * web，选择搜索出来的maven-archetype-webapps -> next —>
		 * 输入groupid，输入archetypeid，输入version -> 完成 <br>
		 * 2. 打开pom.xml，添加参数配置
		 * <properties><project.build.sourceEncoding>UTF-8</project.build.sourceEncoding><encoding>UTF-8</encoding>
		 * <java.version>1.8</java.version><maven.compiler.source>1.8</maven.compiler.source><maven.compiler.target>1.8</maven.compiler.target>
		 * </properties>
		 * 
		 * 3. 右键项目 -> maven -> update project 4.
		 * 在servers的tab页，新增一个tomcat7，添加创建的maven项目启动tomcat，就可以访问了
		 * 
		 * -----------------------创建maven-web项目-----------------------
		 */

	}

}
