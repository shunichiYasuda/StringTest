import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMain_May25 {

	public static void main(String[] args) {
		String origin = "";
		File file = new File("sample_May25.txt");
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				origin += line;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//
		origin = origin.replaceAll("\n", "");
		origin = origin.replaceAll("\t", "");
		// System.out.println(origin);
		List<String> quizList = new ArrayList<String>();
		String regex = "<Q>(.+?)</Q>";
		// regex = "(.+?)<s:.+?>";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(origin);
		while (m.find()) {
			String str = m.group();
			str = str.replace("<Q>", "<p>");
			str = str.replace("</Q>", "</p>");
			quizList.add(str);
		}
		for (String target : quizList) {
			System.out.println("問題：" + target);
			// 単純数値問題<qn:...> を置き換える。
			p = Pattern.compile("<qn:(.+?)>");
			m = p.matcher(target);
			while (m.find()) {
				String str = m.group();
				// System.out.println(str);
				String newStr = "{1:NM:=" + m.group(1) + "}";
				target = target.replace(str, newStr);
			}
			// System.out.println("変換後：" + target);
			// <s:...> までの小問に分割する
			// <s:....>までの <q: ...>を処理したい
			regex = "(.+?)<s:.+?>";
			p = Pattern.compile(regex);
			m = p.matcher(target);
			String selectItems = null;
			String[] selectItemArray = null;
			//数値問題入力において<s:...>が存在したら、それは「選択肢から番号を選んで入力」を意味する。
			//そのときは<table>をつくるのでフラグが必要
			boolean tableFlag = false;
			while (m.find()) { // <s:...>で終わる小問ごとの処理
				//ここへ来たと言うことは<s:...>があったということ
				tableFlag = true;
				String str = m.group();
				// str は<s:...>までの文字列
				// System.out.println("最初のstr:" + str);
				String originalStr = new String(str);
				// いったん<s:....>を抜き出す。
				Pattern p_s = Pattern.compile("<s:(.+?)>");
				Matcher m_s = p_s.matcher(str);
				while (m_s.find()) {
					selectItems = m_s.group(1);
					// System.out.println("selectItems:"+selectItems);
					selectItemArray = selectItems.split(",");
					// codeArea.appendText("selectitems:"+selectItems+"\n");
					String oldStr = m_s.group();
					str = str.replace(oldStr, "");
				}
				// 確認
//				for (String s : selectItemArray) {
//					System.out.println(s);
//				}
				//System.out.println("消去後:" + str);
				//selectItemArray をシャッフル
				List <String> tmpList = new ArrayList<String>(Arrays.asList(selectItemArray));
				Collections.shuffle(tmpList);
				selectItemArray = tmpList.toArray(new String[tmpList.size()]);
				for (String s : selectItemArray) {
					System.out.println(s);
				}
				// <q:...>の処理
				String regex2 = "<q:(.+?)>";
				Pattern p2 = Pattern.compile(regex2);
				Matcher m2 = p2.matcher(str);
				while (m2.find()) {
					String oldStr = m2.group();
					String ans = m2.group(1);
					int index = 0;
					for(int i=0;i<selectItemArray.length;i++) {
						if(ans.equals(selectItemArray[i])) index = (i+1);
					}
					String newStr = "{1:NM:=" + index + "}";
					str = str.replace(oldStr, newStr);
				}
				System.out.println("変換後:" + str);
				target = target.replace(originalStr, str);
				System.out.println("target: "+target);
			} // end of while(m.find()) :小問ごとの処理
			//<s:....>を<table>にする。
			if(tableFlag) {
				String tableStr = "<table><caption><b>選択肢</b></caption>";
				//選択肢の数
				int num = selectItemArray.length;
				int remainder = num%5;
				
				//1行は5列ときめうち
			}
		} // end of for( target : 問題ごとの処理

	} //end of main()
	

}
