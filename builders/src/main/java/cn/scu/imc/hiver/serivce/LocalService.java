package cn.scu.imc.hiver.serivce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
@Slf4j
@Service
public class LocalService {


    public String execCommand(List<String> commands) {
        Runtime runtime = Runtime.getRuntime();
        commands.stream().forEach(command -> {
            try {
                Process process = runtime.exec(command);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            } catch (IOException e) {
                log.error("Exec command " + command + "Error: " + e.getMessage());
                return;
            }
        });
        return null;
    }




}
