package com.ecnu.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class Main {

    public static void main(String[] args) {
        String sourceFileName = Config.directoryName + File.separator + Config.fileName;
        String codeContent = readSourceCode(sourceFileName);
        // System.out.println(codeContent);
        updateProgramName();

        Compiler compiler = new Compiler();
        compiler.compile(codeContent);

        compiler.execute();

    }

    /**
     * read the source code file.
     * @param sourceFileName the path of the file
     * @return code content with the string type
     */
    private static String readSourceCode(String sourceFileName) {
        File sourceFile = new File(sourceFileName);
        StringBuilder codeBuffer = new StringBuilder();
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile), StandardCharsets.UTF_8))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                codeBuffer.append(line).append("\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return codeBuffer.toString();
    }

    /**
     * the functions' name in different code files are different
     */
    private static void updateProgramName() {
        String prefix = Config.fileName.split("\\.")[0];
        Config.subProgramName[0] = prefix + "_main";
        Config.subProgramName[1] = prefix + "_isr_1";
        Config.subProgramName[2] = prefix + "_isr_2";
    }

}
