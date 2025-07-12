# Guidelines for My MCP Server

This document provides guidelines for using, extending, and contributing to the My MCP Server project, a simple color translation service that provides color names and their corresponding hex values.

## Usage Guidelines

### Setting Up the MCP Server

1. Build the project using Maven:
   ```
   mvn package
   ```

2. Configure Claude Desktop to use the local MCP server by updating your `claude_desktop_config.json`:
   ```json
   {
     "mcpServers": {
       "localmcp": {
         "type": "command",
         "command": "<path-to-java>",
         "args": [
           "-Dspring.ai.mcp.server.stdio=true",
           "-jar", 
           "<path-to-jar>/mymcp-0.0.1-SNAPSHOT.jar"
         ]
       }
     }
   }
   ```

3. Ensure Developer mode is enabled in Claude Desktop.

### Interacting with the MCP Server

The server exposes the following tools:

1. `my_mcp_colors` - Get a list of all available colors
2. `my_mcp_color` - Get a specific color by name
3. `my_mcp_add_color` - Add a new color with a name and hex value

## Development Guidelines

### Adding New Colors

When adding new colors to the default set:

1. Follow the existing pattern in `ColorTranslationService.initializeDefaultColors()`
2. Use standard color names that are widely recognized
3. Ensure hex values are in the format `#RRGGBB` (e.g., `#FF0000` for red)

### Extending Functionality

If you want to extend the functionality of the MCP server:

1. Add new methods to the `ColorTranslationService` class or create new service classes
2. Annotate methods with `@Tool` to expose them as MCP tools
3. Provide clear descriptions for each tool
4. Implement proper validation for inputs
5. Return meaningful results or error messages

### Code Style

1. Follow Java conventions for naming:
   - Classes: PascalCase (e.g., `HexColor`)
   - Methods and variables: camelCase (e.g., `getColorHex`)
   - Constants: UPPER_SNAKE_CASE

2. Include JavaDoc comments for all public methods
3. Use meaningful variable and method names
4. Keep methods focused on a single responsibility

## Testing Guidelines

1. Write unit tests for all new functionality
2. Test edge cases (null inputs, invalid hex values, etc.)
3. Run the full test suite before submitting changes:
   ```
   mvn test
   ```

## Contribution Guidelines

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Write or update tests
5. Ensure all tests pass
6. Submit a pull request with a clear description of your changes

## Best Practices

1. Keep the MCP server lightweight and focused on its core functionality
2. Document any new tools or features
3. Maintain backward compatibility when possible
4. Follow the existing architecture and patterns