# I-KG

## Author

[王家若]("github.com/wjr22")
[朱晓悦]()
[杜雨雯]()

## Description

知识图谱在线生成系统（其实不能自动生成）
主要是自然语言处理，以期望获得短文本的实体、实体关系，并连结生成图，最初目标是完成高中课本的知识图谱，但其实其价值不大，1来它局限在基础科学中，实体库有限，又不属于特殊领域，意义不大，但也可以算练手作品。

## Build

本项目采用spring boot + neo4j 模式，前后端分离，数据完全靠Ajax，

## Defect

主要问题还是在NLP，我们采用的算法是简单但缺点很大的TF-IDF以抽取实体，Apriori算法（不完全版）获得关系，但我们错误估计了关键词和实体的关系，关键词不等于实体，且此类算法的效果极为依赖阈值的设定，所以后续考虑采用爬虫抽取百科实体，直接得到实体。

### TF-IDF: 

$$ Tf_i = \frac{C_{w_i}}{C_w} $$
即相关词的频率（term frequency）
$$ IDF_i = log_2\left(\frac{D}{1+\{j:t_i\in D_j\}}\right)\\D:总文档数，t_i：词\\\{j:t_i\in D_j\} : 即出现t_i的文档数$$
即相关词在总文档数中的逆文档频率（Inverse document frequency）
$$ TF-IDF = Tf_i * IDF_i$$
即同时考虑频率与多文档共现频率，由二者决定该词是否为关键词，一般来说一篇文章的关键词共现次数应该是低的，所以最终TF-IDF值高，这也是IDF公式的原因。

### Apriori:

很出名的关联规则算法，我就不详细介绍了
![Apriori](src\main\resources\static\images\Apriori)