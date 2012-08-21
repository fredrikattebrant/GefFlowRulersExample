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
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

/**
 * @author Fredrik Attebrant
 *
 */
public class TeamLabelFigure extends RoundedRectangle {
		private static final int TEAM_LABEL_FONT_SIZE = 9;
		
		Label label;
		
		public TeamLabelFigure(){
			setCornerDimensions(new Dimension(1,1));
			this.setLineWidth(1);
			setBackgroundColor(ColorConstants.darkGreen);
			setAntialias(SWT.ON);
			label = new Label();
			label.setFont(new Font(null, "teamlabelfont", TEAM_LABEL_FONT_SIZE, SWT.BOLD));
			
			setPreferredSize(50, 20);
			add(label);
		}
		
		public void setText(String name) {
			label.setText(name);
		}
		public Label getNameLabel(){
			return this.label;
		}
		@Override
		public void setBackgroundColor(Color c)
		{
			super.setBackgroundColor(c);
			super.setForegroundColor(ColorConstants.lightGray);
			//set a high contrast color for text and background
			try
			{
				label.setForegroundColor(ColorConstants.buttonLightest);
				label.setBackgroundColor(c);
				
			}
			catch(NullPointerException e)
			{
				
			}
		}
		
		public void setLabelBounds(Rectangle r){
			label.setBounds(r);
		}
		
		@Override
		public void fillShape(Graphics g)
		{
			Rectangle f = bounds;	
			Color fillColor = getBackgroundColor();

			g.setForegroundColor(ColorConstants.listForeground);
			g.setBackgroundColor(fillColor);

			g.fillGradient(f.x, f.y, f.width, f.height, true);
			g.setForegroundColor(this.getForegroundColor());
		}
		
}
