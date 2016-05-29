package com.carbonrider.xjcplugins;

import java.util.Collections;
import java.util.List;

import org.xml.sax.ErrorHandler;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JPackage;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;
import com.sun.tools.xjc.util.DOMUtils;

/**
 * <p>
 * XJC plug-in for declaring that generated classes extend given classes.
 * </p>
 * 
 * @author Yogesh Jadhav
 * @version 1.0
 */

public class ExtendsPlugin extends Plugin {
	private static final String NAMESPACE = "http://www.carbonrider.com/xjc-plugins/extends";
	private static final String INTERFACE_TAG = "extends";

	public String getOptionName() {
		return "Xextends";
	}

	public String getUsage() {
		return "  -Xextends       :  use extends plugin";
	}

	public List<String> getCustomizationURIs() {
		return Collections.singletonList(NAMESPACE);
	}

	public boolean run(Outline model, Options opt, ErrorHandler errorHandler) {
		for (ClassOutline classOutline : model.getClasses()) {
			CPluginCustomization customization = classOutline.target
					.getCustomizations().find(NAMESPACE, INTERFACE_TAG);
			if (customization != null) {
				customization.markAsAcknowledged();
				String superClassName = DOMUtils
						.getElementText(customization.element);
				JCodeModel jc = new JCodeModel();
				JPackage jp = jc._package(customization.element
						.getAttribute("package"));
				try {
					classOutline.implClass._extends(jp._class(superClassName));
				} catch (JClassAlreadyExistsException e) {
					throw new RuntimeException(e);
				}

			}
		}
		return true;
	}

	public boolean isCustomizationTagName(String nsUri, String localName) {
		return NAMESPACE.equals(nsUri) && INTERFACE_TAG.equals(localName);
	}

}