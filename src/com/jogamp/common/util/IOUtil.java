//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.jogamp.common.util;

import com.jogamp.common.ExceptionUtils;
import com.jogamp.common.net.AssetURLContext;
import com.jogamp.common.net.Uri;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.os.Platform;
import com.jogamp.common.os.Platform.CPUFamily;
import com.jogamp.common.os.Platform.CPUType;
import com.jogamp.common.os.Platform.OSType;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilePermission;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.SyncFailedException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.regex.Pattern;
import jogamp.common.Debug;
import jogamp.common.os.AndroidUtils;
import jogamp.common.os.PlatformPropsImpl;

public class IOUtil {
    public static final boolean DEBUG;
    private static final boolean DEBUG_EXE;
    private static final boolean DEBUG_EXE_NOSTREAM;
    private static final boolean DEBUG_EXE_EXISTING_FILE;
    private static final String java_io_tmpdir_propkey = "java.io.tmpdir";
    private static final String user_home_propkey = "user.home";
    private static final String XDG_CACHE_HOME_envkey = "XDG_CACHE_HOME";
    public static final String tmpSubDir = "jogamp";
    private static final Pattern patternSingleBS;
    public static final Pattern patternSpaceEnc;
    private static final Object exeTestLock;
    private static WeakReference<byte[]> exeTestCodeRef;
    private static File tempRootExec;
    private static File tempRootNoexec;
    private static volatile boolean tempRootSet;

    private IOUtil() {
    }

    private static final Constructor<?> getFOSCtor() {
        Constructor var0;
        Throwable var1;
        try {
            var0 = ReflectionUtil.getConstructor("java.io.FileOutputStream", new Class[]{File.class}, true, IOUtil.class.getClassLoader());
            var1 = null;
        } catch (Throwable var3) {
            var0 = null;
            var1 = var3;
        }

        if (DEBUG) {
            System.err.println("IOUtil: java.io.FileOutputStream available: " + (null != var0));
            if (null != var1) {
                var1.printStackTrace();
            }
        }

        return var0;
    }

    public static int copyURLConn2File(URLConnection var0, File var1) throws IOException {
        var0.connect();
        int var2 = 0;
        BufferedInputStream var3 = new BufferedInputStream(var0.getInputStream());

        try {
            var2 = copyStream2File(var3, var1, var0.getContentLength());
        } finally {
            ((InputStream)var3).close();
        }

        return var2;
    }

    public static int copyStream2File(InputStream var0, File var1, int var2) throws IOException {
        BufferedOutputStream var3 = new BufferedOutputStream(new FileOutputStream(var1));

        try {
            var2 = copyStream2Stream(var0, var3, var2);
        } finally {
            ((OutputStream)var3).close();
        }

        return var2;
    }

    public static int copyStream2Stream(InputStream var0, OutputStream var1, int var2) throws IOException {
        return copyStream2Stream(Platform.getMachineDataInfo().pageSizeInBytes(), var0, var1, var2);
    }

    public static int copyStream2Stream(int var0, InputStream var1, OutputStream var2, int var3) throws IOException {
        byte[] var4 = new byte[var0];

        int var5;
        int var6;
        for(var5 = 0; (var6 = var1.read(var4)) != -1; var5 += var6) {
            var2.write(var4, 0, var6);
        }

        return var5;
    }

    public static StringBuilder appendCharStream(StringBuilder var0, Reader var1) throws IOException {
        char[] var2 = new char[1024];

        int var3;
        while(0 < (var3 = var1.read(var2))) {
            var0.append(var2, 0, var3);
        }

        return var0;
    }

    public static byte[] copyStream2ByteArray(InputStream var0) throws IOException {
        if (!(var0 instanceof BufferedInputStream)) {
            var0 = new BufferedInputStream((InputStream)var0);
        }

        int var1 = 0;
        int var2 = ((InputStream)var0).available();
        byte[] var3 = new byte[var2];
        int var4 = 0;

        do {
            if (var1 + var2 > var3.length) {
                byte[] var5 = new byte[var1 + var2];
                System.arraycopy(var3, 0, var5, 0, var1);
                var3 = var5;
            }

            var4 = ((InputStream)var0).read(var3, var1, var2);
            if (var4 >= 0) {
                var1 += var4;
            }

            var2 = ((InputStream)var0).available();
        } while(var2 > 0 && var4 >= 0);

        if (var1 != var3.length) {
            byte[] var7 = new byte[var1];
            System.arraycopy(var3, 0, var7, 0, var1);
            var3 = var7;
        }

        return var3;
    }

