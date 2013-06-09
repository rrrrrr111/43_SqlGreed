package ru.roman.bim.service.subtitlesmerge.creator;

import org.apache.commons.io.FileUtils;
import ru.roman.bim.service.subtitlesmerge.dto.MergedFragmentModel;
import ru.roman.bim.service.subtitlesmerge.dto.MergingResultDto;
import ru.roman.bim.service.subtitlesmerge.dto.ParsedData;

import java.io.File;

/** @author Roman 09.06.13 12:37 */
public class SrtCreator extends AbstractCreator {



    public void createSrt(ParsedData parsedData, MergingResultDto data) {
        try {

            String firstFileName = data.getFirstFileName();
            final StringBuilder resultStr = new StringBuilder();

            for (MergedFragmentModel model : data.getList()) {
                resultStr.append(model.getNum())
                        .append("\n")
                        .append(model.getStartTime())
                        .append(" --> ")
                        .append(model.getStopTime())
                        .append("\n")
                        .append(model.getFirstTextFragment());
                if (model.getSecondTextFragment() != null) {
                    resultStr.append("\n")
                            .append("<i>")
                            .append(model.getSecondTextFragment())
                            .append("</i>");
                }
                resultStr.append("\n\n");
            }


            firstFileName = removeExtension(firstFileName) + "_merged.srt";
            final File resultFile = FileUtils.getFile(firstFileName);
            FileUtils.write(resultFile, resultStr, "Windows-1251", false);


        } catch (Exception e) {
            throw new RuntimeException("Error while creation srt file", e);
        }
    }


}