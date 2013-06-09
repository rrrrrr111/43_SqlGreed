package ru.roman.bim.service.subtitlesmerge.creator;

import org.apache.commons.io.FileUtils;
import ru.roman.bim.service.subtitlesmerge.dto.MergedFragmentModel;
import ru.roman.bim.service.subtitlesmerge.dto.MergingResultDto;
import ru.roman.bim.service.subtitlesmerge.dto.ParsedData;

import java.io.File;

/** @author Roman 09.06.13 13:29 */
public class HtmlCreator extends AbstractCreator {


    public String createHtml(ParsedData parsedData, MergingResultDto data) {
        try {

            String fileName = data.getFirstFileName();
            final StringBuilder resultStr = new StringBuilder();

            resultStr.append("<html>\n<head>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" +
                    "  <style>\n" +
                    "   .mainText {\n" +
                    "    font-family: Verdana, Arial, Helvetica, sans-serif; /* Гарнитура текста */ \n" +
                    "    font-size: 12pt; \n" +
                    "    color: black; \n" +
                    "   } \n" +
                    "   .transl {\n" +
                    "    font-family: 'Times New Roman', Times, serif; \n" +
                    "    font-size: 8pt; /* Размер шрифта в пунктах */ \n" +
                    "    color: blue; \n" +
                    "   }\n" +
                    "  </style>" +
                    "</head>\n<body>\n");

            for (MergedFragmentModel model : data.getList()) {
                resultStr.append("<text class='mainText'>")
                        .append(model.getFirstTextFragment())
                        .append("</text>");
                if (model.getSecondTextFragment() != null) {
                    resultStr.append("&nbsp;&nbsp;<i class='transl'>")
                            .append(model.getSecondTextFragment())
                            .append("</i>");
                }
                resultStr.append("<br/>\n");
            }

            resultStr.append("</body>\n</html>");

            fileName = removeExtension(fileName) + "_merged.html";
            final File resultFile = FileUtils.getFile(fileName);
            FileUtils.write(resultFile, resultStr, "UTF-8", false);
            return fileName;

        } catch (Exception e) {
            throw new RuntimeException("Error while creation html file", e);
        }
    }
}