    public static ByteBuffer copyStream2ByteBuffer(InputStream var0) throws IOException {
        return copyStream2ByteBuffer(var0, -1);
    }

    public static ByteBuffer copyStream2ByteBuffer(InputStream var0, int var1) throws IOException {
        if (!(var0 instanceof BufferedInputStream)) {
            var0 = new BufferedInputStream((InputStream)var0);
        }

        int var2 = ((InputStream)var0).available();
        if (var1 < var2) {
            var1 = var2;
        }

        MachineDataInfo var3 = Platform.getMachineDataInfo();
        ByteBuffer var4 = Buffers.newDirectByteBuffer(var3.pageAlignedSize(var1));
        byte[] var5 = new byte[var3.pageSizeInBytes()];
        int var6 = Math.min(var3.pageSizeInBytes(), var2);
        int var7 = 0;

        do {
            if (var2 > var4.remaining()) {
                ByteBuffer var8 = Buffers.newDirectByteBuffer(var3.pageAlignedSize(var4.position() + var2));
                var8.put(var4);
                var4 = var8;
            }

            var7 = ((InputStream)var0).read(var5, 0, var6);
            if (var7 > 0) {
                var4.put(var5, 0, var7);
            }

            var2 = ((InputStream)var0).available();
            var6 = Math.min(var3.pageSizeInBytes(), var2);
        } while(var7 > 0);

        var4.flip();
        return var4;
    }

    public static String slashify(String var0, boolean var1, boolean var2) throws URISyntaxException {
        String var3 = patternSingleBS.matcher(var0).replaceAll("/");
        if (var1 && !var3.startsWith("/")) {
            var3 = "/" + var3;
        }

        if (var2 && !var3.endsWith("/")) {
            var3 = var3 + "/";
        }

        return cleanPathString(var3);
    }

    public static String getFileSuffix(File var0) {
        return getFileSuffix(var0.getName());
    }

    public static String getFileSuffix(String var0) {
        int var1 = var0.lastIndexOf(46);
        return var1 < 0 ? null : toLowerCase(var0.substring(var1 + 1));
    }

    private static String toLowerCase(String var0) {
        return var0 == null ? null : var0.toLowerCase();
    }

    public static FileOutputStream getFileOutputStream(File var0, boolean var1) throws IOException {
        Constructor var2 = getFOSCtor();
        if (null == var2) {
            throw new IOException("Cannot open file (" + var0 + ") for writing, FileOutputStream feature not available.");
        } else if (var0.exists() && !var1) {
            throw new IOException("File already exists (" + var0 + ") and overwrite=false");
        } else {
            try {
                return (FileOutputStream)var2.newInstance(var0);
            } catch (Exception var4) {
                throw new IOException("error opening " + var0 + " for write. ", var4);
            }
        }
    }

    public static String getClassFileName(String var0) {
        return var0.replace('.', '/') + ".class";
    }

    public static URL getClassURL(String var0, ClassLoader var1) throws IOException {
        URL var2 = var1.getResource(getClassFileName(var0));
        if (null == var2) {
            throw new IOException("Cannot not find: " + var0);
        } else {
            return var2;
        }
    }

    public static String getBasename(String var0) throws URISyntaxException {
        var0 = slashify(var0, false, false);
        int var1 = var0.lastIndexOf(47);
        if (var1 >= 0) {
            var0 = var0.substring(var1 + 1);
        }

        return var0;
    }

    public static String getDirname(String var0) throws URISyntaxException {
        var0 = slashify(var0, false, false);
        int var1 = var0.lastIndexOf(47);
        if (var1 >= 0) {
            var0 = var0.substring(0, var1 + 1);
        }

        return var0;
    }

    /** @deprecated */
    public static URLConnection getResource(Class<?> var0, String var1) {
        ClassLoader var2 = null != var0 ? var0.getClassLoader() : IOUtil.class.getClassLoader();
        return getResource(var1, var2, var0);
    }

