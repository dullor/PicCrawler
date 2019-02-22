import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		String downloadAddress="C:/Users/Venti/Desktop/Game";
		Web fengJing=new Web("http://pic.netbian.com/4kfengjing/",downloadAddress);
		fengJing.download(fengJing.getPicUrl());
	}

}
