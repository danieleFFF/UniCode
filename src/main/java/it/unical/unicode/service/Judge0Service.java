package it.unical.unicode.service;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.regex.*;

@Service
public class Judge0Service {

    public Map<String, Object> execute(String languageId, String code, String stdin) {
        Map<String, Object> response = new HashMap<>();

        try {
            String output = simulateExecution(languageId, code, stdin);

            response.put("stdout", output);
            response.put("stderr", "");
            response.put("compile_output", "");
            response.put("status", Map.of("id", 3, "description", "Accepted"));

        } catch (Exception e) {
            response.put("stdout", "");
            response.put("stderr", e.getMessage());
            response.put("compile_output", "");
            response.put("status", Map.of("id", 6, "description", "Compilation Error"));
        }

        return response;
    }

    private String simulateExecution(String languageId, String code, String stdin) {
        switch (languageId) {
            case "71":
                return simulatePython(code, stdin);
            case "54":
                return simulateCpp(code, stdin);
            case "62":
                return simulateJava(code, stdin);
            case "63":
                return simulateJavaScript(code, stdin);
            default:
                return stdin;
        }
    }

    private String simulatePython(String code, String stdin) {
        Map<String, Integer> variables = new HashMap<>();
        String[] inputs = stdin.trim().split("\\s+");
        int inputIndex = 0;

        if (!code.contains("input()") && stdin.isEmpty()) {
            throw new RuntimeException("Syntax Error: Expected input() calls");
        }

        String[] lines = code.split("\n");
        String lastPrintValue = "";

        for (String line : lines) {
            line = line.trim();

            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }

            if (!line.matches("^(import|from|def|class|if|else|elif|for|while|try|except|with|return|break|continue|pass|\\w+\\s*=|print\\(|input\\().*")) {
                throw new RuntimeException("Syntax Error: Invalid syntax at line: " + line);
            }

            if (line.contains("int(input())")) {
                Pattern pattern = Pattern.compile("(\\w+)\\s*=\\s*int\\(input\\(\\)\\)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find() && inputIndex < inputs.length) {
                    String varName = matcher.group(1);
                    try {
                        int value = Integer.parseInt(inputs[inputIndex++]);
                        variables.put(varName, value);
                    } catch (NumberFormatException e) {
                    }
                }
            }
            else if (line.contains("input()")) {
                Pattern pattern = Pattern.compile("(\\w+)\\s*=\\s*input\\(\\)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find() && inputIndex < inputs.length) {
                    String varName = matcher.group(1);
                    variables.put(varName, 0);
                }
            }
            else if (line.startsWith("print(")) {
                String printContent = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")")).trim();

                printContent = printContent.replaceAll("\"", "").replaceAll("'", "");

