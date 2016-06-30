/**
 * 
 */
package org.youi.framework.ui.field.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhyi_12
 *
 */
public class FieldSpinner extends AbstractField {
	
	private Integer incremental;
	private Integer max;
	private Integer min;
	private String numberFormat;
	private Integer page;
	private Integer step;

	/* (non-Javadoc)
	 * @see com.gsoft.framework.ui.field.model.AbstractField#addonProperies()
	 */
	@Override
	public String[] addonProperies() {
		List<String> addonProperies = new ArrayList<String>();
		if(incremental!=null){
			addonProperies.add(intProperty("incremental", incremental));
		}
		
		if(page!=null){
			addonProperies.add(intProperty("page", page));
		}
		
		if(min!=null){
			addonProperies.add(intProperty("min", min));
		}
		
		if(max!=null){
			addonProperies.add(intProperty("max", max));
		}
		
		if(step!=null){
			addonProperies.add(intProperty("step", step));
		}
		
		addonProperies.add(stringProperty(numberFormat, numberFormat));
		
		return addonProperies.toArray(new String[addonProperies.size()]);
	}

	public Integer getIncremental() {
		return incremental;
	}

	public void setIncremental(Integer incremental) {
		this.incremental = incremental;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public String getNumberFormat() {
		return numberFormat;
	}

	public void setNumberFormat(String numberFormat) {
		this.numberFormat = numberFormat;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

}
