/*
 * Lilith - a log event viewer.
 * Copyright (C) 2007-2018 Joern Huxhorn
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
 * Copyright 2007-2018 Joern Huxhorn
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

package de.huxhorn.lilith.logback.appender.access;

import ch.qos.logback.access.spi.AccessEvent;
import de.huxhorn.lilith.data.access.logback.TransformingEncoder;
import de.huxhorn.lilith.data.access.protobuf.AccessEventProtobufEncoder;
import de.huxhorn.lilith.logback.appender.core.MultiplexSocketAppenderBase;

public class AccessMultiplexSocketAppender
	extends MultiplexSocketAppenderBase<AccessEvent>
{
	/**
	 * The default port number of compressed new-style remote logging server (10010).
	 */
	public static final int COMPRESSED_DEFAULT_PORT = 10_010;

	/**
	 * The default port number of uncompressed new-style remote logging server (10011).
	 */
	public static final int UNCOMPRESSED_DEFAULT_PORT = 10_011;

	private boolean usingDefaultPort;
	private TransformingEncoder transformingEncoder;

	public AccessMultiplexSocketAppender()
	{
		this(true);
	}

	public AccessMultiplexSocketAppender(boolean compressing)
	{
		super();
		usingDefaultPort = true;
		transformingEncoder = new TransformingEncoder();
		setEncoder(transformingEncoder);
		setCompressing(compressing);
	}

	@Override
	protected void applicationIdentifierChanged()
	{
		transformingEncoder.setApplicationIdentifier(getApplicationIdentifier());
	}

	@Override
	protected void uuidChanged()
	{
		transformingEncoder.setUUID(getUUID());
	}

	@Override
	public void setPort(int port)
	{
		super.setPort(port);
		usingDefaultPort = false;
	}

	/**
	 * GZIPs the event if set to true.
	 *
	 * Automatically chooses the correct default port if it was not previously set manually.
	 *
	 * @param compressing if events will be gzipped or not.
	 */
	@SuppressWarnings("WeakerAccess")
	public void setCompressing(boolean compressing)
	{
		if(usingDefaultPort)
		{
			if(compressing)
			{
				setPort(COMPRESSED_DEFAULT_PORT);
			}
			else
			{
				setPort(UNCOMPRESSED_DEFAULT_PORT);
			}
			usingDefaultPort = true;
		}
		transformingEncoder.setLilithEncoder(new AccessEventProtobufEncoder(compressing));
	}

	@Override
	protected void preProcess(AccessEvent e)
	{
		if(e != null)
		{
			e.prepareForDeferredProcessing();
		}
	}
}
