/**
 * 
 */
package org.youi.framework.ui;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.youi.framework.core.convert.IConvert;
import org.youi.framework.core.dataobj.SecurityRecord;
import org.youi.framework.security.AccountPrincipal;
import org.youi.framework.security.EsbSecurityManager;
import org.youi.framework.ui.convert.ConvertProviderFactory;
import org.youi.framework.ui.convert.IConvertContainer;
import org.youi.framework.ui.tag.UiTagWriter;
import org.youi.framework.ui.util.HtmlUtils;
import org.youi.framework.util.SecurityUtils;
import org.youi.framework.util.StringUtils;


/**
 * <p>Title:</p>
 *
 * <p>Description:</p>
 *
 * <p>Copyright:Copyright (c) 2011.9</p>
 *
 * <p>Company:iSoftstone</p>
 *
 * @author Administrator
 * @version 1.0
 */
public class HtmlTag extends I18nContainerTag implements IConvertContainer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6795030265450149231L;
	
	protected boolean xhtml = true;

	private String title;//页面标题
	
	private String token;//强制生成token
	
    /**
     * Are we rendering a lang attribute?
     */
    protected boolean lang = false;
    
    protected UiTagWriter tagWriter;
    
    private ConvertProviderFactory	providerFactory ;
    
    private EsbSecurityManager esbSecurityManager;
    
    List<String> pageScripts = new ArrayList<String>();//页面脚本
    
    List<IConvert<?>> pageConverts ;
    

    public boolean getXhtml() {
        return this.xhtml;
    }

    public void setXhtml(boolean xhtml) {
        this.xhtml = xhtml;
    }

    /**
     * Returns true if the tag should render a lang attribute.
     *
     * @since Struts 1.2
     */
    public boolean getLang() {
        return this.lang;
    }

    /**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
     * Sets whether the tag should render a lang attribute.
     *
     * @since Struts 1.2
     */
    public void setLang(boolean lang) {
        this.lang = lang;
    }

    /**
     * Process the start of this tag.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTagInternal() throws JspException {
    	WebApplicationContext webContext = getWebContext();
		if(webContext!=null&&providerFactory==null){
			providerFactory  = webContext.getBean(ConvertProviderFactory.class);
			providerFactory.setResourceLoader(webContext);
		}
		
		if(webContext!=null&&esbSecurityManager==null){
			try {
				esbSecurityManager = webContext.getBean(EsbSecurityManager.class);
			} catch (BeansException e) {
				//e.printStackTrace();
			}
		}
		
    	initI18n();//初始化i18n相关
    	
    	pageConverts = new ArrayList<IConvert<?>>();
		pageScripts = new ArrayList<String>();
		
    	this.title = this.getI18nMessage(title);
    	//初始化标签内容写入工具栏
    	this.tagWriter = this.createTagWriter();
    	//写入html标签相关内容
    	this.tagWriter.appendHtml(renderHtmlStartElement());
        return EVAL_BODY_INCLUDE;
    }
    
    public void addPageConvert(String convertName){
		IConvert<?> convert = providerFactory.getConvert(convertName ,getLocale());
		if(convert!=null&&!pageConverts.contains(convert)){
			pageConverts.add(convert);
		}
	}
    
	/**
     * Renders an &lt;html&gt; element with appropriate language attributes.
     *
     * @since Struts 1.2
     */
    protected String renderHtmlStartElement() {
        StringBuffer sb = new StringBuffer("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n<html");

        String language = null;
        String country = "";

//        Locale currentLocale =
//            TagUtils.getInstance().getUserLocale(pageContext, Globals.LOCALE_KEY);

//        language = currentLocale.getLanguage();
//        country = currentLocale.getCountry();

        boolean validLanguage = HtmlUtils.isValidRfc2616(language);
        boolean validCountry  = HtmlUtils.isValidRfc2616(country);

        if (this.xhtml) {
//            this.pageContext.setAttribute(Globals.XHTML_KEY, "true",
//                PageContext.PAGE_SCOPE);

            sb.append(" xmlns=\"http://www.w3.org/1999/xhtml\"");
        }

        if ((this.lang || this.xhtml) && validLanguage) {
            sb.append(" lang=\"");
            sb.append(language);

            if (validCountry) {
                sb.append("-");
                sb.append(country);
            }

            sb.append("\"");
        }

        if (this.xhtml && validLanguage) {
            sb.append(" xml:lang=\"");
            sb.append(language);

            if (validCountry) {
                sb.append("-");
                sb.append(country);
            }

            sb.append("\"");
        }

        sb.append(">");
        
        if(StringUtils.isNotEmpty(title)){
        	Object reqTitie = this.pageContext.getRequest().getAttribute(title);
        	sb.append("<title>"+(reqTitie==null?title:reqTitie.toString())+"</title>");
        }
        return sb.toString();
    }

    /**
     * Process the end of this tag.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {
    	StringBuffer  htmls = new StringBuffer();
    	
    	
    	htmls.append("<script>")
    		 //上下文路径
    		 .append( 	"$.youi.serverConfig.contextPath = '").append(this.getContextPath()).append("/';")
//    		 //convert
    		 .append(    getConvertScript())
    		 .append(buildAccountScript())
    		 .append("</script>")
			 .append("</html>");
    	
    	this.tagWriter.appendHtml(htmls.toString());
        return (EVAL_PAGE);
    }
    
    private String buildAccountScript(){
    	StringBuffer  htmls = new StringBuffer();
    	
    	AccountPrincipal account = SecurityUtils.getAccount();
    	
    	if(account!=null){
    		//使用用户缓存
    		htmls.append("$.youi.serverConfig.loginName='"+account.getLoginName()+"';");
    		htmls.append("$.youi.serverConfig.authorization='"+buildAuthorization(account)+"';");
    	}else if(StringUtils.isNotEmpty(token)){
    		htmls.append("$.youi.serverConfig.authorization='"+buildAuthorization(account)+"';");
    	}
    	
    	return htmls.toString();
    }
    
    private String buildAuthorization(AccountPrincipal account) {
    	if(esbSecurityManager!=null){
    		return esbSecurityManager.encryptSecurityInfo(new SecurityRecord());
    	}
		return "";
	}

	/**
     * 生成convert对应的JavaScript代码
     * @return
     */
	private String getConvertScript(){
    	StringBuffer scripts = new StringBuffer();
    	if(pageConverts.size()>0){
			for(IConvert<?> pageConvert:pageConverts){
				scripts.append("$.extend($.youi.serverConfig.convertArray,{"+pageConvert.getName()+":")
				.append(pageConvert.toJson())
				.append("});");
			}
		}
    	return scripts.toString();
    }
    
    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
     * Release any acquired resources.
     */
    public void doFinally() {
    	super.doFinally();
        this.xhtml = false;
        this.lang = false;
        this.title = null;
        this.token = null;
    }

}