    public static URLConnection getResource(String var0, ClassLoader var1, Class<?> var2) {
        if (null == var0) {
            return null;
        } else {
            URLConnection var3 = null;
            if (null != var2) {
                String var4 = var2.getName().replace('.', '/');
                int var5 = var4.lastIndexOf(47);
                if (var5 >= 0) {
                    String var6 = var4.substring(0, var5 + 1);
                    var3 = getResource(var6 + var0, var1);
                    if (DEBUG) {
                        System.err.println("IOUtil: found <" + var0 + "> within class package <" + var6 + "> of given class <" + var2.getName() + ">: " + (null != var3));
                    }
                }
            } else if (DEBUG) {
                System.err.println("IOUtil: null context, skip rel. lookup");
            }

            if (null == var3) {
                var3 = getResource(var0, var1);
                if (DEBUG) {
                    System.err.println("IOUtil: found <" + var0 + "> by classloader: " + (null != var3));
                }
            }

            return var3;
        }
    }

    public static URLConnection getResource(String var0, ClassLoader var1) {
        if (null == var0) {
            return null;
        } else {
            if (DEBUG) {
                System.err.println("IOUtil: locating <" + var0 + ">, has cl: " + (null != var1));
            }

            if (var0.startsWith("asset:")) {
                try {
                    return AssetURLContext.createURL(var0, var1).openConnection();
                } catch (IOException var4) {
                    if (DEBUG) {
                        ExceptionUtils.dumpThrowable("IOUtil", var4);
                    }

                    return null;
                }
            } else {
                try {
                    return AssetURLContext.resolve(var0, var1);
                } catch (IOException var3) {
                    if (DEBUG) {
                        ExceptionUtils.dumpThrowable("IOUtil", var3);
                    }

                    return null;
                }
            }
        }
    }

    public static String getRelativeOf(File var0, String var1) throws URISyntaxException {
        if (null == var1) {
            return null;
        } else if (var0 != null) {
            File var2 = new File(var0, var1);
            return slashify(var2.getPath(), false, false);
        } else {
            return null;
        }
    }

    public static String getParentOf(String var0) throws URISyntaxException {
        int var1 = null != var0 ? var0.length() : 0;
        if (var1 == 0) {
            throw new IllegalArgumentException("path is empty <" + var0 + ">");
        } else {
            int var2 = var0.lastIndexOf("/");
            if (var2 < 0) {
                throw new URISyntaxException(var0, "path contains no '/': <" + var0 + ">");
            } else if (var2 == 0) {
                throw new URISyntaxException(var0, "path has no parents: <" + var0 + ">");
            } else if (var2 < var1 - 1) {
                return var0.substring(0, var2 + 1);
            } else {
                int var3 = var0.lastIndexOf("!") + 1;
                int var4 = var0.lastIndexOf("/", var2 - 1);
                if (var4 >= var3) {
                    return var0.substring(0, var4 + 1);
                } else {
                    String var5 = var0.substring(var3, var2);
                    if (var5.equals("..")) {
                        throw new URISyntaxException(var0, "parent is unresolved: <" + var0 + ">");
                    } else {
                        return var0.substring(0, var3);
                    }
                }
            }
        }
    }

    public static String cleanPathString(String var0) throws URISyntaxException {
        int var2 = var0.length() - 1;

        while(var2 >= 1 && (var2 = var0.lastIndexOf("./", var2)) >= 0) {
            if (0 < var2 && var0.charAt(var2 - 1) == '.') {
                var2 -= 2;
            } else {
                var0 = var0.substring(0, var2) + var0.substring(var2 + 2);
                --var2;
            }
        }

        var2 = 0;

        while((var2 = var0.indexOf("../", var2)) >= 0) {
            if (0 == var2) {
                var2 += 3;
            } else {
                var0 = getParentOf(var0.substring(0, var2)) + var0.substring(var2 + 3);
                var2 = 0;
            }
        }

        return var0;
    }

    public static String getUriFilePathOrASCII(Uri var0) {
        return var0.isFileScheme() ? var0.toFile().getPath() : var0.toASCIIString().get();
    }

    public static URLConnection openURL(URL var0) {
        return openURL(var0, ".");
    }

    public static URLConnection openURL(URL var0, String var1) {
        if (null != var0) {
            try {
                URLConnection var2 = var0.openConnection();
                var2.connect();
                if (DEBUG) {
                    System.err.println("IOUtil: urlExists(" + var0 + ") [" + var1 + "] - true");
                }

                return var2;
            } catch (IOException var3) {
                if (DEBUG) {
                    ExceptionUtils.dumpThrowable("IOUtil: urlExists(" + var0 + ") [" + var1 + "] - false -", var3);
                }
            }
        } else if (DEBUG) {
            System.err.println("IOUtil: no url - urlExists(null) [" + var1 + "]");
        }

        return null;
    }

