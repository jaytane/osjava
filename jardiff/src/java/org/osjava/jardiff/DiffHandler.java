/*
 * org.osjava.jardiff.DiffHandler
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
package org.osjava.jardiff;

public interface DiffHandler
{
    public void startDiff(String string, String string_0_)
        throws DiffException;
    
    public void startRemoved() throws DiffException;
    
    public void classRemoved(ClassInfo classinfo) throws DiffException;
    
    public void endRemoved() throws DiffException;
    
    public void startAdded() throws DiffException;
    
    public void classAdded(ClassInfo classinfo) throws DiffException;
    
    public void endAdded() throws DiffException;
    
    public void startChanged() throws DiffException;
    
    public void startClassChanged(String string) throws DiffException;
    
    public void fieldRemoved(FieldInfo fieldinfo) throws DiffException;
    
    public void methodRemoved(MethodInfo methodinfo) throws DiffException;
    
    public void fieldAdded(FieldInfo fieldinfo) throws DiffException;
    
    public void methodAdded(MethodInfo methodinfo) throws DiffException;
    
    public void classChanged(ClassInfo classinfo, ClassInfo classinfo_1_)
        throws DiffException;
    
    public void fieldChanged(FieldInfo fieldinfo, FieldInfo fieldinfo_2_)
        throws DiffException;
    
    public void methodChanged
        (MethodInfo methodinfo, MethodInfo methodinfo_3_) throws DiffException;
    
    public void endClassChanged() throws DiffException;
    
    public void endChanged() throws DiffException;
    
    public void endDiff() throws DiffException;
}
