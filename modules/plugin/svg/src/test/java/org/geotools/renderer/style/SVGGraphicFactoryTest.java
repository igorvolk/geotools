/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 * 
 *    (C) 2002-2008, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.renderer.style;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;

import junit.framework.TestCase;

import org.geotools.factory.CommonFactoryFinder;
import org.opengis.filter.FilterFactory;

/**
 * 
 *
 * @source $URL$
 */
public class SVGGraphicFactoryTest extends TestCase {

    private SVGGraphicFactory svg;
    private FilterFactory ff;

    @Override
    protected void setUp() throws Exception {
        svg = new SVGGraphicFactory();
        ff = CommonFactoryFinder.getFilterFactory(null);
    }
    
    public void testNull() throws Exception {
        assertNull(svg.getIcon(null, ff.literal("http://www.nowhere.com"), null, 20));
    }
    
    public void testInvalidPaths() throws Exception {
        assertNull(svg.getIcon(null, ff.literal("http://www.nowhere.com"), "image/svg+not!", 20));
        try {
            svg.getIcon(null, ff.literal("ThisIsNotAUrl"), "image/svg", 20);
            fail("Should have throw an exception, invalid url");
        } catch(IllegalArgumentException e) {
        }
    }
    
    public void testLocalURL() throws Exception {
        URL url = SVGGraphicFactory.class.getResource("gradient.svg");
        assertNotNull(url);
        // first call, non cached path
        Icon icon = svg.getIcon(null, ff.literal(url), "image/svg", 20);
        assertNotNull(icon);
        assertEquals(20, icon.getIconHeight());
        // check caching is working
        assertTrue(svg.glyphCache.containsKey(url));
        
        // second call, hopefully using the cached path
        icon = svg.getIcon(null, ff.literal(url), "image/svg", 20);
        assertNotNull(icon);
        assertEquals(20, icon.getIconHeight());
    }
    
    public void testNaturalSize() throws Exception {
        URL url = SVGGraphicFactory.class.getResource("gradient.svg");
        assertNotNull(url);
        // first call, non cached path
        Icon icon = svg.getIcon(null, ff.literal(url), "image/svg", -1);
        assertNotNull(icon);
        assertEquals(500, icon.getIconHeight());
    }
    
    public void testSizeWithPixels() throws Exception {
        URL url = SVGGraphicFactory.class.getResource("gradient-pixels.svg");
        assertNotNull(url);
        // first call, non cached path
        Icon icon = svg.getIcon(null, ff.literal(url), "image/svg", -1);
        assertNotNull(icon);
        assertEquals(500, icon.getIconHeight());
    }

    public void testByteArrayInput() throws Exception {
        String svgString = "<svg xmlns=\"http://www.w3.org/2000/svg\" \n" +
                "     xmlns:xlink=\"http://www.w3.org/1999/xlink\"\n" +
                "     width=\"500\" height=\"500\" viewBox=\"0 0 500 500\">\n" +
                "<defs>\n" +
                "   <linearGradient id=\"gradient1\" gradientUnits=\"userSpaceOnUse\" x1=\"100\" y1=\"10\" x2=\"300\" y2=\"10\">\n" +
                "      <stop offset=\"0\" stop-color=\"blue\"/>\n" +
                "      <stop offset=\"1\" stop-color=\"yellow\"/>\n" +
                "   </linearGradient>\n" +
                "   <radialGradient id=\"gradient2\" cx=\"250\" cy=\"300\" r=\"200\" fx=\"400\" fy=\"200\" gradientUnits=\"userSpaceOnUse\">\n" +
                "      <stop offset=\"0\" stop-color=\"blue\"/>\n" +
                "      <stop offset=\"1\" stop-color=\"yellow\"/>\n" +
                "   </radialGradient>\n" +
                "</defs>\n" +
                "<rect x=\"10\" y=\"10\" width=\"480\" height=\"100\" fill=\"url(#gradient1)\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                "<rect x=\"10\" y=\"210\" width=\"480\" height=\"100\" fill=\"url(#gradient2)\" stroke=\"black\" stroke-width=\"2\"/>\n" +
                "</svg>";
        byte[] svgBytes = svgString.getBytes();
        Icon icon = svg.getIcon(svgBytes, "image/svg", -1);
        assertNotNull(icon);
        assertEquals(500, icon.getIconHeight());
    }

    File file(String name) {
        return new File("src/test/resources/org/geotools/renderer/lite/test-data/" + name
                + ".png");
    }

}
