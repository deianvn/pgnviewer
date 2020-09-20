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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 *
 * @author Deyan Rizov
 *
 */
public class BoardController {

	private Composite composite;
	
	private Button setToStartButton;
	
	private Button setToEndButton;
	
	private Button setNextButton;
	
	private Button setBackButton;
	
	private Runnable setToStartHook;
	
	private Runnable setToEndHook;
	
	private Runnable setNextHook;
	
	private Runnable setBackHook;
	
	/**
	 * 
	 * @param parent
	 */
	public BoardController(Composite parent) {
		this(parent, SWT.NONE);
	}
	
	/**
	 * 
	 * @param parent
	 * @param style
	 */
	public BoardController(Composite parent, int style) {
		composite = new Composite(parent, style);
		createPartControl();
	}
	
	/**
	 * 
	 * @return
	 */
	public Composite getComposite() {
		return composite;
	}
	
	/**
	 * @return the setToStartHook
	 */
	public Runnable getSetToStartHook() {
		return setToStartHook;
	}

	/**
	 * @param setToStartHook the setToStartHook to set
	 */
	public void setSetToStartHook(Runnable setToStartHook) {
		this.setToStartHook = setToStartHook;
	}

	/**
	 * @return the setToEndHook
	 */
	public Runnable getSetToEndHook() {
		return setToEndHook;
	}

	/**
	 * @param setToEndHook the setToEndHook to set
	 */
	public void setSetToEndHook(Runnable setToEndHook) {
		this.setToEndHook = setToEndHook;
	}

	/**
	 * @return the setNextHook
	 */
	public Runnable getSetNextHook() {
		return setNextHook;
	}

	/**
	 * @param setNextHook the setNextHook to set
	 */
	public void setSetNextHook(Runnable setNextHook) {
		this.setNextHook = setNextHook;
	}

	/**
	 * @return the setBackHook
	 */
	public Runnable getSetBackHook() {
		return setBackHook;
	}

	/**
	 * @param setBackHook the setBackHook to set
	 */
	public void setSetBackHook(Runnable setBackHook) {
		this.setBackHook = setBackHook;
	}

	/**
	 * 
	 */
	private void createPartControl() {
		GridLayout layout = new GridLayout(4, true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 1;
		composite.setLayout(layout);
		
//		GridData leftGridData = new GridData();
//		leftGridData.horizontalAlignment = SWT.END;
//		leftGridData.grabExcessHorizontalSpace = true;
//		
//		GridData leftCenterGridData = new GridData();
//		leftGridData.horizontalAlignment = SWT.END;
//		
//		GridData rightGridData = new GridData();
//		rightGridData.horizontalAlignment = SWT.BEGINNING;
//		rightGridData.grabExcessHorizontalSpace = true;
//		
//		GridData rightCenterGridData = new GridData();
//		leftGridData.horizontalAlignment = SWT.BEGINNING;
		
		setToStartButton = new Button(composite, SWT.PUSH);
		setToStartButton.setText("<<");
		setToStartButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		setBackButton = new Button(composite, SWT.PUSH);
		setBackButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		setBackButton.setText("<");
		
		
		setNextButton = new Button(composite, SWT.PUSH);
		setNextButton.setText(">");
		setNextButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		setToEndButton = new Button(composite, SWT.PUSH);
		setToEndButton.setText(">>");
		setToEndButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		setToStartButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				if (setToStartHook != null) {
					setToStartHook.run();
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		setToEndButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				if (setToEndHook != null) {
					setToEndHook.run();
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		setNextButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				if (setNextHook != null) {
					setNextHook.run();
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		setBackButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				if (setBackHook != null) {
					setBackHook.run();
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
}
