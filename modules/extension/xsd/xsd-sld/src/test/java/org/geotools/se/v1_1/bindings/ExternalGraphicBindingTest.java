/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2010, Open Source Geospatial Foundation (OSGeo)
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
package org.geotools.se.v1_1.bindings;


import org.geotools.se.v1_1.SE;
import org.geotools.se.v1_1.SETestSupport;
import org.geotools.styling.ExternalGraphic;
import org.geotools.xlink.XLINK;
import org.w3c.dom.Element;

/**
 * @author Sebastian Graca, ISPiK S.A.
 */
public class ExternalGraphicBindingTest extends SETestSupport {
    public void testOnlineResource() throws Exception {
        document.appendChild(document.createElementNS(SE.NAMESPACE, "ExternalGraphic"));

        Element r = document.createElementNS(SE.NAMESPACE, "OnlineResource");
        r.setAttributeNS(XLINK.NAMESPACE, "href", SETestSupport.class.getResource("inlineContent-image.png").toString());

        Element f = document.createElementNS(SE.NAMESPACE, "Format");
        f.appendChild(document.createTextNode("image/png"));

        document.getDocumentElement().appendChild(r);
        document.getDocumentElement().appendChild(f);

        ExternalGraphic externalGraphic = (ExternalGraphic) parse();
        assertNotNull(externalGraphic);

        assertEquals(SETestSupport.class.getResource("inlineContent-image.png"), externalGraphic.getLocation());
        assertEquals("image/png", externalGraphic.getFormat());
        assertNull(externalGraphic.getInlineContent());
    }

    public void testInlineContent() throws Exception {
        document.appendChild(document.createElementNS(SE.NAMESPACE, "ExternalGraphic"));

        Element c = document.createElementNS(SE.NAMESPACE, "InlineContent");
        c.setAttributeNS(SE.NAMESPACE, "encoding", "base64");
        c.appendChild(document.createTextNode("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAIAAACQkWg2AAAAK3RFWHRDcmVhdGlvbiBUaW1lAFd0IDE0IHdyeiAyMDEwIDEyOjA2OjAyICswMTAweoAlkgAAAAd0SU1FB9oJDgo6HdmGt90AAAAJcEhZcwAACxIAAAsSAdLdfvwAAAAEZ0FNQQAAsY8L/GEFAAABfklEQVR42mP8//8/AwYACn779o2bmxtTiokBG9iwboOfq9+1K9eI0vDp06cZk2e8ffp2zuQ5mPZj0bB963aGLwxTI6YdO3QMiyX/UcHz58/tLeyXZ674O+NfgVthWnTaz58/kRWg27B8yXKu31yeGp6MjIwpVimnjp86fvg4Tic9ffp0zfI1ZU7lfBx8QK6WpFasSdzMiTN//fqFRQPQusULFsvzyFsqWMIFw4zDHt5+uH/Xfiwarl27tnj+4nSzdDYWNrigtIB0iF5IZ1MnMOhQNACNnztjrq64roWiJQMqCDUO+/D6w/YN21E0XLp06fD+w3l2+cjGwy1Jt82YO3Pe+/fvoRqAfupq6bKVs7VQsMAa8REmEf8+/V04fSFUw/Hjxy+cvZBklgwMSqwagIGWYpm6cOEiYDCyAI2fPnG6mrD6n39/Lj25xIADAB325/vvVQtXsXz58uX7z+8vfr7I2ZrNgBew87HfvnebERg+X79+/ffvHwMRgJWVFQBa4Mt756r78AAAAABJRU5ErkJggg=="));

        Element f = document.createElementNS(SE.NAMESPACE, "Format");
        f.appendChild(document.createTextNode("image/png"));

        document.getDocumentElement().appendChild(c);
        document.getDocumentElement().appendChild(f);

        ExternalGraphic externalGraphic = (ExternalGraphic) parse();
        assertNotNull(externalGraphic);

        assertEquals("image/png", externalGraphic.getFormat());
        assertImagesEqual(getReferenceImage("inlineContent-image.png"), externalGraphic.getInlineContent());
        assertNull(externalGraphic.getLocation());
    }

