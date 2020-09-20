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
import com.github.deianvn.pgnparse.PGNMove;
import com.github.deianvn.pgnviewer.gui.event.MoveSelectionEvent;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.github.deianvn.pgnviewer.core.GameManager;

/**
 *
 * @author Deyan Rizov
 *
 */
public class MoveSelector {
	
	private Composite composite;
	
	private GameManager gameManager;
	
	private Button selectedButton;
	
	private MoveSelectionListener selectionListener;
	
	private ArrayList<Button> moves = new ArrayList<Button>();
	
	/**
	 * 
	 * @param parent
	 */
	public MoveSelector(Composite parent) {
		this(parent, SWT.NONE);
	}
	
	/**
	 * 
	 * @param parent
	 * @param style
	 */
	public MoveSelector(Composite parent, int style) {
		composite = new Composite(parent, style);
		GridLayout layout = new GridLayout(2, true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		composite.setLayout(layout);
	}
	
	/**
	 * 
	 * @return
	 */
	public Composite getComposite() {
		return composite;
	}

	/**
	 * @return the gameManager
	 */
	public GameManager getGameManager() {
		return gameManager;
	}

	/**
	 * @param gameManager the gameManager to set
	 */
	public void setGameManager(GameManager gameManager) {
		this.gameManager = gameManager;
		selectedButton = null;
		
		final SelectionListener listener = new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Button b = (Button)e.widget;
				
				if (selectedButton != null) {
					selectedButton.setSelection(false);
				}
				
				b.setSelection(true);
				selectedButton = b;
				
				if (selectionListener != null) {
					selectionListener.selectionEvent(new MoveSelectionEvent(moves.indexOf(b)));
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		};
		
		deleteControls();
		
		PGNGame game = gameManager.getGame();
		Iterator<PGNMove> i = game.getMovesIterator();
		
		while (i.hasNext()) {
			PGNMove move = i.next();
			
			if (move.isEndGameMarked()) {
				continue;
			}
			
			Button m = new Button(composite, SWT.TOGGLE);
			m.setBackground(composite.getBackground());
			m.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			m.setText(move.getFullMove());
			m.addSelectionListener(listener);
			moves.add(m);
			
		}
		
		composite.layout();
		
	}
	
	/**
	 * @return the selectionListener
	 */
	public MoveSelectionListener getSelectionListener() {
		return selectionListener;
	}

	/**
	 * @param selectionListener the selectionListener to set
	 */
	public void setSelectionListener(MoveSelectionListener selectionListener) {
		this.selectionListener = selectionListener;
	}

	/**
	 * 
	 */
	public void next() {
		if (moves.size() <= 0) {
			return;
		}
		
		if (selectedButton == null) {
			selectedButton = moves.get(0);
			selectedButton.setSelection(true);
		} else {
			int index = moves.indexOf(selectedButton) + 1;
			
			if (index > 0 && index < moves.size()) {
				selectedButton.setSelection(false);
				selectedButton = moves.get(index);
				selectedButton.setSelection(true);
			}
		}
	}
	
	/**
	 * 
	 */
	public void prev() {
		if (moves.size() <= 0) {
			return;
		}
		
		if (selectedButton != null) {
			int index = moves.indexOf(selectedButton) - 1;
			
			if (index >= 0 && index < moves.size() - 1) {
				selectedButton.setSelection(false);
				selectedButton = moves.get(index);
				selectedButton.setSelection(true);
			} else if (index == -1) {
				selectedButton.setSelection(false);
				selectedButton = null;
			}
		}
	}
	
	/**
	 * 
	 */
	public void start() {
		if (selectedButton != null) {
			selectedButton.setSelection(false);
			selectedButton = null;
		}
	}
	
	/**
	 * 
	 */
	public void end() {
		if (moves.size() <= 0) {
			return;
		}
		
		if (selectedButton != null) {
			selectedButton.setSelection(false);
		}
		
		selectedButton = moves.get(moves.size() - 1);
		selectedButton.setSelection(true);
	}
	
	/**
	 * 
	 */
	private void deleteControls() {
		moves.clear();
		Control[] controls =  composite.getChildren();
		
		for (Control control : controls) {
			control.dispose();
		}
		
	}

}
