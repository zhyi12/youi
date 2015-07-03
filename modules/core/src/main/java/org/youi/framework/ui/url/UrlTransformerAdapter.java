package org.youi.framework.ui.url;

public interface UrlTransformerAdapter {

	boolean supports(String url);

	public String transform(String url);

}
