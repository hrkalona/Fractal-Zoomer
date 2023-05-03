package fractalzoomer.utils;

import com.sun.jna.Platform;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.core.mpir.LibMpir;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NativeLoader {

//    private static int doublePrec = 53;
//    private static int tempPrec = 300;
//
//    private static double numberA = 3.53419310543634;
//    private static double numberB = -0.532542342123;
    public static Path tmpdir;
    private static String libDir = "fzNativeLibs";
    public static final String mpfrLinuxLib = "libmpfr.so";
    public static final String mpfrWindowsLib = "libmpfr-6.dll";

    //"mpir_skylake_avx2.dll", "mpir_haswell_avx2.dll", "mpir_sandybridge_ivybridge.dll"
    //"mpir_broadwell_avx2.dll"
    public static final String[] mpfrWinLibs = {mpfrWindowsLib, "libgmp-10.dll", "libwinpthread-1.dll"};
   private static String[] winLibs;
    private static final String[] linuxLibs = new String[] {mpfrLinuxLib};

    public static void init() {

    }

    static {

        List<String> resultList = new ArrayList<>();
        Collections.addAll(resultList, mpfrWinLibs);
        Collections.addAll(resultList, ThreadDraw.mpirWinLibs);

        winLibs = new String[resultList.size()];
        winLibs = resultList.toArray(winLibs);

        String resourcesDir = "/fractalzoomer/native/" + Platform.RESOURCE_PREFIX;

        System.out.println("Looking for " + resourcesDir + " in the jar");

        try {
            exportLibraries(resourcesDir);
        }
        catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        }

        try {
            LibMpfr.init();
        }
        catch (Exception ex) {
            System.out.println("Cannot load mpfr: " + ex.getMessage());
        }
        catch (Error er) {
            System.out.println("Cannot load mpfr: " + er.getMessage());
        }

        /*//Test which dll to load

        if(Platform.isWindows() && Native.SIZE_T_SIZE == 8) {
            boolean libOk = false;
            try {
                TestMpirSkylakeAvx2.init();
                libOk = !TestMpirSkylakeAvx2.test();
                TestMpirSkylakeAvx2.delete();

                if (libOk) {
                    mpirWindowsLib = TestMpirSkylakeAvx2.dllName;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } catch (Error er) {
                System.out.println(er.getMessage());
            }

//            if(!libOk) {
//                try {
//                    TestMpirBroadwellAvx2.init();
//                    libOk = !TestMpirBroadwellAvx2.test();
//                    TestMpirBroadwellAvx2.delete();
//
//                    if (libOk) {
//                        mpirWindowsLib = TestMpirBroadwellAvx2.dllName;
//                    }
//                } catch (Exception ex) {
//                    System.out.println(ex.getMessage());
//                } catch (Error er) {
//                    System.out.println(er.getMessage());
//                }
//            }

            if(!libOk) {
                try {
                    TestMpirHaswellAvx2.init();
                    libOk = !TestMpirHaswellAvx2.test();
                    TestMpirHaswellAvx2.delete();

                    if (libOk) {
                        mpirWindowsLib = TestMpirHaswellAvx2.dllName;
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                } catch (Error er) {
                    System.out.println(er.getMessage());
                }
            }

            if(!libOk) {
                try {
                    TestMpirSandybridgeIvybridge.init();
                    libOk = !TestMpirSandybridgeIvybridge.test();
                    TestMpirSandybridgeIvybridge.delete();

                    if (libOk) {
                        mpirWindowsLib = TestMpirSandybridgeIvybridge.dllName;
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                } catch (Error er) {
                    System.out.println(er.getMessage());
                }
            }
        }*/

        try {
            LibMpir.init();
        }
        catch (Exception ex) {
            System.out.println("Cannot load mpir: " + ex.getMessage());
        }
        catch (Error er) {
            System.out.println("Cannot load mpir: " + er.getMessage());
        }

    }

    static boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    public static void exportLibraries(String resourcesDir) throws Exception {
        String tmpDirsLocation = System.getProperty("java.io.tmpdir");
        Path globalTempDir = Paths.get(tmpDirsLocation);
        File[] directories = globalTempDir.toFile().listFiles(File::isDirectory);
        for(File file : directories) {
            String name = file.getName();
            if(name.contains(libDir)) {
                deleteDirectory(file);
            }
        }
        tmpdir = Files.createTempDirectory(globalTempDir, libDir);
        tmpdir.toFile().deleteOnExit();

        String[] libs = Platform.isWindows() ? winLibs : linuxLibs;

        for(String lib : libs) {
            InputStream in = NativeLoader.class.getResourceAsStream(resourcesDir + "/" + lib);
            if(in == null) {
                throw new Exception("The resource " + resourcesDir + "/" + lib + " is not available in the jar file");
            }
            Path tgt = tmpdir.resolve(lib);
            Files.copy(in, tgt);
            tgt.toFile().deleteOnExit();
        }

    }

   /* private static class TestMpirSkylakeAvx2 {
        public static final String dllName = "mpir_skylake_avx2.dll";

        public static Exception LOAD_ERROR = null;
        private static NativeLibrary library;

        public static boolean hasError() {
            return LOAD_ERROR != null;
        }

        static {

            try {
                loadLibMpir();
            } catch (Exception ex) {
                LOAD_ERROR = ex;
            }
            catch (Error ex) {
                LOAD_ERROR = new Exception(ex.getMessage());
            }

        }

        private static void loadLibMpir() throws Exception {

            load(NativeLoader.tmpdir.resolve(dllName).toAbsolutePath().toString());

        }

        public static void init() {
        }

        private static void load(String name) {
            library = NativeLibrary.getInstance(name, TestMpirSkylakeAvx2.class.getClassLoader());
            Native.register(TestMpirSkylakeAvx2.class, library);
        }

        public static void delete() {
            if(!hasError()) {
                Memory.disposeAll();
                Native.unregister(TestMpirSkylakeAvx2.class);
                library.close();
            }
        }

        public static boolean test() {

            if(!hasError()) {
                System.out.println("Testing " + dllName);

                MpfMemory a = new MpfMemory(true);
                MpfMemory b = new MpfMemory(true);
                MpfMemory c = new MpfMemory(true);

                boolean error = false;
                try {
                    __gmpf_init2(a.peer, doublePrec);
                    __gmpf_set_d(a.peer, numberA);

                    __gmpf_init2(b.peer, doublePrec);
                    __gmpf_set_d(b.peer, numberB);

                    __gmpf_init2(c.peer, tempPrec);
                    __gmpf_add(c.peer, a.peer, b.peer);
                    __gmpf_sub(c.peer, a.peer, b.peer);
                    __gmpf_mul(c.peer, a.peer, b.peer);
                    __gmpf_div(c.peer, a.peer, b.peer);
                    __gmpf_sqrt(c.peer, a.peer);
                } catch (Exception ex) {
                    System.out.println("Testing " + dllName + " failed " + ex.getMessage());
                    error = true;
                } catch (Error er) {
                    System.out.println("Testing " + dllName + " failed " + er.getMessage());
                    error = true;
                }

                try {
                    __gmpf_clear(a.peer);
                    __gmpf_clear(b.peer);
                    __gmpf_clear(c.peer);
                } catch (Exception ex) {

                } catch (Error er) {

                }

                c.peer = null;
                b.peer = null;
                a.peer = null;

                return error;
            }

            return true;
        }

        public static native void __gmpf_init2(mpf_t x, long precision);
        public static native int __gmpf_set(mpf_t rop, mpf_t op);
        public static native int __gmpf_set_d(mpf_t rop, double op);
        public static native int __gmpf_set_si (mpf_t rop, long  op);
        public static native void __gmpf_clear(mpf_t op);
        public static native int __gmpf_add(mpf_t rop, mpf_t op1, mpf_t op2);
        public static native int __gmpf_sub(mpf_t rop, mpf_t op1, mpf_t op2);
        public static native int __gmpf_mul(mpf_t rop, mpf_t op1, mpf_t op2);
        public static native int __gmpf_div(mpf_t rop, mpf_t op1, mpf_t op2);

        public static native int __gmpf_sqrt(mpf_t rop, mpf_t op1);

    }*/

   /* private static class TestMpirHaswellAvx2 {
        public static final String dllName = "mpir_haswell_avx2.dll";

        public static Exception LOAD_ERROR = null;
        private static NativeLibrary library;

        public static boolean hasError() {
            return LOAD_ERROR != null;
        }

        static {

            try {
                loadLibMpir();
            } catch (Exception ex) {
                LOAD_ERROR = ex;
            }
            catch (Error ex) {
                LOAD_ERROR = new Exception(ex.getMessage());
            }

        }

        private static void loadLibMpir() throws Exception {

            load(NativeLoader.tmpdir.resolve(dllName).toAbsolutePath().toString());

        }

        public static void init() {
        }

        private static void load(String name) {
            library = NativeLibrary.getInstance(name, TestMpirHaswellAvx2.class.getClassLoader());
            Native.register(TestMpirHaswellAvx2.class, library);
        }

        public static void delete() {
            if(!hasError()) {
                Memory.disposeAll();
                Native.unregister(TestMpirHaswellAvx2.class);
                library.close();
            }
        }

        public static boolean test() {

            if(!hasError()) {
                System.out.println("Testing " + dllName);

                MpfMemory a = new MpfMemory(true);
                MpfMemory b = new MpfMemory(true);
                MpfMemory c = new MpfMemory(true);

                boolean error = false;
                try {
                    __gmpf_init2(a.peer, doublePrec);
                    __gmpf_set_d(a.peer, numberA);

                    __gmpf_init2(b.peer, doublePrec);
                    __gmpf_set_d(b.peer, numberB);

                    __gmpf_init2(c.peer, tempPrec);
                    __gmpf_add(c.peer, a.peer, b.peer);
                    __gmpf_sub(c.peer, a.peer, b.peer);
                    __gmpf_mul(c.peer, a.peer, b.peer);
                    __gmpf_div(c.peer, a.peer, b.peer);
                    __gmpf_sqrt(c.peer, a.peer);
                } catch (Exception ex) {
                    System.out.println("Testing " + dllName + " failed " + ex.getMessage());
                    error = true;
                } catch (Error er) {
                    System.out.println("Testing " + dllName + " failed " + er.getMessage());
                    error = true;
                }

                try {
                    __gmpf_clear(a.peer);
                    __gmpf_clear(b.peer);
                    __gmpf_clear(c.peer);
                } catch (Exception ex) {

                } catch (Error er) {

                }

                c.peer = null;
                b.peer = null;
                a.peer = null;

                return error;
            }

            return true;
        }

        public static native void __gmpf_init2(mpf_t x, long precision);
        public static native int __gmpf_set(mpf_t rop, mpf_t op);
        public static native int __gmpf_set_d(mpf_t rop, double op);
        public static native int __gmpf_set_si (mpf_t rop, long  op);
        public static native void __gmpf_clear(mpf_t op);
        public static native int __gmpf_add(mpf_t rop, mpf_t op1, mpf_t op2);
        public static native int __gmpf_sub(mpf_t rop, mpf_t op1, mpf_t op2);
        public static native int __gmpf_mul(mpf_t rop, mpf_t op1, mpf_t op2);
        public static native int __gmpf_div(mpf_t rop, mpf_t op1, mpf_t op2);

        public static native int __gmpf_sqrt(mpf_t rop, mpf_t op1);

    }*/

    /*private static class TestMpirBroadwellAvx2 {
        public static final String dllName = "mpir_broadwell_avx2.dll";

        public static Exception LOAD_ERROR = null;
        private static NativeLibrary library;

        public static boolean hasError() {
            return LOAD_ERROR != null;
        }

        static {

            try {
                loadLibMpir();
            } catch (Exception ex) {
                LOAD_ERROR = ex;
            }
            catch (Error ex) {
                LOAD_ERROR = new Exception(ex.getMessage());
            }

        }

        private static void loadLibMpir() throws Exception {

            load(NativeLoader.tmpdir.resolve(dllName).toAbsolutePath().toString());

        }

        public static void init() {
        }

        private static void load(String name) {
            library = NativeLibrary.getInstance(name, TestMpirBroadwellAvx2.class.getClassLoader());
            Native.register(TestMpirBroadwellAvx2.class, library);
        }

        public static void delete() {
            if(!hasError()) {
                Memory.disposeAll();
                Native.unregister(TestMpirBroadwellAvx2.class);
                library.close();
            }
        }

        public static boolean test() {

            if(!hasError()) {
                System.out.println("Testing " + dllName);

                MpfMemory a = new MpfMemory(true);
                MpfMemory b = new MpfMemory(true);
                MpfMemory c = new MpfMemory(true);

                boolean error = false;
                try {
                    __gmpf_init2(a.peer, doublePrec);
                    __gmpf_set_d(a.peer, numberA);

                    __gmpf_init2(b.peer, doublePrec);
                    __gmpf_set_d(b.peer, numberB);

                    __gmpf_init2(c.peer, tempPrec);
                    __gmpf_add(c.peer, a.peer, b.peer);
                    __gmpf_sub(c.peer, a.peer, b.peer);
                    __gmpf_mul(c.peer, a.peer, b.peer);
                    __gmpf_div(c.peer, a.peer, b.peer);
                    __gmpf_sqrt(c.peer, a.peer);
                } catch (Exception ex) {
                    System.out.println("Testing " + dllName + " failed " + ex.getMessage());
                    error = true;
                } catch (Error er) {
                    System.out.println("Testing " + dllName + " failed " + er.getMessage());
                    error = true;
                }

                try {
                    __gmpf_clear(a.peer);
                    __gmpf_clear(b.peer);
                    __gmpf_clear(c.peer);
                } catch (Exception ex) {

                } catch (Error er) {

                }

                c.peer = null;
                b.peer = null;
                a.peer = null;

                return error;
            }

            return true;
        }

        public static native void __gmpf_init2(mpf_t x, long precision);
        public static native int __gmpf_set(mpf_t rop, mpf_t op);
        public static native int __gmpf_set_d(mpf_t rop, double op);
        public static native int __gmpf_set_si (mpf_t rop, long  op);
        public static native void __gmpf_clear(mpf_t op);
        public static native int __gmpf_add(mpf_t rop, mpf_t op1, mpf_t op2);
        public static native int __gmpf_sub(mpf_t rop, mpf_t op1, mpf_t op2);
        public static native int __gmpf_mul(mpf_t rop, mpf_t op1, mpf_t op2);
        public static native int __gmpf_div(mpf_t rop, mpf_t op1, mpf_t op2);

        public static native int __gmpf_sqrt(mpf_t rop, mpf_t op1);

    }*/

    /*private static class TestMpirSandybridgeIvybridge {
        public static final String dllName = "mpir_sandybridge_ivybridge.dll";

        public static Exception LOAD_ERROR = null;
        private static NativeLibrary library;

        public static boolean hasError() {
            return LOAD_ERROR != null;
        }

        static {

            try {
                loadLibMpir();
            } catch (Exception ex) {
                LOAD_ERROR = ex;
            }
            catch (Error ex) {
                LOAD_ERROR = new Exception(ex.getMessage());
            }

        }

        private static void loadLibMpir() throws Exception {

            load(NativeLoader.tmpdir.resolve(dllName).toAbsolutePath().toString());

        }

        public static void init() {
        }

        private static void load(String name) {
            library = NativeLibrary.getInstance(name, TestMpirSandybridgeIvybridge.class.getClassLoader());
            Native.register(TestMpirSandybridgeIvybridge.class, library);
        }

        public static void delete() {
            if(!hasError()) {
                Memory.disposeAll();
                Native.unregister(TestMpirSandybridgeIvybridge.class);
                library.close();
            }
        }

        public static boolean test() {

            if(!hasError()) {
                System.out.println("Testing " + dllName);

                MpfMemory a = new MpfMemory(true);
                MpfMemory b = new MpfMemory(true);
                MpfMemory c = new MpfMemory(true);

                boolean error = false;
                try {
                    __gmpf_init2(a.peer, doublePrec);
                    __gmpf_set_d(a.peer, numberA);

                    __gmpf_init2(b.peer, doublePrec);
                    __gmpf_set_d(b.peer, numberB);

                    __gmpf_init2(c.peer, tempPrec);
                    __gmpf_add(c.peer, a.peer, b.peer);
                    __gmpf_sub(c.peer, a.peer, b.peer);
                    __gmpf_mul(c.peer, a.peer, b.peer);
                    __gmpf_div(c.peer, a.peer, b.peer);
                    __gmpf_sqrt(c.peer, a.peer);
                } catch (Exception ex) {
                    System.out.println("Testing " + dllName + " failed " + ex.getMessage());
                    error = true;
                } catch (Error er) {
                    System.out.println("Testing " + dllName + " failed " + er.getMessage());
                    error = true;
                }

                try {
                    __gmpf_clear(a.peer);
                    __gmpf_clear(b.peer);
                    __gmpf_clear(c.peer);
                } catch (Exception ex) {

                } catch (Error er) {

                }

                c.peer = null;
                b.peer = null;
                a.peer = null;

                return error;
            }

            return true;
        }

        public static native void __gmpf_init2(mpf_t x, long precision);
        public static native int __gmpf_set(mpf_t rop, mpf_t op);
        public static native int __gmpf_set_d(mpf_t rop, double op);
        public static native int __gmpf_set_si (mpf_t rop, long  op);
        public static native void __gmpf_clear(mpf_t op);
        public static native int __gmpf_add(mpf_t rop, mpf_t op1, mpf_t op2);
        public static native int __gmpf_sub(mpf_t rop, mpf_t op1, mpf_t op2);
        public static native int __gmpf_mul(mpf_t rop, mpf_t op1, mpf_t op2);
        public static native int __gmpf_div(mpf_t rop, mpf_t op1, mpf_t op2);

        public static native int __gmpf_sqrt(mpf_t rop, mpf_t op1);


    }*/
}
