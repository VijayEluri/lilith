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
package de.huxhorn.lilith.swing.menu;

import de.huxhorn.lilith.data.eventsource.EventWrapper;
import de.huxhorn.lilith.data.logging.LoggingEvent;
import de.huxhorn.lilith.swing.ViewContainer;
import de.huxhorn.lilith.swing.actions.EventWrapperRelated;
import de.huxhorn.lilith.swing.actions.ExcludeCallLocationAction;
import de.huxhorn.lilith.swing.actions.ExcludeFormattedMessageAction;
import de.huxhorn.lilith.swing.actions.ExcludeMessagePatternAction;
import de.huxhorn.lilith.swing.actions.LoggingFilterBaseAction;
import de.huxhorn.lilith.swing.actions.ViewContainerRelated;

import javax.swing.*;

public class ExcludeMenu
	extends JMenu
	implements ViewContainerRelated, EventWrapperRelated
{
	private static final long serialVersionUID = -663125573199455498L;

	private EventWrapper eventWrapper;

	private ExcludeMessagePatternAction messagePatternAction;
	private ExcludeFormattedMessageAction formattedMessageAction;
	private ExcludeCallLocationAction callLocationAction;
	private JMenuItem messagePatternItem;
	private JMenuItem formattedMessageItem;
	private JMenuItem callLocationItem;
	private ExcludeMDCMenu mdcMenu;
	private ExcludeLoggerMenu loggerMenu;

	public ExcludeMenu() {
		super("Exclude...");
		createUI();
		setViewContainer(null);
		setEventWrapper(null);
	}

	private void createUI()
	{
		messagePatternAction = new ExcludeMessagePatternAction();
		formattedMessageAction=new ExcludeFormattedMessageAction();
		callLocationAction=new ExcludeCallLocationAction();
		messagePatternItem = new JMenuItem(messagePatternAction);
		formattedMessageItem = new JMenuItem(formattedMessageAction);
		callLocationItem = new JMenuItem(callLocationAction);
		mdcMenu = new ExcludeMDCMenu();
		loggerMenu = new ExcludeLoggerMenu();
	}

	public void setEventWrapper(EventWrapper eventWrapper)
	{
		this.eventWrapper = eventWrapper;
		messagePatternAction.setEventWrapper(eventWrapper);
		formattedMessageAction.setEventWrapper(eventWrapper);
		callLocationAction.setEventWrapper(eventWrapper);
		mdcMenu.setEventWrapper(eventWrapper);
		loggerMenu.setEventWrapper(eventWrapper);
		updateState();
	}

	public void setViewContainer(ViewContainer viewContainer)
	{
		messagePatternAction.setViewContainer(viewContainer);
		formattedMessageAction.setViewContainer(viewContainer);
		callLocationAction.setViewContainer(viewContainer);
		mdcMenu.setViewContainer(viewContainer);
		loggerMenu.setViewContainer(viewContainer);
		updateState();
	}

	private void updateState()
	{
		EventWrapper wrapper = this.eventWrapper;
		removeAll();

		LoggingEvent loggingEvent = LoggingFilterBaseAction.resolveLoggingEvent(wrapper);
		if(loggingEvent != null)
		{
			add(messagePatternItem);
			add(formattedMessageItem);
			addSeparator();
			add(callLocationItem);
			addSeparator();
			add(mdcMenu);
			addSeparator();
			add(loggerMenu);
			setEnabled(
					messagePatternItem.isEnabled() ||
					formattedMessageItem.isEnabled() ||
					callLocationItem.isEnabled() ||
					mdcMenu.isEnabled() ||
					loggerMenu.isEnabled()
				);
			return;
		}
		setEnabled(false);
	}
}
