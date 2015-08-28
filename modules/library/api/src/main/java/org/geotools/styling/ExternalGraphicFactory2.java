package org.geotools.styling;

import org.opengis.feature.Feature;
import org.opengis.filter.expression.Expression;

import javax.swing.*;

/**
 * Turns the specified byte array into an Icon.<br>
 * The <code>size</code> parameter defines the size of the image (so that
 * vector based symbols can be drawn at the specified size directly), or may
 * be zero or negative if the size was not specified (in that case the "natural" size of
 * the image will be used, which is the size in pixels for raster images, and
 * 16 for any format that does not have a specific size, according to the SLD spec).<br>
 * <code>null</code> will be returned if this factory cannot handle the
 * provided byte array.
 */
public interface ExternalGraphicFactory2 {
    public Icon getIcon(byte[] byteContent, String format, int size) throws Exception;
}
