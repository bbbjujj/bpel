/*******************************************************************************
 * Copyright (c) 2006 University College London Software Systems Engineering
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contribution by:
 *     University College London Software Systems Engineering
 *******************************************************************************/
package org.eclipse.bpel.runtimes.publishers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jst.server.generic.core.internal.GenericPublisher;
import org.eclipse.wst.server.core.IModuleArtifact;

/**
 * An abstract base class offering some utility methods facilitating the process
 * of deploying a BPEL process onto a runtime and indicating a publisher of
 * BPEL processes. This extends the generic server
 * framework. Implementers of the <code>genericpublishers</code> extension point
 * wishing to publish BPEL processes onto some runtime, may find it convenient
 * to sub-class this class.   
 * <p>
 * The minimum requirement on sub-classes is to provide an implementation of
 * <code>GenericPublisher{@link #publish(IModuleArtifact[], IProgressMonitor)}</code>, 
 * <code>GenericPublisher{@link #unpublish(IProgressMonitor)}</code> and of 
 * <code></code>
 *  
 *
 * @author Bruno Wassermann, written Jun 8, 2006
 */
public abstract class GenericBPELPublisher extends GenericPublisher {

// TODO add utility methods that may be useful to concrete implementations

	/**
	 * Displays engine-specific validation warnings and errors in the BPEL
	 * Designer.
	 * <p>
	 * Sub-classes can call this method to provide information
	 * about any problems detected during deployment.
	 */
	protected final void submitValidationMarkers() {
		// TODO decide on signature/arguments
	}
	
	/**
	 * Provides access to the relevant project in the workspace in order to
	 * store a local copy of a generated deployment archive
	 * 
	 * @param deployArchive
	 * @param path should probably be relative from project root and if null
	 * will save archive in project root
	 */
	protected void storeDeploymentArchive(IFile deployArchive, IPath path) {
		// TODO figure out whether this is really useful
		// TODO figure out the signature (should it be a file, what's the best
		// way to allow specifying an additional path?) - maybe look at some
		// of the methods in the WTP tutorial.
	}
	
	/*
	 * we don't have something like validate in here as we are not the provider
	 * of an extension point, but simply providing some utility methods for 
	 * sub-classes to use if they so wish. 
	 * 
	 * if we had provided our own extension point to do publishing, then 
	 * we would probably have specified a validate() method in the abstract base
	 * class so that our framework could call its implementation in extensions.
	 * 
	 * That's a rubbish argument. If it's just a utility method it should have
	 * static methods. Otherwise, allow it to be sub-classed and declare some
	 * methods as abstract to indicate to sub-classes what they need to implement.
	 * 
	 * What is true though is that we cannot call such methods automatically as
	 * we are not really offering an extension point.
	 * 
	 * TODO clarify explain this point about usage in the class comment! the methods
	 * will not be called automatically as we are not providing an extension 
	 * point as initially expected.
	 */
	
	
}
