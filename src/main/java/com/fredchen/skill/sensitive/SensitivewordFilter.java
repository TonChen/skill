package com.fredchen.skill.sensitive;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 敏感词过滤
 * @Project：test
 * @Author : chenming
 * @Date ： 2014年4月20日 下午4:17:15
 * @version 1.0
 */
public class SensitivewordFilter {
	@SuppressWarnings("rawtypes")
	private Map sensitiveWordMap = null;
	public static int minMatchTYpe = 1; // 最小匹配规则
	public static int maxMatchType = 2; // 最大匹配规则

	/**
	 * 构造函数，初始化敏感词库
	 */
	public SensitivewordFilter() {
		sensitiveWordMap = new SensitiveWordInit().initKeyWord();
	}

	/**
	 * 判断文字是否包含敏感字符
	 * 
	 * @author chenming
	 * @date 2014年4月20日 下午4:28:30
	 * @param txt
	 *            文字
	 * @param matchType
	 *            匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * @return 若包含返回true，否则返回false
	 * @version 1.0
	 */
	public boolean isContaintSensitiveWord(String txt, int matchType) {
		boolean flag = false;
		for (int i = 0; i < txt.length(); i++) {
			int matchFlag = this.CheckSensitiveWord(txt, i, matchType); // 判断是否包含敏感字符
			if (matchFlag > 0) { // 大于0存在，返回true
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 获取文字中的敏感词
	 * 
	 * @author chenming
	 * @date 2014年4月20日 下午5:10:52
	 * @param txt
	 *            文字
	 * @param matchType
	 *            匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * @return
	 * @version 1.0
	 */
	public Set<String> getSensitiveWord(String txt, int matchType) {
		Set<String> sensitiveWordList = new HashSet<String>();

		for (int i = 0; i < txt.length(); i++) {
			int length = CheckSensitiveWord(txt, i, matchType); // 判断是否包含敏感字符
			if (length > 0) { // 存在,加入list中
				sensitiveWordList.add(txt.substring(i, i + length));
				i = i + length - 1; // 减1的原因，是因为for会自增
			}
		}

		return sensitiveWordList;
	}

	/**
	 * 替换敏感字字符
	 * 
	 * @author chenming
	 * @date 2014年4月20日 下午5:12:07
	 * @param txt
	 * @param matchType
	 * @param replaceChar
	 *            替换字符，默认*
	 * @version 1.0
	 */
	public String replaceSensitiveWord(String txt, int matchType,
			String replaceChar) {
		String resultTxt = txt;
		Set<String> set = getSensitiveWord(txt, matchType); // 获取所有的敏感词
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}

		return resultTxt;
	}

	/**
	 * 获取替换字符串
	 * 
	 * @author chenming
	 * @date 2014年4月20日 下午5:21:19
	 * @param replaceChar
	 * @param length
	 * @return
	 * @version 1.0
	 */
	private String getReplaceChars(String replaceChar, int length) {
		String resultReplace = replaceChar;
		for (int i = 1; i < length; i++) {
			resultReplace += replaceChar;
		}

		return resultReplace;
	}

	/**
	 * 检查文字中是否包含敏感字符，检查规则如下：<br>
	 * 
	 * @author chenming
	 * @date 2014年4月20日 下午4:31:03
	 * @param txt
	 * @param beginIndex
	 * @param matchType
	 * @return，如果存在，则返回敏感词字符的长度，不存在返回0
	 * @version 1.0
	 */
	@SuppressWarnings({ "rawtypes" })
	public int CheckSensitiveWord(String txt, int beginIndex, int matchType) {
		boolean flag = false; // 敏感词结束标识位：用于敏感词只有1位的情况
		int matchFlag = 0; // 匹配标识数默认为0
		char word = 0;
		Map nowMap = sensitiveWordMap;
		for (int i = beginIndex; i < txt.length(); i++) {
			word = txt.charAt(i);
			nowMap = (Map) nowMap.get(word); // 获取指定key
			if (nowMap != null) { // 存在，则判断是否为最后一个
				matchFlag++; // 找到相应key，匹配标识+1
				if ("1".equals(nowMap.get("isEnd"))) { // 如果为最后一个匹配规则,结束循环，返回匹配标识数
					flag = true; // 结束标志位为true
					if (SensitivewordFilter.minMatchTYpe == matchType) { // 最小规则，直接返回,最大规则还需继续查找
						break;
					}
				}
			} else { // 不存在，直接返回
				break;
			}
		}
		if (matchFlag < 2 || !flag) { // 长度必须大于等于1，为词
			if(matchFlag == 1 && flag){
				
			}else{
				matchFlag = 0;
			}
		}
		return matchFlag;
	}

	public static void main(String[] args) {
		SensitivewordFilter filter = new SensitivewordFilter();
		System.out.println("词库："+ filter.sensitiveWordMap);
		System.out.println("敏感词的数量：" + filter.sensitiveWordMap.size());
		String string = "太多的伤感情怀也许只局限于饲养基地 荧幕中的情节，主人公尝试着去用某种方式渐渐的很潇洒地释自杀指南怀那些自己经历的伤感。"
				+ "然后法轮功我们的扮演的角色就是跟随着主人公的喜红客联盟 怒哀乐而过于牵强的把自己的情感也附加于银幕情节中，然后感动就流泪，"
				+ "难过就躺在某一个人的怀里尽情的阐述心扉或者手机卡复制器一个人一杯红酒一部电影在夜三级片深人静的晚上，关上电话静静的发呆着。和覅是否风骚奖品建瓯市"
				+ "但是费时又恢复发送几时放假啊发顺丰发哈怕是否按时发货时事骚发后暗示法发是否安防撒发顺丰 ，发哦发货，发送或发哦吗，发光器，发爱咖啡符文啊服务我这样我们就将我们的敏感词库构建成了一个类似与一颗一颗的树，这样我们判断一个词是否为敏感词时就大大减少了检索的匹配范围。比如我们要判断日本人，根据第一个字我们就可以确认需要检索的是那棵树，然后再在这棵树中进行检索。但是如何来判断一个敏感词已经结束了呢？利用标识位来判断。"
				+ "所以对于这个关键是如何来构建一棵棵这样的敏感词树。下面我已Java中的HashMap为例来实现DFA算法。具体过程如下"
				+ "日本人，日本鬼子为例"
				+ "1、在hashMap中查询“日”看其是否在hashMap中存在，如果不存在，则证明已“日”开头的敏感词还不存在，则我们直接构建这样的一棵树。跳至3。"
				+ "2、如果在hashMap中查找到了，表明存在以“日”开头的敏感词，设置hashMap = hashMap.get()，跳至1，依次匹配“本”、“人”。"
				+"* 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>"
				+"敏感词库我们一个简单的方法给实现了，那么如何实现检索呢？检索过程无非就是hashMap的get实现，找到就证明该词为敏感词，否则不为敏感词。过程如下：假如我们匹配“中国人民万岁”。"
				+"1、第一个字“中”，我们在hashMap中可以找到。得到一个新的map = hashMap.get()。"
				+"如果map == null，则不是敏感词。否则跳至3"
				+"获取map中的isEnd，通过isEnd是否等于1来判断该词是否为最后一个。如果isEnd == 1表示该词为敏感词，否则跳至1。通过这个步骤我们可以判断“中国人民”为敏感词，但是如果我们输入“中国女人”则不是敏感词了。"
				+"检查文字中是否包含敏感字符，检查规则如下：<br>"
				+"如果存在，则返回敏感词字符的长度，不存在返回0"
				+"在文章末尾我提供了利用Java实现敏感词过滤的文件下载。下面是一个测试类来证明这个算法的效率和可靠性。"
				+"从上面的结果可以看出，敏感词库有771个，检测语句长度为184个字符，查出6个敏感词。总共耗时1毫秒。可见速度还是非常可观的。 下面提供两个文档下载："
				+"以上用户言论只代表其个人观点，不代表CSDN网站的观点或立场"
				+"尊重作者的成果，转载请注明出处！"
				+ "3、判断该字是否为该词中的最后一个字。若是表示敏感词结束，设置标志位isEnd = 1，否则设置标志位isEnd = 0；";
		System.out.println("待检测语句字数：" + string.length());
		long beginTime = System.currentTimeMillis();
		Set<String> set = filter.getSensitiveWord(string, 1);
		long endTime = System.currentTimeMillis();
		System.out.println("是否包含敏感词：" + filter.isContaintSensitiveWord(string, 1));
		System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
		System.out.println("替换后字符串："+filter.replaceSensitiveWord(string, 1, "*"));
		System.out.println("总共消耗时间为：" + (endTime - beginTime) + " 毫秒");
	}
}
