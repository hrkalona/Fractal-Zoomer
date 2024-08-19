//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.jogamp.common.util;

import com.jogamp.common.net.Uri;
import com.jogamp.common.net.Uri.Encoded;
import com.jogamp.common.os.NativeLibrary;
import com.jogamp.common.os.Platform;
import com.jogamp.common.os.Platform.OSType;
import jogamp.common.Debug;

import java.io.*;
import java.net.*;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarUtil {
    private static final boolean DEBUG = Debug.debug("JarUtil");
    private static final int BUFFER_SIZE = 4096;
    private static Resolver resolver;

    public JarUtil() {
    }

    public static void setResolver(Resolver var0) throws IllegalArgumentException, IllegalStateException, SecurityException {
        if (var0 == null) {
            throw new IllegalArgumentException("Null Resolver passed");
        } else if (resolver != null) {
            throw new IllegalStateException("Resolver already set!");
        } else {
            SecurityManager var1 = System.getSecurityManager();
            if (var1 != null) {
                var1.checkSetFactory();
            }

            resolver = var0;
        }
    }

    public static boolean hasJarUri(String var0, ClassLoader var1) {
        try {
            return null != getJarUri(var0, var1);
        } catch (Exception var3) {
            return false;
        }
    }

    public static Uri getJarUri(String var0, ClassLoader var1) throws IllegalArgumentException, IOException, URISyntaxException {
        if (null != var0 && null != var1) {
            URL var3 = IOUtil.getClassURL(var0, var1);
            String var4 = var3.getProtocol();
            Uri var2;
            if (null != resolver && !var4.equals("jar") && !var4.equals("file") && !var4.equals("http") && !var4.equals("https")) {
                URL var5 = resolver.resolve(var3);
                var2 = Uri.valueOf(var5);
                if (DEBUG) {
                    System.err.println("getJarUri Resolver: " + var3 + "\n\t-> " + var5 + "\n\t-> " + var2);
                }
            } else {
                var2 = Uri.valueOf(var3);
                if (DEBUG) {
                    System.err.println("getJarUri Default " + var3 + "\n\t-> " + var2);
                }
            }

            if (!var2.isJarScheme()) {
                throw new IllegalArgumentException("Uri is not using scheme jar: <" + var2 + ">");
            } else {
                if (DEBUG) {
                    System.err.println("getJarUri res: " + var0 + " -> " + var3 + " -> " + var2);
                }

                return var2;
            }
        } else {
            throw new IllegalArgumentException("null arguments: clazzBinName " + var0 + ", cl " + var1);
        }
    }

    public static Uri.Encoded getJarBasename(Uri var0) throws IllegalArgumentException {
        if (null == var0) {
            throw new IllegalArgumentException("Uri is null");
        } else if (!var0.isJarScheme()) {
            throw new IllegalArgumentException("Uri is not using scheme jar: <" + var0 + ">");
        } else {
            Uri.Encoded var1 = var0.schemeSpecificPart;
            int var2 = var1.lastIndexOf(33);
            if (0 <= var2) {
                var1 = var1.substring(0, var2);
                var2 = var1.lastIndexOf(47);
                if (0 > var2) {
                    var2 = var1.lastIndexOf(58);
                    if (0 > var2) {
                        throw new IllegalArgumentException("Uri does not contain protocol terminator ':', in <" + var0 + ">");
                    }
                }

                var1 = var1.substring(var2 + 1);
                if (DEBUG) {
                    System.err.println("getJarName res: " + var1);
                }
                return var1;
            } else {
                throw new IllegalArgumentException("Uri does not contain jar uri terminator '!', in <" + var0 + ">");
            }
        }
    }

    public static Uri.Encoded getJarBasename(String var0, ClassLoader var1) throws IllegalArgumentException, IOException, URISyntaxException {
        return getJarBasename(getJarUri(var0, var1));
    }

    public static Uri.Encoded getJarEntry(Uri var0) {
        if (null == var0) {
            throw new IllegalArgumentException("Uri is null");
        } else if (!var0.isJarScheme()) {
            throw new IllegalArgumentException("Uri is not a using scheme jar: <" + var0 + ">");
        } else {
            Uri.Encoded var1 = var0.schemeSpecificPart;
            int var2 = var1.lastIndexOf(33);
            if (0 <= var2) {
                Uri.Encoded var3 = var1.substring(var2 + 1);
                if (DEBUG) {
                    System.err.println("getJarEntry res: " + var0 + " -> " + var1 + " -> " + var2 + " -> " + var3);
                }

                return var3;
            } else {
                throw new IllegalArgumentException("JAR Uri does not contain jar uri terminator '!', uri <" + var0 + ">");
            }
        }
    }

    public static Uri getJarFileUri(String var0, ClassLoader var1) throws IllegalArgumentException, IOException, URISyntaxException {
        if (null != var0 && null != var1) {
            Uri var2 = getJarUri(var0, var1).getContainedUri();
            Uri var3 = Uri.cast("jar:" + var2.toString() + "!/");
            if (DEBUG) {
                System.err.println("getJarFileUri res: " + var3);
            }

            return var3;
        } else {
            throw new IllegalArgumentException("null arguments: clazzBinName " + var0 + ", cl " + var1);
        }
    }

    public static Uri getJarFileUri(Uri var0, Uri.Encoded var1) throws IllegalArgumentException, URISyntaxException {
        if (null != var0 && null != var1) {
            return Uri.cast("jar:" + var0.toString() + var1 + "!/");
        } else {
            throw new IllegalArgumentException("null arguments: baseUri " + var0 + ", jarFileName " + var1);
        }
    }

    public static Uri getJarFileUri(Uri var0) throws IllegalArgumentException, URISyntaxException {
        if (null == var0) {
            throw new IllegalArgumentException("jarSubUri is null");
        } else {
            return Uri.cast("jar:" + var0.toString() + "!/");
        }
    }

    public static Uri getJarFileUri(Uri.Encoded var0) throws IllegalArgumentException, URISyntaxException {
        if (null == var0) {
            throw new IllegalArgumentException("jarSubUriS is null");
        } else {
            return Uri.cast("jar:" + var0 + "!/");
        }
    }

    public static Uri getJarEntryUri(Uri var0, Uri.Encoded var1) throws IllegalArgumentException, URISyntaxException {
        if (null == var1) {
            throw new IllegalArgumentException("jarEntry is null");
        } else {
            return Uri.cast(var0.toString() + var1);
        }
    }

    public static JarFile getJarFile(String var0, ClassLoader var1) throws IOException, IllegalArgumentException, URISyntaxException {
        return getJarFile(getJarFileUri(var0, var1));
    }

    public static JarFile getJarFile(Uri var0) throws IOException, IllegalArgumentException, URISyntaxException {
        if (null == var0) {
            throw new IllegalArgumentException("null jarFileUri");
        } else {
            if (DEBUG) {
                System.err.println("getJarFile.0: " + var0.toString());
            }

            URL var1 = var0.toURL();
            if (DEBUG) {
                System.err.println("getJarFile.1: " + var1.toString());
            }

            URLConnection var2 = var1.openConnection();
            if (var2 instanceof JarURLConnection) {
                JarURLConnection var3 = (JarURLConnection)var1.openConnection();
                JarFile var4 = var3.getJarFile();
                if (DEBUG) {
                    System.err.println("getJarFile res: " + var4.getName());
                }

                return var4;
            } else {
                if (DEBUG) {
                    System.err.println("getJarFile res: NULL");
                }

                return null;
            }
        }
    }

    /** @deprecated */
    public static URI getRelativeOf(Class<?> var0, String var1, String var2) throws IllegalArgumentException, IOException, URISyntaxException {
        return getRelativeOf(var0, Encoded.cast(var1), Encoded.cast(var2)).toURI();
    }

    public static Uri getRelativeOf(Class<?> var0, Uri.Encoded var1, Uri.Encoded var2) throws IllegalArgumentException, IOException, URISyntaxException {
        ClassLoader var3 = var0.getClassLoader();
        Uri var4 = getJarUri(var0.getName(), var3);
        if (DEBUG) {
            System.err.println("JarUtil.getRelativeOf: (classFromJavaJar " + var0 + ", classJarUri " + var4 + ", cutOffInclSubDir " + var1 + ", relResPath " + var2 + "): ");
        }

        Uri var5 = var4.getContainedUri();
        if (null == var5) {
            throw new IllegalArgumentException("JarSubUri is null of: " + var4);
        } else {
            Uri.Encoded var6 = var5.getDirectory().getEncoded();
            if (DEBUG) {
                System.err.println("JarUtil.getRelativeOf: uri " + var5.toString() + " -> " + var6);
            }

            Uri.Encoded var7;
            if (var6.endsWith(var1.get())) {
                var7 = var6.concat(var2);
            } else {
                var7 = var6.concat(var1).concat(var2);
            }

            if (DEBUG) {
                System.err.println("JarUtil.getRelativeOf: ...  -> " + var7);
            }

            Uri var8 = getJarFileUri(var7);
            if (DEBUG) {
                System.err.println("JarUtil.getRelativeOf: fin " + var8);
            }

            return var8;
        }
    }

    public static Map<String, String> getNativeLibNames(JarFile var0) {
        if (DEBUG) {
            System.err.println("JarUtil: getNativeLibNames: " + var0);
        }

        HashMap var1 = new HashMap();
        Enumeration var2 = var0.entries();

        while(var2.hasMoreElements()) {
            JarEntry var3 = (JarEntry)var2.nextElement();
            String var4 = var3.getName();
            String var5 = NativeLibrary.isValidNativeLibraryName(var4, false);
            if (null != var5) {
                var1.put(var5, var4);
            }
        }

        return var1;
    }

    public static final int extract(File var0, Map<String, String> var1, JarFile var2, String var3, boolean var4, boolean var5, boolean var6) throws IOException {
        if (DEBUG) {
            System.err.println("JarUtil: extract: " + var2.getName() + " -> " + var0 + ", extractNativeLibraries " + var4 + " (" + var3 + ")" + ", extractClassFiles " + var5 + ", extractOtherFiles " + var6);
        }

        int var7 = 0;
        Enumeration var8 = var2.entries();

        while(true) {
            while(var8.hasMoreElements()) {
                JarEntry var9 = (JarEntry)var8.nextElement();
                String var10 = var9.getName();
                String var11 = NativeLibrary.isValidNativeLibraryName(var10, false);
                boolean var12 = null != var11;
                if (var12) {
                    if (!var4) {
                        if (DEBUG) {
                            System.err.println("JarUtil: JarEntry : " + var10 + " native-lib skipped, skip all native libs");
                        }
                        continue;
                    }

                    if (null != var3) {
                        String var13;
                        String var14;
                        try {
                            var13 = IOUtil.slashify(var3, false, true);
                            var14 = IOUtil.getDirname(var10);
                        } catch (URISyntaxException var25) {
                            throw new IOException(var25);
                        }

                        if (!var13.equals(var14)) {
                            if (DEBUG) {
                                System.err.println("JarUtil: JarEntry : " + var10 + " native-lib skipped, not in path: " + var13);
                            }
                            continue;
                        }
                    }
                }

                boolean var26 = var10.endsWith(".class");
                if (var26 && !var5) {
                    if (DEBUG) {
                        System.err.println("JarUtil: JarEntry : " + var10 + " class-file skipped");
                    }
                } else if (!var12 && !var26 && !var6) {
                    if (DEBUG) {
                        System.err.println("JarUtil: JarEntry : " + var10 + " other-file skipped");
                    }
                } else {
                    boolean var27 = var10.endsWith("/");
                    boolean var15 = var10.indexOf(47) == -1 && var10.indexOf(File.separatorChar) == -1;
                    if (DEBUG) {
                        System.err.println("JarUtil: JarEntry : isNativeLib " + var12 + ", isClassFile " + var26 + ", isDir " + var27 + ", isRootEntry " + var15);
                    }

                    File var16 = new File(var0, var10);
                    if (var27) {
                        if (DEBUG) {
                            System.err.println("JarUtil: MKDIR: " + var10 + " -> " + var16);
                        }

                        var16.mkdirs();
                    } else {
                        File var17 = new File(var16.getParent());
                        if (!var17.exists()) {
                            if (DEBUG) {
                                System.err.println("JarUtil: MKDIR (parent): " + var10 + " -> " + var17);
                            }

                            var17.mkdirs();
                        }

                        BufferedInputStream var18 = new BufferedInputStream(var2.getInputStream(var9));
                        BufferedOutputStream var19 = new BufferedOutputStream(new FileOutputStream(var16));
                        boolean var20 = true;

                        int var28;
                        try {
                            var28 = IOUtil.copyStream2Stream(4096, var18, var19, -1);
                        } finally {
                            var18.close();
                            var19.close();
                        }

                        boolean var21 = false;
                        if (var28 > 0) {
                            ++var7;
                            if (var12 && (var15 || !var1.containsKey(var11))) {
                                var1.put(var11, var16.getAbsolutePath());
                                var21 = true;
                                fixNativeLibAttribs(var16);
                            }
                        }

                        if (DEBUG) {
                            System.err.println("JarUtil: EXTRACT[" + var7 + "]: [" + var11 + " -> ] " + var10 + " -> " + var16 + ": " + var28 + " bytes, addedAsNativeLib: " + var21);
                        }
                    }
                }
            }

            return var7;
        }
    }

    private static final void fixNativeLibAttribs(File var0) {
        if (OSType.MACOS == Platform.getOSType()) {
            String var1 = var0.getAbsolutePath();

            try {
                fixNativeLibAttribs(var1);
                if (DEBUG) {
                    System.err.println("JarUtil.fixNativeLibAttribs: " + var1 + " - OK");
                }
            } catch (Throwable var3) {
                if (DEBUG) {
                    System.err.println("JarUtil.fixNativeLibAttribs: " + var1 + " - " + var3.getClass().getSimpleName() + ": " + var3.getMessage());
                }
            }
        }

    }

    private static native boolean fixNativeLibAttribs(String var0);

    public static final void validateCertificates(Certificate[] var0, JarFile var1) throws IOException, SecurityException {
        if (DEBUG) {
            System.err.println("JarUtil: validateCertificates: " + var1.getName());
        }

        if (var0 != null && var0.length != 0) {
            byte[] var2 = new byte[1024];
            Enumeration var3 = var1.entries();

            while(var3.hasMoreElements()) {
                JarEntry var4 = (JarEntry)var3.nextElement();
                if (!var4.isDirectory() && !var4.getName().startsWith("META-INF/")) {
                    validateCertificate(var0, var1, var4, var2);
                }
            }

        } else {
            throw new IllegalArgumentException("Null certificates passed");
        }
    }

    private static final void validateCertificate(Certificate[] var0, JarFile var1, JarEntry var2, byte[] var3) throws IOException, SecurityException {
        if (DEBUG) {
            System.err.println("JarUtil: validate JarEntry : " + var2.getName());
        }

        InputStream var4 = var1.getInputStream(var2);

        try {
            while(true) {
                if (var4.read(var3) > 0) {
                    continue;
                }
            }
        } finally {
            var4.close();
        }
    }

    public interface Resolver {
        URL resolve(URL var1);
    }
}
