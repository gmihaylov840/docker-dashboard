package com.mercator.docker.dashboard.commandShell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import static com.mercator.docker.dashboard.common.CommonSettings.NEW_LINE_CHARACTER;

/**
 * Created by Joro on 23.04.2017.
 */
public class CommandExecutor {

    private StringBuilder output;

    public String getExecutionResult() {
        return output.toString();
    }

    public String executeCommand(String... commands) {
        StringBuilder commandBuilder = new StringBuilder();
        for (int i = 0; i < commands.length; ++i) {
            commandBuilder.append(commands[i]);
            if (i < commands.length - 1) {
                commandBuilder.append(" & ");
            }
        }
        System.out.println("Command: " + commandBuilder.toString());

        try {
            Process process = Runtime.getRuntime().exec("cmd /c " + commandBuilder.toString());
//            process.waitFor();
            OutputStream out = process.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append(NEW_LINE_CHARACTER);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println(getExecutionResult());
        return getExecutionResult();
    }

//    private String executeCommand() {
//
//        StringBuffer output = new StringBuffer();
//
//        Process p;
//        try {
//            String[] cmd = new String[]{"cmd /C start cmd.exe"};
//            p = Runtime.getRuntime().exec(cmd);
//            p.waitFor();
//            BufferedReader reader =
//                    new BufferedReader(new InputStreamReader(p.getInputStream()));
//
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                output.append(line + "\n");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return output.toString();
//
//    }
}
