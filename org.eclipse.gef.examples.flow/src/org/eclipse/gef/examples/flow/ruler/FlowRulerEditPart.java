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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;

/**
 * @author Fredrik Attebrant
 *
 */
public class FlowRulerEditPart extends AbstractGraphicalEditPart {
	
	protected GraphicalViewer diagramViewer;
	private FlowRulerProvider rulerProvider;
	
	private boolean north = false;
	private boolean south = false;
	private boolean west = false;
	
	public FlowRulerEditPart(Object model) {
		setModel(model);
	}
	
	@Override
	public void activate() {
		//getRulerProvider().addRulerChangeListener(listener);
		//getRulerFigure().setZoomManager(getZoomManager());
		super.activate();
	}
	
	public FlowRulerProvider getRulerProvider()  {
		return rulerProvider;
	}

	@Override
	protected IFigure createFigure() {
		FlowRulerFigure ruler = new FlowRulerFigure(getOrientation());
		return ruler;
	}

	/**
	 * @return
	 */
	private int getOrientation() {
		if (north == true) 
			return PositionConstants.NORTH;
		else if (south == true) 
			return PositionConstants.SOUTH;
		return PositionConstants.WEST;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new FlowRulerSelectionPolicy());
	}
	
	public boolean isNorth() {
		return north;
	}
	
	public boolean isSouth() {
		return south;
	}
	
	public boolean isWest() {
		return west;
	}
	
	@Override
	public void setParent(EditPart parent) {
		super.setParent(parent);
		if (getParent() != null && diagramViewer == null) {
			diagramViewer = (GraphicalViewer) getViewer().getProperty(GraphicalViewer.class.toString());
			FlowRulerProvider northProvider = 
					(FlowRulerProvider) diagramViewer.getProperty(FlowRulerProvider.PROPERTY_NORTH_RULER);
			if (northProvider != null && northProvider.getRuler() == getModel()) {
				rulerProvider = northProvider;
				north = true;
			} else {
				// TODO - handle south and west
				// ...
			}
		}
	}
	
	public static class FlowRulerSelectionPolicy extends SelectionEditPolicy {

		@Override
		protected void hideFocus() {
			((FlowRulerFigure) getHostFigure()).setDrawFocus(false);
		}

		@Override
		protected void hideSelection() {
			((FlowRulerFigure) getHostFigure()).setDrawFocus(false);
		}
		
		@Override
		protected void showFocus() {
			((FlowRulerFigure) getHostFigure()).setDrawFocus(true);
		}

		@Override
		protected void showSelection() {
			((FlowRulerFigure) getHostFigure()).setDrawFocus(true);
		}
		
	}

}