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
			System.out.println("���F" + target);
			// �P�����l���<qn:...> ��u��������B
			p = Pattern.compile("<qn:(.+?)>");
			m = p.matcher(target);
			while (m.find()) {
				String str = m.group();
				// System.out.println(str);
				String newStr = "{1:NM:=" + m.group(1) + "}";
				target = target.replace(str, newStr);
			}
			// System.out.println("�ϊ���F" + target);
			// <s:...> �܂ł̏���ɕ�������
			// <s:....>�܂ł� <q: ...>������������
			regex = "(.+?)<s:.+?>";
			p = Pattern.compile(regex);
			m = p.matcher(target);
			String selectItems = null;
			String[] selectItemArray = null;
			//���l�����͂ɂ�����<s:...>�����݂�����A����́u�I��������ԍ���I��œ��́v���Ӗ�����B
			//���̂Ƃ���<table>������̂Ńt���O���K�v
			boolean tableFlag = false;
			while (m.find()) { // <s:...>�ŏI��鏬�₲�Ƃ̏���
				//�����֗����ƌ������Ƃ�<s:...>���������Ƃ�������
				tableFlag = true;
				String str = m.group();
				// str ��<s:...>�܂ł̕�����
				// System.out.println("�ŏ���str:" + str);
				String originalStr = new String(str);
				// ��������<s:....>�𔲂��o���B
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
				// �m�F
//				for (String s : selectItemArray) {
//					System.out.println(s);
//				}
				//System.out.println("������:" + str);
				//selectItemArray ���V���b�t��
				List <String> tmpList = new ArrayList<String>(Arrays.asList(selectItemArray));
				Collections.shuffle(tmpList);
				selectItemArray = tmpList.toArray(new String[tmpList.size()]);
				for (String s : selectItemArray) {
					System.out.println(s);
				}
				// <q:...>�̏���
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
				System.out.println("�ϊ���:" + str);
				target = target.replace(originalStr, str);
				System.out.println("target: "+target);
			} // end of while(m.find()) :���₲�Ƃ̏���
			//<s:....>��<table>�ɂ���B
			if(tableFlag) {
				String tableStr = "<table><caption><b>�I����</b></caption>";
				//�I�����̐�
				int num = selectItemArray.length;
				int remainder = num%5;
				
				//1�s��5��Ƃ��߂���
			}
		} // end of for( target : ��育�Ƃ̏���

	} //end of main()
	

}
