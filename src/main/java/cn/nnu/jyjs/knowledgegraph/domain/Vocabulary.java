package cn.nnu.jyjs.knowledgegraph.domain;

/**
 * 词类，包括词名、词描述、频率、所属类、
 */
public class Vocabulary {
	
	private String NatureStr;
	private int frequence;
	private String desciption;
	private int classid;
	private int dicid;
	
	public Vocabulary(String _NatureStr, int frequence) {
		this.NatureStr = _NatureStr;
		this.frequence = frequence;
	}

	public Vocabulary(String _NatureStr) {
	    this.NatureStr = _NatureStr;
	    frequence = 1;
		
	}

	public Vocabulary(){}

	public String getNatureStr() {
		return NatureStr;
	}

	public int getClassid() {
		return classid;
	}

	public void setNatureStr(String nature) {
		this.NatureStr = nature;
	}

	public void setFrequence(int hz) {
		this.frequence = hz;
	}

	public int getFrequence() {
		return frequence;
	}


	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

	public void setClassid(int classid) {
		this.classid = classid;
	}

	public int getDicid() {
		return dicid;
	}

	public void setDicid(int dicid) {
		this.dicid = dicid;
	}

	@Override
	public String toString() {
		return "NatureStr : "+NatureStr+"\t"+"频率 : "+frequence;
	}
}
