/*
 * Lilith - a log event viewer.
 * Copyright (C) 2007-2017 Joern Huxhorn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Copyright 2007-2017 Joern Huxhorn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.huxhorn.lilith.data.logging.xml.codec;

import de.huxhorn.lilith.data.logging.LoggingEvent;
import de.huxhorn.lilith.data.logging.test.LoggingEventIOTestBase;
import java.nio.charset.StandardCharsets;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class LoggingXmlEventIOTest
	extends LoggingEventIOTestBase
{
	private final Logger logger = LoggerFactory.getLogger(LoggingXmlEventIOTest.class);

	public LoggingXmlEventIOTest(Boolean logging)
	{
		super(logging);
	}

	@Test
	public void correctOutputFactoryIsObtained()
	{
		String factoryClassName = LoggingXmlEncoder.XML_OUTPUT_FACTORY.getClass().getName();
		assertTrue(factoryClassName, factoryClassName.startsWith("com.ctc.wstx.stax"));
	}

	@Test
	public void correctInputFactoryIsObtained()
	{
		String factoryClassName = LoggingXmlDecoder.XML_INPUT_FACTORY.getClass().getName();
		assertTrue(factoryClassName, factoryClassName.startsWith("com.ctc.wstx.stax"));
	}

	@Override
	protected void logUncompressedData(byte[] bytes)
	{
		if(logger.isDebugEnabled())
		{
			String data = bytes == null ? null : new String(bytes, StandardCharsets.UTF_8);
			logger.debug("Data: {}", data);
		}
	}

	@Override
	public byte[] write(LoggingEvent event, boolean compressing)
		throws XMLStreamException
	{
		LoggingXmlCodec ser = new LoggingXmlCodec(compressing);
		return ser.encode(event);
	}

	@Override
	public LoggingEvent read(byte[] bytes, boolean compressing)
		throws XMLStreamException
	{
		LoggingXmlCodec des = new LoggingXmlCodec(compressing);
		return des.decode(bytes);
	}
}
