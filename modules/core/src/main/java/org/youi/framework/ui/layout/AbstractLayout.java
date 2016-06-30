/*
 * YOUI框架
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.framework.ui.layout;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

import org.apache.taglibs.standard.resources.Resources;
import org.dom4j.Document;


/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Dec 26, 2010
 */
public abstract class AbstractLayout {
	private String charEncoding = null;
	
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	public static final String VALID_SCHEME_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+.-";
	
	protected String decorator;//
	
	protected Document document;//
	
	public AbstractLayout(){
		
	}
	
	public AbstractLayout(String decorator,Document document){
		this.decorator = decorator;
		this.document = document;
	}
	/**
	 * 初始化布局
	 * @param decorator 
	 * @param document 
	 */
	protected void initLayout(Document document, String decorator){
		this.decorator = decorator;
		this.document = document;
	}
	
	/**
	 * body开始标签后
	 * @param pageContext
	 * @return
	 */
	public abstract String getStartHtml(PageContext pageContext);
	
	/**
	 * body结束标签前
	 * @param pageContext
	 * @return
	 */
	public abstract String getEndHtml(PageContext pageContext);
	
	protected String acquireString(PageContext pageContext,
			String context,
			String url) throws IOException, JspException {
		boolean isAbsoluteUrl = isAbsoluteUrl(url);
		if (isAbsoluteUrl) {//
			// for absolute URLs, delegate to our peer
			BufferedReader r = new BufferedReader(acquireReader(pageContext,
					context,
					url,
					isAbsoluteUrl));
			StringBuffer sb = new StringBuffer();
			int i;

			// under JIT, testing seems to show this simple loop is as fast
			// as any of the alternatives
			while ((i = r.read()) != -1)
				sb.append((char) i);

			return sb.toString();
		} else {
			// handle relative URLs ourselves
			// URL is relative, so we must be an HTTP request
			if (!(pageContext.getRequest() instanceof HttpServletRequest && pageContext
					.getResponse() instanceof HttpServletResponse))
				throw new JspTagException(Resources
						.getMessage("IMPORT_REL_WITHOUT_HTTP"));

			// retrieve an appropriate ServletContext
			ServletContext c = null;
			String targetUrl = url;
			if (context != null)
				c = pageContext.getServletContext().getContext(context);
			else {
				c = pageContext.getServletContext();

				// normalize the URL if we have an HttpServletRequest
				if (!targetUrl.startsWith("/")) {
					String sp = ((HttpServletRequest) pageContext.getRequest())
							.getServletPath();
					targetUrl = sp.substring(0, sp.lastIndexOf('/')) + '/'
							+ targetUrl;
				}
			}

			if (c == null) {
				throw new JspTagException(Resources.getMessage(
						"IMPORT_REL_WITHOUT_DISPATCHER", context, targetUrl));
			}

			// from this context, get a dispatcher
			RequestDispatcher rd = c
					.getRequestDispatcher(stripSession(targetUrl));
			if (rd == null)
				throw new JspTagException(stripSession(targetUrl));

			// include the resource, using our custom wrapper
			ImportResponseWrapper irw = new ImportResponseWrapper(
					(HttpServletResponse) pageContext.getResponse());

			// spec mandates specific error handling form include()
			try {
				rd.include(pageContext.getRequest(), irw);
			} catch (IOException ex) {
				throw new JspException(ex);
			} catch (RuntimeException ex) {
				throw new JspException(ex);
			} catch (ServletException ex) {
				Throwable rc = ex.getRootCause();
				if (rc == null)
					throw new JspException(ex);
				else
					throw new JspException(rc);
			}

			// disallow inappropriate response codes per JSTL spec
			if (irw.getStatus() < 200 || irw.getStatus() > 299) {
				throw new JspTagException(irw.getStatus() + " "
						+ stripSession(targetUrl));
			}

			// recover the response String from our wrapper
			return irw.getString();
		}
	}

	private Reader acquireReader(PageContext pageContext,
			String context,
			String target,
			boolean isAbsoluteUrl) throws IOException,
			JspException {
		if (!isAbsoluteUrl) {
			// for relative URLs, delegate to our peer
			return new StringReader(acquireString(pageContext,
					context,
					target));
		} else {
			// absolute URL
			try {
				// handle absolute URLs ourselves, using java.net.URL
				URL u = new URL(target);
				URLConnection uc = u.openConnection();
				InputStream i = uc.getInputStream();

				// okay, we've got a stream; encode it appropriately
				Reader r = null;
				String charSet;
				String DEFAULT_ENCODING = "UTF-8";
				if (charEncoding != null && !charEncoding.equals("")) {
					charSet = charEncoding;
				} else {
					// charSet extracted according to RFC 2045, section 5.1
					String contentType = uc.getContentType();
					if (contentType != null) {
						charSet = "UTF-8";// Util.getContentTypeAttribute(contentType,"charset");
					} else {
						charSet = DEFAULT_ENCODING;
					}
				}
				try {
					r = new InputStreamReader(i, charSet);
				} catch (Exception ex) {
					r = new InputStreamReader(i, DEFAULT_ENCODING);
				}

				// check response code for HTTP URLs before returning, per spec,
				// before returning
				if (uc instanceof HttpURLConnection) {
					int status = ((HttpURLConnection) uc).getResponseCode();
					if (status < 200 || status > 299)
						throw new JspTagException(status + " " + target);
				}

				return r;
			} catch (IOException ex) {
				throw new JspException(Resources.getMessage("IMPORT_ABS_ERROR",
						target, ex), ex);
			} catch (RuntimeException ex) { // because the spec makes us
				throw new JspException(Resources.getMessage("IMPORT_ABS_ERROR",
						target, ex), ex);
			}
		}
	}

