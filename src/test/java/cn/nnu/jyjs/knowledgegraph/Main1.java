package cn.nnu.jyjs.knowledgegraph;

import cn.nnu.jyjs.knowledgegraph.domain.Assmble;
import cn.nnu.jyjs.knowledgegraph.domain.Vocabulary;
import cn.nnu.jyjs.knowledgegraph.tools.*;
import com.hankcs.hanlp.HanLP;
import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.json.JSONObject;

import java.io.File;
import java.util.*;

public class Main1 {
    private static String input = "数学是研究数量关系和空间形式的一门科学。数学源于对现实世界的抽象，基于抽象结构，通过符号运算、形式推理、模型构建等，理解和表达现实世界中事物的本质、关系和规律。数学与人类生活和社会发展紧密关联。数学不仅是运算和推理的工具，还是表达和交流的语言。数学承载着思想和文化，是人类文明的重要组成部分。数学是自然科学的重要基础，并且在社会科学中发挥越来越大的作用，数学的应用已渗透到现代社会及人们日常生活的各个方面。随着现代科学技术特别是计算机科学、人工智能的迅猛发展，人们获取数据和处理数据的能力都得到很大的提升，伴随着大数据时代的到来，人们常常需要对网络、文本、声音、图像等反映的信息进行数字化处理，这使数学的研究领域与应用领域得到极大拓展。数学直接为社会创造价值，推动社会生产力的发展。\n数学在形成人的理性思维、科学精神和促进个人智力发展的过程中发挥着不可替代的作用。数学素养是现代社会每一个人应该具备的基本素养。\n数学教育承载着落实立德树人根本任务、发展素质教育的功能。数学教育帮助学生掌握现代生活和进一步学习所必需的数学知识、技能、思想和方法；提升学生的数学素养，引导学生会用数学眼光观察世界，会用数学思维思考世界，会用数学语言表达世界；促进学生思维能力、实践能力和创新意识的发展，探寻事物变化规律，增强社会责任感；在学生形成正确人生观、价值观、世界观等方面发挥独特作用。\n高中数学课程是义务教育阶段后普通高级中学的主要课程，具有基础性、选择性和发展性。必修课程面向全体学生，构建共同基础；选择性必修课程、选修课程充分考虑学生的不同成长需求，提供多样性的课程供学生自主选择；高中数学课程为学生的可持续发展和终身学习创造条件。\n（二）基本理念\n1.学生发展为本，立德树人，提升素养\n高中数学课程以学生发展为本，落实立德树人根本任务，培育科学精神和创新意识，提升数学学科核心素养。高中数学课程面向全体学生，实现：人人都能获得良好的数学教育，不同的人在数学上得到不同的发展。\n2.优化课程结构，突出主线，精选内容\n高中数学课程体现社会发展的需求、数学学科的特征和学生的认知规律，发展学生数学学科核心素养。优化课程结构，为学生发展提供共同基础和多样化选择；突出数学主线，凸显数学的内在逻辑和思想方法；精选课程内容，处理好数学学科核心素养与知识技能之间的关系，强调数学与生活以及其他学科的联系，提升学生应用数学解决实际问题的能力，同时注重数学文化的渗透。\n3.把握数学本质，启发服考，改进教学\n高中数学教学以发展学生数学学科核心素养为导向，创设合适的教学情境，启发学生思考，引导学生把握数学内容的本质。提倡独立思考、自主学习、合作交流等多种学习方式，激发学习数学的兴趣，养成良好的学习习惯，促进学生实践能力和创新意识的发展。注重信息技术与数学课程的深度融合，提高教学的实效性。不断引导学生感悟数学的科学价值、应用价值、文化价值和审关价值。学=科网\n4.重视过程评价，聚焦素养，提高质量\n高中数学学习评价关注学生知识技能的掌握，更关注数学学科核心素养的形成和发展，制定科学合理的学业质量要求，促进学生在不同学习阶段数学学科核心素养水平的达成。评价既要关注学生学习的结果，更要重视学生学习的过程。开发合理的评价工具，将知识技能的掌握与数学学科核心素养的达成有机结合，建立目标多元、方式多样、重视过程的评价体系。通过评价，提高学生学习兴趣，帮助学生认识自我，增强自信；帮助教师改进教学，提高质量。\n二、学科核心素养与课程目标\n（一）学科核心素养\n学科核心素养是育人价值的集中体现，是学生通过学科学习而逐步形成的正确价值观念、必备品格和关键能力。数学学科核心素养是数学课程目标的集中体现，是具有数学基本特征的思维品质、关键能力以及情感、态度与价值观的综合体现，是在数学学习和应用的过程中逐步形成和发展的。数学学科核心素养包括：数学抽象、逻辑推理、数学建模、直观想象、数学运算和数据分析。这些数学学科核心素养既相对独立、又相互交融，是一个有机的整体。学科网\n1.数学抽象\n数学抽象是指通过对数量关系与空间形式的抽象，得到数学研究对象的素养。主要包括：从数量与数量关系、图形与图形关系中抽象出数学概念及概念之间的关系，从事物的具体背景中抽象出一般规律和结构，并用数学语言予以表征。\n" ;

