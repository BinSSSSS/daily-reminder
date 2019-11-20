package cn.tblack.reminder.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileWriter {

	
	/**
	 * @ 将指定输入流内的数据写入到文件中， 使用指定的bufferSize进行创建byte数组
	 * @ 创建BufferedOutputStream绑定文件数据流将数据写入到目标路径中
	 * @param in
	 * @param bufferSize
	 * @param targetPath
	 * @throws IOException 
	 */
	public static void writeToFile(InputStream in,int fileSize, int bufferSize, String targetPath) throws IOException {
		
		OutputStream out = new BufferedOutputStream(new FileOutputStream(targetPath));
		
		
		byte[] data = new byte[bufferSize];
		
		int readSize = 0;    //本次读取的大小
		int readSum = 0;  	 //总读取的大小 
		
		
		/*@ 读取并写入到文件中*/
		while((readSize = in.read(data)) != -1) {
			
			out.write(data);
			
			readSum +=  readSize;
			
			/*@ 这里的操作是以时间换空间，每次多余的判断，来避免重复的创建新的byte数组
			 *@ 在剩余可读取的字节数较少的时候， 那么则需要重新的创建出新的byte数组， 否则在写入的时候可能会写入之前存在的数据
			 *@ 因为数据没有完全被覆盖
			 *@ 同样也可以使用空间来换时间， 具体的是每次都创建出一个新的byte数组来存放数据， 这样每次将申请一个新的内存块， 而可以避免if语句*/
			if(fileSize -  readSum <  bufferSize)
			{
				data = new byte[fileSize -  readSum];
			}
			
		}
		
		out.close();
	}


}
