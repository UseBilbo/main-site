package com.usebilbo.util;

import static com.usebilbo.util.Utils.isEmpty;

public final class FileNameUtils {
    private static final char WIN_SEPARATOR_CHAR = '\\';
    private static final char UNIX_SEPARATOR_CHAR = '/';
    private static final char EXTENSION_SEPARATOR = '.';

    private FileNameUtils() {}

    public static String extension(String filename) {
        if (isEmpty(filename)) {
            return "";
        }
        int index = extensionIndex(filename);
        return index == -1 ? "" : filename.substring(index + 1);
    }

    public static String fileName(String path) {
        if (isEmpty(path)) {
            return "";
        }
        int lastSeparator = Math.max(path.lastIndexOf(WIN_SEPARATOR_CHAR), path.lastIndexOf(UNIX_SEPARATOR_CHAR));
        if (lastSeparator >= 0) {
            return path.substring(lastSeparator + 1);
        }
        return path;
    }

    private static int extensionIndex(String path) {
        int extensionPos = path.lastIndexOf(EXTENSION_SEPARATOR);
        int lastSeparator = Math.max(path.lastIndexOf(WIN_SEPARATOR_CHAR), path.lastIndexOf(UNIX_SEPARATOR_CHAR));
        return (lastSeparator > extensionPos ? -1 : extensionPos);
    }

    public static String sanitizeRoot(String root) {
        if (!isEmpty(root) && !root.endsWith("/")) {
            root = root + "/";
        }
        return root;
    }
}