                if (printContent.contains("+") || printContent.contains("-") ||
                        printContent.contains("*") || printContent.contains("/")) {
                    lastPrintValue = evaluateExpression(printContent, variables);
                } else if (variables.containsKey(printContent)) {
                    lastPrintValue = String.valueOf(variables.get(printContent));
                } else {
                    lastPrintValue = printContent;
                }
            }
            else if (line.contains("=") && !line.contains("input()")) {
                Pattern pattern = Pattern.compile("(\\w+)\\s*=\\s*(.+)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String varName = matcher.group(1);
                    String expression = matcher.group(2).trim();

                    if (expression.matches("\\d+")) {
                        variables.put(varName, Integer.parseInt(expression));
                    } else {
                        try {
                            int result = Integer.parseInt(evaluateExpression(expression, variables));
                            variables.put(varName, result);
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
        }

        return lastPrintValue;
    }

    private String evaluateExpression(String expression, Map<String, Integer> variables) {
        expression = expression.replaceAll("\\s+", "");

        for (Map.Entry<String, Integer> entry : variables.entrySet()) {
            expression = expression.replaceAll("\\b" + entry.getKey() + "\\b",
                    String.valueOf(entry.getValue()));
        }

        try {
            if (expression.contains("+")) {
                String[] parts = expression.split("\\+");
                int sum = 0;
                for (String part : parts) {
                    sum += Integer.parseInt(part.trim());
                }
                return String.valueOf(sum);
            }
            else if (expression.contains("-")) {
                String[] parts = expression.split("-");
                int result = Integer.parseInt(parts[0].trim());
                for (int i = 1; i < parts.length; i++) {
                    result -= Integer.parseInt(parts[i].trim());
                }
                return String.valueOf(result);
            }
            else if (expression.contains("*")) {
                String[] parts = expression.split("\\*");
                int product = 1;
                for (String part : parts) {
                    product *= Integer.parseInt(part.trim());
                }
                return String.valueOf(product);
            }
            else if (expression.contains("/")) {
                String[] parts = expression.split("/");
                int result = Integer.parseInt(parts[0].trim());
                for (int i = 1; i < parts.length; i++) {
                    result /= Integer.parseInt(parts[i].trim());
                }
                return String.valueOf(result);
            }
            else {
                return expression;
            }
        } catch (Exception e) {
            return expression;
        }
    }

    private String simulateCpp(String code, String stdin) {
        Map<String, Integer> variables = new HashMap<>();
        String[] inputs = stdin.trim().split("\\s+");
        int inputIndex = 0;
        String lastOutput = "";

        String[] lines = code.split("\n");

        for (String line : lines) {
            line = line.trim();

            if (line.contains("cin >>")) {
                Pattern pattern = Pattern.compile("cin\\s*>>\\s*(\\w+)");
                Matcher matcher = pattern.matcher(line);
                while (matcher.find() && inputIndex < inputs.length) {
                    String varName = matcher.group(1);
                    try {
                        int value = Integer.parseInt(inputs[inputIndex++]);
                        variables.put(varName, value);
                    } catch (NumberFormatException e) {
                    }
                }
            }
            else if (line.contains("cout <<")) {
                String output = line.substring(line.indexOf("<<") + 2).trim();
                output = output.replaceAll(";", "").trim();
                output = output.replaceAll("<<", "").trim();
                output = output.replaceAll("\"", "");

                if (variables.containsKey(output)) {
                    lastOutput = String.valueOf(variables.get(output));
                } else if (output.matches(".*[+\\-*/].*")) {
                    lastOutput = evaluateExpression(output, variables);
                } else {
                    lastOutput = output;
                }
            }
        }

        return lastOutput;
    }

    private String simulateJava(String code, String stdin) {
        Map<String, Integer> variables = new HashMap<>();
        String[] inputs = stdin.trim().split("\\s+");
        int inputIndex = 0;
        String lastOutput = "";

        String[] lines = code.split("\n");

        for (String line : lines) {
            line = line.trim();

            if (line.contains("nextInt()")) {
                Pattern pattern = Pattern.compile("(\\w+)\\s*=\\s*\\w+\\.nextInt\\(\\)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find() && inputIndex < inputs.length) {
                    String varName = matcher.group(1);
                    try {
                        int value = Integer.parseInt(inputs[inputIndex++]);
                        variables.put(varName, value);
                    } catch (NumberFormatException e) {
                    }
                }
            }
            else if (line.contains("System.out.println")) {
                String output = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")")).trim();
                output = output.replaceAll("\"", "");

                if (variables.containsKey(output)) {
                    lastOutput = String.valueOf(variables.get(output));
                } else if (output.matches(".*[+\\-*/].*")) {
                    lastOutput = evaluateExpression(output, variables);
                } else {
                    lastOutput = output;
                }
            }
        }

        return lastOutput;
    }

    private String simulateJavaScript(String code, String stdin) {
        Map<String, Integer> variables = new HashMap<>();
        String[] inputs = stdin.trim().split("\\s+");
        int inputIndex = 0;
        String lastOutput = "";

        String[] lines = code.split("\n");

        for (String line : lines) {
            line = line.trim();

            if (line.contains("prompt(") || line.contains("readline(")) {
                Pattern pattern = Pattern.compile("(\\w+)\\s*=\\s*.*\\(.*\\)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find() && inputIndex < inputs.length) {
                    String varName = matcher.group(1);
                    try {
                        int value = Integer.parseInt(inputs[inputIndex++]);
                        variables.put(varName, value);
                    } catch (NumberFormatException e) {
                    }
                }
            }
            else if (line.contains("console.log")) {
                String output = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")")).trim();
                output = output.replaceAll("\"", "").replaceAll("'", "");

                if (variables.containsKey(output)) {
                    lastOutput = String.valueOf(variables.get(output));
                } else if (output.matches(".*[+\\-*/].*")) {
                    lastOutput = evaluateExpression(output, variables);
                } else {
                    lastOutput = output;
                }
            }
        }

        return lastOutput;
    }
}