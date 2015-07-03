package org.youi.framework.core.convert;

import java.util.Locale;

public interface IConvertProviderFactory {

	public IConvert<?> getConvert(String name,Locale locale);
	
}
