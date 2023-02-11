package com.yi.udfs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author YI
 * @description 主要用于复制插件
 * @date create in 2021/8/17 15:06
 */
public class PrestoMain {
    private static Logger logger = LoggerFactory.getLogger(PrestoMain.class);

    public static void main(String[] args) {
        try {
            runCommand("/usr/bin/aws s3 cp s3://kylin-data/presto-third-udfs.jar /home/hadoop/");
            Thread.sleep(5000);
            runCommand("sudo mv presto-third-udfs.jar /usr/lib/presto/plugin/hive-hadoop2/");
            Thread.sleep(5000);
            runCommand("");
            Thread.sleep(5000);
            runCommand("sudo systemctl start presto-server.service");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接shell命令
     *
     * @param command 命令
     * @throws IOException
     * @throws InterruptedException
     */
    private static void runCommand(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);
        InputStream is = process.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String message;
        while ((message = br.readLine()) != null) {
            System.out.println(message);
        }
        is.close();
        process.waitFor();
    }
}