    private static String getExeTestFileSuffix() {
        switch (PlatformPropsImpl.OS_TYPE) {
            case WINDOWS:
                if (CPUFamily.X86 == PlatformPropsImpl.CPU_ARCH.family) {
                    return ".exe";
                }

                return ".bat";
            default:
                return ".sh";
        }
    }

    private static String getExeTestShellCode() {
        switch (PlatformPropsImpl.OS_TYPE) {
            case WINDOWS:
                return "echo off" + PlatformPropsImpl.NEWLINE;
            default:
                return null;
        }
    }

    private static String[] getExeTestCommandArgs(String var0) {
        switch (PlatformPropsImpl.OS_TYPE) {
            case WINDOWS:
            default:
                return new String[]{var0};
        }
    }

    private static final byte[] readCode(String var0) throws IOException {
        URLConnection var1 = getResource(var0, IOUtil.class.getClassLoader(), IOUtil.class);
        InputStream var2 = var1.getInputStream();
        byte[] var7;

        try {
            var7 = CustomCompress.inflateFromStream(var2);
        } finally {
            var2.close();
        }

        return var7;
    }

    private static void fillExeTestFile(File var0) throws IOException {
        if (OSType.WINDOWS == PlatformPropsImpl.OS_TYPE && CPUFamily.X86 == PlatformPropsImpl.CPU_ARCH.family) {
            byte[] var23;
            synchronized(exeTestLock) {
                Object var3 = null;
                byte[] var25;
                if (null != exeTestCodeRef && null != (var25 = (byte[])exeTestCodeRef.get())) {
                    var23 = var25;
                } else {
                    String var4;
                    if (CPUType.X86_64 == PlatformPropsImpl.CPU_ARCH) {
                        var4 = "bin/exe-windows-x86_64.defl";
                    } else {
                        var4 = "bin/exe-windows-i386.defl";
                    }

                    var23 = readCode(var4);
                    exeTestCodeRef = new WeakReference(var23);
                }
            }

            FileOutputStream var24 = new FileOutputStream(var0);

            try {
                var24.write(var23, 0, var23.length);

                try {
                    var24.getFD().sync();
                } catch (SyncFailedException var20) {
                    ExceptionUtils.dumpThrowable("", var20);
                }
            } finally {
                var24.close();
            }
        } else {
            String var1 = getExeTestShellCode();
            if (isStringSet(var1)) {
                FileWriter var2 = new FileWriter(var0);

                try {
                    var2.write(var1);

                    try {
                        var2.flush();
                    } catch (IOException var18) {
                        ExceptionUtils.dumpThrowable("", var18);
                    }
                } finally {
                    var2.close();
                }
            }
        }

    }

    private static boolean getOSHasNoexecFS() {
        switch (PlatformPropsImpl.OS_TYPE) {
            case OPENKODE:
                return false;
            default:
                return true;
        }
    }

    private static boolean getOSHasFreeDesktopXDG() {
        switch (PlatformPropsImpl.OS_TYPE) {
            case WINDOWS:
            case OPENKODE:
            case ANDROID:
            case MACOS:
                return false;
            default:
                return true;
        }
    }

    public static boolean testFile(File var0, boolean var1, boolean var2) {
        if (!var0.exists()) {
            if (DEBUG) {
                System.err.println("IOUtil.testFile: <" + var0.getAbsolutePath() + ">: does not exist");
            }

            return false;
        } else if (var1 && !var0.isDirectory()) {
            if (DEBUG) {
                System.err.println("IOUtil.testFile: <" + var0.getAbsolutePath() + ">: is not a directory");
            }

            return false;
        } else if (var2 && !var0.canWrite()) {
            if (DEBUG) {
                System.err.println("IOUtil.testFile: <" + var0.getAbsolutePath() + ">: is not writable");
            }

            return false;
        } else {
            return true;
        }
    }

