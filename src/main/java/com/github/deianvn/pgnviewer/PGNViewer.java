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
package com.github.deianvn.pgnviewer;

import com.github.deianvn.pgnparse.PGNGame;
import com.github.deianvn.pgnparse.PGNParseException;
import com.github.deianvn.pgnviewer.core.GameManager;
import com.github.deianvn.pgnviewer.gui.BoardCanvas;
import com.github.deianvn.pgnviewer.gui.BoardController;
import com.github.deianvn.pgnviewer.gui.MoveSelectionListener;
import com.github.deianvn.pgnviewer.gui.MoveSelector;
import com.github.deianvn.pgnviewer.gui.PGNLibrary;
import com.github.deianvn.pgnviewer.gui.event.MoveSelectionEvent;
import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import com.github.deianvn.pgnviewer.core.EventGroupNotAvailableException;
import com.github.deianvn.pgnviewer.core.MalformedPositionException;

public class PGNViewer {

	public static void main(String[] args) {
		
		final Display display = new Display();
		final Shell shell = new Shell(display, SWT.TITLE | SWT.CLOSE | SWT.BORDER);
		shell.setText("PGNViewer");
		
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		shell.setLayout(layout);
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem fileMenu = new MenuItem(menu, SWT.CASCADE);
		fileMenu.setText("&File");
		Menu fileSubmenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenu.setMenu(fileSubmenu);
		
		MenuItem addFileMenuItem = new MenuItem(fileSubmenu, SWT.PUSH);
		addFileMenuItem.setText("&Add file\tCtrl+F");
		
		MenuItem addDirMenuItem = new MenuItem(fileSubmenu, SWT.PUSH);
		addDirMenuItem.setText("&Add folder\tCtrl+D");
		
		final BoardCanvas board = new BoardCanvas(shell, SWT.BORDER);
		GridData boardGridData = new GridData(450, 510);
		board.getCanvas().setLayoutData(boardGridData);
		
		shell.pack();
		shell.setVisible(true);
		
		final Shell movesControllerShell = new Shell(shell, SWT.TITLE | SWT.BORDER | SWT.CLOSE | SWT.RESIZE);
		movesControllerShell.setText("Moves");
		GridLayout movesControllerShellLayout = new GridLayout(1, false);
		movesControllerShellLayout.marginHeight = 0;
		movesControllerShellLayout.marginWidth = 0;
		movesControllerShell.setLayout(movesControllerShellLayout);
		
		final ScrolledComposite selectorSC = new ScrolledComposite(movesControllerShell, SWT.V_SCROLL | SWT.BORDER);
		selectorSC.setLayoutData(new GridData(GridData.FILL_BOTH));
		selectorSC.setExpandHorizontal(true);
		selectorSC.setExpandVertical(true);
		final Composite movesSelectorComposite = new Composite(selectorSC, SWT.NONE);
		selectorSC.setContent(movesSelectorComposite);
		
		GridLayout movesSelectorCompositeLayout = new GridLayout(1, false);
		movesSelectorCompositeLayout.marginHeight = 0;
		movesSelectorCompositeLayout.marginWidth = 0;
		movesSelectorComposite.setLayout(movesSelectorCompositeLayout);
		
		final MoveSelector selector = new MoveSelector(movesSelectorComposite);
		selector.getComposite().setLayoutData(new GridData(GridData.FILL_BOTH));
		
		BoardController controller = new BoardController(movesControllerShell);
		controller.getComposite().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		movesControllerShell.setSize(200, 400);
		movesControllerShell.setVisible(true);
		
		
		final Shell libraryShell = new Shell(shell, SWT.TITLE | SWT.BORDER | SWT.CLOSE | SWT.RESIZE);
		libraryShell.setText("Library");
		GridLayout libraryShellLayout = new GridLayout(1, false);
		libraryShellLayout.marginHeight = 0;
		libraryShellLayout.marginWidth = 0;
		libraryShell.setLayout(libraryShellLayout);
		
		final PGNLibrary library = new PGNLibrary(libraryShell, SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		library.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		
		libraryShell.setSize(300, 400);
		libraryShell.setVisible(true);
		
		addFileMenuItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				FileDialog fileDialog = new FileDialog(shell, SWT.SINGLE | SWT.OPEN);
				fileDialog.setFilterExtensions(new String[] { "*.pgn", "*.PGN", "*.*" });
				String path = fileDialog.open();
				
				if (path != null) {
					try {
						library.addFile(new File(path));
						library.updateTable();
					} catch (IOException | PGNParseException | NullPointerException e1) {
						e1.printStackTrace();
					}
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
		addDirMenuItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				DirectoryDialog dirDialog = new DirectoryDialog(shell, SWT.SINGLE | SWT.OPEN);
				String path = dirDialog.open();
				
				if (path != null) {
					try {
						library.addDir(new File(path), true);
						library.updateTable();
					} catch (IOException | PGNParseException | NullPointerException e1) {
						e1.printStackTrace();
					}
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
		library.getTable().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				int index = library.getTable().getSelectionIndex();
				
				try {
					PGNGame game = library.getGame(index);
					GameManager gameManager = new GameManager(game);
					board.setGameManager(gameManager);
					selector.setGameManager(gameManager);
					selectorSC.setMinSize(selector.getComposite().computeSize(SWT.DEFAULT, SWT.DEFAULT));
					board.redrawFully();
				} catch (IndexOutOfBoundsException ex) {
					ex.printStackTrace();
				} catch (EventGroupNotAvailableException ex) {
					ex.printStackTrace();
				} catch (MalformedPositionException ex) {
					ex.printStackTrace();
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
		board.getCanvas().addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {

			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (board.getGameManager() != null) {
					if (e.keyCode == SWT.ARROW_LEFT) {
						if (e.stateMask == SWT.ALT) {
							while (board.getGameManager().back());
							selector.start();
							board.redraw();
						} else {
							board.getGameManager().back();
							selector.prev();
							board.redraw();
						}
					} else if (e.keyCode == SWT.ARROW_RIGHT) {
						if (e.stateMask == SWT.ALT) {
							while (board.getGameManager().next());
							selector.end();
						} else {
							board.getGameManager().next();
							selector.next();
						}
						board.redraw();
					} else if (e.keyCode == 'f' || e.keyCode == 'F') { //Change to numeric values
						board.setFlip(!board.isFlip());
						board.redrawFully();
				}
				}
				
			}
		});
		
		selector.setSelectionListener(new MoveSelectionListener() {
			
			@Override
			public void selectionEvent(MoveSelectionEvent event) {
				if (board.getGameManager() != null) {
					if (board.getGameManager().getPosition() == event.index + 1) {
						return;
					}
					
					if (event.index < board.getGameManager().getPosition()) {
						while (event.index + 1 != board.getGameManager().getPosition() && board.getGameManager().back());
					} else {
						while (event.index + 1 != board.getGameManager().getPosition() && board.getGameManager().next());
					}
					
					board.redraw();
				}
			}
		});
		
		controller.setSetToStartHook(() -> {

			while (board.getGameManager().back());
			selector.start();
			board.redraw();

		});
		
		controller.setSetToEndHook(new Runnable() {
			
			@Override
			public void run() {
				
				if (board.getGameManager() != null) {
					while (board.getGameManager().next());
					selector.end();
					board.redraw();
				}
				
			}
		});
		
		controller.setSetNextHook(() -> {
			if (board.getGameManager() != null) {
				board.getGameManager().next();
				selector.next();
				board.redraw();
			}

		});
		
		controller.setSetBackHook(() -> {

			if (board.getGameManager() != null) {
				board.getGameManager().back();
				selector.prev();
				board.redraw();
			}

		});
		
		movesControllerShell.addListener(SWT.Close, e -> {

			e.doit = false;
			movesControllerShell.setVisible(false);

		});
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		display.dispose();
		

	}

}
