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

import java.util.Arrays;
import java.util.List;

import org.sonar.api.Extension;
import org.sonar.api.Plugin;

public class DPLPlugin implements Plugin {

	/**
	 * {@inheritDoc}
	 */
	public String getDescription() {
		return "Display Tech Debt/Lines of Code";
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Class<? extends Extension>> getExtensions() {
		return Arrays.asList(DPLMetrics.class, DPLDecorator.class, DPLWidget.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getKey() {
		return "dpl";
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return "Tech Debt/Lines of Code";
	}

}