    public static boolean testDirExec(File var0) throws SecurityException {
        boolean var1 = DEBUG_EXE || DEBUG;
        if (!testFile(var0, true, true)) {
            if (var1) {
                System.err.println("IOUtil.testDirExec: <" + var0.getAbsolutePath() + ">: Not writeable dir");
            }

            return false;
        } else if (!getOSHasNoexecFS()) {
            if (var1) {
                System.err.println("IOUtil.testDirExec: <" + var0.getAbsolutePath() + ">: Always executable");
            }

            return true;
        } else {
            long var2 = var1 ? System.currentTimeMillis() : 0L;

            File var4;
            boolean var5;
            try {
                File var6 = DEBUG_EXE_EXISTING_FILE ? new File(var0, "jogamp_exe_tst" + getExeTestFileSuffix()) : null;
                if (null != var6 && var6.exists()) {
                    var4 = var6;
                    var5 = true;
                } else {
                    var4 = File.createTempFile("jogamp_exe_tst", getExeTestFileSuffix(), var0);
                    var5 = false;
                }
            } catch (SecurityException var28) {
                throw var28;
            } catch (IOException var29) {
                if (var1) {
                    var29.printStackTrace();
                }

                return false;
            }

            long var30 = var1 ? System.currentTimeMillis() : 0L;
            byte var10 = -1;
            int var11 = -1;
            long var8;
            if (!var5 && !var4.setExecutable(true, true)) {
                var8 = var1 ? System.currentTimeMillis() : 0L;
            } else {
                Process var12 = null;

                try {
                    if (!var5) {
                        fillExeTestFile(var4);
                    }

                    var8 = var1 ? System.currentTimeMillis() : 0L;
//                    var12 = Runtime.getRuntime().exec(getExeTestCommandArgs(var4.getCanonicalPath()), (String[])null, (File)null);
//                    if (DEBUG_EXE && !DEBUG_EXE_NOSTREAM) {
//                        new StreamMonitor(new InputStream[]{var12.getInputStream(), var12.getErrorStream()}, System.err, "Exe-Tst: ");
//                    }
//
//                    var12.waitFor();
//                    var11 = var12.exitValue();
                    var10 = 0;
                } catch (SecurityException var25) {
                    throw var25;
                } catch (Throwable var26) {
                    var8 = var1 ? System.currentTimeMillis() : 0L;
                    var10 = -2;
                    if (var1) {
                        System.err.println("IOUtil.testDirExec: <" + var4.getAbsolutePath() + ">: Caught " + var26.getClass().getSimpleName() + ": " + var26.getMessage());
                        var26.printStackTrace();
                    }
                } finally {
                    if (null != var12) {
                        try {
                            var12.destroy();
                        } catch (Throwable var24) {
                            ExceptionUtils.dumpThrowable("", var24);
                        }
                    }

                }
            }

            boolean var31 = 0 == var10;
            if (!DEBUG_EXE && !var5) {
                var4.delete();
            }

            if (var1) {
                long var13 = System.currentTimeMillis();
                System.err.println("IOUtil.testDirExec(): test-exe <" + var4.getAbsolutePath() + ">, existingFile " + var5 + ", returned " + var11);
                System.err.println("IOUtil.testDirExec(): abs-path <" + var0.getAbsolutePath() + ">: res " + var10 + " -> " + var31);
                System.err.println("IOUtil.testDirExec(): total " + (var13 - var2) + "ms, create " + (var30 - var2) + "ms, fill " + (var8 - var30) + "ms, execute " + (var13 - var8) + "ms");
            }

            return var31;
        }
    }

    private static File testDirImpl(File var0, boolean var1, boolean var2, String var3) throws SecurityException {
        if (var1 && !var0.exists()) {
            var0.mkdirs();
        }

        File var4;
        if (var2) {
            var4 = testDirExec(var0) ? var0 : null;
        } else {
            var4 = testFile(var0, true, true) ? var0 : null;
        }

        if (DEBUG) {
            System.err.println("IOUtil.testDirImpl(" + var3 + "): <" + var0.getAbsolutePath() + ">, create " + var1 + ", exec " + var2 + ": " + (null != var4));
        }

        return var4;
    }

    public static File testDir(File var0, boolean var1, boolean var2) throws SecurityException {
        return testDirImpl(var0, var1, var2, "testDir");
    }

    private static boolean isStringSet(String var0) {
        return null != var0 && 0 < var0.length();
    }

    private static File getSubTempDir(File var0, String var1, boolean var2, String var3) throws SecurityException {
        File var4 = null;
        if (null != testDirImpl(var0, true, var2, var3)) {
            for(int var5 = 0; null == var4 && var5 <= 9999; ++var5) {
                String var6 = String.format("_%04d", var5);
                var4 = testDirImpl(new File(var0, var1 + var6), true, var2, var3);
            }
        }

        return var4;
    }

