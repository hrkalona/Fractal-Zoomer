package fractalzoomer.utils;

import com.sun.jna.Platform;
import fractalzoomer.core.TaskRender;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.core.mpir.LibMpir;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class NativeLoader {
    public static Path tmpdir;
    private static String libDir = "fzNativeLibs";
    public static final String mpfrWinLib = "mpfr.dll";
    public static final String mpirWinLib = "mpir.dll";
    public static final String mpfrMacosArmLib = "libmpfr.6.dylib";
    public static final String mpfrLinuxLib = "libmpfr.so";
    public static final String[] mpfrGeneralExtraWinLibs = {"libgmp-10.dll", "libwinpthread-1.dll"};//"libgomp-1.dll", "libgcc_s_seh-1.dll"
    public static final String[] mpfrExtraMacosLibs = {"libomp.dylib", "libgmp.10.dylib"};
    public static final String[] mpfrGeneralVcpkgMsvcExtraWinLibs = {"gmp-10.dll"};
    private static String[] winLibs;
    private static String[] linuxLibs;
    public static String[] macosLibs;

    public static void init() {

    }

    static {

        List<String> resultList = new ArrayList<>();
        if(Platform.isWindows()) {
            if(Platform.is64Bit()) {
                for (String arch : TaskRender.mpirWinArchitecture) {
                    resultList.add(arch + "/" + Platform.RESOURCE_PREFIX + "/" + mpirWinLib);
                }
                for (String arch : TaskRender.mpfrWinArchitecture) {
                    resultList.add(arch + "/" + Platform.RESOURCE_PREFIX + "/" + mpfrWinLib);
                    if (arch.equals(TaskRender.generalArchitecture)) {
                        for (String extra : mpfrGeneralExtraWinLibs) {
                            resultList.add(arch + "/" + Platform.RESOURCE_PREFIX + "/" + extra);
                        }
                    }
                    else if(arch.equals(TaskRender.generalVcpkgMsvcArchitecture)) {
                        for (String extra : mpfrGeneralVcpkgMsvcExtraWinLibs) {
                            resultList.add(arch + "/" + Platform.RESOURCE_PREFIX + "/" + extra);
                        }
                    }
                }
            }
            else { //32 bit
                resultList.add(TaskRender.generalArchitecture + "/" + Platform.RESOURCE_PREFIX + "/" + mpfrWinLib);
                for (String extra : mpfrGeneralExtraWinLibs) {
                    resultList.add(TaskRender.generalArchitecture + "/" + Platform.RESOURCE_PREFIX + "/" + extra);
                }
            }
            winLibs = new String[resultList.size()];
            winLibs = resultList.toArray(winLibs);
        }
        else if(isMacArm64()) {
            resultList.add(TaskRender.generalArchitecture + "/" + Platform.RESOURCE_PREFIX + "/" + mpfrMacosArmLib);
            for (String extra : mpfrExtraMacosLibs) {
                resultList.add(TaskRender.generalArchitecture + "/" + Platform.RESOURCE_PREFIX + "/" + extra);
            }
            macosLibs = new String[resultList.size()];
            macosLibs = resultList.toArray(macosLibs);
        }
        else {
            linuxLibs = new String[] {TaskRender.generalArchitecture + "/" + Platform.RESOURCE_PREFIX + "/" + mpfrLinuxLib};
        }

        String resourcesDir = "/fractalzoomer/native";

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

        String[] libs = new String[0];
        if(Platform.isWindows()) {
            libs = winLibs;
        } else if(isMacArm64()) {
            libs = macosLibs;
        } else if(Platform.isLinux()) {
            libs = linuxLibs;
        }

        for(String lib : libs) {
            InputStream in = NativeLoader.class.getResourceAsStream(resourcesDir + "/" + lib);
            if(in == null) {
                throw new Exception("The resource " + resourcesDir + "/" + lib + " is not available in the jar file");
            }
            Path tgt = tmpdir.resolve(lib);
            Files.createDirectories(tgt.getParent());
            Files.copy(in, tgt);
            tgt.toFile().deleteOnExit();
        }

    }

    public static boolean isMacArm64() {
        return "darwin-aarch64".equals(Platform.RESOURCE_PREFIX);
    }
}
