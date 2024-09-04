package cn.scu.imc.hiver.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PipleLineUtils {



    public static void main(String[] args) {
        String pipelineScript = "{\n" +
                "  \"pipeline\": {\n" +
                "    \"agent\": \"any\",\n" +
                "    \"stages\": [\n" +
                "      {\n" +
                "        \"name\": \"Build\",\n" +
                "        \"steps\": [\n" +
                "          {\"name\": \"Checkout\", \"type\": \"git\"},\n" +
                "          {\"name\": \"Compile\", \"type\": \"shell\", \"command\": \"mvn clean install\"}\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"Test\",\n" +
                "        \"steps\": [\n" +
                "          {\"name\": \"Run Tests\", \"type\": \"shell\", \"command\": \"mvn test\"}\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        try {
            // 读取 JSON 文件
           // JsonNode rootNode = mapper.readTree(new File("C:\\Users\\Administrator\\Desktop\\pipeline.txt"));
            JsonNode rootNode = mapper.readTree(pipelineScript);
            // 解析 pipeline
            JsonNode pipelineNode = rootNode.path("pipeline");
            System.out.println("Agent: " + pipelineNode.path("agent").asText());

            // 解析 stages
            JsonNode stagesNode = pipelineNode.path("stages");
            for (JsonNode stageNode : stagesNode) {
                System.out.println("Stage Name: " + stageNode.path("name").asText());

                // 解析 steps
                JsonNode stepsNode = stageNode.path("steps");
                for (JsonNode stepNode : stepsNode) {
                    System.out.println("  Step Name: " + stepNode.path("name").asText());
                    System.out.println("  Step Type: " + stepNode.path("type").asText());
                    if (stepNode.has("command")) {
                        System.out.println("  Command: " + stepNode.path("command").asText());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