    private static File getFile(String var0) {
        return isStringSet(var0) ? new File(var0) : null;
    }

    public static File getTempDir(boolean var0) throws SecurityException, IOException {
        if (!tempRootSet) {
            synchronized(IOUtil.class) {
                if (!tempRootSet) {
                    tempRootSet = true;
                    File var2 = AndroidUtils.getTempRoot();
                    if (null != var2) {
                        tempRootNoexec = getSubTempDir(var2, "jogamp", false, "Android.ctxTemp");
                        tempRootExec = tempRootNoexec;
                        return tempRootExec;
                    }

                    var2 = getFile(PropertyAccess.getProperty("java.io.tmpdir", false));
                    if (DEBUG) {
                        System.err.println("IOUtil.getTempRoot(): tempX1 <" + var2 + ">, used " + (null != var2));
                    }

                    String var4 = System.getenv("TMPDIR");
                    if (!isStringSet(var4)) {
                        var4 = System.getenv("TEMP");
                    }

                    File var5 = getFile(var4);
                    File var3;
                    if (null != var5 && !var5.equals(var2)) {
                        var3 = var5;
                    } else {
                        var3 = null;
                    }

                    if (DEBUG) {
                        System.err.println("IOUtil.getTempRoot(): tempX3 <" + var5 + ">, used " + (null != var3));
                    }

                    File var14 = getFile(PropertyAccess.getProperty("user.home", false));
                    if (DEBUG) {
                        System.err.println("IOUtil.getTempRoot(): tempX4 <" + var14 + ">, used " + (null != var14));
                    }

                    String var6;
                    if (getOSHasFreeDesktopXDG()) {
                        var6 = System.getenv("XDG_CACHE_HOME");
                        if (!isStringSet(var6) && null != var14) {
                            var6 = var14.getAbsolutePath() + File.separator + ".cache";
                        }
                    } else {
                        var6 = null;
                    }

                    File var7 = getFile(var6);
                    if (null != var7 && !var7.equals(var2)) {
                        var5 = var7;
                    } else {
                        var5 = null;
                    }

                    if (DEBUG) {
                        System.err.println("IOUtil.getTempRoot(): tempX2 <" + var7 + ">, used " + (null != var5));
                    }

                    if (null == tempRootExec && null != var2) {
                        if (OSType.MACOS == PlatformPropsImpl.OS_TYPE) {
                            tempRootExec = getSubTempDir(var2, "jogamp", false, "tempX1");
                        } else {
                            tempRootExec = getSubTempDir(var2, "jogamp", true, "tempX1");
                        }
                    }

                    if (null == tempRootExec && null != var5) {
                        tempRootExec = getSubTempDir(var5, "jogamp", true, "tempX2");
                    }

                    if (null == tempRootExec && null != var3) {
                        tempRootExec = getSubTempDir(var3, "jogamp", true, "tempX3");
                    }

                    if (null == tempRootExec && null != var14) {
                        tempRootExec = getSubTempDir(var14, ".jogamp", true, "tempX4");
                    }

                    if (null != tempRootExec) {
                        tempRootNoexec = tempRootExec;
                    } else {
                        if (null == tempRootNoexec && null != var2) {
                            tempRootNoexec = getSubTempDir(var2, "jogamp", false, "temp01");
                        }

                        if (null == tempRootNoexec && null != var5) {
                            tempRootNoexec = getSubTempDir(var5, "jogamp", false, "temp02");
                        }

                        if (null == tempRootNoexec && null != var3) {
                            tempRootNoexec = getSubTempDir(var3, "jogamp", false, "temp03");
                        }

                        if (null == tempRootNoexec && null != var14) {
                            tempRootNoexec = getSubTempDir(var14, ".jogamp", false, "temp04");
                        }
                    }

                    if (DEBUG) {
                        var6 = null != tempRootExec ? tempRootExec.getAbsolutePath() : null;
                        String var17 = null != tempRootNoexec ? tempRootNoexec.getAbsolutePath() : null;
                        System.err.println("IOUtil.getTempRoot(): temp dirs: exec: " + var6 + ", noexec: " + var17);
                    }
                }
            }
        }

        File var10 = var0 ? tempRootExec : tempRootNoexec;
        if (null == var10) {
            String var13 = var0 ? "executable " : "";
            throw new IOException("Could not determine a temporary " + var13 + "directory");
        } else {
            FilePermission var12 = new FilePermission(var10.getAbsolutePath(), "read,write,delete");
            SecurityUtil.checkPermission(var12);
            return var10;
        }
    }

