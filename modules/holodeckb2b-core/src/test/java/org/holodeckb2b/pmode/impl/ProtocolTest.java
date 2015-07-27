/*
 * Copyright (C) 2015 The Holodeck B2B Team, Sander Fieten
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.holodeckb2b.pmode.impl;

import java.io.File;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author Sander Fieten <sander at holodeck-b2b.org>
 */
public class ProtocolTest {
    
    public ProtocolTest() {
    }    
    
    private Protocol createFromFile(String fName) {
    
        try {
            // retrieve the resource from the pmodetest directory.
            File f = new File(this.getClass().getClassLoader().getResource("pmodetest/prot/" + fName).getPath());
            
            Serializer  serializer = new Persister();
            return serializer.read(Protocol.class, f);
        }
        catch (Exception ex) {
            System.out.println("Exception '" + ex.getLocalizedMessage() + "'");
            return null;
        }
    }
    
    @Test
    public void testCompleteProtocol() {
        Protocol p = createFromFile("complete.xml");
        
        assertNotNull(p);
        
        assertEquals("http://www.oxygenxml.com/" ,p.getAddress());
        assertTrue(p.shouldAddActorOrRoleAttribute());
        assertEquals("1.1", p.getSOAPVersion());
        assertFalse(p.useChunking());
        assertFalse(p.useHTTPCompression());
    }

    @Test
    public void testAddressOnly() {
        Protocol p = createFromFile("addrOnly.xml");
        
        assertNotNull(p);
        
        assertEquals("http://www.oxygenxml.com/address" ,p.getAddress());
        
        assertFalse(p.shouldAddActorOrRoleAttribute());
        assertEquals("1.2", p.getSOAPVersion());
        assertTrue(p.useChunking());
        assertFalse(p.useHTTPCompression());
    }
    
    @Test
    public void testChunkingOnly() {
        Protocol p = createFromFile("chunkOnly.xml");
        
        assertNotNull(p);
        
        assertNull(p.getAddress());        
        assertFalse(p.shouldAddActorOrRoleAttribute());
        assertEquals("1.2", p.getSOAPVersion());
        assertFalse(p.useChunking());
        assertFalse(p.useHTTPCompression());    
    }
    
    @Test
    public void testSoapVersionOnly() {
        Protocol p = createFromFile("soapOnly.xml");
        
        assertNotNull(p);
        
        assertNull(p.getAddress());        
        assertFalse(p.shouldAddActorOrRoleAttribute());
        assertEquals("1.1", p.getSOAPVersion());
        assertTrue(p.useChunking());
        assertFalse(p.useHTTPCompression());    
    }
    
    @Test
    public void testHttpCompressionOnly() {
        Protocol p = createFromFile("httpCompressionOnly.xml");
        
        assertNotNull(p);
        
        assertNull(p.getAddress());        
        assertFalse(p.shouldAddActorOrRoleAttribute());
        assertEquals("1.2", p.getSOAPVersion());
        assertTrue(p.useChunking());
        assertTrue(p.useHTTPCompression());    
    }
    
    @Test
    public void testAddActorOnly() {
        Protocol p = createFromFile("multihopOnly.xml");
        
        // Because the multi-hop actor is only useful when sending there must also be an address
        assertNull(p);
    }
}