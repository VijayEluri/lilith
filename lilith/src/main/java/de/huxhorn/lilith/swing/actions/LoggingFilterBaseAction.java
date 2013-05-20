/*
 * Lilith - a log event viewer.
 * Copyright (C) 2007-2013 Joern Huxhorn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.huxhorn.lilith.swing.actions;

import de.huxhorn.lilith.data.eventsource.EventWrapper;
import de.huxhorn.lilith.data.logging.LoggingEvent;

import javax.swing.*;
import java.io.Serializable;

public abstract class LoggingFilterBaseAction
	extends FilterBaseAction
	implements EventWrapperRelated
{
	private static final long serialVersionUID = -2521087800102653740L;

	protected LoggingEvent loggingEvent;

	protected LoggingFilterBaseAction()
	{
	}

	protected LoggingFilterBaseAction(String name)
	{
		super(name);
	}

	protected LoggingFilterBaseAction(String name, Icon icon)
	{
		super(name, icon);
	}

	@Override
	public void setEventWrapper(EventWrapper eventWrapper)
	{
		setLoggingEvent(resolveLoggingEvent(eventWrapper));
	}


	public static LoggingEvent resolveLoggingEvent(EventWrapper eventWrapper)
	{
		if(eventWrapper == null)
		{
			return null;
		}
		Serializable event = eventWrapper.getEvent();
		if(event == null)
		{
			return null;
		}
		if(event instanceof LoggingEvent)
		{
			return (LoggingEvent) event;
		}
		return null;
	}

	public void setLoggingEvent(LoggingEvent loggingEvent)
	{
		this.loggingEvent = loggingEvent;
		updateState();
	}
}
