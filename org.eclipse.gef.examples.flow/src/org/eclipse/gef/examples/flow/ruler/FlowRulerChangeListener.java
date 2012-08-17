/**
 * Copyright (c) FindOut Technologies AB, 2011-2012
 *
 * All Rights Reserved.
 *
 * NOTICE: All information contained herein is, and remains
 * the property of FindOut Technologies AB and its suppliers,
 * if any. The intellectual and technical concepts contained
 * herein are proprietary to FindOut Technologies AB
 * and its suppliers and may be covered by Swedish and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from FindOut Technologies AB.
 *
 */

package org.eclipse.gef.examples.flow.ruler;

/**
 * @author Fredrik Attebrant
 * 
 */
public interface FlowRulerChangeListener {
	// TODO - not needed?
	
	/**
	 * Invoked when a part is added to the ruler
	 * 
	 * @param part
	 * @param team
	 */
	void notifyPartsChanged(Object part, Object team);

}
