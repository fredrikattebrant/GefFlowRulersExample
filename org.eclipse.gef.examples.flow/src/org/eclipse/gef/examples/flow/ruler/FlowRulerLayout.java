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

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Layout manager for {@link FlowRuler}s
 * 
 * @author Fredrik Attebrant
 *
 */
public class FlowRulerLayout extends XYLayout implements LayoutManager {
	
	@Override
	protected Dimension calculatePreferredSize(IFigure container, int wHint, int hHint) {
		return new Dimension(1, 1);
	}
	
	@Override
	public Object getConstraint(IFigure child) {
		return constraints.get(child);
	}
	
	@Override
	public void layout(IFigure container) {
		List children = container.getChildren();
		Rectangle rulerSize = container.getClientArea();
		for (int i = 0; i < children.size(); i++) {
			IFigure child = (IFigure) children.get(i);
			Dimension childSize = child.getPreferredSize();
			Object constraint = getConstraint(child);
			int position = ((Integer) constraint).intValue();
			switch (((FlowRulerFigure) container).getOrientation()) {
			case PositionConstants.NORTH:
			case PositionConstants.SOUTH:
				childSize.height = rulerSize.height - 1;
				Rectangle.SINGLETON.setLocation(position - (childSize.width / 2), rulerSize.y);
				break;
			case PositionConstants.WEST:
				childSize.width = rulerSize.width - 1;
				Rectangle.SINGLETON.setLocation(rulerSize.x, position - (childSize.height / 2));
				break;
			default:
				break;
			}
			Rectangle.SINGLETON.setSize(childSize);
			child.setBounds(Rectangle.SINGLETON);
		}
	}

}
