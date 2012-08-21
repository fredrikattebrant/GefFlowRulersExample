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
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Transposer;
import org.eclipse.gef.editparts.ZoomListener;
import org.eclipse.gef.editparts.ZoomManager;

/**
 * @author Fredrik Attebrant
 * 
 */
public class FlowRulerFigure extends Figure {

	public Label labelFigure;
	public TeamLabelFigure teamLabelFigure;

	public int textMargin = 30; //FA: orig: = 3;

	protected Transposer transposer = new Transposer();
	private ZoomManager zoomManager;
	private boolean drawFocus = false;
	
	/*
	 * This is an artificial border. When asked for the preferred size, the
	 * figure adds this width to its preferred width. The border is painted in
	 * the paintFigure(Graphics) method.
	 */
	private static final int BORDER_WIDTH = 3;


	private int orientation;
	private double dpu = -1.0;

	private ZoomListener zoomListener = new ZoomListener() {
		public void zoomChanged(double newZoomValue) {
			handleZoomChanged();
		}
	};

	protected void handleZoomChanged() {
		dpu = -1.0;
		repaint();
		layout();
	}

	protected double getDPU() {
		if (dpu <= 0) {
			dpu = 1.0;
			if (zoomListener != null) {
				dpu = dpu * zoomManager.getZoom();
			}
		}
		return dpu;
	}

	public FlowRulerFigure(int orientation) {
		boolean isHorizontal;
		String baseLabelText;
		switch (orientation) {
		case PositionConstants.NORTH:
		case PositionConstants.SOUTH:
			isHorizontal = true;
			baseLabelText = "Team";
			break;
		default:
			isHorizontal = false;
			baseLabelText = "Release";
			break;
		}
		ToolbarLayout layout = new ToolbarLayout(isHorizontal);
		setLayoutManager(layout);
		

		this.orientation = orientation;
		setBackgroundColor(ColorConstants.lightBlue); //ColorConstants.listBackground);
		setForegroundColor(ColorConstants.listForeground);
		setOpaque(true);
		//setLayoutManager(new FlowRulerLayout());
		
		for (int i = 0; i < 10; i++) {
			teamLabelFigure = new TeamLabelFigure();
			add(teamLabelFigure);
			teamLabelFigure.setText(baseLabelText + i);
			setConstraint(teamLabelFigure, new Integer(10));
		}
//		
//		for (int i = 0; i < 10; i++) {
//			labelFigure = new Label();
//			add(labelFigure);
//			labelFigure.setFont(new Font(null, "teamlabelfont", 9, SWT.BOLD));
//			labelFigure.setText(baseLabelText + i);
//			labelFigure.setPreferredSize(10, 5);
//			setConstraint(labelFigure, new Integer(10));
//		}
	}

	public int getOrientation() {
		return orientation;
	}

	public void setDrawFocus(boolean drawFocus) {
		if (this.drawFocus != drawFocus) {
			this.drawFocus = drawFocus;
			repaint();
		}
	}

	@Override
	public Dimension getPreferredSize(int wHint, int hHint) {
		Dimension prefSize = new Dimension();
		switch (getOrientation()) {
		case PositionConstants.NORTH:
		case PositionConstants.SOUTH:
			prefSize.height = (textMargin * 2) + BORDER_WIDTH + FigureUtilities.getFontMetrics(getFont()).getAscent();
			break;
		case PositionConstants.WEST:
			prefSize.width = (textMargin * 2) + BORDER_WIDTH + FigureUtilities.getFontMetrics(getFont()).getAscent();
		default:
			break;
		}
		return prefSize;
	}

	/**
	 * @return
	 */
	public boolean getDrawFocus() {
		return drawFocus;
	}

	public void setZoomManager(ZoomManager manager) {
		if (zoomManager != manager) {
			if (zoomManager != null) {
				zoomManager.removeZoomListener(zoomListener);
			}
			zoomManager = manager;
			if (zoomManager != null) {
				zoomManager.addZoomListener(zoomListener);
			}
		}
	}
	
	@Override
	public void invalidate() {
		super.invalidate();
		dpu = -1.0;
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		double dotsPerUnit = getDPU();
		Rectangle clip = transposer.t(graphics.getClip(Rectangle.SINGLETON));
		Rectangle figClientArea = transposer.t(getClientArea());
		
		Rectangle clippedBounds = clip;
		clippedBounds.x = figClientArea.x;
		clippedBounds.width = figClientArea.width - BORDER_WIDTH;
		
		// Paint the background
		if (isOpaque()) {
			graphics.fillRectangle(transposer.t(clippedBounds));
		}
		
		// massive amount of code for marks -- leaving out
		// TODO: Figure out what to do here instead!
		System.out.println("FlowRulerFigure.paintFigure() - marker stuff left out..");
		
		clippedBounds.expand(BORDER_WIDTH, 0);
		System.out.println("Clipped bound: " + clippedBounds.x + ", " + clippedBounds.y);
		graphics.setForegroundColor(ColorConstants.buttonDarker);
		graphics.drawLine(
				transposer.t(clippedBounds.getTopRight().translate(-1, -1)),
				transposer.t(clippedBounds.getBottomRight().translate(-1, -1)));
	}
	
	/*
	 * 
	 * 
	 * SPACE
	 * 
	 * 
	 * 
	 * 
	 */
	
}
