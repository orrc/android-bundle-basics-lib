package com.github.orrc.android.bundle;

public interface BundleParser {
    /** Determines the application ID of an Android Bundle (.aab) file. */
    String getApplicationId();

    /** Determines the version code of an Android Bundle (.aab) file. */
    long getVersionCode();
}