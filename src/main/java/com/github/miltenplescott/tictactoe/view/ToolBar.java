/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    BSD-3-Clause
 */
package com.github.miltenplescott.tictactoe.view;

import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author Milten Plescott
 */
public class ToolBar extends JToolBar {

	private Frame frame;
	private JSlider slider;
//	private final int sliderDefaultMin = 252;
//	private final int sliderDefaultMax = frame.getScreenShorterDimensionDivisibleBy9();
	//private final int sliderMin = 252;
	//private final int sliderMax = Frame.getInstance().getScreenShorterDimensionDivisibleBy9();

	public ToolBar(Frame frame) {
		super("toolbar", JToolBar.HORIZONTAL);
		this.frame = frame;
		setFloatable(false);
		add(new JLabel("Window size: "));

		int sliderDefaultMin = 252;
		int sliderDefaultMax = frame.getScreenShorterDimensionDivisibleBy9();

		slider = new JSlider(JSlider.HORIZONTAL, sliderDefaultMin, sliderDefaultMax, 400);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setPaintTrack(true);
		slider.setMinorTickSpacing(9);
		slider.setEnabled(false);

		@SuppressWarnings("UseOfObsoleteCollectionType")
		Hashtable sliderLabels = new Hashtable();
		sliderLabels.put(slider.getMinimum(), new JLabel("MIN"));
		sliderLabels.put(slider.getMaximum(), new JLabel("MAX"));

		slider.setPaintLabels(true);
		slider.setLabelTable(sliderLabels);

		add(slider);
	}

	// minor spacing is 9 --> (MAX - MIN) % 9 should equal 0
	// the above is always true, if both MAX and MIN are divisible by 9 without reminder
	public void setSliderRange(int min, int max, int current) {
		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setValue(current);

		@SuppressWarnings("UseOfObsoleteCollectionType")
		Hashtable sliderLabels = new Hashtable();
		sliderLabels.put(slider.getMinimum(), new JLabel("MIN"));
		sliderLabels.put(slider.getMaximum(), new JLabel("MAX"));

		slider.setPaintLabels(true);
		slider.setLabelTable(sliderLabels);
	}

	public void addSliderListener() {
		slider.addChangeListener((ChangeEvent evnt) -> {
			if (!slider.getValueIsAdjusting()) {
				frame.changeFrameSize(slider.getValue());
			}
		});
		slider.setEnabled(true);
	}

	public JSlider getSlider() {
		return slider;
	}

}