    public static String file1Name = "1";

    public static String file1Content = " 集合\n" +
            "教材分析：集合概念及其基本理论，称为集合论，是近、现代数学的一个重要的基础，一方面，许多重要的数学分支，都建立在集合理论的基础上。另一方面，集合论及其所反映的数学思想，在越来越广泛的领域种得到应用。\n" +
            "课 型：新授课\n" +
            "教学目标：（1）通过实例，了解集合的含义，体会元素与集合的理解集合“属于”关系；\n" +
            "（2）能选择自然语言、图形语言、集合语言（列举法或描述法）描述不同的具体问题，感受集合语言的意义和作用；\n" +
            "教学重点：集合的基本概念与表示方法；\n" +
            "教学难点：运用集合的两种常用表示方法——列举法与描述法，正确表示一些简单的集合；\n" +
            "教学过程：\n" +
            "引入课题\n" +
            "军训前学校通知：8月15日8点，高一年段在体育馆集合进行军训动员；试问这个通知的对象是全体的高一学生还是个别学生？\n" +
            "在这里，集合是我们常用的一个词语，我们感兴趣的是问题中某些特定（是高一而不是高二、高三）对象的总体，而不是个别的对象，为此，我们将学习一个新的概念——集合（宣布课题），即是一些研究对象的总体。\n" +
            "阅读课本P2-P3内容\n" +
            "新课教学\n" +
            "（一）集合的有关概念\n" +
            "集合理论创始人康托尔称集合为一些确定的、不同的东西的全体，人们能意识到这些东西，并且能判断一个给定的东西是否属于这个总体。\n" +
            "一般地，研究对象统称为元素（element），一些元素组成的总体叫集合（set），也简称集。\n" +
            "思考1：课本P3的思考题，并再列举一些集合例子和不能构成集合的例子，对学生的例子予以讨论、点评，进而讲解下面的问题。\n" +
            "关于集合的元素的特征\n" +
            "（1）确定性：设A是一个给定的集合，x是某一个具体对象，则或者是A的元素，或者不是A的元素，两种情况必有一种且只有一种成立。\n" +
            "（2）互异性：一个给定集合中的元素，指属于这个集合的互不相同的个体（对象），因此，同一集合中不应重复出现同一元素。\n" +
            "（3）集合相等：构成两个集合的元素完全一样\n" +
            "元素与集合的关系；\n" +
            "（1）如果a是集合A的元素，就说a属于（belong to）A，记作a∈A\n" +
            "（2）如果a不是集合A的元素，就说a不属于（not belong to）A，记作aA（或a A）（举例）\n" +
            "常用数集及其记法\n" +
            "非负整数集（或自然数集），记作N\n" +
            "正整数集，记作N*或N+；\n" +
            "整数集，记作Z\n" +
            "有理数集，记作Q\n" +
            "实数集，记作R\n" +
            "（二）集合的表示方法\n" +
            "我们可以用自然语言来描述一个集合，但这将给我们带来很多不便，除此之外还常用列举法和描述法来表示集合。\n" +
            "列举法：把集合中的元素一一列举出来，写在大括号内。\n" +
            "如：{1，2，3，4，5}，{x2，3x+2，5y3-x，x2+y2}，…；\n" +
            "例1．（课本例1）\n" +
            "思考2，引入描述法\n" +
            "说明：集合中的元素具有无序性，所以用列举法表示集合时不必考虑元素的顺序。\n" +
            "描述法：把集合中的元素的公共属性描述出来，写在大括号{}内。\n" +
            "具体方法：在大括号内先写上表示这个集合元素的一般符号及取值（或变化）范围，再画一条竖线，在竖线后写出这个集合中元素所具有的共同特征。\n" +
            "如：{x|x-3>2}，{(x,y)|y=x2+1}，{直角三角形}，…；\n" +
            "例2．（课本例2）\n" +
            "说明：（课本P5最后一段）\n" +
            "思考3：（课本P6思考）\n" +
            "强调：描述法表示集合应注意集合的代表元素\n" +
            "{(x,y)|y= x2+3x+2}与 {y|y= x2+3x+2}不同，只要不引起误解，集合的代表元素也可省略，例如：{整数}，即代表整数集Z。\n" +
            "辨析：这里的{ }已包含“所有”的意思，所以不必写{全体整数}。下列写法{实数集}，{R}也是错误的。\n" +
            "说明：列举法与描述法各有优点，应该根据具体问题确定采用哪种表示法，要注意，一般集合中元素较多或有无限个元素时，不宜采用列举法。\n" +
            "（三）课堂练习（课本P6练习）\n" +
            "归纳小结\n" +
            "本节课从实例入手，非常自然贴切地引出集合与集合的概念，并且结合实例对集合的概念作了说明，然后介绍了集合的常用表示方法，包括列举法、描述法。\n" +
            "作业布置\n";

