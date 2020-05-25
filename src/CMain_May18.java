import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMain_May18 {

	public static void main(String[] args) {
		String str = "財の価格を   $p$、その供給量を$x^{s}$とすると供給関数は一般的に$x^{s} = S(p)$ と表される。いま、この関数が具体的に";
		str =str.replaceAll("(\\$.+?\\$)", "\\\\($1\\\\)");
		System.out.println(str);
		//
		str ="\\ban{1} $y$ & \\ban{2} $f$ & \\ban{3} $x$ & \\ban{4} 右上がり \\\\\r\n" + 
				"	\\ban{5} 右下がり & \\ban{6} 垂直 & \\ban{7} 水平 & ";
		System.out.println(str);
		String regex ="(\\\\ban\\{\\d})";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		while(m.find()) {
			//System.out.println(m.group());
			String subStr = m.group();
			System.out.println(subStr.replace(regex, ""));
		}
	}

}
