/**
 * 
 */
package org.youi.framework.ui;

import javax.servlet.jsp.JspException;

import org.youi.framework.core.Constants;



/**
 * @author Administrator
 *
 */
public class StyleTag extends AbstractTag{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3488087378939102966L;

	private String href;
	
	private String type = "text/css";
	
	private String rel = "stylesheet";
	

	@Override
	public int doStartTagInternal() throws JspException {
		super.doStartTagInternal();
		StringBuffer htmls = new StringBuffer();
		
		Object  sessionTheme = 
			pageContext.getServletContext().getAttribute(Constants.SESSION_THEME);
		
		String theme;
		if(sessionTheme==null){
			theme = "youi";
		}else{
			theme = "theme/"+sessionTheme.toString();
		}
		String href = this.getUrlPath(this.href.replace("/theme/", "/"+theme+"/"));
		
		htmls.append("<link ")
			.append(" href=\""+href+"\" ")
			.append(" type=\""+type+"\" ")
			.append(" rel=\""+rel+"\" >")
			.append("</link>");
		this.tagWriter.appendHtml(htmls.toString());
		return EVAL_PAGE;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}
}