    public static String file2Name = "2";

    public static String file2Content = "高一数学集合与集合的表示方法、集合之间的关系、运算人教实（B）【本讲教育信息】一.教学内容：集合与集合的表示方法、集合之间的关系、运算 二、学习目标1、首先通过实例，引入集合与集合的元素的概念，接着给出了空集的含义。然后，学习了集合的两种表示方法（列举法和特征性质描述法）；从观察集合与集合之间元素的关系开始，给出子集、真子集以及集合相等的概念，同时学习了用维恩（Venn）图表示集合，学习了交集、并集以及全集、补集的初步知识。2、理解集合的表示法，能选择自然语言、图形语言、集合语言（列举法或描述法）描述不同的具体问题，感受集合语言的意义和作用．3、理解集合之间包含与相等的含义，能识别给定集合的子集．培养分析、比较、归纳的逻辑思维能力.4、能在具体情境中，了解全集与空集的含义．理解两个集合的并集与交集的含义，会求两个简单集合的交集与并集．培养从具体到抽象的思维能力．5、理解在给定集合中，一个子集的补集的含义，会求给定子集的补集．6、能使用Venn图表达集合的关系及运算，体会直观图示对理解抽象概念的作用． 三、知识要点1、集合①定义：某些指定的对象集在一起就成为一个集合，集合中的每个对象叫做这个集合的元素。②表示列举法：将集合中的元素一一列举出来，用大括号括起来，如{a，b，c}描述法：将集合中的元素的共同属性表示出来，形式为：P＝{x∣P（x）}.\n";

    public static void main(String[] args){
        //testPart();
        //testTFIDF(2.6,file1Name);
        //List<String> phraseList = HanLP.extractPhrase(input, 3);
        //System.out.println(phraseList);
        //KeyWordComputer kwc = new KeyWordComputer(5);
        //Collection<Keyword> result = kwc.computeArticleTfidf(file1Name, file1Content);
        //System.out.println(result);
        testApriori();
    }

    public static void testApriori(){
        List<File> ls = getFileList("C:\\Users\\wangj\\Desktop\\必修二 (1)\\utf8");
        Map<String,String> map = new HashMap<>();
        for(File f:ls){
            String content = FileReader.readByte(f.getPath());
            String name = f.getName();
            map.put(name,content);
        }
        TFIDF.setFileByMap(map);
        List<List<Vocabulary>> voc = new LinkedList<>();
        List<List<String>> c = new LinkedList<>();
        for(Map.Entry<String,String> e: map.entrySet()){
            List<Vocabulary> vs = TFIDF.CalcKey(e.getKey(),null);
            List<String> ss = new LinkedList<>();
            for(Vocabulary v:vs){
                ss.add(v.getNatureStr());
            }
            voc.add(vs);
            c.add(ss);
        }
        List<List<Vocabulary>> re=Apriori.calc(c);
        for(List<Vocabulary> re1:re){
            for(Vocabulary re2:re1){
                System.out.print(re2.getNatureStr()+" \t");
            }
            System.out.println();
        }
    }
    public static List<File> getFileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        List<File> filelist = new LinkedList<>();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else {
                    String strFileName = files[i].getAbsolutePath();
                    System.out.println("---" + strFileName);
                    filelist.add(files[i]);
                }
            }
        }
        return filelist;
    }
    public static void testTFIDF(double th, String file){
        Map<String,String> f = new LinkedHashMap<>();
        f.put(file1Name,file1Content);
        f.put(file2Name,file2Content);
        List<Vocabulary> lists;
        TFIDF.setFileByMap(f);
        TFIDF.setThreshold(th);
        lists = TFIDF.CalcKey(file,null);
    }

    public static void testPart(){
        System.out.println("ansj分词结果： ");
        Assmble assmble = ParticipleProcessing.processing(input);
        int i=1;
        for (Map.Entry<String,Vocabulary> e:
             assmble.getMaps().entrySet()) {
            System.out.println(i+". "+e.getKey()+"   "+e.getValue().getFrequence());
            i++;
        }
        System.out.println("二元拼接 ： ");
        List<Vocabulary> list = ParticipleProcessing.duelJoint(assmble,10);
        for(i=0; i<list.size(); i++){
            System.out.println(list.get(i).getNatureStr());
        }
    }

    public static void testBaiduApi(){
        JSONObject js=BaiduApi.request("强调：描述法表示集合应注意集合的代表元素");
        System.out.println(js);
    }

}
