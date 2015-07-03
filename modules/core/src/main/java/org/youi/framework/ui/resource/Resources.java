/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 *
 * You can obtain a copy of the license at
 * glassfish/bootstrap/legal/CDDLv1.0.txt or
 * https://glassfish.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * HEADER in each file and include the License file at
 * glassfish/bootstrap/legal/CDDLv1.0.txt.  If applicable,
 * add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your
 * own identifying information: Portions Copyright [yyyy]
 * [name of copyright owner]
 *
 * Copyright 2005 Sun Microsystems, Inc. All rights reserved.
 *
 * Portions Copyright Apache Software Foundation.
 */ 

package org.youi.framework.ui.resource;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * <p>Provides locale-neutral access to string resources.  Only the
 * documentation and code are in English. :-)
 *
 * <p>The major goal, aside from globalization, is convenience.
 * Access to resources with no parameters is made in the form:</p>
 * <pre>
 *     Resources.getMessage(MESSAGE_NAME);
 * </pre>
 *
 * <p>Access to resources with one parameter works like</p>
 * <pre>
 *     Resources.getMessage(MESSAGE_NAME, arg1);
 * </pre>
 *
 * <p>... and so on.</p>
 *
 * @author Shawn Bayern
 */
public class Resources {

    //*********************************************************************
    // Static data

    /** The location of our resources. */
    private static final String RESOURCE_LOCATION = "com.gsoft.framework.taglib.resource.Resources";

    /** Our class-wide ResourceBundle. */
    private static ResourceBundle rb = ResourceBundle.getBundle(RESOURCE_LOCATION);

    //*********************************************************************
    // Public static methods

    /** Retrieves a message with no arguments. */
    public static String getMessage(String name)
	    throws MissingResourceException {
	return rb.getString(name);
    }

    /** Retrieves a message with arbitrarily many arguments. */
    public static String getMessage(String name, Object[] a)
	    throws MissingResourceException {
	String res = rb.getString(name);
	return MessageFormat.format(res, a);
    }

    /** Retrieves a message with one argument. */
    public static String getMessage(String name, Object a1)
	    throws MissingResourceException {
	return getMessage(name, new Object[] { a1 });
    }

    /** Retrieves a message with two arguments. */
    public static String getMessage(String name, Object a1, Object a2)
	    throws MissingResourceException {
	return getMessage(name, new Object[] { a1, a2 });
    }

    /** Retrieves a message with three arguments. */
    public static String getMessage(String name,
				    Object a1,
				    Object a2,
				    Object a3)
	    throws MissingResourceException {
	return getMessage(name, new Object[] { a1, a2, a3 });
    }

    /** Retrieves a message with four arguments. */
    public static String getMessage(String name,
			 	    Object a1,
				    Object a2,
				    Object a3,
				    Object a4)
	    throws MissingResourceException {
	return getMessage(name, new Object[] { a1, a2, a3, a4 });
    }

    /** Retrieves a message with five arguments. */
    public static String getMessage(String name,
				    Object a1,
				    Object a2,
				    Object a3,
				    Object a4,
				    Object a5)
	    throws MissingResourceException {
	return getMessage(name, new Object[] { a1, a2, a3, a4, a5 });
    }

    /** Retrieves a message with six arguments. */
    public static String getMessage(String name,
				    Object a1,
				    Object a2,
				    Object a3,
				    Object a4,
				    Object a5,
				    Object a6)
	    throws MissingResourceException {
	return getMessage(name, new Object[] { a1, a2, a3, a4, a5, a6 });
    }

}
