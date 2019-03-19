package cn.nnu.jyjs.knowledgegraph.Interface;

import java.io.File;

public interface Reader {
    StringBuffer readFiletoString(String filePath);
    File readFiletoFile(String filePath);

}
