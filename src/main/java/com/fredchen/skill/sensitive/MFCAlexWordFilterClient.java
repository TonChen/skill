package com.fredchen.skill.sensitive;
/**
 * Alex.Zhang
 * Beijing Normal University, Grade 2000
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
class SocketIOBlock{//Storage block in queue
	Socket socket;
	OutputStream os;
	InputStream is;
}
class HeartBeatThread implements Runnable{//Heart Beat Thread for keeping tcp alive
	@Override
	public void run() {
		int len=MFCAlexWordFilterClient.getQueueLength();
		for(int i=0;i<len;i++){
			MFCAlexWordFilterClient.filterText("Heart beat request");
		}
	}
}
public class MFCAlexWordFilterClient {
	static{//start tcp heart beat
		ScheduledExecutorService ses=Executors.newSingleThreadScheduledExecutor();
		ses.schedule(new HeartBeatThread(), 10, TimeUnit.MINUTES);
	}
	private static ConcurrentLinkedQueue<SocketIOBlock> queue=new ConcurrentLinkedQueue<SocketIOBlock>();
	public static int getQueueLength(){
		return queue.size();
	}
	public static String simpleFilter(String text){
		return filter("3",text);
	}
	public static String filterText(String text){
		return filter("2",text);
	}
	public static String filterHtml(String html){
		return filter("1",html);
	}
	private static SocketIOBlock newSocket(){//新建tcp链接
		SocketIOBlock block=new SocketIOBlock();
		System.out.println("newSocket() is called");
		int i=0;
		while(true){
			++i;
			if(i>10){//break if try to connect for 10 times
				break;
			}
			try {
				Socket socket=new Socket();
				SocketAddress endpoint=new InetSocketAddress("127.0.0.1",8001);
				socket.connect(endpoint, 5000);
				block.os=socket.getOutputStream();
				block.is=socket.getInputStream();
				block.socket=socket;
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return block;
	}
	private static String filter(String type,String content){
		SocketIOBlock block;
		if((block=queue.poll())==null){//new a socket, if there is no available socket in the pool
			block=newSocket();
		}
		byte[] bytes;
		int i=0;
		while(true){//send request
			++i;
			if(i>10){//break if try to connect for 10 times
				content="";
				break;
			}
			try {
				bytes = (type+content).getBytes("utf-8");
				block.os.write(bytes);
				block.os.flush();
				bytes=new byte[bytes.length*2];
				int len=block.is.read(bytes);
				if(len>0){
					content=new String(bytes,0,len,"utf-8");
				}else{
					content="";
				}
				queue.add(block);
				break;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				try {
					block.socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				block=newSocket();
				e.printStackTrace();
			}
		}
		return content;
	}
	public static void main(String[] args){
		System.out.println(MFCAlexWordFilterClient.filterText("龟公，adsfadf"));
		ExecutorService es=Executors.newFixedThreadPool(100);
		for(int i=0;i<100;i++){
			es.execute(new TestLoopThread());
		}
	}
}
class TestLoopThread extends Thread{
	private static double time=100;
	@Override
	public void run() {
		while(true){
			//MFCAlexWordFilterClient.filterText("龟公，adsfadf");
			long start=System.currentTimeMillis();
//			System.out.println(MFCAlexWordFilterClient.filterText("龟公，adsfadf"));
			MFCAlexWordFilterClient.simpleFilter("龟公，adsfadf");
			long end=System.currentTimeMillis();
			System.out.print("\r"+(end-start+time*999)/1000+"\t"+(end-start)+"      ");
			time=(end-start+time*999)/1000;
		}
	}
}