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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RangeModel;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.AutoexposeHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.SimpleRootEditPart;
import org.eclipse.gef.editparts.ViewportAutoexposeHelper;

/**
 * @author Fredrik Attebrant
 *
 */
public class FlowRulerRootEditPart extends SimpleRootEditPart {

	private static final Insets VERTICAL_THRESHOLD = new Insets(18, 0, 18, 0);
	private static final Insets HORIZONTAL_THRESHOLD = new Insets(0, 18, 0, 18);

	private boolean horizontal;

	/**
	 * Constructor
	 * 
	 * @param isHorzontal
	 *            whether or not the corresponding model ruler is horizontal
	 */
	public FlowRulerRootEditPart(boolean isHorzontal) {
		super();
		horizontal = isHorzontal;
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractEditPart#addChildVisual(org.eclipse.gef.EditPart,
	 *      int)
	 */
	protected void addChildVisual(EditPart childEditPart, int index) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		getViewport().setContents(child);
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		return new RulerViewport();
	}

	/**
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		if (adapter == AutoexposeHelper.class) {
			if (((FlowRulerEditPart) getContents()).isNorth())
				return new ViewportAutoexposeHelper(this, HORIZONTAL_THRESHOLD);
			return new ViewportAutoexposeHelper(this, VERTICAL_THRESHOLD);
		}
		return super.getAdapter(adapter);
	}

	/**
	 * Convenience method to get to the viewport
	 * 
	 * @return the figure cast as a viewport
	 */
	protected Viewport getViewport() {
		return (Viewport) getFigure();
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractEditPart#removeChildVisual(org.eclipse.gef.EditPart)
	 */
	protected void removeChildVisual(EditPart childEditPart) {
		getViewport().setContents(null);
	}

	/**
	 * A RulerViewport shares a RangeModel with that of the primary
	 * GraphicalViewer. The shared RangeModel is set on this viewport externally
	 * by a client (in this case, RulerComposite).
	 * 
	 * @author Pratik Shah
	 * @since 3.0
	 */
	public class RulerViewport extends Viewport {
		/**
		 * Constructor
		 */
		public RulerViewport() {
			super(true);
			setLayoutManager(null);
			// The range model that's not shared is initialized such that it
			// can't scroll
			// anymore (otherwise, CTRL + SHIFT + ARROW scrolls it).
			RangeModel bogusRangeModel;
			if (horizontal)
				bogusRangeModel = getVerticalRangeModel();
			else
				bogusRangeModel = getHorizontalRangeModel();
			bogusRangeModel.setMinimum(0);
			bogusRangeModel.setMaximum(100);
			bogusRangeModel.setValue(0);
			bogusRangeModel.setExtent(100);
		}

		/**
		 * This is the method that does the actual layout. We don't want a
		 * layout to occur when layout() is invoked, rather when something
		 * changes in the shared RangeModel.
		 * 
		 * @param force
		 *            if <code>true</code>, the contents will be resized and
		 *            revalidated; otherwise, just a repaint will occur.
		 */
		protected void doLayout(boolean force) {
			repaint();
			/*
			 * @TODO:Pratik It seems you don't really need this force argument.
			 * Those that invoke doLayout(false) can invoke repaint()
			 * themselves. doLayout() should just layout.
			 */
			if (force) {
				RangeModel rModel;
				if (horizontal)
					rModel = getHorizontalRangeModel();
				else
					rModel = getVerticalRangeModel();
				Rectangle contentBounds = Rectangle.SINGLETON;
				if (horizontal) {
					contentBounds.y = 0;
					contentBounds.x = rModel.getMinimum();
					contentBounds.height = this.getContents()
							.getPreferredSize().height;
					contentBounds.width = rModel.getMaximum()
							- rModel.getMinimum();
				} else {
					contentBounds.y = rModel.getMinimum();
					contentBounds.x = 0;
					contentBounds.height = rModel.getMaximum()
							- rModel.getMinimum();
					contentBounds.width = this.getContents().getPreferredSize().width;
				}
				if (!this.getContents().getBounds().equals(contentBounds)) {
					this.getContents().setBounds(contentBounds);
					this.getContents().revalidate();
				}
			}
		}

		/**
		 * @see org.eclipse.draw2d.IFigure#getPreferredSize(int, int)
		 */
		public Dimension getPreferredSize(int wHint, int hHint) {
			if (this.getContents() == null)
				return new Dimension();
			Dimension pSize = this.getContents().getPreferredSize(wHint, hHint);
			if (horizontal) {
				RangeModel rModel = getHorizontalRangeModel();
				pSize.width = rModel.getMaximum() - rModel.getMinimum();
			} else {
				RangeModel rModel = getVerticalRangeModel();
				pSize.height = rModel.getMaximum() - rModel.getMinimum();
			}
			return pSize
					.expand(getInsets().getWidth(), getInsets().getHeight());
		}

		/**
		 * Since the RangeModel is shared with that of the editor's, a
		 * RulerViewport should not adjust it.
		 * 
		 * @see org.eclipse.draw2d.Viewport#readjustScrollBars()
		 */
		protected void readjustScrollBars() {
		}

		/**
		 * @see org.eclipse.draw2d.Figure#paintBorder(org.eclipse.draw2d.Graphics)
		 */
		protected void paintBorder(Graphics graphics) {
			super.paintBorder(graphics);
			if (this.getContents() != null
					&& ((FlowRulerFigure) this.getContents()).getDrawFocus()) {
				Rectangle focusBounds = getBounds().getCopy();
				int orientation = ((FlowRulerFigure) this.getContents()).getOrientation();
				if (orientation == PositionConstants.NORTH ||
						orientation == PositionConstants.SOUTH) {
					focusBounds.resize(-2, -4);
					focusBounds.x++;
				} else {
					focusBounds.resize(-4, -2);
					focusBounds.y++;
				}
				graphics.setForegroundColor(ColorConstants.black);
				graphics.setBackgroundColor(ColorConstants.white);
				graphics.drawFocus(focusBounds);
			}
		}

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent event) {
			if (this.getContents() != null
					&& event.getSource() instanceof RangeModel) {
				String property = event.getPropertyName();
				doLayout(RangeModel.PROPERTY_MAXIMUM.equals(property)
						|| RangeModel.PROPERTY_MINIMUM.equals(property)
						|| RangeModel.PROPERTY_VALUE.equals(property));
			}
		}

		/**
		 * @see org.eclipse.draw2d.Viewport#setContents(org.eclipse.draw2d.IFigure)
		 */
		public void setContents(IFigure figure) {
			super.setContents(figure);
			// Need to layout when contents change
			if (this.getContents() != null)
				doLayout(true);
		}

		/**
		 * RulerViewport uses local coordinates.
		 * 
		 * @see org.eclipse.draw2d.Figure#useLocalCoordinates()
		 */
		protected boolean useLocalCoordinates() {
			return true;
		}
	}

}
