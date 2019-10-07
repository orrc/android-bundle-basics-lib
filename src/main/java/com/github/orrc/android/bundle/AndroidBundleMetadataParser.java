package com.github.orrc.android.bundle;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNode;
import com.android.tools.build.bundletool.xml.XPathResolver;
import com.android.tools.build.bundletool.xml.XmlNamespaceContext;
import com.android.tools.build.bundletool.xml.XmlProtoToXmlConverter;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Based on bundletool:
 * https://github.com/google/bundletool/blob/0.10.3/src/main/java/com/android/tools/build/bundletool/commands/DumpManager.java
 * <p>
 * Originally adapted by Joe Hansche:
 * https://github.com/jhansche
 */
public class AndroidBundleMetadataParser implements BundleParser {

    private final XPath xPath = XPathFactory.newInstance().newXPath();

    private final Document manifestXml;

    public AndroidBundleMetadataParser(File bundleFile) throws IOException {
        XmlProtoNode manifestProto = parseBundleManifest(bundleFile);
        xPath.setNamespaceContext(new XmlNamespaceContext(manifestProto));
        manifestXml = XmlProtoToXmlConverter.convert(manifestProto);
    }

    @Override
    public String getApplicationId() {
        return getXPathValue(manifestXml, xPath, "/manifest/@package");
    }

    @Override
    public long getVersionCode() {
        String result = getXPathValue(manifestXml, xPath, "/manifest/@android:versionCode");
        return Long.parseLong(result);
    }

    private XmlProtoNode parseBundleManifest(File bundleFile) throws IOException {
        // We want to to inspect the manifest of the base module
        final String manifestPath = "base/manifest/AndroidManifest.xml";

        // Extract the file from the bundle
        ZipFile zipFile = new ZipFile(bundleFile);
        ZipEntry manifestFile = zipFile.getEntry(manifestPath);
        if (manifestFile == null) {
            throw new IOException(String.format("File '%s' not found.", manifestPath));
        }

        // Parse the protobuf-encoded file
        try (InputStream is = zipFile.getInputStream(manifestFile)) {
            return new XmlProtoNode(Resources.XmlNode.parseFrom(is));
        }
    }

    private static String getXPathValue(Document document, XPath xPath, String xPathExpression) {
        try {
            XPathExpression compiledXPathExpression = xPath.compile(xPathExpression);
            XPathResolver.XPathResult xPathResult = XPathResolver.resolve(document, compiledXPathExpression);
            return xPathResult.toString();
        } catch (XPathExpressionException exc) {
            // Shouldn't happen
            throw new RuntimeException(exc);
        }
    }

}
