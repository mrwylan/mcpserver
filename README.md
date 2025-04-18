* my own mcp server - a simple list of colors and hex values

This simple demo is based on the youtoube tutorial from dan vega.

[Dan Vega, mcp server tutorial](https://youtu.be/w5YVHG1j3Co?si=jBv4C1hch-Ub2ee4)

[See Spring Boot documentation](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-server-boot-starter-docs.html#_creating_a_spring_boot_application_with_mcp_server)

** prerequisite

To demo the running mcp server i used claude desktop installation.
Developer mode should be enabled.

claude_desktop_config.json
````json
{
  "mcpServers": {
    "localmcp": {
      "type": "command",
      "command": "D:\\jdks\\jdk-21+35\\bin\\java.exe",
      "args": [
	    "-Dspring.ai.mcp.server.stdio=true",
	    "-jar", 
		"D:\\ws\\ws_mcp_server\\my_mcp_server\\target\\mymcp-0.0.1-SNAPSHOT.jar"
	  ]
    }
  }
}
````

Replace the settings with your specific build and java path.

Run `mvn package` to build the jar.

** what the mcp exposes

The server exposes a list of color name, hex value pairs. 
With this list you can interact: list all, get one, add one color entry.

** a chat, done with this demo

[Claude Chat Link](https://claude.ai/share/bc9dc8aa-3c41-49eb-988c-a2b620953883)