    public static File createTempFile(String var0, String var1, boolean var2) throws IllegalArgumentException, IOException, SecurityException {
        return File.createTempFile(var0, var1, getTempDir(var2));
    }

    public static void close(Closeable var0, boolean var1) throws RuntimeException {
        if (null != var0) {
            try {
                var0.close();
            } catch (IOException var3) {
                if (var1) {
                    throw new RuntimeException(var3);
                }

                if (DEBUG) {
                    System.err.println("Caught Exception: ");
                    var3.printStackTrace();
                }
            }
        }

    }

    public static IOException close(Closeable var0, IOException[] var1, PrintStream var2) {
        try {
            var0.close();
        } catch (IOException var4) {
            if (null != var1[0]) {
                if (null != var2) {
                    var2.println("Caught " + var4.getClass().getSimpleName() + ": " + var4.getMessage());
                    var4.printStackTrace(var2);
                }

                return var4;
            }

            var1[0] = var4;
        }

        return null;
    }

    static {
        Debug.initSingleton();
        DEBUG = Debug.debug("IOUtil");
        DEBUG_EXE = PropertyAccess.isPropertyDefined("jogamp.debug.IOUtil.Exe", true);
        DEBUG_EXE_NOSTREAM = PropertyAccess.isPropertyDefined("jogamp.debug.IOUtil.Exe.NoStream", true);
        DEBUG_EXE_EXISTING_FILE = false;
        patternSingleBS = Pattern.compile("\\\\{1}");
        patternSpaceEnc = Pattern.compile("%20");
        exeTestLock = new Object();
        exeTestCodeRef = null;
        tempRootExec = null;
        tempRootNoexec = null;
        tempRootSet = false;
    }

    public static class ClassResources {
        public final ClassLoader classLoader;
        public final Class<?> contextCL;
        public final String[] resourcePaths;

        public final int resourceCount() {
            return this.resourcePaths.length;
        }

        /** @deprecated */
        public ClassResources(Class<?> var1, String[] var2) {
            this(var2, var1.getClassLoader(), var1);
        }

        public ClassResources(String[] var1, ClassLoader var2, Class<?> var3) {
            for(int var4 = var1.length - 1; var4 >= 0; --var4) {
                if (null == var1[var4]) {
                    throw new IllegalArgumentException("resourcePath[" + var4 + "] is null");
                }
            }

            this.classLoader = var2;
            this.contextCL = var3;
            this.resourcePaths = var1;
        }

        public URLConnection resolve(int var1) throws ArrayIndexOutOfBoundsException {
            return IOUtil.getResource(this.resourcePaths[var1], this.classLoader, this.contextCL);
        }
    }

    public static class StreamMonitor implements Runnable {
        private final InputStream[] istreams;
        private final boolean[] eos;
        private final PrintStream ostream;
        private final String prefix;

        public StreamMonitor(InputStream[] var1, PrintStream var2, String var3) {
            this.istreams = var1;
            this.eos = new boolean[var1.length];
            this.ostream = var2;
            this.prefix = var3;
            InterruptSource.Thread var4 = new InterruptSource.Thread((ThreadGroup)null, this, "StreamMonitor-" + Thread.currentThread().getName());
            var4.setDaemon(true);
            var4.start();
        }

        public void run() {
            byte[] var1 = new byte[4096];

            try {
                int var2 = this.istreams.length;
                int var3 = 0;

                do {
                    for(int var4 = 0; var4 < this.istreams.length; ++var4) {
                        if (!this.eos[var4]) {
                            int var5 = this.istreams[var4].read(var1);
                            if (var5 > 0) {
                                if (null != this.ostream) {
                                    if (null != this.prefix) {
                                        this.ostream.write(this.prefix.getBytes());
                                    }

                                    this.ostream.write(var1, 0, var5);
                                }
                            } else {
                                ++var3;
                                this.eos[var4] = true;
                            }
                        }
                    }

                    if (null != this.ostream) {
                        this.ostream.flush();
                    }
                } while(var3 < var2);
            } catch (IOException var9) {
            } finally {
                if (null != this.ostream) {
                    this.ostream.flush();
                }

            }

        }
    }
}
