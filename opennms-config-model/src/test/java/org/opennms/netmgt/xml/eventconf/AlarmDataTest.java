/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2012 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2012 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.xml.eventconf;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;
import org.opennms.core.test.xml.XmlTestNoCastor;

public class AlarmDataTest extends XmlTestNoCastor<AlarmData> {

	public AlarmDataTest(final AlarmData sampleObject, final String sampleXml, final String schemaFile) {
		super(sampleObject, sampleXml, schemaFile);
	}

	@Parameters
	public static Collection<Object[]> data() throws ParseException {
		AlarmData alarmData0 = new AlarmData();
		alarmData0.setReductionKey("%uei%:%dpname%:%nodeid%");
		alarmData0.setAlarmType(3);
		AlarmData alarmData1 = new AlarmData();
		alarmData1.setReductionKey("%uei%:%dpname%:%nodeid%");
		alarmData1.setAlarmType(3);
		alarmData1.setAutoClean(true);
		alarmData1.setClearKey("uei.opennms.org/internal/importer/importFailed:%parm[importResource]%");
		return Arrays.asList(new Object[][] {
				{alarmData0,
				"<alarm-data reduction-key=\"%uei%:%dpname%:%nodeid%\" alarm-type=\"3\"/>",
				"target/classes/xsds/eventconf.xsd" },
				{alarmData1,
				"<alarm-data reduction-key=\"%uei%:%dpname%:%nodeid%\" alarm-type=\"3\" auto-clean=\"true\" clear-key=\"uei.opennms.org/internal/importer/importFailed:%parm[importResource]%\"/>",
				"target/classes/xsds/eventconf.xsd" } 
		});
	}

}
