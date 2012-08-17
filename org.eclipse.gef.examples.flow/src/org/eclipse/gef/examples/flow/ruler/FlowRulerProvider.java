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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.gef.rulers.RulerProvider;

/**
 * @author Fredrik Attebrant
 *
 */
public class FlowRulerProvider extends RulerProvider {
	
	public static final String PROPERTY_NORTH_RULER = "north ruler";
	public static final String PROPERTY_WEST_RULER = "west ruler";
	public static final String PROPERTY_SOUTH_RULER = "south ruler";

	private FlowRuler ruler;
	
	private PropertyChangeListener rulerListener = new PropertyChangeListener() {
		
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals(FlowRuler.PROPERTY_CHILDREN)) {
				// TODO: Handle child stuff
			}
		}
	};
	
	public FlowRulerProvider(FlowRuler ruler) {
		this.ruler = ruler;
	}
	
	@Override
	public Object getRuler() {
		return ruler;
	}

}
