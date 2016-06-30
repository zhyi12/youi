/**
 * 
 */
package org.youi.framework.ui.grid;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

import org.springframework.util.StringUtils;
import org.youi.framework.ui.AbstractUiTag;
import org.youi.framework.ui.GiuiConstants;
import org.youi.framework.ui.util.JsUtils;

/**
 * @author zhyi_12
 *
 */
public class GridListTag  extends AbstractUiTag implements BodyTag{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6855769706092027118L;
	
	private String src;//数据地址
	
	private String template;//模版
	
	private String rowStyle;//行样式
	
	protected BodyContent  bodyContent;

	public int doStartTagInternal() throws JspException {
		this.tagWriter = createTagWriter();//创建tagWriter 
		if(StringUtils.isEmpty(id)){
			id = widgetUUID();
		}
		id = wrapWithPageId(id);//
        return EVAL_BODY_BUFFERED;
    }
	
	public int doAfterBody() throws JspException {
	 	return SKIP_BODY;
	 }
	
	public int doEndTag() throws JspException {
		StringBuilder htmls = new StringBuilder();
		htmls.append(uiStartHtml());
		htmls.append(uiContentHtml());
		htmls.append(uiEndHtml());
		
		if(script){
			if(pageTag!=null){
				pageTag.addPageScript(uiScripts());
			}else{
				htmls.append(uiScripts());
			}
		}
		this.tagWriter.appendHtml(htmls.toString());
		return EVAL_PAGE;
	}

	@Override
	public String uiContentHtml() {
		return "";
	}

	@Override
	protected String uiStyles() {
		return "youi-gridList";
	}

	@Override
	protected String uiScripts() {
		String rowTemplate = StringUtils.isEmpty(template)?
				this.bodyContent.getString().trim().replaceAll("[\\t\\n\\r]", "").replaceAll("'", "\""):template;
		StringBuilder scripts = new StringBuilder();
		scripts.append("$('#"+id+"')."+GiuiConstants.TAG_GRID_LIST+"({")
			.append(JsUtils.propertyValue("src", getActionPath(src), JsUtils.JSON_PROP_STR))
			.append(JsUtils.propertyValue("rowStyle", rowStyle, JsUtils.JSON_PROP_STR))
			.append(JsUtils.propertyValue("template", rowTemplate, JsUtils.JSON_PROP_STR))
			.append(	"	initHtml:false")
			.append(	"});");
		return wrapScripts(scripts.toString());
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	@Override
	public void setBodyContent(BodyContent b) {
		this.bodyContent = b;
	}

	@Override
	public void doInitBody() throws JspException {
		
	}

	public String getRowStyle() {
		return rowStyle;
	}

	public void setRowStyle(String rowStyle) {
		this.rowStyle = rowStyle;
	}

	@Override
	public void initUi() {
		
	}

	@Override
	public void doFinally() {
		super.doFinally();
		this.bodyContent = null;
		this.rowStyle = null;
	}

}
