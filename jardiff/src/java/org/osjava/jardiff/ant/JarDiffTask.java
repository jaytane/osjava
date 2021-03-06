/*
 * org.osjava.jardiff.ant.JarDiffTask
 *
 * $Id: IOThread.java 1952 2005-08-28 18:03:41Z cybertiger $
 * $URL: https://svn.osjava.org/svn/osjava/trunk/osjava-nio/src/java/org/osjava/nio/IOThread.java $
 * $Rev: 1952 $
 * $Date: 2005-08-28 18:03:41 +0000 (Sun, 28 Aug 2005) $
 * $Author: cybertiger $
 *
 * Copyright (c) 2005, Antony Riley
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * + Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * + Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * + Neither the name JarDiff nor the names of its contributors may
 *   be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.osjava.jardiff.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.HashSet;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.osjava.jardiff.JarDiff;
import org.osjava.jardiff.DiffException;
import org.osjava.jardiff.StreamDiffHandler;
import org.osjava.jardiff.SimpleDiffCriteria;

/**
 * Process two jarfiles generating a public API difference report.
 * This is useful for keeping track of API changes between versions of a 
 * project.
 *
 * @author <a href="mailto:antony@cyberiantiger.org">Antony Riley</a>
 */
public class JarDiffTask extends Task {

    /**
     * The jarfile this diff is from.
     */
    private File fromJar = null;

    /**
     * The jarfile this diff is to.
     */
    private File toJar = null;

    /**
     * The file to write the report to.
     */
    private File out = null;

    /**
     * The name to use for the from version.
     */
    private String fromName = null;

    /**
     * The name to use for the to verison.
     */
    private String toName = null;

    /**
     * Force output, even if the existing output is newer than
     * the jar files.
     */
    private boolean force = false;

    /**
     * Run the task, generating the jardiff report.
     *
     * @throws BuildException When there is an error creating the diff,
     *                        When there is a problem with the xml parser,
     *                        When there is a problem with the xslt transformer
     *                        When the attributes specified are invalid.
     */
    public void execute() throws BuildException {
        try {
            if(fromJar == null) {
                throw new BuildException("no fromjar file specified",
                        getLocation());
            }
            if(toJar == null) {
                throw new BuildException("no tojar file specified",
                        getLocation());
            }
            if(out == null) {
                throw new BuildException("no out file specified", 
                        getLocation());
            }
            if(fromName == null) {
                fromName = fromJar.getName();
            }
            if(toName == null) {
                toName = toJar.getName();
            }
            if(!fromJar.exists() || !fromJar.isFile() || !fromJar.canRead()) {
                String msg = "fromjar is not a file, or cannot be read";
                throw new BuildException(msg, getLocation());
            }
            if(!toJar.exists() || !toJar.isFile() || !toJar.canRead()) {
                String msg = "tojar is not a file, or cannot be read";
                throw new BuildException(msg, getLocation());
            }
            long outModified = out.lastModified();
            long oldModified = fromJar.lastModified();
            long newModified = toJar.lastModified();
            if(force || oldModified > outModified || newModified > outModified)
            {
                log("Writing xml api diff to "+out);
                JarDiff jd = new JarDiff();
                jd.setOldVersion(fromName);
                jd.setNewVersion(toName);
                jd.loadOldClasses(fromJar);
                jd.loadNewClasses(toJar);
                jd.diff(
                        new StreamDiffHandler(new FileOutputStream(out)),
                        new SimpleDiffCriteria()
                       );
            }
        } catch (DiffException de) {
            throw new BuildException(de);
        } catch (IOException ioe) {
            throw new BuildException(ioe);
        }
    }

    /**
     * Set the from jar file.
     * Required attribute.
     *
     * @param fromJar a jar file.
     */
    public void setFromjar(File fromJar) {
        this.fromJar = fromJar;
    }

    /**
     * Set the to jar file.
     * Required attribute.
     * @param toJar a jar file.
     */
    public void setTojar(File toJar) {
        this.toJar = toJar;
    }

    /**
     * Set the out file.
     * Required attribute.
     *
     * @param out an output file.
     */
    public void setOut(File out) {
        this.out = out;
    }

    /**
     * Set the from jar visible name.
     * Optional attribute.
     * Defaults to the filename of fromjar.
     *
     * @param fromName a visible name.
     */
    public void setFromname(String fromName) {
        this.fromName = fromName;
    }

    /**
     * Set the to jar visible name.
     * Optional attribute.
     * Defaults to the filename of tojar.
     *
     * @param toName a visible name.
     */
    public void setToname(String toName) {
        this.toName = toName;
    }

    /**
     * Force output even if there is an existing diff file which is
     * newer than the source jar files.
     * Optional attribute.
     * Defaults to false.
     *
     * @param force true to force output, false otherwise
     */
    public void setForce(boolean force) {
        this.force = force;
    }
}
