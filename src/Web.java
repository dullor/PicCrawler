import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Web {
	private URL url;
	private StringBuffer webCode=new StringBuffer();
	private List<String> picUrl=new ArrayList<>();

	public Web(String url) {
		try {
			this.url=new URL(url);
			this.OpenUrl();
		} catch (MalformedURLException e) {
			System.out.println("create URL wrong!");
			e.printStackTrace();
		}
	}

	public String getWebCode() {
		return webCode.toString();
	}

	public List<String> getPicUrl(){
		this.findPicUrl();
		return picUrl;
	}

	public void OpenUrl() {
		try {
			URLConnection connection = url.openConnection();
			connection.addRequestProperty("User-Agent", 
					"Proxy-Authorization: Basic H1P1C1X4D0RSMV7D:EB70A98534459279");
			BufferedReader bR = new BufferedReader(new InputStreamReader
					(connection.getInputStream(), "gbk"));
			String line = null;
			while ((line = bR.readLine()) != null)
				webCode.append(line + "\n");
			bR.close();
		} catch (Exception e) {
			System.out.println("Open url wrong");
		}
	}

	public void findPicUrl() {
		Pattern p = Pattern.compile("/tupian/\\d+\\.html");
		Matcher m = p.matcher(webCode);
		while(m.find()) {
			picUrl.add(m.group());
		}
		String nextPage=findNextPage();
		System.out.println(nextPage);
		if(nextPage!=null) {
			String tempUrl=url.toString();
			int indexOfINDEX=tempUrl.lastIndexOf("index");
			if(indexOfINDEX!=-1)
				picUrl.addAll(new Web(tempUrl.substring(0,indexOfINDEX)+nextPage).getPicUrl());
			else
				picUrl.addAll(new Web(url+nextPage).getPicUrl());
		}

	}

	public String findNextPage() {
		Pattern p = Pattern.compile("index_\\d+.html\">下一页</a>");
		Matcher m = p.matcher(webCode);
		if(m.find()) {
			String tempUrl=m.group();
			String[] rel=tempUrl.split("\"");
			return rel[0];
		}
		return null;
	}

	public String getPicBedUrl(String pic) {
		String webCode=new Web("http://pic.netbian.com/"+pic).getWebCode();
		Pattern p = Pattern.compile("img\"><img src=\"/uploads/allimg/\\d+/[a-z0-9_-]+.jpg");
		Matcher m = p.matcher(webCode);
		if(m.find()) {
			String target=m.group();
			String[] split=target.split("\"");
			return split[2];
		}
		return "";
	}

	public boolean download(List<String> picUrl){
		try {
			for(String pic:picUrl) {
				String picBed=getPicBedUrl(pic);
				System.out.println(picBed);
				URLConnection conn = ProxyConnect("http://pic.netbian.com/"+picBed);
				InputStream in = conn.getInputStream();
				FileOutputStream fo = new FileOutputStream(new File("E:/fengjing"
						+ "/"+picBed.substring(30)+".jpg"));
				byte[] buf = new byte[1024];
				int length = 0;
				while ((length = in.read(buf, 0, buf.length)) != -1) 
					fo.write(buf, 0, length);
				in.close();
				fo.close();
			}
		}catch(Exception e) {
			System.out.println("wrong");
		}

		return true;

	}

	static public URLConnection ProxyConnect(String URL) throws Exception{
		URL url = new URL(URL);
		URLConnection conn = url.openConnection();
		conn.setConnectTimeout(3000);
		conn = url.openConnection();
		conn.addRequestProperty("User-Agent",
				"Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0)");
		return conn;
	}

}
