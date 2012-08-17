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

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.rulers.RulerProvider;

/**
 * @author Fredrik Attebrant
 *
 */
public class FlowRulerEditPartFactory implements EditPartFactory {

	protected GraphicalViewer diagramViewer;

	public FlowRulerEditPartFactory(GraphicalViewer primaryViewer) {
		diagramViewer = primaryViewer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart,
	 * java.lang.Object)
	 */
	public EditPart createEditPart(EditPart parentEditPart, Object model) {
		// the model can be null when the contents of the root edit part are set
		// to null
		EditPart part = null;
		if (isRuler(model)) {
			part = createRulerEditPart(parentEditPart, model);
		} else if (model != null) {
			//part = createGuideEditPart(parentEditPart, model);
			System.out.println("FlowRulerEditPartFactory.createEditPart() - no GUIDES supported");
		}
		return part;
	}

/*	
	protected EditPart createGuideEditPart(EditPart parentEditPart, Object model) {
		return new GuideEditPart(model);
	}
 */

	protected EditPart createRulerEditPart(EditPart parentEditPart, Object model) {
		return new FlowRulerEditPart(model);
	}
	protected Object getHorizontalRuler() {
		Object ruler = null;
		//TODO: Should this be replaced with:
		// FlowRulerProvider.PROPERTY_SOUTH_RULER or FlowRulerProvider.PROPERTY_NORTH_RULER ?
		RulerProvider provider = (RulerProvider) diagramViewer
				.getProperty(RulerProvider.PROPERTY_HORIZONTAL_RULER);
		if (provider != null) {
			ruler = provider.getRuler();
		}
		return ruler;
	}

	protected Object getVerticalRuler() {
		Object ruler = null;
		//TODO: Should this be replaced with:
		// FlowRulerProvider.PROPERTY_WEST_RULER ?
		RulerProvider provider = (RulerProvider) diagramViewer
				.getProperty(RulerProvider.PROPERTY_VERTICAL_RULER);
		if (provider != null) {
			ruler = provider.getRuler();
		}
		return ruler;
	}

	protected boolean isRuler(Object model) {
		boolean result = false;
		if (model != null) {
			result = model == getHorizontalRuler()
					|| model == getVerticalRuler();
		}
		return result;
	}


}
