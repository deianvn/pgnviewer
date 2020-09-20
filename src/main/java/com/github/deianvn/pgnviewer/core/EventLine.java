/*
 * This file is part of JPGNViewer.
 *
 * JPGNViewer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPGNViewer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JPGNViewer.  If not, see <http://www.gnu.org/licenses/>. 
 */
package com.github.deianvn.pgnviewer.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Deyan Rizov
 *
 */
public class EventLine {

	private final ArrayList<LinkedList<Event>> events;
	
	private int position = 0;
	
	public EventLine() {
		events = new ArrayList<>();
	}
	
	public void createEventGroup() {
		events.add(new LinkedList<>());
	}
	
	public void addEvent(Event event) throws EventGroupNotAvailableException {
		if (event == null) {
			throw new NullPointerException("Event cannot be null");
		}
		
		if (events.size() == 0) {
			throw new EventGroupNotAvailableException();
		}
		
		events.get(events.size() - 1).add(event);
	}
	
	public int getSize() {
		return events.size();
	}
	
	public boolean isEmpty() {
		return events.size() == 0;
	}
	
	public int getPosition() {
		return position;
	}
	
	public boolean processNext() {
		if (isEmpty() || position == getSize()) {
			return false;
		}

		for (Event event : events.get(position)) {
			try {
				event.processForeward();
			} catch (ProcessException e) {
				return false;
			}
		}
		
		return ++position < getSize();
	}
	
	public boolean processBack() {
		if (isEmpty() || position == 0) {
			return false;
		}
		
		position--;
		Iterator<Event> i = events.get(position).descendingIterator();
		
		while (i.hasNext()) {
			Event event = i.next();
			
			try {
				event.processBackwards();
			} catch (ProcessException e) {
				return false;
			}
		}
		
		return position > 0;
	}
	
}
