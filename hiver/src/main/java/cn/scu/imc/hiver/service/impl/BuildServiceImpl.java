package cn.scu.imc.hiver.service.impl;


import cn.hutool.core.io.FileUtil;
import cn.scu.imc.hiver.bo.BuildHistoryVo;
import cn.scu.imc.hiver.entity.Build;
import cn.scu.imc.hiver.entity.Project;
import cn.scu.imc.hiver.entity.User;
import cn.scu.imc.hiver.netty.rpc.client.ClientBusiHandler;
import cn.scu.imc.hiver.netty.rpc.client.FileClientHandler;
import cn.scu.imc.hiver.netty.vo.Command;
import cn.scu.imc.hiver.repository.BuildRepository;
import cn.scu.imc.hiver.service.IBuildService;
import cn.scu.imc.hiver.service.IProjectService;
import cn.scu.imc.hiver.service.IUserService;
import cn.scu.imc.hiver.utils.HiveUtil;
import cn.scu.imc.hiver.utils.PropertiesUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class BuildServiceImpl implements IBuildService {

    private final static String FILESEPARATOR = System.getProperty("file.separator");
    private final static String LOGFILE = "build.log";
    private final static String BLANK = "        ";
    private final static String ZIPFILE= ".zip";


    @Resource
    private IProjectService projectService;
    @Resource
    private BuildRepository buildRepository;
    @Resource
    private FileClientHandler fileClientHandler;
    @Resource
    private ClientBusiHandler clientBusiHandler;
    @Resource
    private IUserService userService;


    @Transactional
    public void build(Integer projectId) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Project project = projectService.getByProjectId(projectId);
        List<Build> buildHistory = buildRepository.findByProjectId(projectId);
        Optional<Build> latest = buildHistory.stream().max(Comparator.comparing(Build::getCreateDate));
        Integer version = latest.isPresent() ? latest.get().getVersion() : 1;

        String workspace = (String) PropertiesUtils.getValueByKey("hive.hive.workspace");
        String workDir = workspace + FILESEPARATOR + project.getProjectName() + FILESEPARATOR + version;
        File workspaceDir = new File(workDir);
        if (!FileUtil.exist(workspaceDir)) {
            workspaceDir.mkdirs();
        }
        File logFile = new File(workDir + FILESEPARATOR + LOGFILE);

        setBuildHeader(logFile, project.getProjectName(), project.getBranch());
        //download repository
        downLoadRepository(workspaceDir, logFile, project.getBranch(), project.getRepository());
        String pipelineScript = project.getPipelineScript();

        ObjectMapper mapper = new ObjectMapper();
        try {
            // 读取 JSON 文件
            JsonNode rootNode = mapper.readTree(pipelineScript);
            // 解析 pipeline
            JsonNode pipelineNode = rootNode.path("pipeline");
            System.out.println("Agent: " + pipelineNode.path("agent").asText());

            //compressFolder
            compressedFolder(workDir, project.getProjectName());
            File sourceDir = new File(workDir + FILESEPARATOR + project.getProjectName());
            if (sourceDir.exists()) {
                sourceDir.delete();
            }
            fileClientHandler.uploadFile(logFile, new File(workDir + FILESEPARATOR + project.getProjectName() + ZIPFILE),
                    project.getProjectName(), version, 1);
            // 解析 stages
            JsonNode stagesNode = pipelineNode.path("stages");

            for (JsonNode stageNode : stagesNode) {
                StringBuffer commands = new StringBuffer();
                System.out.println("Stage Name: " + stageNode.path("name").asText());
                // 解析 steps
                for (JsonNode stepNode : stageNode.get("steps")) {
                    System.out.println("  Step Name: " + stepNode.path("name").asText());
                    System.out.println("  Step Type: " + stepNode.path("type").asText());
                    if (stepNode.has("command")) {
                        commands.append(stepNode.path("command").asText() + ";");
                    }
                }
                if (commands.length() > 0) {
                    clientBusiHandler.send(new Command(project.getProjectName(), version, stageNode.path("name").asText(),commands.toString()));
                }

            }
            stopWatch.stop();
            Build build = new Build();
            build.setProjectId(projectId);
            build.setVersion(version);
            build.setStatus(0);
            build.setDuration(Long.valueOf((long)stopWatch.getTotalTimeSeconds()));
            buildRepository.save(build);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }



    public static void compressedFolder(String workDir, String projectName) {
        try {
            FileOutputStream fos = new FileOutputStream(workDir + FILESEPARATOR + projectName + ZIPFILE);
            ZipOutputStream zos = new ZipOutputStream(fos);

            File folder = new File(workDir + FILESEPARATOR + projectName);
            addFolderToZip(folder, "", zos);

            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static void addFolderToZip(File folder, String parentFolder, ZipOutputStream zos) throws IOException {
        byte[] buffer = new byte[1024];

        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                addFolderToZip(file, parentFolder + file.getName() + "/", zos);
            } else {
                FileInputStream fis = new FileInputStream(file);
                zos.putNextEntry(new ZipEntry(parentFolder + file.getName()));

                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                fis.close();
            }
        }
    }



    private void downLoadRepository(File workDir, File logFile, String branch, String repository) throws IOException {
        //download repository
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("git clone -b " + branch + " " + repository, null, workDir);
        // 创建用于写入日志的文件
        BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
        try {

            writer.write("【Stage Name】: 下载源码 \n");
            writer.write(BLANK + HiveUtil.now() +" clone from: " + repository +  "\n");
            // 读取标准输出
            BufferedReader stdOutput = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
            String line;
            while ((line = stdOutput.readLine()) != null) {
                writer.write(BLANK + HiveUtil.now() + " STDOUT: " + line);
                writer.newLine();
            }
            stdOutput.close();
            // 读取标准错误
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = stdError.readLine()) != null) {
                writer.write(BLANK + HiveUtil.now()  + line);
                writer.newLine();
            }

            stdError.close();
            // 等待进程结束
            process.waitFor();
        }catch (IOException | InterruptedException e) {
            writer.write(BLANK + HiveUtil.now() + " ERROR: " + e.getMessage());
        }finally {
            // 确保所有数据都写入文件
            writer.flush();
            writer.close();
        }
    }

    private void setBuildHeader(File logFile, String projectName, String branch) {
        try {
            User user = HiveUtil.getCurrentUser();
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
            writer.write("【构建者："+ user.getUserName() +"】\n");
            writer.write("【构建时间："+ HiveUtil.formatDate(new Date(), "yyyy-HH-mm HH:mm:ss") +"】\n");
            writer.write("【构建项目："+ projectName +"】\n");
            writer.write("【构建分支 " + branch + "】\n");
            writer.write("\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BuildHistoryVo> getHistoryBuild(Integer projectId) {
        List<Build> build = buildRepository.findByProjectId(projectId);
         return build.stream().map(item -> {
            User buildUser = userService.findById(item.getCreateId());
            return new BuildHistoryVo(buildUser.getUserName(), HiveUtil.convertSecondsToTime(item.getDuration()),
                    item.getCreateDate(), Integer.valueOf(0).equals(item.getStatus()) ? "成功" : "失败");
        }).collect(Collectors.toList());
    }
}
