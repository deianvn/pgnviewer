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
package com.github.deianvn.pgnviewer.gui;

import com.github.deianvn.pgnparse.PGNGame;
import com.github.deianvn.pgnparse.PGNParseException;
import com.github.deianvn.pgnparse.PGNSource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class PGNLibrary {
	
	private Table table;
	
	private ArrayList<PGNGame> games = new ArrayList<>();
	
	public PGNLibrary(Composite parent) {
		this(parent, SWT.NONE);
	}
	
	public PGNLibrary(Composite parent, int style) {
		table = new Table(parent, style);
		TableColumn column = new TableColumn(table, SWT.NONE);
		column.setText("");
		column = new TableColumn(table, SWT.NONE);
		column.setText("White");
		column = new TableColumn(table, SWT.NONE);
		column.setText("Black");
		column = new TableColumn(table, SWT.NONE);
		column.setText("Date");
		column = new TableColumn(table, SWT.NONE);
		column.setText("Event");
		column = new TableColumn(table, SWT.NONE);
		column.setText("Result");
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		updateTable();
	}
	
	public Table getTable() {
		return table;
	}
	
	public void addSource(PGNSource source) throws PGNParseException {
		if (source != null) {

			for (PGNGame game : source.forceListGames()) {
				table.getDisplay().syncExec(() -> {

					TableItem item = new TableItem(table, SWT.NONE);
					String number = "" + (games.size() + 1);
					String white = "";
					String black = "";
					String date = "";
					String event = "";
					String result = "";

					if (game.containsTagKey("White")) {
						white = game.getTag("White");
					}

					if (game.containsTagKey("Black")) {
						black = game.getTag("Black");
					}

					if (game.containsTagKey("Date")) {
						date = game.getTag("Date");
					}

					if (game.containsTagKey("Event")) {
						event = game.getTag("Event");
					}

					if (game.containsTagKey("Result")) {
						result = game.getTag("Result");
					}

					item.setText(new String[]{number, white, black, date, event, result});

					games.add(game);

				});
			}
		}
	}
	
	public void addFile(File file) throws IOException, PGNParseException {
		addSource(new PGNSource(file));
	}
	
	public void addURL(URL url) throws IOException, PGNParseException {
		addSource(new PGNSource(url));
	}
	
	public void addDir(File file) throws IOException, PGNParseException {
		addDir(file, false);
	}
	
	public void addDir(File file, boolean rec) throws IOException, PGNParseException {
		if (file.isFile()) {
			if (file.getName().toLowerCase().endsWith(".pgn")) {
				addFile(file);
			}
		} else if (rec) {
			File[] files = file.listFiles();
			
			for (File f : files) {
				addDir(f, rec);
			}
		}
	}
	
	public PGNGame getGame(int index) {
		return games.get(index);
	}
	
	public void updateTable() {
		if (table != null) {
			TableColumn[] columns = table.getColumns();
			
			for (TableColumn column : columns) {
				column.pack();
			}
			
		}
	}
	
}
