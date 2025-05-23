package info.wylan.mymcp;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class MyMcpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyMcpServerApplication.class, args);
    }

    @Bean
    public List<ToolCallback> colorTools(ColorTranslationService colorTranslationService) {
        return List.of(ToolCallbacks.from(colorTranslationService));
    }

}
