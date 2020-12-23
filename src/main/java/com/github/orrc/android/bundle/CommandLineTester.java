package com.github.orrc.android.bundle;

import java.io.File;
import java.io.IOException;

class CommandLineTester {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println(String.format("Usage: %s <path-to-aab>", CommandLineTester.class.getSimpleName()));
            System.exit(1);
        }

        final String filename = args[0].trim();
        BundleParser bundleParser = new AndroidBundleMetadataParser(new File(filename));

        String appId = bundleParser.getApplicationId();
        System.out.println("Application ID:   " + appId);

        long versionCode = bundleParser.getVersionCode();
        System.out.println("Version code:     " + versionCode);

        String versionName = bundleParser.getVersionName();
        System.out.println("Version name:     " + versionName);

        String minSdkVersion = bundleParser.getMinSdkVersion();
        System.out.println("minSdkVersion:    " + minSdkVersion);

        String targetSdkVersion = bundleParser.getTargetSdkVersion();
        System.out.println("targetSdkVersion: " + targetSdkVersion);
    }

}