    public void testInlineContentWithINvalidData() throws Exception {
        document.appendChild(document.createElementNS(SE.NAMESPACE, "ExternalGraphic"));

        Element c = document.createElementNS(SE.NAMESPACE, "InlineContent");
        c.setAttributeNS(SE.NAMESPACE, "encoding", "base64");
        c.appendChild(document.createTextNode("PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4NCg=="));

        Element f = document.createElementNS(SE.NAMESPACE, "Format");
        f.appendChild(document.createTextNode("image/png"));

        document.getDocumentElement().appendChild(c);
        document.getDocumentElement().appendChild(f);

        ExternalGraphic externalGraphic = (ExternalGraphic) parse();
        assertNotNull(externalGraphic);

        assertEquals("image/png", externalGraphic.getFormat());
        assertNotNull(externalGraphic.getInlineContent());
        assertEquals(1, externalGraphic.getInlineContent().getIconWidth());
        assertEquals(1, externalGraphic.getInlineContent().getIconHeight());
        assertNull(externalGraphic.getLocation());
    }

//    public void testInlineSVGContent() throws Exception {
//        document.appendChild(document.createElementNS(SE.NAMESPACE, "ExternalGraphic"));
//
//        Element c = document.createElementNS(SE.NAMESPACE, "InlineContent");
//        c.setAttributeNS(SE.NAMESPACE, "encoding", "xml");
//        c.appendChild(document.createTextNode("<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
//                "<!-- Created with Inkscape (http://www.inkscape.org/) -->\n" +
//                "<svg xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:cc=\"http://creativecommons.org/ns#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:sodipodi=\"http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd\" xmlns:inkscape=\"http://www.inkscape.org/namespaces/inkscape\" id=\"svg3323\" sodipodi:version=\"0.32\" inkscape:version=\"0.46+pre4\" width=\"134.24727\" height=\"105.05726\" version=\"1.0\" sodipodi:docbase=\"C:\\Important\\svg\" sodipodi:docname=\"home.svg\" inkscape:output_extension=\"org.inkscape.output.svg.inkscape\">\n" +
//                "  <metadata id=\"metadata3328\">\n" +
//                "    <rdf:RDF>\n" +
//                "      <cc:Work rdf:about=\"\">\n" +
//                "        <dc:format>image/svg+xml</dc:format>\n" +
//                "        <dc:type rdf:resource=\"http://purl.org/dc/dcmitype/StillImage\"/>\n" +
//                "      </cc:Work>\n" +
//                "    </rdf:RDF>\n" +
//                "  </metadata>\n" +
//                "  <defs id=\"defs3326\">\n" +
//                "    <inkscape:perspective sodipodi:type=\"inkscape:persp3d\" inkscape:vp_x=\"0 : 72.240166 : 1\" inkscape:vp_y=\"0 : 1000 : 0\" inkscape:vp_z=\"134.24727 : 72.240166 : 1\" inkscape:persp3d-origin=\"67.123634 : 48.16011 : 1\" id=\"perspective6291\"/>\n" +
//                "  </defs>\n" +
//                "  <sodipodi:namedview inkscape:window-height=\"938\" inkscape:window-width=\"1280\" inkscape:pageshadow=\"2\" inkscape:pageopacity=\"0.0\" guidetolerance=\"10.0\" gridtolerance=\"10.0\" objecttolerance=\"10.0\" borderopacity=\"1.0\" bordercolor=\"#666666\" pagecolor=\"#ffffff\" id=\"base\" inkscape:zoom=\"0.8125\" inkscape:cx=\"438.61548\" inkscape:cy=\"-63.718918\" inkscape:window-x=\"-4\" inkscape:window-y=\"-4\" inkscape:current-layer=\"svg3323\" showgrid=\"false\"/>\n" +
//                "  <path style=\"fill: rgb(0, 0, 0); fill-opacity: 1; fill-rule: evenodd; stroke: rgb(0, 0, 0); stroke-width: 4; stroke-linecap: round; stroke-linejoin: round; stroke-miterlimit: 4; stroke-opacity: 1;\" d=\"M 67.125 2 L 2 46.125 L 132.25 46.125 L 104.65625 27.4375 L 104.65625 3.96875 L 86.1875 3.96875 L 86.1875 14.90625 L 67.125 2 z \" id=\"path11341\"/>\n" +
//                "  <path style=\"opacity: 1; fill: rgb(0, 0, 0); fill-opacity: 1; fill-rule: evenodd; stroke: rgb(0, 0, 0); stroke-width: 4.08939; stroke-linecap: round; stroke-linejoin: round; stroke-miterlimit: 4; stroke-dasharray: none; stroke-opacity: 1;\" d=\"M 21 52.03125 L 21 103 L 112.625 103 L 112.625 52.03125 L 21 52.03125 z M 35.125 63.6875 L 56.65625 63.6875 L 56.65625 85.21875 L 35.125 85.21875 L 35.125 63.6875 z M 73.28125 70.125 L 95.4375 70.125 L 95.4375 102.75 L 73.28125 102.75 L 73.28125 70.125 z \" id=\"rect11344\"/>\n" +
//                "  <path style=\"fill: rgb(0, 0, 0); fill-opacity: 1; fill-rule: evenodd; stroke: none; stroke-width: 4; stroke-linecap: butt; stroke-linejoin: miter; stroke-opacity: 1;\" d=\"M 43.75 63.96875 L 43.75 72.4375 L 34.5 72.4375 L 34.5 76.4375 L 43.75 76.4375 L 43.75 85.53125 L 47.75 85.53125 L 47.75 76.4375 L 56.34375 76.4375 L 56.34375 72.4375 L 47.75 72.4375 L 47.75 63.96875 L 43.75 63.96875 z \" id=\"path11352\"/>\n" +
//                "</svg>]]>"));
//
//        Element f = document.createElementNS(SE.NAMESPACE, "Format");
//        f.appendChild(document.createTextNode("image/svg"));
//
//        document.getDocumentElement().appendChild(c);
//        document.getDocumentElement().appendChild(f);
//
//        ExternalGraphic externalGraphic = (ExternalGraphic) parse();
//        assertNotNull(externalGraphic);
//
//        assertEquals("image/png", externalGraphic.getFormat());
//        assertImagesEqual(getReferenceImage("inlineContent-svg.png"), externalGraphic.getInlineContent());
//        assertNull(externalGraphic.getLocation());
//    }

}
