/*
 * Copyright (C) 2011 Gist Labs, LLC.
 *
 * http://gistlabs.com/software/sonar-dpl-plugin
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.gistlabs.sonar.plugins.dplplugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;

import com.google.common.collect.Lists;

public class DPLDecorator implements Decorator {

	private List<Metric> techDebtMetricList = new ArrayList<Metric>();

	/**
	 * {@inheritDoc}
	 */
	public DPLDecorator(Metric[] metrics) {
		for (Metric metric : metrics) {
			if ("technical_debt".equals(metric.getKey())) {
				techDebtMetricList.add(metric);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Metric> dependsOn() {
		return Arrays.asList(CoreMetrics.LINES);
	}

	/**
	 * {@inheritDoc}
	 */
	@DependsUpon
	public List<Metric> dependsOnMetrics() {
		List<Metric> list = Lists.newLinkedList();
		list.add(CoreMetrics.NCLOC);
		list.addAll(techDebtMetricList);
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@DependedUpon
	public List<Metric> generatesMetrics() {
		return Arrays.asList(DPLMetrics.DPL, DPLMetrics.DPL_Color);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean shouldExecuteOnProject(Project project) {
		return true;
	}

	/**
	 * Divide Technical Debt $ by number of lines of code (NCLOC)
	 * 
	 * {@inheritDoc}
	 */
	public void decorate(Resource resource, DecoratorContext context) {
		Metric techDebtMetric = lookupTechDebtMetric();
		if (techDebtMetric==null)
			return;
		Measure techDebtMeasure = context.getMeasure(techDebtMetric);
		Double techDebt = MeasureUtils.getValue(techDebtMeasure, 0.0);
		
		Measure linesOfCodeMeasure = context.getMeasure(CoreMetrics.NCLOC);
		if (linesOfCodeMeasure==null)
			return;
		
		Double loc = MeasureUtils.getValue(linesOfCodeMeasure, 0.0);
		if (loc.equals(0.0))
			return;

		Double dpl = techDebt / loc;
		final Measure measure = new Measure(DPLMetrics.DPL, dpl);
		context.saveMeasure(measure);
		
		Double dpl_color = calculateColor(dpl);
		final Measure measure_color = new Measure(DPLMetrics.DPL_Color, dpl_color);
		context.saveMeasure(measure_color);

	}

	/**
	 * Calculate a percentage of DPL based on the region of $3 to $8
	 * 
	 * @param dpl
	 * @return
	 */
	private Double calculateColor(Double dpl) {
		Double max = 8.0;
		Double min = 3.0;
		
		Double dpl2;
		dpl2 = Math.min(dpl, max);
		dpl2 = Math.max(dpl, min);
		
		return ((dpl2-min)/(max-min))*100;
	}

	private Metric lookupTechDebtMetric() {
		if (techDebtMetricList.size() > 0) {
			return techDebtMetricList.get(0);
		} else {
			return null;
		}

	}

}
