package com.mercator.docker.dashboard.commandShell;

import com.mercator.docker.dashboard.domain.DockerContainer;
import com.mercator.docker.dashboard.domain.DockerHeaders;
import com.mercator.docker.dashboard.domain.HeaderElement;

import java.util.*;

import static com.mercator.docker.dashboard.common.CommonSettings.NEW_LINE_CHARACTER;

/**
 * Created by Joro on 23.04.2017.
 */
public class CommandAnalyzer {
    private Map<DockerHeaders, HeaderElement> headerElementMap = new HashMap<>();
    private CommandExecutor commandExecutor = new CommandExecutor();

    public void analyzeCommand(String command) throws Exception {
        commandExecutor.executeCommand(command);
        analyzeHeaderRow(commandExecutor.getExecutionResult());
    }

    public DockerContainer extractContainerInfoFromLine(String line) {
        return new DockerContainer(extractElementInfo(DockerHeaders.CONTAINER_ID, line),
                extractElementInfo(DockerHeaders.IMAGE, line), extractElementInfo(DockerHeaders.COMMAND, line),
                extractElementInfo(DockerHeaders.CREATED, line), extractElementInfo(DockerHeaders.STATUS, line),
                extractElementInfo(DockerHeaders.PORTS, line), extractElementInfo(DockerHeaders.NAMES, line));
    }

    public List<String> getResultLines() {
        String[] lines = commandExecutor.getExecutionResult().split(NEW_LINE_CHARACTER);

        List<String> responseLines = new ArrayList<>();
        responseLines.addAll(Arrays.asList(lines).subList(1, lines.length));
        return responseLines;
    }

    private Map<DockerHeaders, HeaderElement> getHeaderElementMap() {
        return headerElementMap;
    }

    private void analyzeHeaderRow(String executionResult) throws Exception {
        String[] lines = executionResult.split(NEW_LINE_CHARACTER);

        if (lines.length > 1) {
            String headerRow = lines[0];
            addHeaderElement(DockerHeaders.CONTAINER_ID, headerRow, DockerHeaders.IMAGE);
            addHeaderElement(DockerHeaders.IMAGE, headerRow, DockerHeaders.COMMAND);
            addHeaderElement(DockerHeaders.COMMAND, headerRow, DockerHeaders.CREATED);
            addHeaderElement(DockerHeaders.CREATED, headerRow, DockerHeaders.STATUS);
            addHeaderElement(DockerHeaders.STATUS, headerRow, DockerHeaders.PORTS);
            addHeaderElement(DockerHeaders.PORTS, headerRow, DockerHeaders.NAMES);
            addHeaderElement(DockerHeaders.NAMES, headerRow, DockerHeaders.NONE);
        }
    }

    private void addHeaderElement(DockerHeaders headerElement, String headerRow, DockerHeaders nextHeaderElement)
            throws Exception {
        String headerElementName = headerElement.getHeaderName();
        int index = headerRow.indexOf(headerElementName);
        if (index < 0) {
            throw new Exception(String.format("Header Element [%s] was not found", headerElementName));
        }

        int nextElementIndex = -1;
        if (!nextHeaderElement.equals(DockerHeaders.NONE)) {
            nextElementIndex = headerRow.indexOf(nextHeaderElement.getHeaderName());
        }

        headerElementMap.put(headerElement, new HeaderElement(index, nextElementIndex));
    }

    private String extractElementInfo(DockerHeaders dockerHeader, String line) {
        HeaderElement headerElement = getHeaderElementMap().get(dockerHeader);
        int endIndex = headerElement.getEndIndex();
        if (endIndex < 0) {
            endIndex = line.length();
        }
        String elementInfo = line.substring(headerElement.getStartIndex(), endIndex);
        return elementInfo.trim();
    }

}
