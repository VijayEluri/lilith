/*
 * Lilith - a log event viewer.
 * Copyright (C) 2007-2011 Joern Huxhorn
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
 * Copyright 2007-2011 Joern Huxhorn
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

package de.huxhorn.lilith.sender;

import java.io.DataOutputStream;
import java.io.IOException;

public class ZeroDelimitedWriteByteStrategy
	implements WriteByteStrategy
{
	/**
	 * Writes the byte array if it contains any data, followed by a zero-byte.
	 *
	 * @param dataOutputStream the stream the bytes will be written to.
	 * @param bytes            the bytes that are written
	 * @throws java.io.IOException if an exception is thrown while writing the bytes.
	 */
	@Override
	public void writeBytes(DataOutputStream dataOutputStream, byte[] bytes)
		throws IOException
	{
		if(bytes != null)
		{
			if(bytes.length > 0)
			{
				dataOutputStream.write(bytes);
			}
			dataOutputStream.write(0);
		}
	}
}
