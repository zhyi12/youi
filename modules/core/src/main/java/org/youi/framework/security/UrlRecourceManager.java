package org.youi.framework.security;

import java.util.Map;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午05:20:28</p>
 */
public interface UrlRecourceManager {
	
	/**
	 * <pre>
	 *   获取URL地址访问权限配置集合, 键-URL匹配符，值-权限表达式,格式如下：
	 * &lt;"/page/gsoft.mgn.project/project.html"，"roles[admin,super]"&gt; </pre>
	 * @return
	 */
	public Map<String, String> getFilterChainDefinitionMap();
	
}
