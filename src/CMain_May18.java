import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMain_May18 {

	public static void main(String[] args) {
		String str = "���̉��i��   $p$�A���̋����ʂ�$x^{s}$�Ƃ���Ƌ����֐��͈�ʓI��$x^{s} = S(p)$ �ƕ\�����B���܁A���̊֐�����̓I��";
		str =str.replaceAll("(\\$.+?\\$)", "\\\\($1\\\\)");
		System.out.println(str);
		//
		str ="\\ban{1} $y$ & \\ban{2} $f$ & \\ban{3} $x$ & \\ban{4} �E�オ�� \\\\\r\n" + 
				"	\\ban{5} �E������ & \\ban{6} ���� & \\ban{7} ���� & ";
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
