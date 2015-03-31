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

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import java.util.Arrays;
import java.util.List;

public class DPLMetrics implements Metrics {

	/**
	 * The primary Metric, Dollars of tech debt per LoC
	 */
	public static final Metric DPL = new Metric.Builder("dpl", "Debt/LoC",
			Metric.ValueType.FLOAT).setBestValue(0.0)
			.setDirection(Metric.DIRECTION_WORST)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).setQualitative(true)
			.create();

	/**
	 * Auxilliary Metric, for use in radiator/heat map. Ranges from $3 to $8 by percent of DPL
	 */
	public static final Metric DPL_Color = new Metric.Builder("dplc",
			"Debt/LoC Color", Metric.ValueType.PERCENT).setBestValue(0.0)
			.setWorstValue(100.0).setDirection(Metric.DIRECTION_WORST)
			.setDomain(CoreMetrics.DOMAIN_GENERAL).setQualitative(true)
			.create();

	/**
	 * {@inheritDoc}
	 */
	public List<Metric> getMetrics() {
		return Arrays.asList(DPL, DPL_Color);
	}
}
