package cn.nnu.jyjs.knowledgegraph.domain;

import java.util.List;

public class FileContent {

	public String filename;
	public StringBuffer str;
	public List<Vocabulary> words;
	public FileContent(){

	}
	public FileContent(String fileName, String str){
		this.filename =fileName;
		this.str = new StringBuffer(str);
	}
	public void setWords(List<Vocabulary> lists){ this.words = lists;}
	public List<Vocabulary> getWords(){ return words;}
	public void setStr(StringBuffer str){this.str = str;}
	public String getStr(){return str.toString();}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilename() {
		return filename;
	}
}
