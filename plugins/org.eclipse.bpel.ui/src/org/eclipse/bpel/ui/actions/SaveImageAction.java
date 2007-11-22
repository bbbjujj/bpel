/*******************************************************************************
 * Copyright (c) 2007 Intel Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Oleg Danilov, Intel - Initial API and Implementation
 *
 *******************************************************************************/

package org.eclipse.bpel.ui.actions;

import java.io.File;

import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.BPELMultipageEditorPart;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class SaveImageAction extends Action implements IEditorActionDelegate {

	private BPELMultipageEditorPart fEditor;

	private String getFileName(IEditorPart editorPart) {
		String[] filterExtensions = new String[] { "*.png", "*.jpg", "*.bmp" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		FileDialog fileDialog = new FileDialog(editorPart.getEditorSite()
				.getShell(), SWT.SAVE);
		String fullName = editorPart.getEditorInput().getName();
		int pos = fullName.lastIndexOf('.');
		String name = (pos == -1) ? fullName : fullName.substring(0, pos);
		fileDialog.setFileName(name);
		fileDialog.setFilterExtensions(filterExtensions);
		fileDialog.setText(Messages.SaveImageAction_0);
		return fileDialog.open();
	}

	public void run(IAction action) {
		String fileName = getFileName(fEditor);
		if (fileName == null)
			return;

		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			if (!MessageDialog.openQuestion(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(),
					Messages.SaveImageAction_1, NLS.bind(
							Messages.SaveImageAction_2,
							(new Object[] { fileName })))) {
				return;
			}
		}

		try {
			saveBPELAsImage(fEditor, fileName);
		} catch (Exception ex) {
			MessageDialog.openError(fEditor.getEditorSite().getShell(),
					Messages.SaveImageAction_3, NLS.bind(
							Messages.SaveImageAction_4,
							(new Object[] { fileName })));
		}
	}

	private void saveBPELAsImage(IEditorPart editorPart, String fileName) {
		GraphicalViewer viewer = (GraphicalViewer) fEditor
				.getAdapter(GraphicalViewer.class);
		ScalableRootEditPart rootEditPart = (ScalableRootEditPart) viewer
				.getEditPartRegistry().get(LayerManager.ID);
		IFigure figure = ((LayerManager) rootEditPart)
				.getLayer(LayerConstants.PRINTABLE_LAYERS);
		Dimension size = figure.getSize();
		Image image = new Image(null, size.width, size.height);
		GC gc = new GC(image);
		Graphics graphics = new SWTGraphics(gc);
		figure.paint(graphics);

		ImageLoader imgLoader = new ImageLoader();
		imgLoader.data = new ImageData[1];
		imgLoader.data[0] = image.getImageData();

		int format = SWT.IMAGE_JPEG;
		if (fileName.endsWith(".bmp")) //$NON-NLS-1$
			format = SWT.IMAGE_BMP;
		else if (fileName.endsWith(".png")) //$NON-NLS-1$
			format = SWT.IMAGE_PNG;

		imgLoader.save(fileName, format);

		// dispose all
		gc.dispose();
		image.dispose();

	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (fEditor == null) {
			action.setEnabled(false);
		} else {
			action.setEnabled(fEditor.getActiveEditor() instanceof BPELEditor);
		}
	}

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (targetEditor instanceof BPELMultipageEditorPart) {
			fEditor = (BPELMultipageEditorPart) targetEditor;
		} else {
			fEditor = null;
		}
	}
}