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

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.RubyRailsWidget;

public class DPLWidget extends AbstractRubyTemplate implements RubyRailsWidget {

	public String getId() {
		return "dpl";
	}

	public String getTitle() {
		return "Technical Debt / Line of Code";
	}

	@Override
	protected String getTemplatePath() {
		return "/com/gistlabs/sonar/plugins/dplplugin/dplWidget.erb";
	}
}
