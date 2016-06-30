/**
 * 
 */
package org.youi.framework.ui.field;

import java.util.ArrayList;
import java.util.List;

import org.youi.framework.ui.field.model.AbstractField;
import org.youi.framework.util.StringUtils;


/**
 * @author zhyi_12
 *
 */
public class BootstrapLayoutContent extends AbstractLayoutContent implements ILayoutContent {

	@Override
	public String getContent() {
		StringBuffer htmls = new StringBuffer();
		StringBuffer fieldHtmls = new StringBuffer();
		int columns = this.getColumns();//1 2 3 4 6  //只支持 1 2 3 4 6 
		
		if(columns<1){
			columns = 1;
		}
		
		if(columns>6||columns==5){
			columns = 6;
		}
		//col-sm-
		int cellCol = 12/columns;//
		int fieldIndex = 0;
		List<Integer> widths = calLabelWidths();
		
		if(this.getFields()!=null){
			for(Object oField:this.getFields()){
				AbstractField field = (AbstractField)oField;
				if(field.fieldType().equals("fieldHidden")){
					htmls.append(field.getHtml());
					continue;
				}
				//预定义padding
				int column = parseFieldColumn(field.getColumn(),columns);
				
				int colIndex = fieldIndex%columns;
				
				fieldIndex = fieldIndex+column;
				
				int labelWidth = 80;
				if(colIndex<widths.size()){
					labelWidth = widths.get(colIndex);
				}
				
				labelWidth = (labelWidth/10);
				labelWidth = labelWidth*10;
				
				
				StringBuffer gStyles = new StringBuffer();
				gStyles.append("form-group col-sm-"+Math.min(12,cellCol*column)+" label-"+labelWidth+"");
			
				if(field.isNotNull()){
					gStyles.append(" notNull");
				}
				
				fieldHtmls.append("<div class=\""+gStyles+"\">")
					.append(	"<label class=\"control-label field-label\">")
					.append(	field.getCaption()==null?"":(field.getCaption()+"："))
					.append(	"</label>")
					.append(	field.getHtml())
					.append(this.isShowTooltips()?"<div class=\"field-tooltips input-group-addon\">"+StringUtils.findNotEmpty(field.getTooltips()," ")+"</div>":"")
					.append("</div>");
			}
		}
		htmls.append(fieldHtmls);
		return htmls.toString();
	}

	private int parseFieldColumn(int column, int columns) {
		if(column<0){
			column = 1;
		}
		
		//columns总列数  column 占位数
		
		if(columns>columns||columns==5){
			column = 6;
		}
		return column;
	}

	private List<Integer> calLabelWidths(){
		String labelWidths = this.getLabelWidths();
		List<Integer> widths = new ArrayList<Integer>();
		
		if(StringUtils.isNotEmpty(labelWidths)){
			String[] widthArray = labelWidths.split(",");
			for(String width:widthArray){
				try {
					widths.add(Integer.parseInt(width));
				} catch (NumberFormatException e) {
					widths.add(Integer.parseInt(DEFAULT_LABEL_WIDTH));
				}
			}
		}
		return widths;
	}
}
