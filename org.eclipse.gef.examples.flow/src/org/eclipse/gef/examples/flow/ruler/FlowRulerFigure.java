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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;


/**
 * @author Fredrik Attebrant
 *
 */
public class FlowRulerFigure extends Figure {
	
	private int orientation;
	private boolean drawFocus = false;
	
	public FlowRulerFigure(int orientation) {
		this.orientation = orientation;
		setBackgroundColor(ColorConstants.listBackground);
		setForegroundColor(ColorConstants.listForeground);
		setOpaque(true);
		setLayoutManager(new FlowRulerLayout());
	}
	
	public int getOrientation() {
		return orientation;
	}
	
	public void setDrawFocus(boolean drawFocus) {
		if (this.drawFocus != drawFocus) {
			this.drawFocus  = drawFocus;
			repaint();
		}
	}

	/**
	 * @return
	 */
	public boolean getDrawFocus() {
		return drawFocus;
	}
 
}
