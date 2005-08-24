package org.osjava.sj.loader.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class ReplaceTest extends TestCase {

    public ReplaceTest(String name) {
        super(name);
    }

    public void setUp() {
    }

    public void tearDown() {
    }

    public void testReplace() {
        assertEquals("one:two:three", Utils.replace("one--two--three", "--", ":" ) );
        assertEquals("one:two:", Utils.replace("one--two--", "--", ":" ) );
        assertEquals(":two:three", Utils.replace("--two--three", "--", ":" ) );
    }

}
