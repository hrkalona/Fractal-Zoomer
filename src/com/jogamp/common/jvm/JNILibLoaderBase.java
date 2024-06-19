//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.jogamp.common.jvm;

import com.jogamp.common.net.Uri;
import com.jogamp.common.net.Uri.Encoded;
import com.jogamp.common.os.NativeLibrary;
import com.jogamp.common.util.JarUtil;
import com.jogamp.common.util.PropertyAccess;
import com.jogamp.common.util.cache.TempJarCache;
import jogamp.common.Debug;
import jogamp.common.os.PlatformPropsImpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class JNILibLoaderBase {
    public static final boolean DEBUG;
    protected static final boolean PERF;
    private static final Object perfSync;
    private static long perfTotal;
    private static long perfCount;
    private static final HashSet<String> loaded;
    private static LoaderAction loaderAction;
    private static final String nativeJarTagPackage = "jogamp.nativetag";
    private static final Method customLoadLibraryMethod;

    public JNILibLoaderBase() {
    }

    public static boolean isLoaded(String var0) {
        return loaded.contains(var0);
    }

    public static void addLoaded(String var0) {
        loaded.add(var0);
        if (DEBUG) {
            System.err.println("JNILibLoaderBase: Loaded Native Library: " + var0);
        }

    }

    public static void disableLoading() {
        setLoadingAction((LoaderAction)null);
    }

    public static void enableLoading() {
        setLoadingAction(new DefaultAction());
    }

    public static synchronized void setLoadingAction(LoaderAction var0) {
        loaderAction = var0;
    }

    private static final boolean addNativeJarLibsImpl(Class<?> var0, Uri var1, Uri.Encoded var2, Uri.Encoded var3) throws IOException, SecurityException, URISyntaxException {
        if (DEBUG) {
            StringBuilder var4 = new StringBuilder();
            var4.append("JNILibLoaderBase: addNativeJarLibsImpl(").append(PlatformPropsImpl.NEWLINE);
            var4.append("  classFromJavaJar  = ").append(var0).append(PlatformPropsImpl.NEWLINE);
            var4.append("  classJarURI       = ").append(var1).append(PlatformPropsImpl.NEWLINE);
            var4.append("  jarBasename       = ").append(var2).append(PlatformPropsImpl.NEWLINE);
            var4.append("  os.and.arch       = ").append(PlatformPropsImpl.os_and_arch).append(PlatformPropsImpl.NEWLINE);
            var4.append("  nativeJarBasename = ").append(var3).append(PlatformPropsImpl.NEWLINE);
            var4.append(")");
            System.err.println(var4.toString());
        }

        long var22 = PERF ? System.currentTimeMillis() : 0L;
        boolean var6 = false;
        Uri var7 = var1.getContainedUri();
        if (null == var7) {
            throw new IllegalArgumentException("JarSubURI is null of: " + var1);
        } else {
            Uri var8 = var7.getDirectory();
            if (DEBUG) {
                System.err.printf("JNILibLoaderBase: addNativeJarLibsImpl: initial: %s -> %s%n", var7, var8);
            }

            String var9 = String.format("natives/%s/", PlatformPropsImpl.os_and_arch);
            if (DEBUG) {
                System.err.printf("JNILibLoaderBase: addNativeJarLibsImpl: nativeLibraryPath: %s%n", var9);
            }

            Uri var10 = JarUtil.getJarFileUri(var8.getEncoded().concat(var3));
            if (DEBUG) {
                System.err.printf("JNILibLoaderBase: addNativeJarLibsImpl: module: %s -> %s%n", var3, var10);
            }

            try {
                var6 = TempJarCache.addNativeLibs(var0, var10, var9);
            } catch (Exception var21) {
                if (DEBUG) {
                    System.err.printf("JNILibLoaderBase: addNativeJarLibsImpl: Caught %s%n", var21.getMessage());
                    var21.printStackTrace();
                }
            }

            if (!var6) {
                ClassLoader var23 = var0.getClassLoader();
                URL var11 = var23.getResource(var9);
                if (null != var11) {
                    Uri var12 = JarUtil.getJarFileUri(var8.getEncoded().concat(var2));

                    try {
                        if (TempJarCache.addNativeLibs(var0, var12, var9)) {
                            var6 = true;
                            if (DEBUG) {
                                System.err.printf("JNILibLoaderBase: addNativeJarLibsImpl: fat: %s -> %s%n", var2, var12);
                            }
                        }
                    } catch (Exception var20) {
                        if (DEBUG) {
                            System.err.printf("JNILibLoaderBase: addNativeJarLibsImpl: Caught %s%n", var20.getMessage());
                            var20.printStackTrace();
                        }
                    }
                }

                if (!var6) {
                    String var26 = var0.getPackage().getName();
                    int var13 = var26.lastIndexOf(46);
                    String var25;
                    if (0 <= var13) {
                        var25 = var26.substring(var13 + 1);
                    } else {
                        var25 = var26;
                    }

                    var26 = PlatformPropsImpl.os_and_arch.replace('-', '.');
                    String var28 = "jogamp.nativetag." + var25 + "." + var26 + ".TAG";

                    try {
                        if (DEBUG) {
                            System.err.printf("JNILibLoaderBase: addNativeJarLibsImpl: ClassLoader/TAG: Locating module %s, os.and.arch %s: %s%n", var25, var26, var28);
                        }

                        Uri var14 = JarUtil.getJarUri(var28, var23);
                        if (DEBUG) {
                            System.err.printf("JNILibLoaderBase: addNativeJarLibsImpl: ClassLoader/TAG: %s -> %s%n", var28, var14);
                        }

                        var6 = TempJarCache.addNativeLibs(var0, var14, var9);
                    } catch (Exception var19) {
                        if (DEBUG) {
                            System.err.printf("JNILibLoaderBase: addNativeJarLibsImpl: Caught %s%n", var19.getMessage());
                            var19.printStackTrace();
                        }
                    }
                }
            }

            if (DEBUG || PERF) {
                long var24 = System.currentTimeMillis() - var22;
                long var27;
                long var29;
                synchronized(perfSync) {
                    var29 = perfCount + 1L;
                    var27 = perfTotal + var24;
                    perfTotal = var27;
                    perfCount = var29;
                }

                double var16 = (double)var27 / (double)var29;
                System.err.printf("JNILibLoaderBase: addNativeJarLibsImpl.X: %s / %s -> ok: %b; duration: now %d ms, total %d ms (count %d, avrg %.3f ms)%n", var2, var3, var6, var24, var27, var29, var16);
            }

            return var6;
        }
    }

    public static final boolean addNativeJarLibsJoglCfg(Class<?>[] var0) {
        return addNativeJarLibs(var0, "-all");
    }

    public static boolean addNativeJarLibs(Class<?>[] var0, String var1) {
        if (DEBUG) {
            StringBuilder var2 = new StringBuilder();
            var2.append("JNILibLoaderBase: addNativeJarLibs(").append(PlatformPropsImpl.NEWLINE);
            var2.append("  classesFromJavaJars   = ").append(Arrays.asList(var0)).append(PlatformPropsImpl.NEWLINE);
            var2.append("  singleJarMarker       = ").append(var1).append(PlatformPropsImpl.NEWLINE);
            var2.append(")");
            System.err.println(var2.toString());
        }

        boolean var3 = false;
        if (TempJarCache.isInitialized()) {
            var3 = addNativeJarLibsWithTempJarCache(var0, var1);
        } else if (DEBUG) {
            System.err.println("JNILibLoaderBase: addNativeJarLibs0: disabled due to uninitialized TempJarCache");
        }

        return var3;
    }

    private static boolean addNativeJarLibsWithTempJarCache(Class<?>[] var0, String var1) {
        int var3 = 0;

        boolean var2;
        try {
            boolean var4 = false;
            var2 = true;

            for(int var5 = 0; var5 < var0.length; ++var5) {
                Class var6 = var0[var5];
                if (var6 != null) {
                    ClassLoader var7 = var6.getClassLoader();
                    Uri var8 = JarUtil.getJarUri(var6.getName(), var7);
                    Uri.Encoded var9 = JarUtil.getJarBasename(var8);
                    if (var9 != null) {
                        Uri.Encoded var10 = var9.substring(0, var9.lastIndexOf("."));
                        if (DEBUG) {
                            System.err.printf("JNILibLoaderBase: jarBasename: %s%n", var10);
                        }

                        if (var1 != null && var10.indexOf(var1) >= 0) {
                            var4 = true;
                        }

                        Uri.Encoded var11 = Encoded.cast(String.format("%s-natives-%s.jar", var10.get(), PlatformPropsImpl.os_and_arch));
                        var2 = addNativeJarLibsImpl(var6, var8, var9, var11);
                        if (var2) {
                            ++var3;
                        }

                        if (DEBUG && var4) {
                            System.err.printf("JNILibLoaderBase: addNativeJarLibs0: done: %s%n", var10);
                        }
                    }
                }
            }
        } catch (Exception var12) {
            System.err.printf("JNILibLoaderBase: Caught %s: %s%n", var12.getClass().getSimpleName(), var12.getMessage());
            if (DEBUG) {
                var12.printStackTrace();
            }

            var2 = false;
        }

        if (DEBUG) {
            System.err.printf("JNILibLoaderBase: addNativeJarLibsWhenInitialized: count %d, ok %b%n", var3, var2);
        }

        return var2;
    }

    protected static synchronized boolean loadLibrary(String var0, boolean var1, ClassLoader var2) {
        return loaderAction != null ? loaderAction.loadLibrary(var0, var1, var2) : false;
    }

    protected static synchronized void loadLibrary(String var0, String[] var1, boolean var2, ClassLoader var3) {
        if (loaderAction != null) {
            loaderAction.loadLibrary(var0, var1, var2, var3);
        }

    }

    private static void loadLibraryInternal(String var0, ClassLoader var1) {
        byte var2 = 0;
        if (null != customLoadLibraryMethod && !var0.equals("jawt")) {
            if (DEBUG) {
                System.err.println("JNILibLoaderBase: customLoad(" + var0 + ") - mode 1");
            }

            try {
                customLoadLibraryMethod.invoke((Object)null, var0);
                var2 = 1;
            } catch (Exception var9) {
                Object var4 = var9;
                if (var9 instanceof InvocationTargetException) {
                    var4 = ((InvocationTargetException)var9).getTargetException();
                }

                if (var4 instanceof Error) {
                    throw (Error)var4;
                }

                if (var4 instanceof RuntimeException) {
                    throw (RuntimeException)var4;
                }

                throw (UnsatisfiedLinkError)(new UnsatisfiedLinkError("can not load library " + var0)).initCause(var9);
            }
        } else {
            String var3 = NativeLibrary.findLibrary(var0, var1);
            if (DEBUG) {
                System.err.println("JNILibLoaderBase: loadLibraryInternal(" + var0 + "), TempJarCache: " + var3);
            }

            if (null != var3) {
                if (DEBUG) {
                    System.err.println("JNILibLoaderBase: System.load(" + var3 + ") - mode 2");
                }

                System.load(var3);
                var2 = 2;
            } else {
                if (DEBUG) {
                    System.err.println("JNILibLoaderBase: System.loadLibrary(" + var0 + ") - mode 3");
                }

                try {
                    System.loadLibrary(var0);
                    var2 = 3;
                } catch (UnsatisfiedLinkError var11) {
                    if (DEBUG) {
                        System.err.println("ERROR (retry w/ enumLibPath) - " + var11.getMessage());
                    }

                    List var5 = NativeLibrary.enumerateLibraryPaths(var0, var0, var0, var1);
                    Iterator var6 = var5.iterator();

                    while(0 == var2 && var6.hasNext()) {
                        String var7 = (String)var6.next();
                        if (DEBUG) {
                            System.err.println("JNILibLoaderBase: System.load(" + var7 + ") - mode 4");
                        }

                        try {
                            System.load(var7);
                            var2 = 4;
                        } catch (UnsatisfiedLinkError var10) {
                            if (DEBUG) {
                                System.err.println("n/a - " + var10.getMessage());
                            }

                            if (!var6.hasNext()) {
                                throw var10;
                            }
                        }
                    }
                }
            }
        }

        if (DEBUG) {
            System.err.println("JNILibLoaderBase: loadLibraryInternal(" + var0 + "): OK - mode " + var2);
        }

    }

    static {
        Debug.initSingleton();
        DEBUG = Debug.debug("JNILibLoader");
        PERF = DEBUG || PropertyAccess.isPropertyDefined("jogamp.debug.JNILibLoader.Perf", true);
        perfSync = new Object();
        perfTotal = 0L;
        perfCount = 0L;
        loaded = new HashSet();
        loaderAction = new DefaultAction();
        Method var2 = (Method)AccessController.doPrivileged(new PrivilegedAction<Method>() {
            public Method run() {
                boolean var1 = PropertyAccess.getBooleanProperty("sun.jnlp.applet.launcher", true);
                Class var2 = null;
                Method var3 = null;
                if (var1) {
                    try {
                        var2 = Class.forName("org.jdesktop.applet.util.JNLPAppletLauncher");
                    } catch (ClassNotFoundException var6) {
                        System.err.println("JNILibLoaderBase: <org.jdesktop.applet.util.JNLPAppletLauncher> not found, despite enabled property <sun.jnlp.applet.launcher>, JNLPAppletLauncher was probably used before");
                        System.setProperty("sun.jnlp.applet.launcher", Boolean.FALSE.toString());
                    } catch (LinkageError var7) {
                        throw var7;
                    }

                    if (null != var2) {
                        try {
                            var3 = var2.getDeclaredMethod("loadLibrary", String.class);
                        } catch (NoSuchMethodException var10) {
                            if (JNILibLoaderBase.DEBUG) {
                                var10.printStackTrace();
                            }

                            var2 = null;
                        }
                    }
                }

                if (null == var2) {
                    String var4 = PropertyAccess.getProperty("jnlp.launcher.class", false);
                    if (null != var4) {
                        try {
                            var2 = Class.forName(var4);
                            var3 = var2.getDeclaredMethod("loadLibrary", String.class);
                        } catch (ClassNotFoundException var8) {
                            if (JNILibLoaderBase.DEBUG) {
                                var8.printStackTrace();
                            }
                        } catch (NoSuchMethodException var9) {
                            if (JNILibLoaderBase.DEBUG) {
                                var9.printStackTrace();
                            }

                            var2 = null;
                        }
                    }
                }

                return var3;
            }
        });
        customLoadLibraryMethod = var2;
    }

    private static class DefaultAction implements LoaderAction {
        private DefaultAction() {
        }

        public boolean loadLibrary(String var1, boolean var2, ClassLoader var3) {
            boolean var4 = true;
            if (!JNILibLoaderBase.isLoaded(var1)) {
                try {
                    JNILibLoaderBase.loadLibraryInternal(var1, var3);
                    JNILibLoaderBase.addLoaded(var1);
                    if (JNILibLoaderBase.DEBUG) {
                        System.err.println("JNILibLoaderBase: loaded " + var1);
                    }
                } catch (UnsatisfiedLinkError var6) {
                    var4 = false;
                    if (JNILibLoaderBase.DEBUG) {
                        var6.printStackTrace();
                    }

                    if (!var2 && var6.getMessage().indexOf("already loaded") < 0) {
                        throw var6;
                    }
                }
            }

            return var4;
        }

        public void loadLibrary(String var1, String[] var2, boolean var3, ClassLoader var4) {
            if (!JNILibLoaderBase.isLoaded(var1)) {
                if (null != var2) {
                    for(int var5 = 0; var5 < var2.length; ++var5) {
                        this.loadLibrary(var2[var5], var3, var4);
                    }
                }

                this.loadLibrary(var1, false, var4);
            }

        }
    }

    public interface LoaderAction {
        boolean loadLibrary(String var1, boolean var2, ClassLoader var3);

        void loadLibrary(String var1, String[] var2, boolean var3, ClassLoader var4);
    }
}
