public class Main {

	public static void main(String[] args) {
		Web fengJing=new Web("http://pic.netbian.com/4kmeinv/");
		fengJing.download(fengJing.getPicUrl());
		System.out.println("");
	}

}