	/** Wraps responses to allow us to retrieve results as Strings. */
	private class ImportResponseWrapper extends HttpServletResponseWrapper {

		// ************************************************************
		// Overview

		/*
		 * We provide either a Writer or an OutputStream as requested. We
		 * actually have a true Writer and an OutputStream backing both, since
		 * we don't want to use a character encoding both ways (Writer ->
		 * OutputStream -> Writer). So we use no encoding at all (as none is
		 * relevant) when the target resource uses a Writer. And we decode the
		 * OutputStream's bytes using OUR tag's 'charEncoding' attribute, or
		 * ISO-8859-1 as the default. We thus ignore setLocale() and
		 * setContentType() in this wrapper.
		 * 
		 * In other words, the target's asserted encoding is used to convert
		 * from a Writer to an OutputStream, which is typically the medium
		 * through with the target will communicate its ultimate response. Since
		 * we short-circuit that mechanism and read the target's characters
		 * directly if they're offered as such, we simply ignore the target's
		 * encoding assertion.
		 */

		// ************************************************************
		// Data
		/** The Writer we convey. */
		private StringWriter sw = new StringWriter();

		/** A buffer, alternatively, to accumulate bytes. */
		private ByteArrayOutputStream bos = new ByteArrayOutputStream();

		/** A ServletOutputStream we convey, tied to this Writer. */
		private ServletOutputStream sos = new LayoutServletOutputStream(bos);

		/** 'True' if getWriter() was called; false otherwise. */
		private boolean isWriterUsed;

		/** 'True if getOutputStream() was called; false otherwise. */
		private boolean isStreamUsed;

		/** The HTTP status set by the target. */
		private int status = 200;

		// ************************************************************
		// Constructor and methods

		/** Constructs a new ImportResponseWrapper. */
		public ImportResponseWrapper(HttpServletResponse response) {
			super(response);
		}

		/** Returns a Writer designed to buffer the output. */
		public PrintWriter getWriter() {
			if (isStreamUsed)
				throw new IllegalStateException(Resources
						.getMessage("IMPORT_ILLEGAL_STREAM"));
			isWriterUsed = true;
			return new PrintWriter(sw);
		}

		/** Returns a ServletOutputStream designed to buffer the output. */
		public ServletOutputStream getOutputStream() {
			if (isWriterUsed)
				throw new IllegalStateException(Resources
						.getMessage("IMPORT_ILLEGAL_WRITER"));
			isStreamUsed = true;
			return sos;
		}

		/** Has no effect. */
		public void setContentType(String x) {
			// ignore
		}

		/** Has no effect. */
		public void setLocale(Locale x) {
			// ignore
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public int getStatus() {
			return status;
		}

		/**
		 * Retrieves the buffered output, using the containing tag's
		 * 'charEncoding' attribute, or the tag's default encoding, <b>if
		 * necessary</b>.
		 */
		// not simply toString() because we need to throw
		// UnsupportedEncodingException
		public String getString() throws UnsupportedEncodingException {
			if (isWriterUsed)
				return sw.toString();
			else if (isStreamUsed) {
				if (charEncoding != null && !charEncoding.equals(""))
					return bos.toString(charEncoding);
				else
					return bos.toString(DEFAULT_ENCODING);
			} else
				return ""; // target didn't write anything
		}
	}

	// *********************************************************************
	// Some private utility methods

	// *********************************************************************
	// Public utility methods

	/**
	 * Returns <tt>true</tt> if our current URL is absolute, <tt>false</tt>
	 * otherwise.
	 */
	public static boolean isAbsoluteUrl(String url) {
		// a null URL is not absolute, by our definition
		if (url == null)
			return false;

		// do a fast, simple check first
		int colonPos;
		if ((colonPos = url.indexOf(":")) == -1)
			return false;

		// if we DO have a colon, make sure that every character
		// leading up to it is a valid scheme character
		for (int i = 0; i < colonPos; i++)
			if (VALID_SCHEME_CHARS.indexOf(url.charAt(i)) == -1)
				return false;

		// if so, we've got an absolute url
		return true;
	}

	/**
	 * Strips a servlet session ID from <tt>url</tt>. The session ID is
	 * encoded as a URL "path parameter" beginning with "jsessionid=". We thus
	 * remove anything we find between ";jsessionid=" (inclusive) and either EOS
	 * or a subsequent ';' (exclusive).
	 */
	public static String stripSession(String url) {
		StringBuffer u = new StringBuffer(url);
		int sessionStart;
		while ((sessionStart = u.toString().indexOf(";jsessionid=")) != -1) {
			int sessionEnd = u.toString().indexOf(";", sessionStart + 1);
			if (sessionEnd == -1)
				sessionEnd = u.toString().indexOf("?", sessionStart + 1);
			if (sessionEnd == -1) // still
				sessionEnd = u.length();
			u.delete(sessionStart, sessionEnd);
		}
		return u.toString();
	}
	
	private static class LayoutServletOutputStream extends ServletOutputStream {

		private ByteArrayOutputStream bos;
		public LayoutServletOutputStream(ByteArrayOutputStream bos) {
			this.bos = bos;
		}

		//@Override
		public boolean isReady() {
			return true;
		}

		//@Override
//		public void setWriteListener(WriteListener writeListener) {
//			
//		}

		@Override
		public void write(int b) throws IOException {
			bos.write(b);
		}
		
	}
}
